package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.widget.ImageView;

import java.util.concurrent.Callable;

/**
 * Created by ddw on 2017/12/15.
 */

public class TaskInfo {


    private Callable mCallable;

    private String url;

    private ImageView mImageView;

    public TaskInfo(Callable callable, String url, ImageView mImageView) {
        mCallable = callable;
        this.url = url;
        this.mImageView = mImageView;
    }

    public Callable getCallable() {
        return mCallable;
    }

    public void setCallable(Callable callable) {
        mCallable = callable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskInfo) {
            ImageView imageView = ((TaskInfo) obj).getImageView();
            if (null != imageView) {
                return imageView == this.mImageView && ((TaskInfo) obj).getUrl().equals(this.url);
            } else {
                return ((TaskInfo) obj).getUrl().equals(this.url);
            }
        } else {
            return false;
        }
    }
}
