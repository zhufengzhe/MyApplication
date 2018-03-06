package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by ddw on 2017/9/18.
 */

public class BitmapUtil {


    private static final String TAG = BitmapUtil.class.getSimpleName();

    public static void setImage(final View imageView, final int imageId, final Resources resources, final int position) {
//
////        Log.e(TAG, "setImage: lrucahce大小 ：" + LruCacheUtil.getInstance().getSize());
////        if (LruCacheUtil.getInstance().containKey(position)) {
////            Log.e(TAG, "setImage: 从缓存中读取图片，position : " + position);
//            imageView.setBackground(new BitmapDrawable(resources, LruCacheUtil.getInstance().getBitmap(position)));
//            return;
//        }
//
////        Log.e(TAG, "setImage: position " + position + " , 压缩前 : " + BitmapFactory.decodeResource(resources, imageId).getByteCount());
//
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//
//        options.inJustDecodeBounds = true;
//
//        BitmapFactory.decodeResource(resources, imageId, options);
//
//        final int imgWidth = options.outWidth;
//        final int imgHeight = options.outHeight;
//
//        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//                int viewWidth = imageView.getMeasuredWidth();
//                int viewHeight = imageView.getMeasuredHeight();
//
//                int simpleSize = 1;
//
//                if (viewWidth > 0 && viewHeight > 0)
//                    if (imgWidth > viewWidth || imgHeight > viewHeight) {
//
//                        int widthSimpleSize = Math.round(imgWidth / viewWidth);
//                        int heightSimpleSize = Math.round(imgHeight / viewHeight);
//
//                        simpleSize = widthSimpleSize > heightSimpleSize ? widthSimpleSize : heightSimpleSize;
//                    }
//
//                simpleSize = simpleSize % 2 == 0 ? simpleSize : simpleSize + 1;
//
//                options.inSampleSize = simpleSize;
//
//                options.inJustDecodeBounds = false;
//
//                Bitmap bitmap = BitmapFactory.decodeResource(resources, imageId, options);
//
//                BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
//
////                LruCacheUtil.getInstance().put(position, bitmap);
//
//                imageView.setBackground(drawable);
//
////                Log.e(TAG, "setImage:  position " + position + " , 压缩后 : " + bitmap.getByteCount());
//
//                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
    }

    public static Bitmap yasuoBitmap(int resId, int width, int height, Resources resources) {

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;//让图片的边界可编辑，可以改变图片的大小

        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId, options);

        int bitmapWidth = options.outWidth;//图片的宽

        int bitmapHeight = options.outHeight;//图片的高

        int simpleSize = 1;

        if (width > 0 && width > 0)

            if (bitmapWidth > width || bitmapHeight > height) {

                int widthSimpleSize = Math.round(bitmapWidth / width);

                int heightSimpleSize = Math.round(bitmapHeight / height);

                simpleSize = widthSimpleSize >= heightSimpleSize ? widthSimpleSize : heightSimpleSize;
            }

//        Log.e(TAG, "yasuoBitmap: " + simpleSize );

        options.inSampleSize = simpleSize / 2;

        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeResource(resources, resId, options);
//
//        Log.e(TAG, "yasuoBitmap: width ---> " + bitmap.getWidth());
//        Log.e(TAG, "yasuoBitmap: height ---> " + bitmap.getHeight());

        return bitmap;
    }

    /**
     * 压缩网络图片
     *
     * @param is 图片数据流
     * @param imageView imageview
     * @return
     */
    public static Bitmap yasuoNetBitmap(InputStream is, ImageView imageView) {

        if (null == is) {
            throw new NullPointerException("图片输入流为空");
        }

        BufferedInputStream stream = new BufferedInputStream(is);

        Bitmap bitmap = null;

        try {

            stream.mark(stream.available());

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;//让图片的边界可编辑，可以改变图片的大小

            bitmap = BitmapFactory.decodeStream(stream, null, options);

            int bitmapWidth = options.outWidth;//图片的宽

            int bitmapHeight = options.outHeight;//图片的高

            int imageViewMeasuredWidth = 0;

            int imageViewMeasuredHeight = 0;

            if (null != imageView) {

                imageView.measure(0, 0);

                imageViewMeasuredWidth = imageView.getMeasuredWidth();

                imageViewMeasuredHeight = imageView.getMeasuredHeight();
            }

            int simpleSize = 1;

            if (imageViewMeasuredWidth > 0 && bitmapWidth > 0)

                if (bitmapWidth > imageViewMeasuredWidth || bitmapHeight > imageViewMeasuredHeight) {

                    int widthSimpleSize = Math.round(bitmapWidth / imageViewMeasuredWidth);

                    int heightSimpleSize = Math.round(bitmapHeight / imageViewMeasuredHeight);

                    simpleSize = widthSimpleSize >= heightSimpleSize ? widthSimpleSize : heightSimpleSize;
                }

            Log.i(TAG, "yasuoBitmap: " + simpleSize);

            options.inSampleSize = simpleSize / 2;

            options.inJustDecodeBounds = false;

            stream.reset();

            bitmap = BitmapFactory.decodeStream(stream, null, options);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return bitmap;
    }
}
