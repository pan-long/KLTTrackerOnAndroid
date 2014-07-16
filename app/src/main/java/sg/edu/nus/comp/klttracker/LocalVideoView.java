package sg.edu.nus.comp.klttracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.util.AttributeSet;
import android.widget.Toast;
import android.widget.VideoView;

import org.ddogleg.struct.FastQueue;

import boofcv.android.ConvertBitmap;
import boofcv.struct.image.ImageInt8;
import boofcv.struct.image.ImageUInt8;
import georegression.struct.point.Point2D_F64;

/**
 * Created by panlong on 16/7/14.
 */
public class LocalVideoView extends VideoView {
    private MediaMetadataRetriever mediaMetadataRetriever;
    private PointProcessing pointProcessing;

    private Paint paintLine = new Paint();
    private Paint paintRed = new Paint();
    private Paint paintBlue = new Paint();

    private FastQueue<Point2D_F64> trackSrc;
    private FastQueue<Point2D_F64> trackDst;
    private FastQueue<Point2D_F64> trackSpawn;

    private Bitmap frameBitmap;
    private byte[] storage;

    public LocalVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        mediaMetadataRetriever = new MediaMetadataRetriever();

        paintLine.setColor(Color.RED);
        paintLine.setStrokeWidth(1.5f);
        paintRed.setColor(Color.MAGENTA);
        paintRed.setStyle(Paint.Style.FILL);
        paintBlue.setColor(Color.BLUE);
        paintBlue.setStyle(Paint.Style.FILL);
    }

    public void setProcessing(PointProcessing pointProcessing) {
        this.pointProcessing = pointProcessing;
    }

    public void setVideoSource(String videoSource) {
        setVideoPath(videoSource);
        mediaMetadataRetriever.setDataSource(videoSource);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int currentPosition = getCurrentPosition();
        Toast.makeText(getContext(),
                "Current Position: " + currentPosition + " (ms)",
                Toast.LENGTH_LONG).show();
        frameBitmap = mediaMetadataRetriever.getFrameAtTime(currentPosition * 1000);

        storage = ConvertBitmap.declareStorage(frameBitmap, storage);
        ImageUInt8 gray = new ImageUInt8(frameBitmap.getWidth(), frameBitmap.getHeight());
        ConvertBitmap.bitmapToGray(frameBitmap, gray, storage);
        pointProcessing.process(gray);

        trackSrc = pointProcessing.getTrackSrc();
        trackDst = pointProcessing.getTrackDst();
        trackSpawn = pointProcessing.getTrackSpawn();

        System.out.println(trackSrc.size());

        super.onDraw(canvas);
        drawTracking(canvas);

        invalidate();
    }

    protected void drawTracking(Canvas canvas) {
        for( int i = 0; i < trackSrc.size(); i++ ) {
            Point2D_F64 s = trackSrc.get(i);
            Point2D_F64 p = trackDst.get(i);
            canvas.drawLine((int)s.x,(int)s.y,(int)p.x,(int)p.y,paintLine);
            canvas.drawCircle((int)p.x,(int)p.y,2f, paintRed);
        }

        for( int i = 0; i < trackSpawn.size(); i++ ) {
            Point2D_F64 p = trackSpawn.get(i);
            canvas.drawCircle((int)p.x,(int)p.y,3, paintBlue);
        }
    }
}
