package com.zjxfyb.whf.facecomparedemo.api;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zjxfyb.whf.facecomparedemo.conts.Constant;
import com.zjxfyb.whf.facecomparedemo.modle.FaceCompareBean;
import com.zjxfyb.whf.facecomparedemo.modle.FaceDetectBean;
import com.zjxfyb.whf.facecomparedemo.modle.FaceLoginBean;
import com.zjxfyb.whf.facecomparedemo.modle.FaceRegistBean;
import com.zjxfyb.whf.facecomparedemo.modle.FaceSearchBean;
import com.zjxfyb.whf.facecomparedemo.modle.FaceSetDetailBean;
import com.zjxfyb.whf.facecomparedemo.modle.GetFaceSetsBean;
import com.zjxfyb.whf.facecomparedemo.netUtils.NetWorkUtil;
import com.zjxfyb.whf.facecomparedemo.utils.BitmapUtil;
import com.zjxfyb.whf.facecomparedemo.utils.Utils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by whf on 2017/7/24.
 */

public class FaceImpl {

    private static Gson gson = new Gson();

    public static class FaceDetectImpl {

        private static String attributes = "gender,age,smiling,glass,headpose,facequality,blur";
        private static String landmark = "0";

        public static Observable<FaceDetectBean> faceDetect(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            BitmapUtil.compressImg(stream, bitmap);
            byte[] image_file = stream.toByteArray();
            Map<String, RequestBody> map = new HashMap<>();
            if (null != image_file && image_file.length > 0) {
                map.put("image_file\"; filename=\"image_file", RequestBody.create(MediaType.parse("multipart/form-data"), image_file));
            }

            map.put("return_landmark", RequestBody.create(MediaType.parse("multipart/form-data"), landmark));

            if (!TextUtils.isEmpty(attributes)) {
                map.put("return_attributes", RequestBody.create(MediaType.parse("multipart/form-data"), attributes));
            }

            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));

            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_DETECT, map).map(new Function<String, FaceDetectBean>() {
                @Override
                public FaceDetectBean apply(String s) throws Exception {
                    return gson.fromJson(s, FaceDetectBean.class);
                }
            });
        }
    }

    public static class FaceSetImpl {

        public static Observable<String> faceSetCreate(String displayName, String outerId, String tags, String FaceTokens, String userData, int ForceMerge) {

            Map<String, RequestBody> map = new HashMap<>();

            if (!TextUtils.isEmpty(displayName)) {
                map.put("display_name", RequestBody.create(MediaType.parse("multipart/form-data"), displayName));
            }
            if (!TextUtils.isEmpty(outerId)) {
                map.put("outer_id", RequestBody.create(MediaType.parse("multipart/form-data"), outerId));
            }
            if (!TextUtils.isEmpty(tags)) {
                map.put("tags", RequestBody.create(MediaType.parse("multipart/form-data"), tags));
            }
            if (!TextUtils.isEmpty(FaceTokens)) {
                map.put("face_tokens", RequestBody.create(MediaType.parse("multipart/form-data"), FaceTokens));
            }
            if (!TextUtils.isEmpty(userData)) {
                map.put("user_data", RequestBody.create(MediaType.parse("multipart/form-data"), userData));
            }

            map.put("force_merge", RequestBody.create(MediaType.parse("multipart/form-data"), ForceMerge + ""));
            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));

            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_SET_CREATE, map);
        }

        public static Observable<String> faceSetAdd(String faceTokens, String faceSetToken) {

            Map<String, RequestBody> map = new HashMap<>();

            if (!TextUtils.isEmpty(faceTokens)) {
                map.put("face_tokens", RequestBody.create(MediaType.parse("multipart/form-data"), faceTokens));
            }
            if (!TextUtils.isEmpty(faceSetToken)) {
                map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
            }

            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));

            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_SET_ADD, map);
        }

        public static Observable<String> faceSetRemove(String faceTokens, String faceSetToken) {

            Map<String, RequestBody> map = new HashMap<>();

            if (!TextUtils.isEmpty(faceTokens)) {
                map.put("face_tokens", RequestBody.create(MediaType.parse("multipart/form-data"), faceTokens));
            }
            if (!TextUtils.isEmpty(faceSetToken)) {
                map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
            }

            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));

            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_SET_REMOVEFACE, map);
        }

        public static Observable<String> faceSetDetele(String faceSetToken, int checkEmpty) {

            Map<String, RequestBody> map = new HashMap<>();

            map.put("check_empty", RequestBody.create(MediaType.parse("multipart/form-data"), checkEmpty + ""));

            if (!TextUtils.isEmpty(faceSetToken)) {
                map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
            }

            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));

            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_SET_DETELEFACE, map);
        }

        public static Observable<FaceSetDetailBean> getDetailForFaceToken(String faceSetToken) {
            Map<String, RequestBody> map = new HashMap<>();
            if (!TextUtils.isEmpty(faceSetToken)) {
                map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
            }
            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));

            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_SET_GETDETAIL, map).map(new Function<String, FaceSetDetailBean>() {
                @Override
                public FaceSetDetailBean apply(String s) throws Exception {
                    return gson.fromJson(s, FaceSetDetailBean.class);
                }
            });
        }

        /**
         * 获取所有的FaceSet
         * Get all the FaceSetImpl.
         */
        public static Observable<GetFaceSetsBean> getFaceSets() {

            String tags = null;
            Map<String, RequestBody> map = new HashMap<>();
            if (!TextUtils.isEmpty(tags)) {
                map.put("tags", RequestBody.create(MediaType.parse("multipart/form-data"), tags));
            }
            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));

            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_SET_GETALLSET, map).map(new Function<String, GetFaceSetsBean>() {
                @Override
                public GetFaceSetsBean apply(String s) throws Exception {
                    return gson.fromJson(s, GetFaceSetsBean.class);
                }
            });
        }
    }

    public static class FaceSearchImpl {
        /**
         * @param faceToken         与Faceset中人脸比对的face_token
         * @param faceSetToken      Faceset的标识
         * @param returnResultCount 返回比对置信度最高的n个结果，范围[1,5]。默认值为1
         */
        public static Observable<FaceSearchBean> faceSearchForToken(String faceToken, String faceSetToken, int returnResultCount) {
            Map<String, RequestBody> map = new HashMap<>();
            if (!TextUtils.isEmpty(faceToken)) {
                map.put("face_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceToken));
            }
            if (!TextUtils.isEmpty(faceSetToken)) {
                map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
            }
            map.put("return_result_count", RequestBody.create(MediaType.parse("multipart/form-data"), returnResultCount + ""));
            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));
            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_SEARCH, map).map(new Function<String, FaceSearchBean>() {
                @Override
                public FaceSearchBean apply(String s) throws Exception {
                    return gson.fromJson(s, FaceSearchBean.class);
                }
            });
        }
    }

    public static class FaceCompareImpl {
        /**
         * @param face_token1 第一个人脸标识face_token
         * @param face_token2 第二个人脸标识face_token
         */
        public static Observable<FaceCompareBean> compareFace(String face_token1, String face_token2) {
            Map<String, RequestBody> map = new HashMap<>();
            if (!TextUtils.isEmpty(face_token1)) {
                map.put("face_token1", RequestBody.create(MediaType.parse("multipart/form-data"), face_token1));
            }
            if (!TextUtils.isEmpty(face_token2)) {
                map.put("face_token2", RequestBody.create(MediaType.parse("multipart/form-data"), face_token2));
            }
            map.put("api_key", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.KEY));
            map.put("api_secret", RequestBody.create(MediaType.parse("multipart/form-data"), Constant.SECRET));
            return NetWorkUtil.PostObservable(Constant.FACE_BASEURL, Constant.PATH_COMPARE, map).map(new Function<String, FaceCompareBean>() {
                @Override
                public FaceCompareBean apply(String s) throws Exception {
                    return gson.fromJson(s, FaceCompareBean.class);
                }
            });
        }
    }

    public static class FaceLoginImpl {
        public static Observable<FaceLoginBean> faceLogin(String face_token) {
            Map<String, String> map = new HashMap<>();
            map.put("faceId", face_token);
            map.put("sign", Utils.getSign(face_token));
            map.put("eqId", Constant.DEVICE_ID);
            return NetWorkUtil.GetObservable(Constant.NET_BASEURL, Constant.PATH_FACELOGIN, map).map(new Function<String, FaceLoginBean>() {
                @Override
                public FaceLoginBean apply(String s) throws Exception {
                    JSONObject object = new JSONObject(s);
                    String result = object.getString("result");
                    if (result.equals("1")){
                        return gson.fromJson(s,FaceLoginBean.class);
                    }else {
                        return null;
                    }
                }
            });
        }
    }

    public static class FaceRegistImpl {
        public static Observable<FaceRegistBean> faceRegist(String faceId, String customerName, String phoneNum, String birthday, int sex, byte[] photo) {
            Map<String, RequestBody> map = new HashMap<>();
            map.put("faceId", RequestBody.create(MediaType.parse("multipart/form-data"), faceId));
            map.put("customerName", RequestBody.create(MediaType.parse("multipart/form-data"), customerName));
            map.put("phoneNum", RequestBody.create(MediaType.parse("multipart/form-data"), phoneNum));
            map.put("birthday", RequestBody.create(MediaType.parse("multipart/form-data"), birthday));
            map.put("sex", RequestBody.create(MediaType.parse("multipart/form-data"), sex + ""));
            map.put("Photo\"; filename=\"Photo", RequestBody.create(MediaType.parse("multipart/form-data"), photo));
            map.put("sign", RequestBody.create(MediaType.parse("multipart/form-data"), Utils.getSign(faceId)));
            return NetWorkUtil.PostObservable(Constant.NET_BASEURL,Constant.PATH_FACEREGIST,map).map(new Function<String, FaceRegistBean>() {
                @Override
                public FaceRegistBean apply(String s) throws Exception {
                    JSONObject object = new JSONObject(s);
                    String result = object.getString("result");
                    if (result.equals("1")){
                        return gson.fromJson(s,FaceRegistBean.class);
                    }else {
                        return null;
                    }
                }
            });
        }
    }
}
