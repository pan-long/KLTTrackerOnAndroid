package sg.edu.nus.comp.klttracker;

/**
 * Created by panlong on 24/6/14.
 */
public class KLTVideoDisplayActivity extends VideoDisplayActivity{

    public static DemoPreference preference;

    public DemoVideoDisplayActivity() {
    }

    public DemoVideoDisplayActivity(boolean hidePreview) {
        super(hidePreview);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preference = DemoMain.preference;
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
