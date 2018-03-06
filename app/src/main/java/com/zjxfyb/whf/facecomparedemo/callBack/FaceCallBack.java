package com.zjxfyb.whf.facecomparedemo.callBack;

/**
 * Created by whf on 2017/8/11.
 */

public interface FaceCallBack<T> {

    void onSuccess(T body);

    void onFaild(String body);

}
