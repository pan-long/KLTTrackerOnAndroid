package sg.edu.nus.comp.klttracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by a0105529 on 7/11/14.
 */
public class KLTLocalVideoDisplayActivity extends Activity{


    protected class DrawingView extends SurfaceView implements SurfaceHolder.Callback{
        public DrawingView(Context context){
            super(context);
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void onDraw(Canvas canvas){

        }
    }
}
