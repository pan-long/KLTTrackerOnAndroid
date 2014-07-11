package sg.edu.nus.comp.klttracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import boofcv.android.BoofAndroidFiles;


public class KLTMainActivity extends Activity {
    // contains information on all the cameras.  less error prone and easier to deal with
    public static List<CameraSpecs> specs = new ArrayList<CameraSpecs>();

    // specifies which camera to use an image size
    public static Preference preference;

    // If another activity modifies the demo preferences this needs to be set to true so that it knows to reload
    // camera parameters.
    public static boolean changedPreferences = false;

    private Button button_camera;
    private Button button_video;

    public KLTMainActivity() {
        loadCameraSpecs();
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klt_main);

        final Intent camera_intent = new Intent(this, KLTDisplayActivity.class);
        final Intent video_intent = new Intent(this, KLTLocalVideoDisplayActivity.class);
//        startActivity(intent);

        button_camera = (Button)findViewById(R.id.button_open_camera);
        button_video = (Button)findViewById(R.id.button_open_video);

        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.mode = Preference.CAMERA_MODE;
                startActivity(camera_intent);
            }
        });

        button_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.mode = Preference.VIDEO_MODE;
                startActivity(video_intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( preference == null ) {
            preference = new Preference();
            setDefaultPreferences();
        } else if( changedPreferences ) {
            loadIntrinsic();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.klt_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.preferences: {
                Intent intent = new Intent(this, PreferenceActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadCameraSpecs() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraSpecs c = new CameraSpecs();
            specs.add(c);

            Camera.getCameraInfo(i, c.info);
            Camera camera = Camera.open(i);
            Camera.Parameters params = camera.getParameters();
            c.sizePreview.addAll(params.getSupportedPreviewSizes());
            c.sizePicture.addAll(params.getSupportedPictureSizes());
            camera.release();
        }
    }

    private void setDefaultPreferences() {
        preference.showFps = true;

        // There are no cameras.  This is possible due to the hardware camera setting being set to false
        // which was a work around a bad design decision where front facing cameras wouldn't be accepted as hardware
        // which is an issue on tablets with only front facing cameras
        if( specs.size() == 0 ) {
            // if the device does not have a camera, set the mode to VIDEO_MODE and disable open camera
            // button
            preference.mode = Preference.VIDEO_MODE;
            button_camera.setEnabled(false);
            dialogNoCamera();
        }
        else {
            // if the device has a camera, set the mode to CAMERA_MODE
            preference.mode = Preference.CAMERA_MODE;

            // select a front facing camera as the default
            for (int i = 0; i < specs.size(); i++) {
                CameraSpecs c = specs.get(i);

                if (c.info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    preference.cameraId = i;
                    break;
                } else {
                    // default to a front facing camera if a back facing one can't be found
                    preference.cameraId = i;
                }
            }

            CameraSpecs camera = specs.get(preference.cameraId);
            preference.preview = UtilVarious.closest(camera.sizePreview, 320, 240);
            preference.picture = UtilVarious.closest(camera.sizePicture, 640, 480);
        }

        // see if there are any intrinsic parameters to load
        loadIntrinsic();
    }

    private void dialogNoCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your device has no cameras, setting the mode into video mode!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadIntrinsic() {
        preference.intrinsic = null;
        try {
            FileInputStream fos = openFileInput("cam"+preference.cameraId+".txt");
            Reader reader = new InputStreamReader(fos);
            preference.intrinsic = BoofAndroidFiles.readIntrinsic(reader);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            Toast.makeText(this, "Failed to load intrinsic parameters", Toast.LENGTH_LONG).show();
        }
    }

    public void  onContentChanged  () {
        super.onContentChanged();
    }
}
