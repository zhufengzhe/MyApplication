package com.zjxfyb.whf.facecomparedemo.api;

import android.content.Context;
import android.text.TextUtils;

import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.conts.Contents;
import com.zjxfyb.whf.facecomparedemo.modle.FaceSearchBean;
import com.zjxfyb.whf.facecomparedemo.utils.RetrofitUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by whf on 2017/7/8.
 */

public class FaceSearchImpl {

    /**
     *
     * @param context
     * @param faceToken 与Faceset中人脸比对的face_token
     * @param image_url 需要比对的人脸的网络图片URL
     * @param image_file 需要比对的人脸的图片的二进制数组
     * @param faceSetToken Faceset的标识
     * @param returnResultCount 返回比对置信度最高的n个结果，范围[1,5]。默认值为1
     * @param callBack
     */
    public static void faceSearchForToken(Context context, String faceToken, String image_url, byte[] image_file, String faceSetToken, int returnResultCount, final FaceCallBack<FaceSearchBean> callBack) {
        FaceSearchApi faceSearchApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceSearchApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(faceToken)) {
            map.put("face_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceToken));
        }
        if (!TextUtils.isEmpty(image_url)) {
            map.put("image_url", RequestBody.create(MediaType.parse("multipart/form-data"), image_url));
        }
        if (null != image_file && image_file.length > 0) {
            map.put("image_file\"; filename=\"image_file", RequestBody.create(MediaType.parse("multipart/form-data"), image_file));
        }
        if (!TextUtils.isEmpty(faceSetToken)) {
            map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
        }

        map.put("return_result_count", RequestBody.create(MediaType.parse("multipart/form-data"), returnResultCount + ""));

        Call<FaceSearchBean> stringCall = faceSearchApi.faceSearch(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<FaceSearchBean>() {
            @Override
            public void onResponse(Call<FaceSearchBean> call, Response<FaceSearchBean> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FaceSearchBean> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     *
     * @param context
     * @param faceToken 与Faceset中人脸比对的face_token
     * @param image_url 需要比对的人脸的网络图片URL
     * @param image_file 需要比对的人脸的图片的二进制数组
     * @param outer_id Faceset的标识
     * @param returnResultCount 返回比对置信度最高的n个结果，范围[1,5]。默认值为1
     * @param callBack
     */
    public static void faceSearchForOuter(Context context, String faceToken, String image_url, byte[] image_file, String outer_id, int returnResultCount, final FaceCallBack<FaceSearchBean> callBack) {
        FaceSearchApi faceSearchApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceSearchApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(faceToken)) {
            map.put("face_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceToken));
        }
        if (!TextUtils.isEmpty(image_url)) {
            map.put("image_url", RequestBody.create(MediaType.parse("multipart/form-data"), image_url));
        }
        if (null != image_file && image_file.length > 0) {
            map.put("image_file\"; filename=\"image_file", RequestBody.create(MediaType.parse("multipart/form-data"), image_file));
        }
        if (!TextUtils.isEmpty(outer_id)) {
            map.put("outer_id", RequestBody.create(MediaType.parse("multipart/form-data"), outer_id));
        }

        map.put("return_result_count", RequestBody.create(MediaType.parse("multipart/form-data"), returnResultCount + ""));

        Call<FaceSearchBean> stringCall = faceSearchApi.faceSearch(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<FaceSearchBean>() {
            @Override
            public void onResponse(Call<FaceSearchBean> call, Response<FaceSearchBean> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FaceSearchBean> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

//    public interface FaceSearchCallBack {
//        void onSuccess(FaceSearchBean body);
//
//        void onFaild(String body);
//    }
}
