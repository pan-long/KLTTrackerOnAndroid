package sg.edu.nus.comp.klttracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.util.AttributeSet;
import android.view.View;
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
    private PointProcessing pointProcessing;
    private MediaMetadataRetriever mediaMetadataRetriever;

    private Paint paintLine = new Paint();
    private Paint paintRed = new Paint();
    private Paint paintBlue = new Paint();

    private FastQueue<Point2D_F64> trackSrc;
    private FastQueue<Point2D_F64> trackDst;
    private FastQueue<Point2D_F64> trackSpawn;

    private Bitmap frameBitmap;
    private byte[] storage;

    private static final int VIDEO_WIDTH = 320;
    private static final int VIDEO_HEIGHT = 240;
    private int view_width;
    private int view_height;

    private float scaleX;
    private float scaleY;

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

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    LocalVideoView.this.postInvalidate();
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        view_width = xNew;
        view_height = yNew;

        scaleX = view_width / (float)VIDEO_WIDTH;
        scaleY = view_height / (float)VIDEO_HEIGHT;

    }

    public void setProcessing(PointProcessing pointProcessing) {
        this.pointProcessing = pointProcessing;
    }

    public void setVideoSource(String videoSource) {
        setVideoPath(videoSource);
        mediaMetadataRetriever.setDataSource(videoSource);

//        video_height = Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
//        video_width = Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        scaleX = view_width / (float)VIDEO_WIDTH;
        scaleY = view_height / (float)VIDEO_HEIGHT;

    }

    int number = 0;
    @Override
    public void onDraw(Canvas canvas) {
        System.out.println(number + ": " + System.currentTimeMillis());
        int currentPosition = getCurrentPosition();
        frameBitmap = mediaMetadataRetriever.getFrameAtTime(currentPosition * 1000);

        Bitmap scaledFrameBitmap = Bitmap.createScaledBitmap(frameBitmap, VIDEO_WIDTH, VIDEO_HEIGHT, false);
        storage = ConvertBitmap.declareStorage(scaledFrameBitmap, storage);
        ImageUInt8 gray = new ImageUInt8(VIDEO_WIDTH, VIDEO_HEIGHT);

        ConvertBitmap.bitmapToGray(scaledFrameBitmap, gray, storage);
        System.out.println(number + ": " + System.currentTimeMillis());
        System.out.println(number + ": " + System.currentTimeMillis());

        pointProcessing.process(gray);
        System.out.println(number + ": " + System.currentTimeMillis());

        trackSrc = pointProcessing.getTrackSrc();
        trackDst = pointProcessing.getTrackDst();
        trackSpawn = pointProcessing.getTrackSpawn();

        super.onDraw(canvas);
        drawTracking(canvas);

        frameBitmap.recycle();
        scaledFrameBitmap.recycle();
        number ++;
    }

    protected void drawTracking(Canvas canvas) {
        for( int i = 0; i < trackSrc.size(); i++ ) {
            Point2D_F64 s = trackSrc.get(i);
            Point2D_F64 p = trackDst.get(i);
            canvas.drawLine((int)s.x * scaleX,(int)s.y * scaleY,(int)p.x * scaleX,(int)p.y * scaleY,paintLine);
            canvas.drawCircle((int)p.x * scaleX,(int)p.y * scaleY,2f, paintRed);
        }

        for( int i = 0; i < trackSpawn.size(); i++ ) {
            Point2D_F64 p = trackSpawn.get(i);
            canvas.drawCircle((int)p.x * scaleX,(int)p.y * scaleY,3, paintBlue);
        }
    }
}
