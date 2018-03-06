package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * Created by ddw on 2017/11/14.
 */

public class LruCacheUtil {


    private static final String TAG = LruCacheUtil.class.getSimpleName();
    private static LruCacheUtil mLruChcheUtil;
    private static LruCache<String, Bitmap> mLruCache;

    private LruCacheUtil() {
    }

    public static LruCacheUtil getInstance() {

        if (mLruChcheUtil == null) {
            synchronized (LruCacheUtil.class) {
                if (mLruChcheUtil == null) {
                    mLruChcheUtil = new LruCacheUtil();
                    long size = Runtime.getRuntime().maxMemory();
//                    Log.e(TAG, "getInstance: " + (int) (size * 3 / 8) );
                    mLruCache = new LruCache<String, Bitmap>((int) (size * 3 / 8)) {
                        @Override
                        protected int sizeOf(String key, Bitmap value) {
                            return value.getRowBytes() * value.getHeight();
                        }
                    };

                }
            }
        }
        return mLruChcheUtil;
    }

    public void put(String key, Bitmap bitmap) {

        if (null != mLruCache) {

            Log.e(TAG, "put: 缓存图片, key -->  : " + key);
            Bitmap put = mLruCache.put(key, bitmap);
//            Log.e(TAG, "put: " + put );
//            Log.e(TAG, "put: AppDataBean.imageCount --> " + AppDataBean.imageCount );
//            Log.e(TAG, "put: mLruCache.size() --> " + mLruCache.putCount() );
//            if (AppDataBean.imageCount == mLruCache.putCount()){
//                EventBus.getDefault().post(new InitDataEvent(true));
//            }
        }
    }

    public Bitmap getBitmap(String key) {

        if (null != mLruCache && mLruCache.size() > 0) {
            Bitmap bitmap = mLruCache.get(key);
            return bitmap;
        }
        return null;
    }

    public boolean containKey(String key) {

        if (null != mLruCache) {
            if (null != mLruCache.get(key)) {
                return true;
            }
        }

        return false;
    }

    public int getSize(){

        if (null != mLruCache){
            return mLruCache.size();
        }
        return 0;
    }
}
