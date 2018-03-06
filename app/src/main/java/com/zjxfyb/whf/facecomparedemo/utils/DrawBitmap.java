package com.zjxfyb.whf.facecomparedemo.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class DrawBitmap {

    private static final String vertexCode =
            "attribute vec4 vPosition;" +
                    "attribute vec2 vCoordinate;" +
                    "uniform mat4 vMatrix;" +
                    "varying vec2 aCoordinate;" +
                    "void main()" +
                    "{ " +
                    "gl_Position=vMatrix*vPosition;" +
                    "aCoordinate=vCoordinate;" +
                    "}";

    private static final String fragmentCode = "precision mediump float;" +
            "uniform sampler2D vTexture;" +
            "varying vec2 aCoordinate;" +
            "void main(){" +
            "gl_FragColor=texture2D(vTexture,aCoordinate);" +
            "}";
    private static final String TAG = DrawBitmap.class.getSimpleName();

    //    //四边形的顶点坐标系
//    private float[] vertex = new float[]{
//            -0.5f, -0.3f,
//            0.5f, -0.3f,
//            -0.5f, 0.3f,
//            0.5f, 0.3f
//    };
    //纹理坐标系
    private float[] coord = new float[]{
            1.0f, 0,
            0, 0,
            1.0f, 1.0f,
            0, 1.0f
//            0.0f, 0.0f,
//            1.0f, 0f,
//            1.0f, 1.0f,
//            0f, 1.0f
    };
    //纹理存储定义，一般用来存名称
    private int[] textures = new int[5];
    //顶点、纹理缓冲
    FloatBuffer coordBuffer;

    private Bitmap mBitmap;
    private int mProgram;
    private int mVPosition;
    private int mVCoordinate;
    private int mVMatrix;
    private int mVTexture;
    private List<FloatBuffer> vertexBuffers;


    public DrawBitmap() {

        vertexBuffers = new ArrayList<>();

        mProgram = GLES20.glCreateProgram();

        int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        GLES20.glShaderSource(vertexShader, vertexCode);

        GLES20.glCompileShader(vertexShader);

        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        GLES20.glShaderSource(fragmentShader, fragmentCode);

        GLES20.glCompileShader(fragmentShader);

        GLES20.glAttachShader(mProgram, vertexShader);

        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);

        // 启用纹理
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        // 创建纹理
        GLES20.glGenTextures(5, textures, 0);

    }

    public void setRectPoints(List<float[]> rectPoints) {

        TransPointUtil.normaltransPoint(rectPoints, vertexBuffers);

        for (int i = 0; i < rectPoints.size(); i++) {

            mBitmap = initFontBitmap("划船不靠桨");

            if (null != mBitmap) {

                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[i]);

                //线性滤波
                //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);

                if (!mBitmap.isRecycled()){
                    mBitmap.recycle();
                    mBitmap = null;
                }

            }

        }

    }

    public void drawBitmap(float[] mMVPMatrix) {

//        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
//        Log.e(TAG, "onDrawFrame: glClear " + GLES20.glGetError());

        //准备纹理缓冲
        ByteBuffer coordbb = ByteBuffer.allocateDirect(coord.length * 4);
        coordbb.order(ByteOrder.nativeOrder());
        coordBuffer = coordbb.asFloatBuffer();
        coordBuffer.put(coord);
        coordBuffer.position(0);

        GLES20.glUseProgram(mProgram);
//        Log.e(TAG, "onDrawFrame: glUseProgram " + GLES20.glGetError());

        mVPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
//        Log.e(TAG, "onDrawFrame: mVPosition" + mVPosition);
        mVCoordinate = GLES20.glGetAttribLocation(mProgram, "vCoordinate");
//        Log.e(TAG, "onDrawFrame: mVCoordinate" + mVCoordinate);
        mVMatrix = GLES20.glGetUniformLocation(mProgram, "vMatrix");
//        Log.e(TAG, "onDrawFrame: mVMatrix" + mVMatrix);
        mVTexture = GLES20.glGetUniformLocation(mProgram, "vTexture");
//        Log.e(TAG, "onDrawFrame: glGetUniformLocation " + GLES20.glGetError());

        GLES20.glUniformMatrix4fv(mVMatrix, 1, false, mMVPMatrix, 0);
        GLES20.glEnableVertexAttribArray(mVPosition);
        GLES20.glEnableVertexAttribArray(mVCoordinate);
        GLES20.glUniform1i(mVTexture, 0);
//        Log.e(TAG, "onDrawFrame:  glUniform1i " + GLES20.glGetError());


        GLES20.glVertexAttribPointer(mVCoordinate, 2, GLES20.GL_FLOAT, false, 8, coordBuffer);

        for (FloatBuffer buffer : vertexBuffers) {

            GLES20.glVertexAttribPointer(mVPosition, 2, GLES20.GL_FLOAT, false, 8, buffer);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        }

        vertexBuffers.clear();

        GLES20.glDisableVertexAttribArray(mVPosition);
        GLES20.glDisableVertexAttribArray(mVCoordinate);
//        GLES20.glDisableVertexAttribArray(mVMatrix);
    }


    /**
     * android中绘制字体，使用画布canvas
     */
    public Bitmap initFontBitmap(String font) {
        int width = 80;
        int height = 200;
        int radios = 8;
        int huankuan = 6;
        //位图
        final Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //背景颜色
        canvas.drawColor(Color.TRANSPARENT);
        Paint textPaint = new Paint();
        //字体设置
        String fontType = "宋体";
        Typeface typeface = Typeface.create(fontType, Typeface.BOLD);
        //消除锯齿
        textPaint.setAntiAlias(true);
        //字体为红色
        textPaint.setColor(Color.RED);
        textPaint.setTypeface(typeface);
        textPaint.setTextSize(16);
        //绘制字体
        canvas.drawText(font, width + radios * 2, height + huankuan, textPaint);
        canvas.drawText("年龄:9", width + radios * 2, height + huankuan + 16, textPaint);

        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#FF4040"));
        circlePaint.setStrokeWidth(huankuan);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(width, height, radios, circlePaint);


        Paint rectPaint = new Paint();
        circlePaint.setAntiAlias(true);
        rectPaint.setColor(Color.parseColor("#FF4040"));
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.drawRect(new RectF(width - huankuan / 2, height + radios, width + huankuan / 2, height + radios * 3), rectPaint);

        canvas.drawRect(new RectF(width - radios, height + radios * 2 + huankuan / 2, width + radios, height + radios * 2 - huankuan / 2), rectPaint);


//        canvas.drawRect(new RectF(),rectPaint);

//        final Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.man);
//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mImg.setImageBitmap(bitmap);
//            }
//        });
        return bitmap;
    }
}
