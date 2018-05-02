package com.zjxfyb.whf.facecomparedemo;

import android.text.TextUtils;

import com.zjxfyb.whf.facecomparedemo.conts.Constant;
import com.zjxfyb.whf.facecomparedemo.modle.FaceDetectBean;
import com.zjxfyb.whf.facecomparedemo.modle.GetFaceSetsBean;
import com.zjxfyb.whf.facecomparedemo.netUtils.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ddw on 2018/4/25.
 */

public class FaceSearchMapper implements BiFunction<FaceDetectBean, GetFaceSetsBean.FacesetsBean, Observable<String>> {

    private int returnResultCount = 1;

    private FaceSearchMapper() {
    }

    private String attributes = "gender,age,smiling,glass,headpose,facequality,blur";
    private int landmark = 0;

    @Override
    public Observable<String> apply(FaceDetectBean faceDetectBean, GetFaceSetsBean.FacesetsBean facesetsBean) throws Exception {

        Map<String, RequestBody> map = new HashMap<>();

        if (!TextUtils.isEmpty(faceDetectBean.getFaces().get(0).getFace_token())) {
            map.put("face_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceDetectBean.getFaces().get(0).getFace_token()));
        }
        if (!TextUtils.isEmpty(facesetsBean.getFaceset_token())) {
            map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), facesetsBean.getFaceset_token()));
        }

        map.put("return_result_count", RequestBody.create(MediaType.parse("multipart/form-data"), returnResultCount + ""));
        map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
        map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));
        return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, "facepp/v3/search", map);
    }

    public static FaceSearchMapper getInstance() {
        return FaceDetectMapperInstance.mMapper;
    }

    private static class FaceDetectMapperInstance {
        private static FaceSearchMapper mMapper = new FaceSearchMapper();
    }
}
