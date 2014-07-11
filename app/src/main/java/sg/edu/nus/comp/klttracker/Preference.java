package sg.edu.nus.comp.klttracker;

import boofcv.struct.calib.IntrinsicParameters;

/**
 * @author Peter Abeles
 */
public class Preference {
    // currently we support two modes, for camera and local video
    static final int CAMERA_MODE = 0;
    static final int VIDEO_MODE = 1;

    int mode;
    int cameraId;
    int preview;
    int picture;
    boolean showFps;
    IntrinsicParameters intrinsic;
}
