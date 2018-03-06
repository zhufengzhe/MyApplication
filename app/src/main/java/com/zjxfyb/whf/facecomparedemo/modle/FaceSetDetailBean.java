package com.zjxfyb.whf.facecomparedemo.modle;

import java.util.List;

/**
 * Created by whf on 2017/7/24.
 */

public class FaceSetDetailBean {


    /**
     * faceset_token : 7ec6ca5e42221255fd76862c07b2dfe7
     * tags : faceRegistSet
     * time_used : 379
     * user_data : 最帅的那个
     * display_name : 帅哥
     * face_tokens : ["d785fb4ec42106dd4a35d52bd8933f93"]
     * face_count : 1
     * request_id : 1500888101,295dfbd7-c96e-482a-8a88-3826c1e6e870
     * outer_id : 1500522027230
     */

    private String faceset_token;
    private String tags;
    private int time_used;
    private String user_data;
    private String display_name;
    private int face_count;
    private String request_id;
    private String outer_id;
    private List<String> face_tokens;

    public String getFaceset_token() {
        return faceset_token;
    }

    public void setFaceset_token(String faceset_token) {
        this.faceset_token = faceset_token;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getUser_data() {
        return user_data;
    }

    public void setUser_data(String user_data) {
        this.user_data = user_data;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getFace_count() {
        return face_count;
    }

    public void setFace_count(int face_count) {
        this.face_count = face_count;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getOuter_id() {
        return outer_id;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }

    public List<String> getFace_tokens() {
        return face_tokens;
    }

    public void setFace_tokens(List<String> face_tokens) {
        this.face_tokens = face_tokens;
    }
}
