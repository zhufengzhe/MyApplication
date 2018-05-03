package com.zjxfyb.whf.facecomparedemo.netUtils;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by ddw on 2018/4/14.
 */

public interface NetApi {

    @GET("{urlpath}")
    Observable<String> getApi(@Path(value = "urlpath",encoded = true) String urlpath, @QueryMap Map<String, String> queryMap);

    @FormUrlEncoded
    @POST("{urlpath}")
    Observable<String> postFormApi(@Path(value = "urlpath",encoded = true) String urlpath, @FieldMap Map<String, Object> queryMap);

    @Multipart
    @POST("{urlpath}")
    Observable<String> postFileApi(@Path(value = "urlpath",encoded = true) String urlpath, @PartMap Map<String, RequestBody> partMap);

    @POST("{urlpath}")
    Observable<ResponseBody> postApi(@Path(value = "urlpath",encoded = true) String urlpath, @Body RequestBody body);
}
