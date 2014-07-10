package sg.edu.nus.comp.klttracker;

import boofcv.struct.calib.IntrinsicParameters;

/**
 * @author Peter Abeles
 */
public class Preference {
    int mode;
    int cameraId;
    int preview;
    int picture;
    boolean showFps;
    IntrinsicParameters intrinsic;
}
