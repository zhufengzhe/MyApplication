package com.zjxfyb.whf.facecomparedemo;

import android.util.Log;

import com.zjxfyb.whf.facecomparedemo.api.FaceImpl;
import com.zjxfyb.whf.facecomparedemo.modle.FaceLoginBean;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * Created by ddw on 2018/4/25.
 */

public class FaceLoginMapper implements Function<String, Observable<FaceLoginBean>> {

    private final String TAG = FaceLoginMapper.class.getSimpleName();

    private FaceLoginMapper(){}

    @Override
    public Observable<FaceLoginBean> apply(String face_token) throws Exception {
        Log.i(TAG, "apply: 在服务器查找是否包含改人脸");
        return FaceImpl.FaceLoginImpl.faceLogin(face_token);
    }

    public static FaceLoginMapper getInstance(){
        return FaceDetectMapperInstance.mMapper;
    }

    private static class FaceDetectMapperInstance{
        private static FaceLoginMapper mMapper = new FaceLoginMapper();
    }
}
