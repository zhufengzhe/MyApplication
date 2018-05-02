package com.zjxfyb.whf.facecomparedemo.modle;

/**
 * Created by ddw on 2017/10/26.
 */

public class FaceLoginBean {
    /**
     * result : 1
     * msg : 登录成功
     * customerId : 1
     * discount : 0.97
     * phoneNum : 15669067668
     * recommend : null
     * customerName : 逐风者
     * status : 666
     */

    private String result;
    private String msg;
    private String customerId;
    private String discount;
    private String phoneNum;
    private String recommend;
    private String customerName;
    private String status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FaceLoginBean{" +
                "result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", customerId='" + customerId + '\'' +
                ", discount='" + discount + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", recommend=" + recommend +
                ", customerName='" + customerName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
