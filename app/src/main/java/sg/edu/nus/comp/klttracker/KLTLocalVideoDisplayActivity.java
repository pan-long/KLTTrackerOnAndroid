package sg.edu.nus.comp.klttracker;

import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;

import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.factory.feature.tracker.FactoryPointTracker;
import boofcv.struct.image.ImageSInt16;
import boofcv.struct.image.ImageUInt8;
import georegression.struct.point.Point2D_F64;

/**
 * Created by a0105529 on 7/11/14.
 */
public class KLTLocalVideoDisplayActivity extends Activity implements MediaPlayer.OnErrorListener{
    private final String videoSource = "/sdcard/Video/test.mp4";

    protected final Object lockGui = new Object();
    protected PointProcessing pointProcessing;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private MediaController mediaController;
    private LocalVideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_video_display_activity);

        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(videoSource);

        videoView = (LocalVideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(videoSource));
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        videoView.setOnErrorListener(this);

        videoView.requestFocus();
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
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

        videoView.setProcessing(pointProcessing);

        videoView.resume();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        Toast.makeText(KLTLocalVideoDisplayActivity.this,
                "Error!!!",
                Toast.LENGTH_LONG).show();

        return true;
    }

}
