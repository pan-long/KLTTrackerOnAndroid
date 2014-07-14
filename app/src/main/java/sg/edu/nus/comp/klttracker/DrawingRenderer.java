package sg.edu.nus.comp.klttracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import boofcv.android.ConvertBitmap;
import boofcv.struct.image.ImageUInt8;

/**
* Created by a0105734 on 7/14/14.
*/
class DrawingRenderer implements GLSurfaceView.Renderer {
    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private float[] vertices;
    private float[] texture;

    private MediaMetadataRetriever mediaMetadataRetriever;
    private Bitmap frame;
    private KLTLocalVideoDisplayActivity.PointProcessing pointProcessing;
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
        this.pointProcessing = KLTLocalVideoDisplayActivity.pointProcessing;

        mediaMetadataRetriever = new MediaMetadataRetriever();

        mediaMetadataRetriever.setDataSource(filePath);

        video_height = Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        video_width = Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));

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

        float ratio = video_height / (float)video_width;

        if (scale == mHeight / (double)video_height) {
            vertices = new float[] {
                    -video_height / ratio, -video_height, 0f,
                    -video_height / ratio, video_height, 0f,
                    video_height / ratio, -video_height, 0f,
                    video_height / ratio, video_height, 0f
            };

            texture = new float[] {
                    -video_height / ratio, -video_height,
                    -video_height / ratio, video_height,
                    video_height / ratio, -video_height,
                    video_height / ratio, video_height
            };
        } else {
            vertices = new float[] {
                    -video_width, -video_width * ratio, 0f,
                    -video_width, video_width * ratio, 0f,
                    video_width, -video_width * ratio, 0f,
                    video_width, video_width * ratio, 0f
            };

            texture = new float[] {
                    -video_width, -video_width * ratio,
                    -video_width, video_width * ratio,
                    video_width, -video_width * ratio,
                    video_width, video_width * ratio
            };
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuffer.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);

        gl10.glViewport(0, 0, width, height);
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glLoadIdentity();

        GLU.gluPerspective(gl10, 45f, (float) width / (float) height, 0.1f, 10000f);
//        GLU.gluOrtho2D(gl10, (float) -width / 2, (float) width/2, (float) - height/2, (float) height/2);

        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        if (currentTime > previousTime) {
            frame = mediaMetadataRetriever.getFrameAtTime(currentTime, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            storage = ConvertBitmap.declareStorage(frame, storage);
            ImageUInt8 gray = new ImageUInt8(video_width, video_height);
            ConvertBitmap.bitmapToGray(frame, gray, storage);

            if (pointProcessing == null)
                System.out.println("point processing is null!!!");

            pointProcessing.process(gray);

            loadGLTexture(gl10);

            gl10.glEnable(GL10.GL_TEXTURE_2D);
            gl10.glShadeModel(GL10.GL_SMOOTH);
            gl10.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
            gl10.glClearDepthf(1.0f);
            gl10.glEnable(GL10.GL_DEPTH_TEST);
            gl10.glDepthFunc(GL10.GL_LEQUAL);

            gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
            gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl10.glLoadIdentity();
            gl10.glTranslatef(0f, 0f, -1000f);

            gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

            gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            gl10.glFrontFace(GL10.GL_CW);

//            gl10.glColor4f(0f, 1f, 0f, 0.5f);
            gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl10.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

            gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length/3);

            gl10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            gl10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            previousTime = currentTime;
        }
    }

    private int[] textures = new int[1];

    private void loadGLTexture(GL10 gl10) {
        gl10.glGenTextures(1, textures, 0);
        gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, frame, 0);

        frame.recycle();
    }
}
