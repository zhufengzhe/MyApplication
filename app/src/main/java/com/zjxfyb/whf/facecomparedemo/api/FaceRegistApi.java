package com.zjxfyb.whf.facecomparedemo.api;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by whf on 2017/8/10.
 */

public interface FaceRegistApi {


    @Multipart
    @POST("/ddw-interface/customer/register")
    Call<String> faceRegist(@PartMap Map<String, RequestBody> stringMap);
}
