package sg.edu.nus.comp.klttracker;

import android.os.Bundle;
import sg.edu.nus.comp.klttracker.boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import sg.edu.nus.comp.klttracker.boofcv.abst.feature.tracker.PointTracker;
import sg.edu.nus.comp.klttracker.boofcv.factory.feature.tracker.FactoryPointTracker;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageSInt16;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageUInt8;

/**
 * Created by panlong on 24/6/14.
 */
public class KltDisplayActivity extends PointTrackerDisplayActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConfigGeneralDetector config = new ConfigGeneralDetector();
        config.maxFeatures = 150;
        config.threshold = 40;
        config.radius = 3;

        PointTracker<ImageUInt8> tracker =
                FactoryPointTracker.klt(new int[]{1,2,4},config,3,ImageUInt8.class, ImageSInt16.class);

        setProcessing(new PointProcessing(tracker));
    }

}
