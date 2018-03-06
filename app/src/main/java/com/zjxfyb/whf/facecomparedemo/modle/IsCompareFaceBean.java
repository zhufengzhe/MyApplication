package com.zjxfyb.whf.facecomparedemo.modle;

/**
 * Created by whf on 2017/7/24.
 */

public class IsCompareFaceBean {

    private String face_token;

    private boolean isCompare;

    private FaceDetectBean.FacesBean faceBean;

    public IsCompareFaceBean(FaceDetectBean.FacesBean faceBean, String face_token, boolean isCompare) {
        this.face_token = face_token;
        this.isCompare = isCompare;
        this.faceBean = faceBean;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public boolean isCompare() {
        return isCompare;
    }

    public void setCompare(boolean compare) {
        isCompare = compare;
    }

    public FaceDetectBean.FacesBean getFaceBean() {
        return faceBean;
    }

    public void setFaceBean(FaceDetectBean.FacesBean faceBean) {
        this.faceBean = faceBean;
    }

    @Override
    public String toString() {
        return "IsCompareFaceBean{" +
                "face_token='" + face_token + '\'' +
                ", isCompare=" + isCompare +
                '}';
    }
}
