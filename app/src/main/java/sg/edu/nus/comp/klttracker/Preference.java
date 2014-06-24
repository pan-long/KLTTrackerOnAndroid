package sg.edu.nus.comp.klttracker;

import sg.edu.nus.comp.klttracker.boofcv.struct.calib.IntrinsicParameters;

/**
 * @author Peter Abeles
 */
public class Preference {
    int cameraId;
    int preview;
    int picture;
    boolean showFps;
    IntrinsicParameters intrinsic;
}
