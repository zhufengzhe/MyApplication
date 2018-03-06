package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by ddw on 2017/12/15.
 */

public class DownloadImageCallable implements Callable<Bitmap> {

    private String tag;
    private String url;
    private ImageView imageView;
    private Handler uiHandler;
    private String key;
    private static final String TAG = DownloadImageCallable.class.getSimpleName();

    public DownloadImageCallable(String url, ImageView imageView, Handler uiHandler, String key, String tag) {
        this.tag = tag;
        this.url = url;
        this.imageView = imageView;
        this.uiHandler = uiHandler;
        this.key = key;
    }

    @Override
    public Bitmap call() throws Exception {

        Bitmap bitmap = null;
        HttpURLConnection mConn = null;
        try {
            URL url = new URL(this.url);

            mConn = (HttpURLConnection) url.openConnection();

            mConn.setConnectTimeout(30000);

            mConn.setDoInput(true);

            int responseCode = mConn.getResponseCode();

            if (responseCode == 200) {

//                InputStream inputStream = mConn.getInputStream();
//
//                BitmapFactory.Options options = new BitmapFactory.Options();
//
//                options.inJustDecodeBounds = true;
//
//                options.inSampleSize = 3;
//
//                options.inJustDecodeBounds = false;

                bitmap = BitmapUtil.yasuoNetBitmap(mConn.getInputStream(), imageView);

                Log.i(TAG, "call: 压缩网络图片 --> " + bitmap);

                if (bitmap != null) {

                    Log.i(TAG, "call: 下载到图片,地址 --> " + this.url);
                    LruCacheUtil.getInstance().put(key, bitmap);
                    File dir = new File(Environment.getExternalStorageDirectory() + "/diskDir");

                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    File file = new File(dir, "/" + key + ".png");
                    Log.i(TAG, "call: 将bitmap写入到文件中 file.path --> " + file.getAbsolutePath());
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    FileOutputStream stream = new FileOutputStream(file, true);
                    stream.write(outputStream.toByteArray());
                }

                ImageInfo info = new ImageInfo(imageView, tag, bitmap, LoadedFrom.NETWORK);
                Message msg = Message.obtain();
                msg.obj = info;
                uiHandler.sendMessage(msg);

            }
        } catch (MalformedURLException e) {

            Log.i(TAG, "call: 网络加载异常，地址 --> " + this.url + "\n 异常信息 --> " + e.getMessage());

            e.printStackTrace();

        } catch (IOException e) {

            Log.i(TAG, "call: 网络加载异常，地址 --> " + this.url + "\n 异常信息 --> " + e.getMessage());

            e.printStackTrace();

        } catch (Exception e) {

            Log.i(TAG, "call: 网络加载异常，地址 --> " + this.url + "\n 异常信息 --> " + e.getMessage());

            e.printStackTrace();
        } finally {

            if (mConn != null) {

                mConn.disconnect();
            }
        }

        return bitmap;
    }
}
