package com.zjxfyb.whf.facecomparedemo.utils;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import static com.zjxfyb.whf.facecomparedemo.utils.TransPointUtil.ShortBufferUtil;
import static com.zjxfyb.whf.facecomparedemo.utils.TransPointUtil.loadShader;

/**
 * Created by whf on 2017/7/11.
 */

public class DrawRect {

    private static final int COORDS_PER_VERTEX = 3;
    private static final String TAG = DrawRect.class.getSimpleName();
    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" + "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    "  gl_Position = vPosition * uMVPMatrix; gl_PointSize = 8.0;" + "}";

    private final String fragmentShaderCode = "precision mediump float;" + "uniform vec4 vColor;" + "void main() {"
            + "  gl_FragColor = vColor;" + "}";

    private final GL10 gl;

    // 画框
    public List<FloatBuffer> vertexBuffers;
    private int mProgram = -1;


    //顶点个数
    private int vertexCount = 0;
    //顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节
    //矩形框的颜色
    private float[] color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    /**
     * 顶点的绘制顺序
     */
    short index[] = {1, 0, 3, 2, 2, 1, 0, 3};
    private ShortBuffer indexBuffer;

    private int mMatrixHandler;

    public synchronized void setRectPoint(List<float[]> rectPoints) {

        vertexBuffers = new ArrayList<>();

        TransPointUtil.transPoint(rectPoints, vertexBuffers);

//        vertexCount = rectPoint.length / COORDS_PER_VERTEX;
        /**
         *  将矩形坐标转换成OpenGL ES所需的FloatBuffer
         */
//        Log.e(TAG, "DrawRect: " + Arrays.toString(points));
    }

    public DrawRect(GL10 gl) {

        this.gl = gl;

        indexBuffer = ShortBufferUtil(index);
        /**
         *  加载着色器
         */
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        /**
         *  创建空的opengl程序
         */
        mProgram = GLES20.glCreateProgram();
        /**
         *  将顶点着色器家在到程序中
         */
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        /**
         *  连接到着色器
         */
        GLES20.glLinkProgram(mProgram);
    }


    public void draw(float[] MVPMatrix) {

//        gl.glLoadIdentity();

//        Log.e(TAG, "draw: 画图");
        /**
         *  将程序家在到GLES2.0中
         */
        GLES20.glUseProgram(mProgram);
        //获取变换矩阵vMatrix成员句柄
        mMatrixHandler = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        //指定vMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandler, 1, false, MVPMatrix, 0);
        /**
         *  获取顶点着色器的vPosition句柄
         */
        int vPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        /**
         *  启用三角形顶点的句柄
         */
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glLineWidth(5);
        /**
         *  获取片元着色器
         */
        int vColor = GLES20.glGetUniformLocation(mProgram, "vColor");
        /**
         *  设置绘制矩形框的颜色
         */
        GLES20.glUniform4fv(vColor, 1, color, 0);

        synchronized (this) {

            if (null != vertexBuffers && vertexBuffers.size() > 0) {

                for (FloatBuffer vertexBuffer : vertexBuffers) {
                    /**
                     *  准备矩形框的数据
                     */
                    GLES20.glVertexAttribPointer(vPosition, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
                    /**
                     *  绘制矩形
                     */
                    GLES20.glDrawElements(GLES20.GL_LINES, index.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

                }

                vertexBuffers.clear();
            }
        }
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(vPosition);
    }


}
