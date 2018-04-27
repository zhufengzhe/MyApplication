package com.zjxfyb.whf.facecomparedemo;

import android.text.TextUtils;

import com.zjxfyb.whf.facecomparedemo.conts.Constant;
import com.zjxfyb.whf.facecomparedemo.modle.FaceDetectBean;
import com.zjxfyb.whf.facecomparedemo.netUtils.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ddw on 2018/4/25.
 */

public class FaceSetMapper implements Function<FaceDetectBean, Observable<String>> {

    private String tags = null;

    private FaceSetMapper() {
    }

    @Override
    public Observable<String> apply(FaceDetectBean bean) throws Exception {
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(tags)) {
            map.put("tags", RequestBody.create(MediaType.parse("multipart/form-data"), tags));
        }
        map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
        map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));
        return NetWorkUtil.PostObservable(Constant.BASEURL, "facepp/v3/faceset/getfacesets", map);
    }

    public static FaceSetMapper getInstance() {
        return FaceSetMapper.FaceSetMapperInstance.mMapper;
    }

    private static class FaceSetMapperInstance {
        private static FaceSetMapper mMapper = new FaceSetMapper();
    }
}
