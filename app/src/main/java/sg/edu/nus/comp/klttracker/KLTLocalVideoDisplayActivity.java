package sg.edu.nus.comp.klttracker;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.ddogleg.struct.FastQueue;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.tracker.PointTrack;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.android.ConvertBitmap;
import boofcv.core.image.GConvertImage;
import boofcv.factory.feature.tracker.FactoryPointTracker;
import boofcv.struct.image.ImageSInt16;
import boofcv.struct.image.ImageUInt8;
import boofcv.struct.image.MultiSpectral;
import georegression.struct.point.Point2D_F64;

/**
 * Created by a0105529 on 7/11/14.
 */
public class KLTLocalVideoDisplayActivity extends Activity {
    protected final Object lockGui = new Object();
    protected PointProcessing pointProcessing;

    private boolean videoIsPaused;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        GLSurfaceView DrawingView = new GLSurfaceView(this);

        if (hasGLES20())
            DrawingView.setEGLContextClientVersion(2);
        else
            dialogNoOpenGLES20();

        DrawingView.setRenderer(new DrawingRenderer("sample_video.mp4", 50));
        setContentView(DrawingView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConfigGeneralDetector config = new ConfigGeneralDetector();
        config.maxFeatures = 150;
        config.threshold = 40;
        config.radius = 3;

        PointTracker<ImageUInt8> tracker =
                FactoryPointTracker.klt(new int[]{2, 4}, config, 3, ImageUInt8.class, ImageSInt16.class);

        pointProcessing = new PointProcessing(tracker);
    }

    // methods for opengles support
    private boolean hasGLES20() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000;
    }

    private void dialogNoOpenGLES20() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your device does not support OpenGLES2.0!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected class DrawingRenderer implements GLSurfaceView.Renderer {
        private MediaMetadataRetriever mediaMetadataRetriever;
        private int video_height;
        private int mHeight;
        private int video_width;
        private int mWidth;
        private byte[] storage;
        private long mOffset;
        private long previousTime;
        private long currentTime;

        public DrawingRenderer(String filename, long offset) {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(filename);
            video_height = Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            video_width = Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));

            this.mOffset = offset;

            previousTime = currentTime = 0;

            Thread thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        if (!videoIsPaused) {
                            currentTime += mOffset;


                            try {
                                sleep(mOffset / 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };
            thread.start();
        }

        @Override
        public void onSurfaceCreated(GL10 notUsed, EGLConfig eglConfig) {
        }

        @Override
        public void onSurfaceChanged(GL10 notUsed, int width, int height) {
            mHeight = height;
            mWidth = width;
        }

        @Override
        public void onDrawFrame(GL10 notUsed) {
            if (currentTime > previousTime) {
                Bitmap frame = mediaMetadataRetriever.getFrameAtTime(mOffset);
                storage = ConvertBitmap.declareStorage(frame, storage);
                ImageUInt8 gray = new ImageUInt8(video_width, video_height);
                ConvertBitmap.bitmapToGray(frame, gray, storage);

                pointProcessing.process(gray);

                previousTime = currentTime;
            }
        }
    }

    protected class PointProcessing {
        PointTracker<ImageUInt8> tracker;

        long tick;

        Bitmap bitmap;
        byte[] storage;
        int imageWidth, imageHeight;

        List<PointTrack> active = new ArrayList<PointTrack>();
        List<PointTrack> spawned = new ArrayList<PointTrack>();
        List<PointTrack> inactive = new ArrayList<PointTrack>();

        // storage for data structures that are displayed in the GUI
        FastQueue<Point2D_F64> trackSrc = new FastQueue<Point2D_F64>(Point2D_F64.class, true);
        FastQueue<Point2D_F64> trackDst = new FastQueue<Point2D_F64>(Point2D_F64.class, true);
        FastQueue<Point2D_F64> trackSpawn = new FastQueue<Point2D_F64>(Point2D_F64.class, true);


        public PointProcessing(PointTracker<ImageUInt8> tracker) {
            //super(boofcv.struct.image.ImageType.single(boofcv.struct.image.ImageUInt8.class));
            //super(ImageType.ms(3, ImageUInt8.class));
            this.tracker = tracker;
        }

        protected void process(ImageUInt8 gray) {
//            ImageUInt8 gray = new ImageUInt8(imageWidth, imageHeight);
//            GConvertImage.convert(color, gray);
            tracker.process(gray);

            // drop tracks which are no longer being used
            inactive.clear();
            tracker.getInactiveTracks(inactive);
            for (int i = 0; i < inactive.size(); i++) {
                PointTrack t = inactive.get(i);
                TrackInfo info = t.getCookie();
                if (tick - info.lastActive > 2) {
                    tracker.dropTrack(t);
                }
            }

            active.clear();
            tracker.getActiveTracks(active);
            for (int i = 0; i < active.size(); i++) {
                PointTrack t = active.get(i);
                TrackInfo info = t.getCookie();
                info.lastActive = tick;
            }

            spawned.clear();
            if (active.size() < 50) {
                tracker.spawnTracks();

                // update the track's initial position
                for (int i = 0; i < active.size(); i++) {
                    PointTrack t = active.get(i);
                    TrackInfo info = t.getCookie();
                    info.spawn.set(t);
                }

                tracker.getNewTracks(spawned);
                for (int i = 0; i < spawned.size(); i++) {
                    PointTrack t = spawned.get(i);
                    if (t.cookie == null) {
                        t.cookie = new TrackInfo();
                    }
                    TrackInfo info = t.getCookie();
                    info.lastActive = tick;
                    info.spawn.set(t);
                }
            }

            synchronized (lockGui) {
                //ConvertBitmap.grayToBitmap(gray,bitmap,storage);
                //ConvertBitmap.multiToBitmap(color, bitmap, storage);

                trackSrc.reset();
                trackDst.reset();
                trackSpawn.reset();

                for (int i = 0; i < active.size(); i++) {
                    PointTrack t = active.get(i);
                    TrackInfo info = t.getCookie();
                    Point2D_F64 s = info.spawn;
                    Point2D_F64 p = active.get(i);

                    trackSrc.grow().set(s);
                    trackDst.grow().set(p);
                }

                for (int i = 0; i < spawned.size(); i++) {
                    Point2D_F64 p = spawned.get(i);
                    trackSpawn.grow().set(p);
                }
            }

            tick++;
        }

        public FastQueue<Point2D_F64> getTrackSrc() {
            return trackSrc;
        }

        public FastQueue<Point2D_F64> getTrackDst() {
            return trackDst;
        }

        public FastQueue<Point2D_F64> getTrackSpawn() {
            return trackSpawn;
        }
    }

    private static class TrackInfo {
        long lastActive;
        Point2D_F64 spawn = new Point2D_F64();
    }
}
