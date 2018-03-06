package com.zjxfyb.whf.facecomparedemo.modle;

import java.util.List;

/**
 * Created by whf on 2017/7/19.
 */

public class GetFaceSetsBean {


    /**
     * time_used : 622
     * facesets : [{"faceset_token":"045b286c5e4a9eeee6c3d89f44057e95","outer_id":"1500377566465","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"8636fbc59c5d8808e44788a181050064","outer_id":"1500377790900","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"a47b05ae3aceb8743a61108c363db86c","outer_id":"1500377794584","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"2ca98ccae63a028b2a6e304cfd93fd61","outer_id":"1500377795768","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"dfe0b710c83d6377bccbe4ed495a7655","outer_id":"1500377800239","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"022418f821adca6ea940607d4f6a0cd7","outer_id":"1500380223062","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"c831c681713f31faaee03d3f654b32ec","outer_id":"1500428528410","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"60fac44dcec8016dac51d6969ee05e07","outer_id":"1500428531008","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"30d1eba964d5e7dceffb7e52a31da939","outer_id":"1500428549654","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"ecb2d6c49fa7d1a17aa7bb5fd3f06a5b","outer_id":"1500428564339","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"7224e58fd456b700721e952176471bf6","outer_id":"1500428695406","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"70e069b1bb8d4b5299dd7186a3c231e0","outer_id":"1500428697884","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"7411df9c11dfd536cb64287213a2b9f7","outer_id":"1500428701342","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"e75761cfdef51c9e14e70a8aa1fa24d6","outer_id":"1500428976290","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"fd27c0da59d7b309c73dd9f9f58acd95","outer_id":"1500428978580","display_name":"帅哥","tags":"faceRegistSet"},{"faceset_token":"2fd7042803bda299019ada0a28ee72bc","outer_id":"1500428981294","display_name":"帅哥","tags":"faceRegistSet"}]
     * request_id : 1500429770,abd4ae21-e19e-40cc-8761-11d451575384
     */

    private int time_used;
    private String request_id;
    private List<FacesetsBean> facesets;

    @Override
    public String toString() {
        return "GetFaceSetsBean{" +
                "time_used=" + time_used +
                ", request_id='" + request_id + '\'' +
                ", facesets=" + facesets +
                '}';
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<FacesetsBean> getFacesets() {
        return facesets;
    }

    public void setFacesets(List<FacesetsBean> facesets) {
        this.facesets = facesets;
    }

    public static class FacesetsBean {
        /**
         * faceset_token : 045b286c5e4a9eeee6c3d89f44057e95
         * outer_id : 1500377566465
         * display_name : 帅哥
         * tags : faceRegistSet
         */

        private String faceset_token;
        private String outer_id;
        private String display_name;
        private String tags;

        @Override
        public String toString() {
            return "FacesetsBean{" +
                    "faceset_token='" + faceset_token + '\'' +
                    ", outer_id='" + outer_id + '\'' +
                    ", display_name='" + display_name + '\'' +
                    ", tags='" + tags + '\'' +
                    '}';
        }

        public String getFaceset_token() {
            return faceset_token;
        }

        public void setFaceset_token(String faceset_token) {
            this.faceset_token = faceset_token;
        }

        public String getOuter_id() {
            return outer_id;
        }

        public void setOuter_id(String outer_id) {
            this.outer_id = outer_id;
        }

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }
    }
}
