package sg.edu.nus.comp.klttracker;

import android.hardware.Camera;

import java.util.ArrayList;
import java.util.List;

/**
 * To speed things up, just collect information on the cameras once and store them in this data structure.
 *
 * @author Peter Abeles
 */
public class CameraSpecs {
    Camera.CameraInfo info = new Camera.CameraInfo();
    List<Camera.Size> sizePreview = new ArrayList<Camera.Size>();
    List<Camera.Size> sizePicture = new ArrayList<Camera.Size>();
}
