package com.zjxfyb.whf.facecomparedemo.api;

import android.content.Context;
import android.text.TextUtils;

import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.conts.Constant;
import com.zjxfyb.whf.facecomparedemo.modle.FaceCompareBean;
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
 * Created by whf on 2017/7/7.
 */

public class FaceCompareImpl {

    /**
     *
     * @param context 上下文
     * @param face_token1 第一个人脸标识face_token
     *                    first face_token
     * @param image_url1  第一个人脸的url
     *                   image url of first face
     * @param image_file1 第一个人脸的图片文件的byte流
     *                  file of first face
     *
     *                  三个参数只需要传一个就行了
     *   only need one of three parameter
     * @param image_base64_1  base64编码的二进制图片数据
            如果同时传入了image_url1、image_file1和image_base64_1参数，本API使用顺序为image_file1优先，image_url1最低。
     * @param face_token2  第二个人脸标识face_token
     *                   second face_token
     * @param image_url2  第二个人脸的url的byte流
     *                   image url of second face
     * @param image_file2  第二个人脸的图片文件
     *                  file of second face
     *   三个参数只需要传一个就行了
     *   only need one of three parameter
     * @param image_base64_2 base64编码的二进制图片数据
            如果同时传入了image_url1、image_file1和image_base64_1参数，本API使用顺序为image_file1优先，image_url1最低。
     * @param callBack 成功和失败的回调
     */
    public static void compareFace(Context context, String face_token1, String image_url1, byte[] image_file1, String image_base64_1, String face_token2, String image_url2, byte[] image_file2, String image_base64_2, final FaceCallBack<FaceCompareBean> callBack) {
        FaceCompareApi faceCompareApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceCompareApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(face_token1)) {
            map.put("face_token1", RequestBody.create(MediaType.parse("multipart/form-data"), face_token1));
        }


        if (!TextUtils.isEmpty(image_url1)) {
            map.put("image_url1", RequestBody.create(MediaType.parse("multipart/form-data"), image_url1));
        }


        if (!TextUtils.isEmpty(image_base64_1)) {
            map.put("image_base64_1", RequestBody.create(MediaType.parse("multipart/form-data"), image_base64_1));
        }


        if (!TextUtils.isEmpty(face_token2)) {
            map.put("face_token2", RequestBody.create(MediaType.parse("multipart/form-data"), face_token2));
        }


        if (!TextUtils.isEmpty(image_url2)) {
            map.put("image_url2", RequestBody.create(MediaType.parse("multipart/form-data"), image_url2));
        }


        if (!TextUtils.isEmpty(image_base64_2)) {
            map.put("image_base64_2", RequestBody.create(MediaType.parse("multipart/form-data"), image_base64_2));
        }


        if (image_file1 != null && image_file1.length > 0) {
            map.put("image_file1\"; filename=\"image_file1", RequestBody.create(MediaType.parse("multipart/form-data"), image_file1));
        }
        if (image_file2 != null && image_file2.length > 0) {
            map.put("image_file2\"; filename=\"image_file2", RequestBody.create(MediaType.parse("multipart/form-data"), image_file2));
        }

        Call<FaceCompareBean> stringCall = faceCompareApi.faceCompare(RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET), map);

        stringCall.enqueue(new Callback<FaceCompareBean>() {
            @Override
            public void onResponse(Call<FaceCompareBean> call, Response<FaceCompareBean> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                }else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FaceCompareBean> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    public interface FaceCompareCallBack {

        void onSuccess(FaceCompareBean body);

        void onFaild(String body);
    }
}
