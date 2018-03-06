package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by ddw on 2018/1/5.
 */

public class ImageLoaderBuilder {

    private ImageLoader mLoader;

    private String mUrl;

    public ImageLoaderBuilder(ImageLoader loader, String url) {
        mLoader = loader;
        mUrl = url;
    }

    public void into(final ImageView imageView) {

        if (null != imageView) {
            //从界面移除后取消加载请求
            imageView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    mLoader.removeTask(imageView);
                }
            });

        }

        mLoader.addTask(mUrl, imageView);
    }

    public void downloadImage() {
        into(null);
    }
}