package com.zjxfyb.whf.facecomparedemo.api;


import com.zjxfyb.whf.facecomparedemo.modle.FaceSetDetailBean;
import com.zjxfyb.whf.facecomparedemo.modle.GetFaceSetsBean;

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

public interface FaceSetApi {

    @Multipart
    @POST("/facepp/v3/faceset/create")
    Call<String> facaSetCreat(@Part("api_key") RequestBody api_key, @Part("api_secret") RequestBody api_secret, @PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("/facepp/v3/faceset/addface")
    Call<String> facaSetAddFace(@Part("api_key") RequestBody api_key, @Part("api_secret") RequestBody api_secret, @PartMap Map<String, RequestBody> map);


    @Multipart
    @POST("/facepp/v3/faceset/delete")
    Call<String> facaSetDeteleFace(@Part("api_key") RequestBody api_key, @Part("api_secret") RequestBody api_secret, @PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("/facepp/v3/faceset/getdetail")
    Call<FaceSetDetailBean> getDetail(@Part("api_key") RequestBody api_key, @Part("api_secret") RequestBody api_secret, @PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("/facepp/v3/faceset/getfacesets")
    Call<GetFaceSetsBean> getFaceSets(@Part("api_key") RequestBody api_key, @Part("api_secret") RequestBody api_secret, @PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("/facepp/v3/faceset/update")
    Call<String> update(@Part("api_key") RequestBody api_key, @Part("api_secret") RequestBody api_secret, @PartMap Map<String, RequestBody> map);
}
