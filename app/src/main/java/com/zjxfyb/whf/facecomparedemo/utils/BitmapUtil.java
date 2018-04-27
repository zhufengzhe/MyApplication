package com.zjxfyb.whf.facecomparedemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by ddw on 2018/4/25.
 */

public class BitmapUtil {

    private static final String TAG = BitmapUtil.class.getSimpleName();

    public static void compressImg(ByteArrayOutputStream stream, Bitmap bitmap) {

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        Log.e(TAG, "compressImg: 压缩图片");
        int i = 100;
        while (stream.size() > 1024 * 1024 / 4) {
            i -= 5;
            if (i < 0) {
                break;
            }
            stream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, i, stream);
//            Log.e(TAG, "compressImg: i = " + i + " ; stream.size = " + stream.size());
        }

        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /*
* 旋转图片
* @param angle
* @param bitmap
* @return Bitmap
*/
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.postRotate(angle);
//        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap != null && !bitmap.isRecycled())
            bitmap.recycle();
        return resizedBitmap;
    }

    public static Bitmap decodeBitmapByByte(Camera camera, byte[] data) {

        Camera.Size previewSize = camera.getParameters().getPreviewSize();

        YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        yuvImage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, stream);

        Bitmap bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

        Log.e(TAG, "run: 开始流程");

        bitmap = BitmapUtil.rotaingImageView(-90, bitmap);

        return bitmap;
    }
}
