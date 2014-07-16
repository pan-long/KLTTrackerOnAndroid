package sg.edu.nus.comp.klttracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by panlong on 16/7/14.
 */
public class LocalVideoView extends VideoView {
    private Bitmap frameBitmap;
    private PointProcessing pointProcessing;

    public LocalVideoView(Context context) {
        super(context);
    }

    public LocalVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocalVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setProcessing(PointProcessing pointProcessing) {
        this.pointProcessing = pointProcessing;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.setBitmap(frameBitmap);
        super.draw(canvas);


    }
}
