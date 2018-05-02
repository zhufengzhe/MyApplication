package com.zjxfyb.whf.facecomparedemo;

import android.graphics.Bitmap;

import com.zjxfyb.whf.facecomparedemo.api.FaceImpl;
import com.zjxfyb.whf.facecomparedemo.modle.FaceDetectBean;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ddw on 2018/4/25.
 */

public class FaceDetectMapper implements Function<Bitmap, Observable<FaceDetectBean>> {

    private FaceDetectMapper(){}

    private String attributes = "gender,age,smiling,glass,headpose,facequality,blur";
    private int landmark = 0;

    @Override
    public Observable<FaceDetectBean> apply(Bitmap bitmap) throws Exception {
        return FaceImpl.FaceDetectImpl.faceDetect(bitmap);
    }

    public static FaceDetectMapper getInstance(){
        return FaceDetectMapperInstance.mMapper;
    }

    private static class FaceDetectMapperInstance{
        private static FaceDetectMapper mMapper = new FaceDetectMapper();
    }
}
