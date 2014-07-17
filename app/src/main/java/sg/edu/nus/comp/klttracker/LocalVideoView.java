package sg.edu.nus.comp.klttracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.util.AttributeSet;
import android.widget.VideoView;

import org.ddogleg.struct.FastQueue;

import java.util.concurrent.CountDownLatch;

import boofcv.android.ConvertBitmap;
import boofcv.struct.image.ImageUInt8;
import georegression.struct.point.Point2D_F64;

/**
 * Created by panlong on 16/7/14.
 */
public class LocalVideoView extends VideoView implements Runnable{
    private PointProcessing pointProcessing;
    private MediaMetadataRetriever mediaMetadataRetriever;

    private Paint paintLine = new Paint();
    private Paint paintRed = new Paint();
    private Paint paintBlue = new Paint();

    private FastQueue<Point2D_F64> trackSrc;
    private FastQueue<Point2D_F64> trackDst;
    private FastQueue<Point2D_F64> trackSpawn;

    private static final int VIDEO_WIDTH = 320;
    private static final int VIDEO_HEIGHT = 240;
    private int view_width;
    private int view_height;

    private float scaleX;
    private float scaleY;

    ImageUInt8 image;
    ImageUInt8 image2;

    CountDownLatch latch = new CountDownLatch(0);

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

        image = new ImageUInt8(VIDEO_WIDTH, VIDEO_HEIGHT);
        image2 = new ImageUInt8(VIDEO_WIDTH, VIDEO_HEIGHT);

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Converting");
                    if (isPlaying()) {
                        int currentPosition = getCurrentPosition();
                        Bitmap scaledFrameBitmap = Bitmap.createScaledBitmap(mediaMetadataRetriever.getFrameAtTime(currentPosition * 1000),
                                VIDEO_WIDTH, VIDEO_HEIGHT, false);
                        byte[] storage = null;
                        storage = ConvertBitmap.declareStorage(scaledFrameBitmap, storage);

                        try {
                            latch.await();
                            ConvertBitmap.bitmapToGray(scaledFrameBitmap, image, storage);
                            latch.countDown();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();

        start();
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

        scaleX = view_width / (float)VIDEO_WIDTH;
        scaleY = view_height / (float)VIDEO_HEIGHT;
    }

    @Override
    public void onDraw(Canvas canvas) {
        trackSrc = pointProcessing.getTrackSrc();
        trackDst = pointProcessing.getTrackDst();
        trackSpawn = pointProcessing.getTrackSpawn();

        super.onDraw(canvas);
        drawTracking(canvas);
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

    @Override
    public void run() {
        while (true) {
            System.out.println("Processing");
            if (isPlaying()) {
                try {
                    latch.await();
                    ImageUInt8 tmp = image;
                    image = image2;
                    image2 = tmp;
                    latch.countDown();

                    pointProcessing.process(image2);
                    postInvalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
