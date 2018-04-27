package com.zjxfyb.whf.facecomparedemo.modle;

import java.util.List;

/**
 * Created by whf on 2017/7/17.
 */

public class FaceDetectBean {


    /**
     * image_id : RnJfVl3gs7xkh37IhY6Rtw==
     * request_id : 1524794995,65d3228b-e7d0-4ccd-85be-c8adc306e4d5
     * time_used : 551
     * faces : [{"attributes":{"headpose":{"yaw_angle":11.540053,"pitch_angle":-2.440348,"roll_angle":1.1521792},"smile":{"threshold":50,"value":32.761},"gender":{"value":"Male"},"age":{"value":29},"blur":{"blurness":{"threshold":50,"value":8.366},"motionblur":{"threshold":50,"value":8.366},"gaussianblur":{"threshold":50,"value":8.366}},"facequality":{"threshold":70.1,"value":69.785}},"face_rectangle":{"width":822,"top":772,"left":67,"height":822},"face_token":"973d411cd9462fdc908d112dc15029f3"}]
     */

    private String image_id;
    private String request_id;
    private int time_used;
    private List<FacesBean> faces;

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

    @Override
    public String toString() {
        return "FaceDetectBean{" +
                "image_id='" + image_id + '\'' +
                ", request_id='" + request_id + '\'' +
                ", time_used=" + time_used +
                ", faces=" + faces +
                '}';
    }

    public static class FacesBean {
        /**
         * attributes : {"headpose":{"yaw_angle":11.540053,"pitch_angle":-2.440348,"roll_angle":1.1521792},"smile":{"threshold":50,"value":32.761},"gender":{"value":"Male"},"age":{"value":29},"blur":{"blurness":{"threshold":50,"value":8.366},"motionblur":{"threshold":50,"value":8.366},"gaussianblur":{"threshold":50,"value":8.366}},"facequality":{"threshold":70.1,"value":69.785}}
         * face_rectangle : {"width":822,"top":772,"left":67,"height":822}
         * face_token : 973d411cd9462fdc908d112dc15029f3
         */

        private AttributesBean attributes;
        private FaceRectangleBean face_rectangle;
        private String face_token;

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

        @Override
        public String toString() {
            return "FacesBean{" +
                    "attributes=" + attributes +
                    ", face_rectangle=" + face_rectangle +
                    ", face_token='" + face_token + '\'' +
                    '}';
        }

        public static class AttributesBean {
            /**
             * headpose : {"yaw_angle":11.540053,"pitch_angle":-2.440348,"roll_angle":1.1521792}
             * smile : {"threshold":50,"value":32.761}
             * gender : {"value":"Male"}
             * age : {"value":29}
             * blur : {"blurness":{"threshold":50,"value":8.366},"motionblur":{"threshold":50,"value":8.366},"gaussianblur":{"threshold":50,"value":8.366}}
             * facequality : {"threshold":70.1,"value":69.785}
             */

            private HeadposeBean headpose;
            private SmileBean smile;
            private GenderBean gender;
            private AgeBean age;
            private BlurBean blur;
            private FacequalityBean facequality;

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

            @Override
            public String toString() {
                return "AttributesBean{" +
                        "headpose=" + headpose +
                        ", smile=" + smile +
                        ", gender=" + gender +
                        ", age=" + age +
                        ", blur=" + blur +
                        ", facequality=" + facequality +
                        '}';
            }

            public static class HeadposeBean {
                /**
                 * yaw_angle : 11.540053
                 * pitch_angle : -2.440348
                 * roll_angle : 1.1521792
                 */

                private double yaw_angle;
                private double pitch_angle;
                private double roll_angle;

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

                @Override
                public String toString() {
                    return "HeadposeBean{" +
                            "yaw_angle=" + yaw_angle +
                            ", pitch_angle=" + pitch_angle +
                            ", roll_angle=" + roll_angle +
                            '}';
                }
            }

            public static class SmileBean {
                /**
                 * threshold : 50.0
                 * value : 32.761
                 */

                private double threshold;
                private double value;

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

                @Override
                public String toString() {
                    return "SmileBean{" +
                            "threshold=" + threshold +
                            ", value=" + value +
                            '}';
                }
            }

            public static class GenderBean {
                /**
                 * value : Male
                 */

                private String value;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                @Override
                public String toString() {
                    return "GenderBean{" +
                            "value='" + value + '\'' +
                            '}';
                }
            }

            public static class AgeBean {
                /**
                 * value : 29
                 */

                private int value;

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                @Override
                public String toString() {
                    return "AgeBean{" +
                            "value=" + value +
                            '}';
                }
            }

            public static class BlurBean {
                /**
                 * blurness : {"threshold":50,"value":8.366}
                 * motionblur : {"threshold":50,"value":8.366}
                 * gaussianblur : {"threshold":50,"value":8.366}
                 */

                private BlurnessBean blurness;
                private MotionblurBean motionblur;
                private GaussianblurBean gaussianblur;

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

                @Override
                public String toString() {
                    return "BlurBean{" +
                            "blurness=" + blurness +
                            ", motionblur=" + motionblur +
                            ", gaussianblur=" + gaussianblur +
                            '}';
                }

                public static class BlurnessBean {
                    /**
                     * threshold : 50.0
                     * value : 8.366
                     */

                    private double threshold;
                    private double value;

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

                    @Override
                    public String toString() {
                        return "BlurnessBean{" +
                                "threshold=" + threshold +
                                ", value=" + value +
                                '}';
                    }
                }

                public static class MotionblurBean {
                    /**
                     * threshold : 50.0
                     * value : 8.366
                     */

                    private double threshold;
                    private double value;

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

                    @Override
                    public String toString() {
                        return "MotionblurBean{" +
                                "threshold=" + threshold +
                                ", value=" + value +
                                '}';
                    }
                }

                public static class GaussianblurBean {
                    /**
                     * threshold : 50.0
                     * value : 8.366
                     */

                    private double threshold;
                    private double value;

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

                    @Override
                    public String toString() {
                        return "GaussianblurBean{" +
                                "threshold=" + threshold +
                                ", value=" + value +
                                '}';
                    }
                }
            }

            public static class FacequalityBean {
                /**
                 * threshold : 70.1
                 * value : 69.785
                 */

                private double threshold;
                private double value;

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

                @Override
                public String toString() {
                    return "FacequalityBean{" +
                            "threshold=" + threshold +
                            ", value=" + value +
                            '}';
                }
            }
        }

        public static class FaceRectangleBean {
            /**
             * width : 822
             * top : 772
             * left : 67
             * height : 822
             */

            private int width;
            private int top;
            private int left;
            private int height;

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

            @Override
            public String toString() {
                return "FaceRectangleBean{" +
                        "width=" + width +
                        ", top=" + top +
                        ", left=" + left +
                        ", height=" + height +
                        '}';
            }
        }
    }
}
