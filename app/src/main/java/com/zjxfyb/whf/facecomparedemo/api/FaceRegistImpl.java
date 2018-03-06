package com.zjxfyb.whf.facecomparedemo.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zjxfyb.whf.facecomparedemo.R;
import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.modle.FaceSearchBean;
import com.zjxfyb.whf.facecomparedemo.utils.RetrofitUtil;
import com.zjxfyb.whf.facecomparedemo.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by whf on 2017/8/11.
 */

public class FaceRegistImpl {

    private static final String TAG = FaceRegistImpl.class.getSimpleName();

    public static void faceRegist(Context context, String faceId, String customerName, String phoneNum, String birthday, String sex, byte[] photo, final FaceCallBack<String> callBack){

        Retrofit stringRetrofit = RetrofitUtil.getInstance().
                creatRetrofit().
                addConverterFactory(ScalarsConverterFactory.create()).
                client(false).
                build();

        FaceRegistApi faceRegistApi = stringRetrofit.create(FaceRegistApi.class);

        Map<String, RequestBody> map = new HashMap<>();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        map.put("faceId", RequestBody.create(MediaType.parse("multipart/form-data"), faceId));
        map.put("customerName", RequestBody.create(MediaType.parse("multipart/form-data"), customerName));
        map.put("phoneNum", RequestBody.create(MediaType.parse("multipart/form-data"), phoneNum));
        map.put("birthday", RequestBody.create(MediaType.parse("multipart/form-data"), birthday));
        map.put("sex", RequestBody.create(MediaType.parse("multipart/form-data"), sex));
        map.put("photo\"; filename=\"photo", RequestBody.create(MediaType.parse("multipart/form-data"), photo));
        map.put("sign", RequestBody.create(MediaType.parse("multipart/form-data"), Utils.getSign("1234567898765432")));

        Call<String> stringCall = faceRegistApi.faceRegist(map);
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
}
