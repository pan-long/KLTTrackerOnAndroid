package sg.edu.nus.comp.klttracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.util.AttributeSet;
import android.view.SurfaceView;

import org.ddogleg.struct.FastQueue;

import boofcv.android.ConvertBitmap;
import boofcv.struct.image.ImageUInt8;
import georegression.struct.point.Point2D_F64;

/**
 * Created by panlong on 16/7/14.
 */
public class LocalVideoView extends SurfaceView {
    private PointProcessing pointProcessing;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private Bitmap frameBitmap;

    private Paint paintLine = new Paint();
    private Paint paintRed = new Paint();
    private Paint paintBlue = new Paint();

    private FastQueue<Point2D_F64> trackSrc;
    private FastQueue<Point2D_F64> trackDst;
    private FastQueue<Point2D_F64> trackSpawn;

    private static final int VIDEO_WIDTH = 32;
    private static final int VIDEO_HEIGHT = 24;
    private int view_width;
    private int view_height;
    private long startTime;

    private float scaleX;
    private float scaleY;

    private boolean isPlaying = false;
    private boolean canBeProcessed = false;

    ImageUInt8 image;
    ImageUInt8 image2;

    byte[] storage;

    private Object lockConvert = new Object();
    private Object lockGui = new Object();

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

        startTime = System.currentTimeMillis();
        isPlaying = true;

        Thread threadConvert = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (isPlaying()) {
                        long currentPosition = getCurrentPosition();

                        synchronized (lockGui) {
                            frameBitmap = Bitmap.createScaledBitmap(mediaMetadataRetriever.getFrameAtTime(currentPosition * 1000),
                                    VIDEO_WIDTH, VIDEO_HEIGHT, false);
                            storage = ConvertBitmap.declareStorage(frameBitmap, storage);
                        }

                        synchronized (lockConvert) {
                            ConvertBitmap.bitmapToGray(frameBitmap, image, storage);
                            canBeProcessed = true;
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

        Thread threadProcessing = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (isPlaying() && canBeProcessed) {
                        synchronized (lockConvert) {
                            ImageUInt8 tmp = image;
                            image = image2;
                            image2 = tmp;
                        }
                        pointProcessing.process(image2);
                        LocalVideoView.this.postInvalidate();
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

        threadConvert.start();
        threadProcessing.start();
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
        mediaMetadataRetriever.setDataSource(videoSource);

        scaleX = view_width / (float)VIDEO_WIDTH;
        scaleY = view_height / (float)VIDEO_HEIGHT;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isPlaying()) {
            synchronized (lockGui) {
                canvas.scale(scaleX, scaleY);
                canvas.drawBitmap(frameBitmap, 0, 0, null);
                frameBitmap.recycle();
            }

            trackSrc = pointProcessing.getTrackSrc();
            trackDst = pointProcessing.getTrackDst();
            trackSpawn = pointProcessing.getTrackSpawn();

            super.onDraw(canvas);
            drawTracking(canvas);
        }
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

    private boolean isPlaying() {
        return isPlaying;
    }

    private long getCurrentPosition() {
        return System.currentTimeMillis() - startTime;
    }
}
