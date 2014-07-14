package sg.edu.nus.comp.klttracker;

import android.media.MediaMetadataRetriever;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
* Created by a0105734 on 7/14/14.
*/
class DrawingRenderer implements GLSurfaceView.Renderer {
    private FloatBuffer vertexBuffer;
    private float vertices[] = {
            -1.0f, -1.0f,  0.0f,
            -1.0f,  1.0f,  0.0f,
            1.0f, -1.0f,  0.0f,
            1.0f,  1.0f,  0.0f

    };

    private MediaMetadataRetriever mediaMetadataRetriever;
    private int video_height;
    private int mHeight;
    private int video_width;
    private int mWidth;
    private double scale;
    private double tranX;
    private double tranY;
    private byte[] storage;
    private long mOffset;
    private long previousTime;
    private long currentTime;

    public DrawingRenderer(String filePath, long offset) {
//            mediaMetadataRetriever = new MediaMetadataRetriever();
//
//            mediaMetadataRetriever.setDataSource(filePath);
//
//            video_height = Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
//            video_width = Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = vertexByteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        this.mOffset = offset;

        previousTime = currentTime = 0;

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!KLTLocalVideoDisplayActivity.videoIsPaused) {
                        currentTime += mOffset;


                        try {
                            sleep(mOffset / 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        mHeight = height;
        mWidth = width;

        scale = Math.min(mHeight/(double)video_height, mWidth/(double)video_width);
        tranX = (width-scale*video_width)/2;
        tranY = (height-scale*video_height)/2;

        gl10.glViewport(0, 0, width, height);
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glLoadIdentity();

        GLU.gluPerspective(gl10, 45f, (float) width / (float) height, 0.1f, 10000f);

        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        if (currentTime > previousTime) {
//                Bitmap frame = mediaMetadataRetriever.getFrameAtTime(mOffset);
//                storage = ConvertBitmap.declareStorage(frame, storage);
//                ImageUInt8 gray = new ImageUInt8(video_width, video_height);
//                ConvertBitmap.bitmapToGray(frame, gray, storage);
//
//                pointProcessing.process(gray);

            gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl10.glLoadIdentity();
            gl10.glTranslatef(0f, 0f, -5f);

            gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl10.glColor4f(0f, 1f, 0f, 0.5f);
            gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length/3);
            gl10.glDisableClientState(GL10.GL_VERTEX_ARRAY);

            previousTime = currentTime;
        }
    }
}
