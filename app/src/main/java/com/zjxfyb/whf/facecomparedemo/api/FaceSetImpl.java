package com.zjxfyb.whf.facecomparedemo.api;

import android.content.Context;
import android.text.TextUtils;

import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.conts.Contents;
import com.zjxfyb.whf.facecomparedemo.modle.FaceSetDetailBean;
import com.zjxfyb.whf.facecomparedemo.modle.GetFaceSetsBean;
import com.zjxfyb.whf.facecomparedemo.utils.RetrofitUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by whf on 2017/7/8.
 */

public class FaceSetImpl {
    /**
     * 创建一个人脸的集合FaceSet，用于存储人脸标识face_token。一个FaceSet能够存储1,000个face_token。
     * Create a face collection called FaceSet to store face_token. One FaceSet can hold up to 1000 face_token
     *
     * @param context     上下文
     * @param displayName 人脸集合的名字，256个字符，不能包括字符^@,&=*'"
     *                    The name of FaceSet. No more than 256 characters, and must not contain characters ^@,&=*'"
     * @param outerId     账号下全局唯一的FaceSet自定义标识，可以用来管理FaceSet对象。最长255个字符，不能包括字符^@,&=*'"
     *                    Custom unique id of Faceset under your account, used for managing FaceSet objects.
     *                    No more than 255 characters, and must not contain characters ^@,&=*'"
     * @param tags        FaceSet自定义标签组成的字符串，用来对FaceSet分组。最长255个字符，多个tag用逗号分隔，
     *                    每个tag不能包括字符^@,&=*'"
     *                    String consists of FaceSet custom tags, used for categorizing FaceSet, comma-seperated.
     *                    No more than 255 characters, and must not contain characters ^@,&=*'"
     * @param FaceTokens  人脸标识face_token，可以是一个或者多个，用逗号分隔。最多不超过5个face_token
     *                    One or more face_token, comma-seperated. The number of face_token must not be larger than 5.
     * @param userData    自定义用户信息，不大于16KB，不能包括字符^@,&=*'"
     *                    Custom user information. No larger than 16KB, and must not contain characters ^@,&=*'"
     * @param ForceMerge  在传入outer_id的情况下，如果outer_id已经存在，是否将face_token加入已经存在的FaceSet中
     *                    0：不将face_tokens加入已存在的FaceSet中，直接返回FACESET_EXIST错误
     *                    1：将face_tokens加入已存在的FaceSet中
     *                    默认值为0
     *                    Determine whether or not add face_token into existing FaceSet, if outer_id is passed and outer_id already exists.
     *                    0: face_tokens will not be added into existing FaceSet, and return FACESET_EXIST error message instead.
     *                    1: add face_tokens into existing FaceSet.
     *                    The default value is 0.
     * @param callBack    回调
     */
    public static void faceSetCreat(Context context, String displayName, String outerId, String tags, String FaceTokens, String userData, int ForceMerge, final FaceCallBack<String> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getStringRetrofit(context).create(FaceSetApi.class);
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

        Call<String> stringCall = faceSetApi.facaSetCreat(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * 为一个已经创建的FaceSet添加人脸标识face_token。一个FaceSet最多存储1,000个face_token。
     * Add face_token into an existing FaceSet. One FaceSet can hold up to 1000 face_token.
     *
     * @param context      上下文
     * @param faceTokens   人脸标识face_token组成的字符串，可以是一个或者多个，用逗号分隔。最多不超过5个face_token
     *                     One or more face_token, comma-seperated. The number of face_token must not be larger than 5.
     * @param faceSetToken FaceSet的标识 可以是faceSetToken
     *                     The id of Faceset.
     * @param callBack     回调
     */
    public static void addFaceByFaceToken(Context context, String faceTokens, String faceSetToken, final FaceCallBack<String> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getStringRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(faceTokens)) {
            map.put("face_tokens", RequestBody.create(MediaType.parse("multipart/form-data"), faceTokens));
        }
        if (!TextUtils.isEmpty(faceSetToken)) {
            map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
        }
        Call<String> stringCall = faceSetApi.facaSetAddFace(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * 为一个已经创建的FaceSet添加人脸标识face_token。一个FaceSet最多存储1,000个face_token。
     * Add face_token into an existing FaceSet. One FaceSet can hold up to 1000 face_token.
     *
     * @param context    上下文
     * @param faceTokens 人脸标识face_token组成的字符串，可以是一个或者多个，用逗号分隔。最多不超过5个face_token
     *                   One or more face_token, comma-seperated. The number of face_token must not be larger than 5.
     * @param outer_id   FaceSet的标识
     *                   The id of Faceset.
     * @param callBack   回调
     */
    public static void addFaceByOuterId(Context context, String faceTokens, String outer_id, final FaceCallBack<String> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getStringRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();
        if (!TextUtils.isEmpty(faceTokens)) {
            map.put("face_tokens", RequestBody.create(MediaType.parse("multipart/form-data"), faceTokens));
        }
        if (!TextUtils.isEmpty(outer_id)) {
            map.put("outer_id", RequestBody.create(MediaType.parse("multipart/form-data"), outer_id));
        }
        Call<String> stringCall = faceSetApi.facaSetAddFace(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * @param faceSetToken 用户提供的FaceSet标识
     *                     User-defined id of Faceset.
     * @param checkEmpty   删除时是否检查FaceSet中是否存在face_token，默认值为1
     *                     0：不检查
     *                     1：检查
     *                     如果设置为1，当FaceSet中存在face_token则不能删除
     *                     Check if the FaceSet contains face_token when deleting.
     *                     0: do not check
     *                     1: check
     *                     The default value is 1.
     *                     If the value is 1, when the FaceSet contains face_token, it cannot be deleted.
     */
    public static void deleteFaceForFaceSetToken(Context context, String faceSetToken, int checkEmpty, final FaceCallBack<String> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getStringRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();

        map.put("check_empty", RequestBody.create(MediaType.parse("multipart/form-data"), checkEmpty + ""));

        if (!TextUtils.isEmpty(faceSetToken)) {
            map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
        }
        Call<String> stringCall = faceSetApi.facaSetDeteleFace(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * @param outer_id   用户提供的FaceSet标识
     *                   User-defined id of Faceset.
     * @param checkEmpty 删除时是否检查FaceSet中是否存在face_token，默认值为1
     *                   0：不检查
     *                   1：检查
     *                   如果设置为1，当FaceSet中存在face_token则不能删除
     *                   Check if the FaceSet contains face_token when deleting.
     *                   0: do not check
     *                   1: check
     *                   The default value is 1.
     *                   If the value is 1, when the FaceSet contains face_token, it cannot be deleted.
     */
    public static void deleteFaceForOuterId(Context context, String outer_id, int checkEmpty, final FaceCallBack<String> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getStringRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();

        map.put("check_empty", RequestBody.create(MediaType.parse("multipart/form-data"), checkEmpty + ""));

        if (!TextUtils.isEmpty(outer_id)) {
            map.put("outer_id", RequestBody.create(MediaType.parse("multipart/form-data"), outer_id));
        }
        Call<String> stringCall = faceSetApi.facaSetDeteleFace(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * 获取一个FaceSet的所有信息
     * Get details about a FaceSet.
     *
     * @param faceSetToken FaceSet的标识
     *                     The id of Faceset
     */
    public static void getDetailForFaceToken(Context context, String faceSetToken, final FaceCallBack<FaceSetDetailBean> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();

        if (!TextUtils.isEmpty(faceSetToken)) {
            map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
        }
        Call<FaceSetDetailBean> stringCall = faceSetApi.getDetail(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<FaceSetDetailBean>() {
            @Override
            public void onResponse(Call<FaceSetDetailBean> call, Response<FaceSetDetailBean> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FaceSetDetailBean> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * 获取一个FaceSet的所有信息
     * Get details about a FaceSet.
     *
     * @param outer_id FaceSet的标识
     *                 The id of Faceset
     */
    public static void getDetailForOuterId(Context context, String outer_id, final FaceCallBack<FaceSetDetailBean> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();

        if (!TextUtils.isEmpty(outer_id)) {
            map.put("outer_id", RequestBody.create(MediaType.parse("multipart/form-data"), outer_id));
        }
        Call<FaceSetDetailBean> stringCall = faceSetApi.getDetail(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<FaceSetDetailBean>() {
            @Override
            public void onResponse(Call<FaceSetDetailBean> call, Response<FaceSetDetailBean> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FaceSetDetailBean> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * 获取所有的FaceSet
     * Get all the FaceSet.
     *
     * @param tags 包含需要查询的FaceSet标签的字符串，用逗号分隔
     *             Tags of the FaceSet to be searched, comma-seperated
     */
    public static void getFaceSets(Context context, String tags, final FaceCallBack<GetFaceSetsBean> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getObjectRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();

        if (!TextUtils.isEmpty(tags)) {
            map.put("tags", RequestBody.create(MediaType.parse("multipart/form-data"), tags));
        }
        Call<GetFaceSetsBean> stringCall = faceSetApi.getFaceSets(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<GetFaceSetsBean>() {
            @Override
            public void onResponse(Call<GetFaceSetsBean> call, Response<GetFaceSetsBean> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetFaceSetsBean> call, Throwable t) {
                t.printStackTrace();
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * 更新一个人脸集合的属性
     * Update the attributes of a FaceSet.
     *
     * @param faceSetToken FaceSet的标识
     *                     The id of Faceset.
     * @param newOuterId   在api_key下全局唯一的FaceSet自定义标识，可以用来管理FaceSet对象。最长255个字符，不能包括字符^@,&=*'"
     *                     Custom unique id of Faceset under your account, used for managing FaceSet objects.
     *                     No more than 255 characters, and must not contain characters ^@,&=*'"
     * @param displayName  人脸集合的名字，256个字符
     *                     The name of FaceSet. No more than 256 characters, and must not contain characters ^@,&=*'"
     * @param userData     自定义用户信息，不大于16KB, 1KB=1024B
     *                     Custom user information. No larger than 16KB, and must not contain characters ^@,&=*'"
     * @param tags         FaceSet自定义标签组成的字符串，用来对FaceSet分组。最长255个字符，多个tag用逗号分隔，每个tag不能包括字符^@,&=*'"
     *                     String consists of FaceSet custom tags, used for categorizing FaceSet, comma-seperated.
     *                     No more than 255 characters, and must not contain characters ^@,&=*'"
     */
    public static void updataForFaceSetToken(Context context, String faceSetToken, String newOuterId, String displayName, String userData, String tags, final FaceCallBack<String> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getStringRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();

        if (!TextUtils.isEmpty(faceSetToken)) {
            map.put("faceset_token", RequestBody.create(MediaType.parse("multipart/form-data"), faceSetToken));
        }
        if (!TextUtils.isEmpty(newOuterId)) {
            map.put("new_outer_id", RequestBody.create(MediaType.parse("multipart/form-data"), newOuterId));
        }
        if (!TextUtils.isEmpty(displayName)) {
            map.put("display_name", RequestBody.create(MediaType.parse("multipart/form-data"), displayName));
        }
        if (!TextUtils.isEmpty(userData)) {
            map.put("user_data", RequestBody.create(MediaType.parse("multipart/form-data"), userData));
        }
        if (!TextUtils.isEmpty(tags)) {
            map.put("tags", RequestBody.create(MediaType.parse("multipart/form-data"), tags));
        }
        Call<String> stringCall = faceSetApi.update(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

    /**
     * 更新一个人脸集合的属性
     * Update the attributes of a FaceSet.
     *
     * @param outer_id FaceSet的标识
     *                     The id of Faceset.
     * @param newOuterId   在api_key下全局唯一的FaceSet自定义标识，可以用来管理FaceSet对象。最长255个字符，不能包括字符^@,&=*'"
     *                     Custom unique id of Faceset under your account, used for managing FaceSet objects.
     *                     No more than 255 characters, and must not contain characters ^@,&=*'"
     * @param displayName  人脸集合的名字，256个字符
     *                     The name of FaceSet. No more than 256 characters, and must not contain characters ^@,&=*'"
     * @param userData     自定义用户信息，不大于16KB, 1KB=1024B
     *                     Custom user information. No larger than 16KB, and must not contain characters ^@,&=*'"
     * @param tags         FaceSet自定义标签组成的字符串，用来对FaceSet分组。最长255个字符，多个tag用逗号分隔，每个tag不能包括字符^@,&=*'"
     *                     String consists of FaceSet custom tags, used for categorizing FaceSet, comma-seperated.
     *                     No more than 255 characters, and must not contain characters ^@,&=*'"
     * @param tags
     */
    public static void updataForOuterId(Context context, String outer_id, String newOuterId, String displayName, String userData, String tags, final FaceCallBack<String> callBack) {

        FaceSetApi faceSetApi = RetrofitUtil.getInstance().getStringRetrofit(context).create(FaceSetApi.class);
        Map<String, RequestBody> map = new HashMap<>();

        if (!TextUtils.isEmpty(outer_id)) {
            map.put("outer_id", RequestBody.create(MediaType.parse("multipart/form-data"), outer_id));
        }
        if (!TextUtils.isEmpty(newOuterId)) {
            map.put("new_outer_id", RequestBody.create(MediaType.parse("multipart/form-data"), newOuterId));
        }
        if (!TextUtils.isEmpty(displayName)) {
            map.put("display_name", RequestBody.create(MediaType.parse("multipart/form-data"), displayName));
        }
        if (!TextUtils.isEmpty(userData)) {
            map.put("user_data", RequestBody.create(MediaType.parse("multipart/form-data"), userData));
        }
        if (!TextUtils.isEmpty(tags)) {
            map.put("tags", RequestBody.create(MediaType.parse("multipart/form-data"), tags));
        }
        Call<String> stringCall = faceSetApi.update(RequestBody.create(MediaType.parse("multipart/form-data"), Contents.KEY), RequestBody.create(MediaType.parse("multipart/form-data"), Contents.SECRET), map);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    try {
                        callBack.onFaild(response.errorBody().string());
                    } catch (IOException e) {
                        callBack.onFaild(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.onFaild(t.getMessage());
            }
        });
    }

}
