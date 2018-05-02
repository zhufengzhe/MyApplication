package com.zjxfyb.whf.facecomparedemo.modle;

/**
 * Created by ddw on 2017/10/26.
 */

public class FaceRegistBean {


    /**
     * result : 1
     * status : 666
     * msg : 注册成功
     * customerId : 10002321
     * outStage : 3
     * recommend : 3
     */

    private String result;
    private String status;
    private String msg;
    private String customerId;
    private String outStage;
    private String recommend;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOutStage() {
        return outStage;
    }

    public void setOutStage(String outStage) {
        this.outStage = outStage;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "FaceRegistBean{" +
                "result='" + result + '\'' +
                ", status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", customerId='" + customerId + '\'' +
                ", outStage='" + outStage + '\'' +
                ", recommend='" + recommend + '\'' +
                '}';
    }
}
