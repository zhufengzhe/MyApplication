package com.zjxfyb.whf.facecomparedemo.api;

import android.content.Context;
import android.text.TextUtils;

import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.conts.Constant;
import com.zjxfyb.whf.facecomparedemo.modle.FaceDetectBean;
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

public class FaceDetectImpl {

    private static final String TAG = FaceDetectImpl.class.getSimpleName();

    /**
     *
     * @param context 上下文
     * @param imageurl 图片地址
     * @param landmark 是否返回人脸的关键点，1：返回，0：不返回
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     * @param callBack 回掉
     */
    public static void faceDetectForUrl(Context context, String imageurl, int landmark, String attributes, final FaceCallBack<FaceDetectBean> callBack) {
        FaceDetectApi faceDetectApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceDetectApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(imageurl)) {
            map.put("image_url", RequestBody.create(MediaType.parse("multipart/form-data"), imageurl));
        }
        map.put("return_landmark", RequestBody.create(MediaType.parse("multipart/form-data"), landmark + ""));

        if (!TextUtils.isEmpty(attributes)) {
            map.put("return_attributes", RequestBody.create(MediaType.parse("multipart/form-data"), attributes));
        }
        Call<FaceDetectBean> stringCall = faceDetectApi.faceDetect(RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET), map);
        stringCall.enqueue(new Callback<FaceDetectBean>() {
            @Override
            public void onResponse(Call<FaceDetectBean> call, Response<FaceDetectBean> response) {
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
            public void onFailure(Call<FaceDetectBean> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }


    /**
     *
     * @param context 上下文
     * @param image_file 图片字节流
     * @param landmark 是否返回人脸的关键点，1：返回，0：不返回
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     * @param callBack 回调
     */
    public static void faceDetectForByte(Context context, byte[] image_file, int landmark, String attributes, final FaceCallBack<FaceDetectBean> callBack) {
        FaceDetectApi faceDetectApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceDetectApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (null != image_file && image_file.length > 0) {
            map.put("image_file\"; filename=\"image_file", RequestBody.create(MediaType.parse("multipart/form-data"), image_file));
        }
        map.put("return_landmark", RequestBody.create(MediaType.parse("multipart/form-data"), landmark + ""));

        if (!TextUtils.isEmpty(attributes)) {
            map.put("return_attributes", RequestBody.create(MediaType.parse("multipart/form-data"), attributes));
        }
        Call<FaceDetectBean> stringCall = faceDetectApi.faceDetect(RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET), map);
        stringCall.enqueue(new Callback<FaceDetectBean>() {
            @Override
            public void onResponse(Call<FaceDetectBean> call, Response<FaceDetectBean> response) {
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
            public void onFailure(Call<FaceDetectBean> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     *
     * @param context 上下文
     * @param image_base64 base64编码的二进制图片数据

    如果同时传入了image_url、image_file和image_base64参数，本API使用顺序为image_file优先，image_url最低。

     * @param landmark  是否返回人脸的关键点，1：返回，0：不返回
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     * @param callBack 回调
     */
    public static void faceDetectForBase64(Context context, String image_base64, int landmark, String attributes, final FaceCallBack<FaceDetectBean> callBack) {
        FaceDetectApi faceDetectApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceDetectApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(image_base64)) {
            map.put("image_base64", RequestBody.create(MediaType.parse("multipart/form-data"), image_base64));
        }
        map.put("return_landmark", RequestBody.create(MediaType.parse("multipart/form-data"), landmark + ""));

        if (!TextUtils.isEmpty(attributes)) {
            map.put("return_attributes", RequestBody.create(MediaType.parse("multipart/form-data"), attributes));
        }
        Call<FaceDetectBean> stringCall = faceDetectApi.faceDetect(RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET), map);
        stringCall.enqueue(new Callback<FaceDetectBean>() {
            @Override
            public void onResponse(Call<FaceDetectBean> call, Response<FaceDetectBean> response) {
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
            public void onFailure(Call<FaceDetectBean> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }
    public interface FaceDetectCallBack {

        void onSuccess(FaceDetectBean body);

        void onFaild(String body);
    }
}
