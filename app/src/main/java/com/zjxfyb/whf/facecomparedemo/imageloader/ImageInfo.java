package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/2.
 */

public class ImageInfo {

    ImageView imageView;

    LoadedFrom loadedFrom;

    String tag;

    Bitmap bitmap;


    public ImageInfo(ImageView imageView, String tag, Bitmap bitmap, LoadedFrom loadedFrom) {
        this.imageView = imageView;
        this.tag = tag;
        this.bitmap = bitmap;
        this.loadedFrom = loadedFrom;
    }


    public LoadedFrom getLoadedFrom() {
        return loadedFrom;
    }

    public void setLoadedFrom(LoadedFrom loadedFrom) {
        this.loadedFrom = loadedFrom;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
