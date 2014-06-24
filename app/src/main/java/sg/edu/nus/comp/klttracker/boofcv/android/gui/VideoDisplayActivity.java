package sg.edu.nus.comp.klttracker.boofcv.android.gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Looper;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by panlong on 24/6/14.
 */
public abstract class VideoDisplayActivity extends Activity implements Camera.PreviewCallback{

    protected Camera mCamera;
    protected Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
    private Visualization mDraw;
    private CameraPreview mPreview;
    protected VideoProcessing processing;

    // Used to inform the user that its doing some calculations
    ProgressDialog progressDialog;
    protected final Object lockProgress = new Object();

    boolean hidePreview = true;
    boolean showFPS = false;

    LinearLayout contentView;
    FrameLayout preview;

    public VideoDisplayActivity() {
    }

    public VideoDisplayActivity(boolean hidePreview) {
        this.hidePreview = hidePreview;
    }

    /**
     * Changes the CV algorithm running.  Should only be called from a GUI thread.
     */
    public void setProcessing( VideoProcessing processing ) {
        if( this.processing != null ) {
            // kill the old process
            this.processing.stopProcessing();
        }

        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new RuntimeException("Not called from a GUI thread. Bad stuff could happen");
        }

        this.processing = processing;
        // if the camera is null then it will be initialized when the camera is initialized
        if( processing != null && mCamera != null ) {
            processing.init(mDraw,mCamera,mCameraInfo);
        }
    }

    /**
     * The parent view for this activity
     */
    public LinearLayout getViewContent() {
        return contentView;
    }

    /**
     * The view containing the camera preview.  The first child in the parent view's list.
     */
    public FrameLayout getViewPreview() {
        return preview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams contentParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        preview = new FrameLayout(this);
        LinearLayout.LayoutParams frameLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT,1);
        contentView.addView(preview, frameLayoutParam);

        mDraw = new Visualization(this);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this,this,hidePreview);

        preview.addView(mPreview);
        preview.addView(mDraw);

        setContentView(contentView, contentParam);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if( mCamera != null )
            throw new RuntimeException("Bug, camera should not be initialized already");

        setUpAndConfigureCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        hideProgressDialog();

        if( processing != null ) {
            VideoProcessing p = processing;
            processing = null;
            p.stopProcessing();
        }

        if (mCamera != null){
            mPreview.setCamera(null);
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * Sets up the camera if it is not already setup.
     */
    private void setUpAndConfigureCamera() {
        // Open and configure the camera
        mCamera = openConfigureCamera(mCameraInfo);

        // Create an instance of Camera
        mPreview.setCamera(mCamera);

        if( processing != null ) {
            processing.init(mDraw,mCamera,mCameraInfo);
        }
    }

    /**
     * Open the camera and configure it.
     *
     * @return camera
     */
    protected abstract Camera openConfigureCamera( Camera.CameraInfo cameraInfo);

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        if( processing != null )
            processing.convertPreview(bytes,camera);
    }

    /**
     * Draws on top of the video stream for visualizing results from vision algorithms
     */
    private class Visualization extends SurfaceView {

        private Paint textPaint = new Paint();

        double history[] = new double[10];
        int historyNum = 0;

        Activity activity;

        long previous = 0;

        public Visualization(Activity context ) {
            super(context);
            this.activity = context;

            // Create out paint to use for drawing
            textPaint.setARGB(255, 200, 0, 0);
            textPaint.setTextSize(60);
            // This call is necessary, or else the
            // draw method will not be called.
            setWillNotDraw(false);
        }

        @Override
        protected void onDraw(Canvas canvas){

            canvas.save();
            if( processing != null )
                processing.onDraw(canvas);

            // Draw how fast it is running
            long current = System.currentTimeMillis();
            long elapsed = current - previous;
            previous = current;
            history[historyNum++] = 1000.0/elapsed;
            historyNum %= history.length;

            double meanFps = 0;
            for( int i = 0; i < history.length; i++ ) {
                meanFps += history[i];
            }
            meanFps /= history.length;

            canvas.restore();
            if( showFPS )
                canvas.drawText(String.format("FPS = %5.2f",meanFps), 50, 50, textPaint);
        }
    }

    /**
     * Displays an indeterminate progress dialog.   If the dialog is already open this will change the message being
     * displayed.  Function blocks until the dialog has been declared.
     *
     * @param message Text shown in dialog
     */
    protected void setProgressMessage(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                synchronized ( lockProgress ) {
                    if( progressDialog != null ) {
                        // a dialog is already open, change the message
                        progressDialog.setMessage(message);
                        return;
                    }
                    progressDialog = new ProgressDialog(VideoDisplayActivity.this);
                    progressDialog.setMessage(message);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                }

                // don't show the dialog until 1 second has passed
                long showTime = System.currentTimeMillis()+1000;
                while( showTime > System.currentTimeMillis() ) {
                    Thread.yield();
                }
                // if it hasn't been dismissed, show the dialog
                synchronized ( lockProgress ) {
                    if( progressDialog != null )
                        progressDialog.show();
                }
            }});

        // block until the GUI thread has been called
        while( progressDialog == null  ) {
            Thread.yield();
        }
    }

    /**
     * Dismisses the progress dialog.  Can be called even if there is no progressDialog being shown.
     */
    protected void hideProgressDialog() {
        // do nothing if the dialog is already being displayed
        synchronized ( lockProgress ) {
            if( progressDialog == null )
                return;
        }

        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            // if inside the UI thread just dismiss the dialog and avoid a potential locking condition
            synchronized ( lockProgress ) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    synchronized ( lockProgress ) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }});

            // block until dialog has been dismissed
            while( progressDialog != null  ) {
                Thread.yield();
            }
        }
    }

    public boolean isShowFPS() {
        return showFPS;
    }

    public void setShowFPS(boolean showFPS) {
        this.showFPS = showFPS;
    }
}
