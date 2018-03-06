package com.zjxfyb.whf.facecomparedemo.utils;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static com.zjxfyb.whf.facecomparedemo.utils.TransPointUtil.ShortBufferUtil;
import static com.zjxfyb.whf.facecomparedemo.utils.TransPointUtil.floatBufferUtil;
import static com.zjxfyb.whf.facecomparedemo.utils.TransPointUtil.loadShader;

public class CameraMatrix {

    private static final String TAG = CameraMatrix.class.getSimpleName();
    // vertex着色器code
    private final String vertexShaderCode = "attribute vec4 vPosition;"
            + "attribute vec2 inputTextureCoordinate;"
            + "varying vec2 textureCoordinate;" + "void main()" + "{"
            + "gl_Position = vPosition; gl_PointSize = 10.0;"
            + "textureCoordinate = inputTextureCoordinate;" + "}";

    // fragment着色器code
    private final String fragmentShaderCode = "#extension GL_OES_EGL_image_external : require\n"
            + "precision mediump float;"
            + "varying vec2 textureCoordinate;\n"
            + "uniform samplerExternalOES s_texture;\n"
            + "const vec3 monoMultiplier = vec3(0.299, 0.587, 0.114);\n"
            + "void main() {"
            + "vec4 color = texture2D(s_texture, textureCoordinate);\n"
            + "float monoColor = dot(color.rgb,monoMultiplier);\n"
            + "gl_FragColor = vec4(monoColor, monoColor, monoColor, 1.0);\n"
            + "}";

    /*    private final String fragmentShaderCode = "#extension GL_OES_EGL_image_external : require\n"
                + "precision mediump float;"
                + "varying vec2 textureCoordinate;\n"
                + "uniform samplerExternalOES s_texture;\n"
                + "void main() {"
                + "  gl_FragColor = texture2D( s_texture, textureCoordinate );\n"
                + "}";*/
    private FloatBuffer vertexBuffer, textureVerticesBuffer;
    private ShortBuffer drawListBuffer;
    private final int mProgram;

//	// private FloatBuffer triangleVB;
//	public ArrayList<ArrayList> points = new ArrayList<ArrayList>();

    private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices
    // （命令绘制顶点）

    // number of coordinates per vertex in this array (顶点坐标数)
    private static final int COORDS_PER_VERTEX = 2;
    // 顶点步幅
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per
    // vertex
    // 直角坐标系
    static float squareCoords[] = {-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f};
    // 结构顶点（8个数字表示了4个点x,y的位置.大小在0-1之间）
    static float textureVertices[] = {0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
            0.0f, 0.0f};

    private int mTextureID;
    private int[] textures;

    static float LineCoords[] = {0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};

    private final int VertexCount = LineCoords.length / 3;
    private float[] mVt;


    public CameraMatrix(int textureID) {
        this.mTextureID = textureID;
        textures = new int[5];

        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        GLES20.glGenTextures(5, textures, 0);

        // initialize vertex byte buffer for shape coordinates(初始化顶点字节缓冲区形状坐标)
        vertexBuffer = floatBufferUtil(squareCoords);
        // initialize byte buffer for the draw list (绘制列表初始化字节缓冲区)
        drawListBuffer = ShortBufferUtil(drawOrder);
        // initialize textureVertices byte buffer for shape
        // coordinates(初始化结构顶点字节缓冲区形状坐标)
        textureVerticesBuffer = floatBufferUtil(textureVertices);

        mProgram = GLES20.glCreateProgram(); // create empty OpenGL ES Program

        // 拿出两个着色器 顶点着色器和碎片着色器
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        GLES20.glAttachShader(mProgram, vertexShader); // add the vertex shader

        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment
        // shader to program
        GLES20.glLinkProgram(mProgram); // creates OpenGL ES program executables

    }

    /**
     * 绘制：
     * <p>
     * 我们在 onDrawFrame 回调中执行绘制操作，绘制的过程其实就是为 shader 代码变量赋值，并调用绘制命令的过程：
     */
    public void draw(float[] mtx) {
        // to program
        GLES20.glUseProgram(mProgram);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextureID);
        // get handle to vertex shader's vPosition member
        // (顶点着色器的vPosition成员得到处理)
        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices(使一个句柄三角形顶点)
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the <insert shape here> coordinate data (准备<插入形状这里>坐标数据)
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        int mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram,
                "inputTextureCoordinate");
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);

        //照相机镜像
        textureVerticesBuffer.clear();

        textureVerticesBuffer.put(transformTextureCoordinates(textureVertices,
                mtx));
        textureVerticesBuffer.position(0);

        GLES20.glVertexAttribPointer(mTextureCoordHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, textureVerticesBuffer);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
    }

    /**
     * 图像旋转
     */
    private float[] transformTextureCoordinates(float[] coords, float[] matrix) {

        float[] result = new float[coords.length];

        if (mVt == null)
            mVt = new float[4];

        for (int i = 0; i < coords.length; i += 2) {
            float[] v = {coords[i], coords[i + 1], 0, 1};
            // for (int j = 0; j < v.length; j ++) {
            // Log.w("ceshi", "v[" + j + "]======" + coords[j]);
            // }
            Matrix.multiplyMV(mVt, 0, matrix, 0, v, 0);
            result[i] = mVt[0];// x轴镜像
//            result[i + 1] = mVt[1];//y轴镜像
            result[i + 1] = coords[i + 1];
        }
        //
        // for (int i = 0; i < coords.length; i ++) {
        // Log.w("ceshi", "coords[" + i + "]======" + coords[i]);
        // }
        //
        // for (int i = 0; i < result.length / 2; i ++) {
        // Log.w("ceshi", "result[" + i + "]======" + result[i]);
        // }

        // [0.0, 1.0, 1.0, 1.0]; v
        // [0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0]; coords
        // [1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0]; result

//        Log.e(TAG, "transformTextureCoordinates: result : " + Arrays.toString(result));
        return result;
    }
}
