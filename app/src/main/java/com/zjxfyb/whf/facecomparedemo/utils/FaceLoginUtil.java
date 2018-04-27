package com.zjxfyb.whf.facecomparedemo.utils;

import com.zjxfyb.whf.facecomparedemo.modle.FaceSearchBean;

/**
 * Created by ddw on 2018/4/27.
 */

public class FaceLoginUtil {

    public static boolean isLoginSuccess(FaceSearchBean faceSearchBean) {
        FaceSearchBean.ThresholdsBean thresholds = faceSearchBean.getThresholds();
        double confidence = faceSearchBean.getResults().get(0).getConfidence();
        String face_token = faceSearchBean.getResults().get(0).getFace_token();
        if (!(confidence < thresholds.get_$1e3()) && confidence > thresholds.get_$1e5()) {

//            Message obtain = Message.obtain();
//            Log.e(TAG, "onSuccess: lastFaceID --> " + lastFaceID);
//            Log.e(TAG, "onSuccess: face_token --> " + face_token);
//            Log.e(TAG, "onSuccess: time --> " + (System.currentTimeMillis() - againBeginTime));
//            if (!TextUtils.isEmpty(lastFaceID) && lastFaceID.equals(face_token) && (System.currentTimeMillis() - againBeginTime) < againTime) {
//                obtain.obj = new IsCompareFaceBean(faceBean, face_token, false);
//            } else {
//                obtain.obj = new IsCompareFaceBean(faceBean, face_token, true);
//            }
//            mHandler.sendMessage(obtain);
//
//            Log.e(TAG, "onSuccess: 登陆成功");
            return true;
        } else {

//            Message obtain = Message.obtain();
//            obtain.obj = new IsCompareFaceBean(faceBean, face_token, false);
//            mHandler.sendMessage(obtain);
//
//            Log.e(TAG, "onSuccess: 登录失败");
            return false;
        }
    }
}
