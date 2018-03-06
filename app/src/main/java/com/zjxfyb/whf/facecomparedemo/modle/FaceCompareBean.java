package com.zjxfyb.whf.facecomparedemo.modle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by whf on 2017/7/7.
 */

public class FaceCompareBean {



    /**
     * confidence : 88.884
     * request_id : 1499134457,0712bf55-5ded-4b8a-b2dc-532d11b816e0
     * time_used : 376
     * thresholds : {"1e-3":62.327,"1e-5":73.975,"1e-4":69.101}
     */

    private double confidence;
    private String request_id;
    private int time_used;
    private ThresholdsBean thresholds;

    @Override
    public String toString() {
        return "FaceCompareBean{" +
                "confidence=" + confidence +
                ", request_id='" + request_id + '\'' +
                ", time_used=" + time_used +
                ", thresholds=" + thresholds +
                '}';
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public ThresholdsBean getThresholds() {
        return thresholds;
    }

    public void setThresholds(ThresholdsBean thresholds) {
        this.thresholds = thresholds;
    }

    public static class ThresholdsBean {
        /**
         * 1e-3 : 62.327
         * 1e-5 : 73.975
         * 1e-4 : 69.101
         */

        @SerializedName("1e-3")
        private double _$1e3;
        @SerializedName("1e-5")
        private double _$1e5;
        @SerializedName("1e-4")
        private double _$1e4;

        @Override
        public String toString() {
            return "ThresholdsBean{" +
                    "_$1e3=" + _$1e3 +
                    ", _$1e5=" + _$1e5 +
                    ", _$1e4=" + _$1e4 +
                    '}';
        }

        public double get_$1e3() {
            return _$1e3;
        }

        public void set_$1e3(double _$1e3) {
            this._$1e3 = _$1e3;
        }

        public double get_$1e5() {
            return _$1e5;
        }

        public void set_$1e5(double _$1e5) {
            this._$1e5 = _$1e5;
        }

        public double get_$1e4() {
            return _$1e4;
        }

        public void set_$1e4(double _$1e4) {
            this._$1e4 = _$1e4;
        }
    }
}
