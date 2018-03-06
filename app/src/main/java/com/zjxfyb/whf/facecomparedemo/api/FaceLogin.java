package com.zjxfyb.whf.facecomparedemo.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by whf on 2017/8/11.
 */

public interface FaceLogin {

    @GET("/ddw-interface/customer/login")
    Call<String> faceLogin(@Query("faceId") String faceId,@Query("sign") String sign);
}
