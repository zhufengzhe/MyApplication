package com.zjxfyb.whf.facecomparedemo;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.zjxfyb.whf.facecomparedemo.conts.Constant;
import com.zjxfyb.whf.facecomparedemo.netUtils.NetWorkUtil;
import com.zjxfyb.whf.facecomparedemo.utils.BitmapUtil;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ddw on 2018/4/25.
 */

public class FaceDetectMapper implements Function<Bitmap, Observable<String>> {

    private FaceDetectMapper(){}

    private String attributes = "gender,age,smiling,glass,headpose,facequality,blur";
    private int landmark = 0;

    @Override
    public Observable<String> apply(Bitmap bitmap) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapUtil.compressImg(stream, bitmap);
        byte[] image_file = stream.toByteArray();
        Map<String, RequestBody> map = new HashMap<>();
        if (null != image_file && image_file.length > 0) {
            map.put("image_file\"; filename=\"image_file", RequestBody.create(MediaType.parse("multipart/form-data"), image_file));
        }
        map.put("return_landmark", RequestBody.create(MediaType.parse("multipart/form-data"), landmark + ""));

        if (!TextUtils.isEmpty(attributes)) {
            map.put("return_attributes", RequestBody.create(MediaType.parse("multipart/form-data"), attributes));
        }
        map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
        map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));
        return NetWorkUtil.PostObservable(Constant.BASEURL, "facepp/v3/detect", map);
    }

    public static FaceDetectMapper getInstance(){
        return FaceDetectMapperInstance.mMapper;
    }

    private static class FaceDetectMapperInstance{
        private static FaceDetectMapper mMapper = new FaceDetectMapper();
    }
}
