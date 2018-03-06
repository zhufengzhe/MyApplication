package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Created by ddw on 2018/1/5.
 */

public class LoadDiskImageCallable implements Callable<Bitmap> {

    private ImageView imageView;
    private Handler uiHandler;
    private String key;
    private String tag;
    private static final String TAG = LoadDiskImageCallable.class.getSimpleName();

    public LoadDiskImageCallable(ImageView imageView, Handler uiHandler, String key, String tag) {
        this.imageView = imageView;
        this.uiHandler = uiHandler;
        this.key = key;
        this.tag = tag;
    }

    @Override
    public Bitmap call() throws Exception {

        File dir = new File(Environment.getExternalStorageDirectory() + "/diskDir");

        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(dir, "/" + key + ".png");

        Bitmap bitmap = null;

        if (file.exists()) {

            Log.i(TAG, "call: 找到文件 path --> " + file.getAbsolutePath());

            //文件存在,加载文件
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            if (null != bitmap) {

                LruCacheUtil.getInstance().put(key, bitmap);//缓存在内存中

                Log.i(TAG, "call: 加载到bitmap，开始加载到界面上");
                //给ui线程发送消息
                ImageInfo info = new ImageInfo(imageView, tag, bitmap, LoadedFrom.DISC);
                Message msg = Message.obtain();
                msg.obj = info;
                uiHandler.sendMessage(msg);
            }
        } else {
            Log.i(TAG, "call: 没有找到本地文件，地址 --> " + file.getAbsolutePath());
        }

        return bitmap;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
