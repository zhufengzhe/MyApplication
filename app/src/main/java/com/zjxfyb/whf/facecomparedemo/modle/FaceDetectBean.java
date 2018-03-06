package com.zjxfyb.whf.facecomparedemo.modle;

import java.util.List;

/**
 * Created by whf on 2017/7/17.
 */

public class FaceDetectBean {


    /**
     * image_id : QjkewG1m8Y83qI90GtTxfg==
     * request_id : 1500283832,2f34be8d-aa76-4609-8ab7-2e3a25c64cd6
     * time_used : 728
     * faces : [{"attributes":{"glass":{"value":"None"},"headpose":{"yaw_angle":11.493495,"pitch_angle":-8.427331,"roll_angle":-0.88286},"smile":{"threshold":30.1,"value":1.678},"gender":{"value":"Male"},"age":{"value":21},"blur":{"blurness":{"threshold":50,"value":0.218},"motionblur":{"threshold":50,"value":0.218},"gaussianblur":{"threshold":50,"value":0.218}},"facequality":{"threshold":70.1,"value":71.316}},"face_rectangle":{"width":824,"top":634,"left":55,"height":824},"face_token":"15c4f2cc4fbc4c73d1803704b8818508"}]
     */

    private String image_id;
    private String request_id;
    private int time_used;
    private List<FacesBean> faces;

    @Override
    public String toString() {
        return "FaceDetectBean{" +
                "image_id='" + image_id + '\'' +
                ", request_id='" + request_id + '\'' +
                ", time_used=" + time_used +
                ", faces=" + faces +
                '}';
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
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

    public List<FacesBean> getFaces() {
        return faces;
    }

    public void setFaces(List<FacesBean> faces) {
        this.faces = faces;
    }

    public static class FacesBean {
        /**
         * attributes : {"glass":{"value":"None"},"headpose":{"yaw_angle":11.493495,"pitch_angle":-8.427331,"roll_angle":-0.88286},"smile":{"threshold":30.1,"value":1.678},"gender":{"value":"Male"},"age":{"value":21},"blur":{"blurness":{"threshold":50,"value":0.218},"motionblur":{"threshold":50,"value":0.218},"gaussianblur":{"threshold":50,"value":0.218}},"facequality":{"threshold":70.1,"value":71.316}}
         * face_rectangle : {"width":824,"top":634,"left":55,"height":824}
         * face_token : 15c4f2cc4fbc4c73d1803704b8818508
         */

        private AttributesBean attributes;
        private FaceRectangleBean face_rectangle;
        private String face_token;

        @Override
        public String toString() {
            return "FacesBean{" +
                    "attributes=" + attributes +
                    ", face_rectangle=" + face_rectangle +
                    ", face_token='" + face_token + '\'' +
                    '}';
        }

        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public FaceRectangleBean getFace_rectangle() {
            return face_rectangle;
        }

        public void setFace_rectangle(FaceRectangleBean face_rectangle) {
            this.face_rectangle = face_rectangle;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public static class AttributesBean {
            /**
             * glass : {"value":"None"}
             * headpose : {"yaw_angle":11.493495,"pitch_angle":-8.427331,"roll_angle":-0.88286}
             * smile : {"threshold":30.1,"value":1.678}
             * gender : {"value":"Male"}
             * age : {"value":21}
             * blur : {"blurness":{"threshold":50,"value":0.218},"motionblur":{"threshold":50,"value":0.218},"gaussianblur":{"threshold":50,"value":0.218}}
             * facequality : {"threshold":70.1,"value":71.316}
             */

            private GlassBean glass;
            private HeadposeBean headpose;
            private SmileBean smile;
            private GenderBean gender;
            private AgeBean age;
            private BlurBean blur;
            private FacequalityBean facequality;

            @Override
            public String toString() {
                return "AttributesBean{" +
                        "glass=" + glass +
                        ", headpose=" + headpose +
                        ", smile=" + smile +
                        ", gender=" + gender +
                        ", age=" + age +
                        ", blur=" + blur +
                        ", facequality=" + facequality +
                        '}';
            }

            public GlassBean getGlass() {
                return glass;
            }

            public void setGlass(GlassBean glass) {
                this.glass = glass;
            }

            public HeadposeBean getHeadpose() {
                return headpose;
            }

            public void setHeadpose(HeadposeBean headpose) {
                this.headpose = headpose;
            }

            public SmileBean getSmile() {
                return smile;
            }

            public void setSmile(SmileBean smile) {
                this.smile = smile;
            }

            public GenderBean getGender() {
                return gender;
            }

            public void setGender(GenderBean gender) {
                this.gender = gender;
            }

            public AgeBean getAge() {
                return age;
            }

            public void setAge(AgeBean age) {
                this.age = age;
            }

            public BlurBean getBlur() {
                return blur;
            }

            public void setBlur(BlurBean blur) {
                this.blur = blur;
            }

            public FacequalityBean getFacequality() {
                return facequality;
            }

            public void setFacequality(FacequalityBean facequality) {
                this.facequality = facequality;
            }

            public static class GlassBean {
                /**
                 * value : None
                 */

                private String value;

                @Override
                public String toString() {
                    return "GlassBean{" +
                            "value='" + value + '\'' +
                            '}';
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class HeadposeBean {
                /**
                 * yaw_angle : 11.493495
                 * pitch_angle : -8.427331
                 * roll_angle : -0.88286
                 */

                private double yaw_angle;
                private double pitch_angle;
                private double roll_angle;

                @Override
                public String toString() {
                    return "HeadposeBean{" +
                            "yaw_angle=" + yaw_angle +
                            ", pitch_angle=" + pitch_angle +
                            ", roll_angle=" + roll_angle +
                            '}';
                }

                public double getYaw_angle() {
                    return yaw_angle;
                }

                public void setYaw_angle(double yaw_angle) {
                    this.yaw_angle = yaw_angle;
                }

                public double getPitch_angle() {
                    return pitch_angle;
                }

                public void setPitch_angle(double pitch_angle) {
                    this.pitch_angle = pitch_angle;
                }

                public double getRoll_angle() {
                    return roll_angle;
                }

                public void setRoll_angle(double roll_angle) {
                    this.roll_angle = roll_angle;
                }
            }

            public static class SmileBean {
                /**
                 * threshold : 30.1
                 * value : 1.678
                 */

                private double threshold;
                private double value;

                @Override
                public String toString() {
                    return "SmileBean{" +
                            "threshold=" + threshold +
                            ", value=" + value +
                            '}';
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }

                public double getValue() {
                    return value;
                }

                public void setValue(double value) {
                    this.value = value;
                }
            }

            public static class GenderBean {
                /**
                 * value : Male
                 */

                private String value;

                @Override
                public String toString() {
                    return "GenderBean{" +
                            "value='" + value + '\'' +
                            '}';
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class AgeBean {
                /**
                 * value : 21
                 */

                private int value;

                @Override
                public String toString() {
                    return "AgeBean{" +
                            "value=" + value +
                            '}';
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class BlurBean {
                /**
                 * blurness : {"threshold":50,"value":0.218}
                 * motionblur : {"threshold":50,"value":0.218}
                 * gaussianblur : {"threshold":50,"value":0.218}
                 */

                private BlurnessBean blurness;
                private MotionblurBean motionblur;
                private GaussianblurBean gaussianblur;

                @Override
                public String toString() {
                    return "BlurBean{" +
                            "blurness=" + blurness +
                            ", motionblur=" + motionblur +
                            ", gaussianblur=" + gaussianblur +
                            '}';
                }

                public BlurnessBean getBlurness() {
                    return blurness;
                }

                public void setBlurness(BlurnessBean blurness) {
                    this.blurness = blurness;
                }

                public MotionblurBean getMotionblur() {
                    return motionblur;
                }

                public void setMotionblur(MotionblurBean motionblur) {
                    this.motionblur = motionblur;
                }

                public GaussianblurBean getGaussianblur() {
                    return gaussianblur;
                }

                public void setGaussianblur(GaussianblurBean gaussianblur) {
                    this.gaussianblur = gaussianblur;
                }

                public static class BlurnessBean {
                    /**
                     * threshold : 50.0
                     * value : 0.218
                     */

                    private double threshold;
                    private double value;

                    @Override
                    public String toString() {
                        return "BlurnessBean{" +
                                "threshold=" + threshold +
                                ", value=" + value +
                                '}';
                    }

                    public double getThreshold() {
                        return threshold;
                    }

                    public void setThreshold(double threshold) {
                        this.threshold = threshold;
                    }

                    public double getValue() {
                        return value;
                    }

                    public void setValue(double value) {
                        this.value = value;
                    }
                }

                public static class MotionblurBean {
                    /**
                     * threshold : 50.0
                     * value : 0.218
                     */

                    private double threshold;
                    private double value;

                    @Override
                    public String toString() {
                        return "MotionblurBean{" +
                                "threshold=" + threshold +
                                ", value=" + value +
                                '}';
                    }

                    public double getThreshold() {
                        return threshold;
                    }

                    public void setThreshold(double threshold) {
                        this.threshold = threshold;
                    }

                    public double getValue() {
                        return value;
                    }

                    public void setValue(double value) {
                        this.value = value;
                    }
                }

                public static class GaussianblurBean {
                    /**
                     * threshold : 50.0
                     * value : 0.218
                     */

                    private double threshold;
                    private double value;


                    @Override
                    public String toString() {
                        return "GaussianblurBean{" +
                                "threshold=" + threshold +
                                ", value=" + value +
                                '}';
                    }

                    public double getThreshold() {
                        return threshold;
                    }

                    public void setThreshold(double threshold) {
                        this.threshold = threshold;
                    }

                    public double getValue() {
                        return value;
                    }

                    public void setValue(double value) {
                        this.value = value;
                    }
                }
            }

            public static class FacequalityBean {
                /**
                 * threshold : 70.1
                 * value : 71.316
                 */

                private double threshold;
                private double value;

                @Override
                public String toString() {
                    return "FacequalityBean{" +
                            "threshold=" + threshold +
                            ", value=" + value +
                            '}';
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }

                public double getValue() {
                    return value;
                }

                public void setValue(double value) {
                    this.value = value;
                }
            }
        }

        public static class FaceRectangleBean {
            /**
             * width : 824
             * top : 634
             * left : 55
             * height : 824
             */

            private int width;
            private int top;
            private int left;
            private int height;

            @Override
            public String toString() {
                return "FaceRectangleBean{" +
                        "width=" + width +
                        ", top=" + top +
                        ", left=" + left +
                        ", height=" + height +
                        '}';
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
