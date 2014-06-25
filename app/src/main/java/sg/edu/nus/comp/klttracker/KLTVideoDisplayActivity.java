package sg.edu.nus.comp.klttracker;

import android.hardware.Camera;
import android.os.Bundle;

import boofcv.android.gui.VideoDisplayActivity;

/**
 * Created by panlong on 24/6/14.
 */
public class KLTVideoDisplayActivity extends VideoDisplayActivity {

    public static Preference preference;

    public KLTVideoDisplayActivity() {
    }

    public KLTVideoDisplayActivity(boolean hidePreview) {
        super(hidePreview);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preference = klt_main.preference;
        setShowFPS(preference.showFps);
    }

    @Override
    protected Camera openConfigureCamera( Camera.CameraInfo info ) {
        Camera mCamera = Camera.open(preference.cameraId);
        Camera.getCameraInfo(preference.cameraId,info);

        Camera.Parameters param = mCamera.getParameters();
        Camera.Size sizePreview = param.getSupportedPreviewSizes().get(preference.preview);
        param.setPreviewSize(sizePreview.width,sizePreview.height);
        Camera.Size sizePicture = param.getSupportedPictureSizes().get(preference.picture);
        param.setPictureSize(sizePicture.width, sizePicture.height);
        mCamera.setParameters(param);

        return mCamera;
    }

}
