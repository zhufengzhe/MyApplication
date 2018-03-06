package com.zjxfyb.whf.facecomparedemo.api;

import android.content.Context;
import android.text.TextUtils;

import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.conts.Contents;
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
 * Created by whf on 2017/7/24.
 */

public class FaceImpl {

    public static void getFaceDetail(Context context, String face_token, final FaceCallBack<String> callBack) {

        FaceBaseApi faceBaseApi = RetrofitUtil.getInstance().getStringRetrofit(context).create(FaceBaseApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(face_token)) {
            map.put("face_token", RequestBody.create(MediaType.parse("multipart/form-data"), face_token));
        }
        Call<String> stringCall = faceBaseApi.faceBaseApi("/facepp/v3/face/getdetail", RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

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
            public void onFailure(Call<String> call, Throwable t) {

                callBack.onFaild(t.getMessage());
            }
        });
    }

//    public interface FaceCallBack {
//
//        void onSuccess(String body);
//
//        void onFaild(String errorBody);
//    }
}
