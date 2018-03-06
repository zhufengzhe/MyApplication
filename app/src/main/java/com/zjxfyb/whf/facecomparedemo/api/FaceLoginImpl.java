package com.zjxfyb.whf.facecomparedemo.api;

import android.util.Log;

import com.zjxfyb.whf.facecomparedemo.utils.RetrofitUtil;
import com.zjxfyb.whf.facecomparedemo.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by whf on 2017/8/11.
 */

public class FaceLoginImpl {

    private static final String TAG = FaceRegistImpl.class.getSimpleName();

    public static void faceLogiin(String faceToken){

        Retrofit build = RetrofitUtil.getInstance().creatRetrofit().addConverterFactory(ScalarsConverterFactory.create()).client(false).build();

        FaceLogin faceLogin = build.create(FaceLogin.class);

        Call<String> stringCall = faceLogin.faceLogin(faceToken, Utils.getSign(faceToken));

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: " + response.body() );
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }
}
