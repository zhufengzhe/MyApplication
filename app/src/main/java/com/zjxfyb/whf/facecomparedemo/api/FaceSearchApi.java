package com.zjxfyb.whf.facecomparedemo.api;

import com.zjxfyb.whf.facecomparedemo.modle.FaceSearchBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by whf on 2017/7/8.
 */

public interface FaceSearchApi {

    @Multipart
    @POST("/facepp/v3/search")
    Call<FaceSearchBean> faceSearch(@Part("api_key") RequestBody api_key, @Part("api_secret") RequestBody api_secret, @PartMap Map<String, RequestBody> stringMap);
}
