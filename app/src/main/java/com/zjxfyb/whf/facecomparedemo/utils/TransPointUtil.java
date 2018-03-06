package com.zjxfyb.whf.facecomparedemo.utils;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

/**
 *  1.加载着色器
 *  2.将Android原生坐标转换为opengl能识别的buffer数据
 *  3.将Android原生坐标系中的坐标转换成OpenGL世界坐标系中的坐标
 * Created by whf on 2017/7/31.
 */

public class TransPointUtil {

    private static final String TAG = TransPointUtil.class.getSimpleName();

    // 定义一个工具方法，将float[]数组转换为OpenGL ES所需的FloatBuffer
    public static FloatBuffer floatBufferUtil(float[] arr) {
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        FloatBuffer mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    /**
     * 加载 著色器
     */
    public static int loadShader(int type, String shaderCode) {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    // 定义一个工具方法，将Short[]数组转换为OpenGL ES所需的ShortBuffer
    public static ShortBuffer ShortBufferUtil(short[] arr) {
        ByteBuffer dlb = ByteBuffer.allocateDirect(arr.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ShortBuffer buffer = dlb.asShortBuffer();
        buffer.put(arr);
        buffer.position(0);

        return buffer;
    }

    /**
     * 将人脸坐标转换成opengl能识别的数据
     *
     * @param rectPoints 人脸坐标jihe
     */
    public static synchronized void transPoint(List<float[]> rectPoints, List<FloatBuffer> vertexBuffers) {

        if (null != rectPoints && rectPoints.size() > 0) {

            for (float[] rectPoint : rectPoints) {

                float[] points = new float[rectPoint.length];

                int count = 0;

                for (int i = 0; i < rectPoint.length; i++) {
                    switch (i % 3) {
                        case 0:
                            switch (count) {
                                case 0:
                                    points[i] = rectPoint[i] / CameraUtil.getInstance().mPreviewHeight * 2 - 1;
                                    break;
                                case 1:
                                    points[i] = rectPoint[i] / CameraUtil.getInstance().mPreviewHeight * 2 - 1;
                                    break;
                                case 2:
                                    points[i] = rectPoint[i] / CameraUtil.getInstance().mPreviewHeight * 2 - 1;
                                    break;
                                case 3:
                                    points[i] = rectPoint[i] / CameraUtil.getInstance().mPreviewHeight * 2 - 1;
                                    break;
                            }
                            break;
                        case 1:
                            switch (count) {
                                case 0:
                                    points[i] = 1 - (rectPoint[i] / CameraUtil.getInstance().mPreviewWidth * 2);
                                    break;
                                case 1:
                                    points[i] = 1 - (rectPoint[i] / CameraUtil.getInstance().mPreviewWidth * 2);
                                    break;
                                case 2:
                                    points[i] = 1 - (rectPoint[i] / CameraUtil.getInstance().mPreviewWidth * 2);
                                    break;
                                case 3:
                                    points[i] = 1 - (rectPoint[i] / CameraUtil.getInstance().mPreviewWidth * 2);
                                    break;
                            }
                            break;
                        case 2:
                            points[i] = rectPoint[i];
                            count++;
                            break;
                    }
                }

//                Log.e(TAG, "transPoint: " + Arrays.toString(points));
                vertexBuffers.add(floatBufferUtil(points));
            }

        }
    }

    /**
     * 将图片坐标转换成OpenGL纹理坐标
     *
     * @param rectPoints 人脸坐标jihe
     */
    public static synchronized void normaltransPoint(List<float[]> rectPoints, List<FloatBuffer> vertexBuffers) {

        if (null != rectPoints && rectPoints.size() > 0) {

            for (float[] rectPoint : rectPoints) {

                float[] points = new float[rectPoint.length / 3 * 2];

//                Log.e(TAG, "normaltransPoint: " + Arrays.toString(rectPoint) );
                float width = Math.abs(rectPoint[6] - rectPoint[0]);
                float height = Math.abs(rectPoint[1] - rectPoint[4]);

                int count = 0;

                for (int i = 0; i < rectPoint.length; i++) {
                    switch (i % 3) {
                        case 0:
                            switch (count) {
                                case 0:
                                    points[6] = rectPoint[i] / CameraUtil.getInstance().mPreviewHeight * 2 - 1;
                                    break;
                                case 1:
                                    points[2] = rectPoint[i] / CameraUtil.getInstance().mPreviewHeight * 2 - 1;
                                    break;
                                case 2:
                                    points[4] = rectPoint[i] / CameraUtil.getInstance().mPreviewHeight * 2 - 1;
                                    break;
                                case 3:
                                    points[0] = rectPoint[i] / CameraUtil.getInstance().mPreviewHeight * 2 - 1;
                                    break;
                            }
                            break;
                        case 1:
                            switch (count) {
                                case 0:
                                    points[1] = 1 - rectPoint[i] / CameraUtil.getInstance().mPreviewWidth * 2 + height / CameraUtil.getInstance().mPreviewWidth * 2;
                                    break;
                                case 1:
                                    points[7] = 1 - rectPoint[i] / CameraUtil.getInstance().mPreviewWidth * 2 + height / CameraUtil.getInstance().mPreviewWidth * 2;
                                    break;
                                case 2:
                                    points[5] = 1 - rectPoint[i] / CameraUtil.getInstance().mPreviewWidth * 2 + height / CameraUtil.getInstance().mPreviewWidth * 2;
                                    break;
                                case 3:
                                    points[3] = 1 - rectPoint[i] / CameraUtil.getInstance().mPreviewWidth * 2 + height / CameraUtil.getInstance().mPreviewWidth * 2;
                                    break;
                            }
                            break;
                        case 2:
                            count++;
                            break;
                    }
                }

//                Log.e(TAG, "normaltransPoint: " + Arrays.toString(points));
                vertexBuffers.add(floatBufferUtil(points));
            }

        }
    }
}
