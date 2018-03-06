package com.zjxfyb.whf.facecomparedemo.api;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by whf on 2017/7/20.
 */

public interface FaceBaseApi {

    @Multipart
    @POST("{faceUrl}")
    Call<String> faceBaseApi(@Path("faceUrl") String faceUrl,@Part("api_key") RequestBody api_key, @Part("api_secret") RequestBody api_secret, @PartMap Map<String,RequestBody> stringMap);
}
