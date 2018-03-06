package com.zjxfyb.whf.facecomparedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.megvii.facepp.sdk.Facepp;
import com.zjxfyb.whf.facecomparedemo.api.FaceDetectImpl;
import com.zjxfyb.whf.facecomparedemo.api.FaceSearchImpl;
import com.zjxfyb.whf.facecomparedemo.api.FaceSetImpl;
import com.zjxfyb.whf.facecomparedemo.base.BaseActivity;
import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.modle.FaceDetectBean;
import com.zjxfyb.whf.facecomparedemo.modle.FaceSearchBean;
import com.zjxfyb.whf.facecomparedemo.modle.FaceSetDetailBean;
import com.zjxfyb.whf.facecomparedemo.modle.GetFaceSetsBean;
import com.zjxfyb.whf.facecomparedemo.modle.IsCompareFaceBean;
import com.zjxfyb.whf.facecomparedemo.utils.CameraMatrix;
import com.zjxfyb.whf.facecomparedemo.utils.CameraUtil;
import com.zjxfyb.whf.facecomparedemo.utils.ConUtil;
import com.zjxfyb.whf.facecomparedemo.utils.DrawBitmap;
import com.zjxfyb.whf.facecomparedemo.utils.DrawRect;
import com.zjxfyb.whf.facecomparedemo.utils.OpenGLUtil;
import com.zjxfyb.whf.facecomparedemo.utils.SensorEventUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;
import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

/**
 * Created by whf on 2017/7/10.
 */

public class FaceppActivity extends BaseActivity implements GLSurfaceView.Renderer, Camera.PreviewCallback, SurfaceTexture.OnFrameAvailableListener {

    private final String TAG = this.getClass().getSimpleName();

    private GLSurfaceView mGLSurfaceView;

    private ImageView mImageView1, mImageView2;

    private SurfaceTexture mSurfaceTexture;

    private float[] mProjMatrix = new float[16];

    private float[] mVMatrix = new float[16];

    private float[] mMVPMatrix = new float[16];

    private CameraMatrix mCameraMatrix;

    private List<float[]> rectPoints;

    private String lastFaceID;

    private int againTime = 120 * 1000;

    private long againBeginTime;

    private boolean isDeteccFace, hasFaces;
    private Facepp mFacepp;
    private SensorEventUtil mSensorEventUtil;
    private int detection_interval = 25;
    private int min_face_size = 200;
    private int mCameraOr;
    private int mPreviewWidth;
    private int mPreviewHeight;
    private List<GetFaceSetsBean.FacesetsBean> mFacesets;
    private DrawRect mDrawRect;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

            IsCompareFaceBean bean = (IsCompareFaceBean) msg.obj;

            if (mMap.keySet().contains(bean.getFaceBean().getFace_token())) {

                List<IsCompareFaceBean> isCompareFaceBeens = mMap.get(bean.getFaceBean().getFace_token());

                if (null != isCompareFaceBeens) {

                    isCompareFaceBeens.add(bean);

                    Log.e(TAG, "handleMessage: " + isCompareFaceBeens.size());

                    verifyLogin(isCompareFaceBeens);
                }

            } else {

                ArrayList<IsCompareFaceBean> isCompareFaceBeen = new ArrayList<>();

                isCompareFaceBeen.add(bean);

                mMap.put(bean.getFaceBean().getFace_token(), isCompareFaceBeen);

                verifyLogin(isCompareFaceBeen);

            }

        }
    };

    private List<FaceDetectBean.FacesBean> mFaces;

    private void verifyLogin(List<IsCompareFaceBean> isCompareFaceBeens) {

        Log.e(TAG, "verifyLogin: isCompareFaceBeens.size() --> " + isCompareFaceBeens.size());
        if (isCompareFaceBeens.size() == mFacesets.size()) {

            boolean needRegist = true;

            for (IsCompareFaceBean isCompareFaceBean : isCompareFaceBeens) {

                if (isCompareFaceBean.isCompare()) {

                    Log.e(TAG, "handleMessage: 人脸登陆成功,facetoken : " + isCompareFaceBean.getFace_token() + " ,本次耗时：" + (System.currentTimeMillis() - mStartTime));
                    mStartTime = 0;
                    needRegist = false;
                    againBeginTime = System.currentTimeMillis();
                    lastFaceID = isCompareFaceBean.getFace_token();
//                    isCompareFaceBeens.clear();
//                    isDeteccFace = false;
//                    return;
                }

                if (TextUtils.isEmpty(isCompareFaceBean.getFace_token())) {
                    needRegist = false;
                }
            }


            if (needRegist) {
                mStartTime = 0;
                Log.e(TAG, "handleMessage: 人脸登陆失败,facetoken : " + isCompareFaceBeens.get(0).getFace_token() + " ,本次耗时：" + (System.currentTimeMillis() - mStartTime));
                if (!TextUtils.isEmpty(lastFaceID) && lastFaceID.equals(isCompareFaceBeens.get(0).getFace_token())) {

                } else
                    faceRegist(isCompareFaceBeens.get(0).getFaceBean());
            }
//            else {
//
//                Log.e(TAG, "handleMessage: 人脸登陆失败,facetoken : " + isCompareFaceBeens.get(0).getFace_token() + " ,本次耗时：" + (System.currentTimeMillis() - mStartTime));
////                isDeteccFace = false;
//            }

//            isCompareFaceBeens.clear();
        }

        if (mMap.keySet().size() == mFaces.size()) {

            for (Map.Entry<String, List<IsCompareFaceBean>> entrySet : mMap.entrySet()) {

                if (entrySet.getValue().size() != mFacesets.size()) {
                    return;
                }
            }

            Log.e(TAG, "verifyLogin: 一个流程已经结束了");
            mMap.clear();
            isDeteccFace = false;
        }
    }

    private long mStartTime;

    private DrawBitmap mDrawBitmap;

    private Map<String, List<IsCompareFaceBean>> mMap = new HashMap<>();

    private List<float[]> faceRects;

    private int cameraId = CAMERA_FACING_FRONT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facepp);

        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.glSurfaceView);
//        Log.e(TAG, "onCreate: 初始化imageview");
        mImageView1 = (ImageView) findViewById(R.id.opengl_image);
        mImageView2 = (ImageView) findViewById(R.id.opengl_image2);

        mGLSurfaceView.setEGLContextClientVersion(2);//设置使用opengl2.0版本

        mGLSurfaceView.setRenderer(this);//renderer  画笔  用来绘制图像

        // RENDERMODE_CONTINUOUSLY不停渲染
        // RENDERMODE_WHEN_DIRTY懒惰渲染，需要手动调用 glSurfaceView.requestRender() 才会进行更新
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);// 设置渲染器模式
        mGLSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUtil.getInstance().autoFocus();
            }
        });

        rectPoints = new ArrayList<>();
        faceRects = new ArrayList<>();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        // bai色背景色
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1f);
        // 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);
//
        //设置混合模式
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

//        // 启用深度测试
//        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//        // 深度测试类型
//        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
//        // 设置深度缓存
//        GLES20.glClearDepthf(1.0f);

        int textureID = OpenGLUtil.createTextureID();

        mCameraMatrix = new CameraMatrix(textureID);
        mSurfaceTexture = new SurfaceTexture(textureID);
        mDrawBitmap = new DrawBitmap();
        mSurfaceTexture.setOnFrameAvailableListener(this);
        CameraUtil.getInstance().prepear(mSurfaceTexture);
        CameraUtil.getInstance().startDetect(this);
//        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);

        Matrix.frustumM(mProjMatrix, 0, -1, 1, -1, 1, 1, 2);
        //设置相机位置
        Matrix.setLookAtM(mVMatrix, 0, 0, 0, 1.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);//前置为正，后值为负
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        Log.e(TAG, "onDrawFrame: ");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);// 清除屏幕和深度缓存

        gl.glMatrixMode(GL10.GL_PROJECTION);//设置为投影矩阵模式

        gl.glLoadIdentity();//重置

        float[] mtx = new float[16];

        //通过surfacetexture获取到每帧数据的纹理
        mSurfaceTexture.getTransformMatrix(mtx);
        //绘制纹理
        mCameraMatrix.draw(mtx);

        if (rectPoints != null && rectPoints.size() > 0) {

            if (hasFaces) {
                if (mDrawRect == null)
                    mDrawRect = new DrawRect(gl);
//                Log.e(TAG, "onDrawFrame: 检测到的人脸数 ：" + rectPoints.size());

                mDrawRect.setRectPoint(rectPoints);
                mDrawBitmap.setRectPoints(rectPoints);
                rectPoints.clear();
                faceRects.clear();
                mDrawRect.draw(mMVPMatrix);
                mDrawBitmap.drawBitmap(mMVPMatrix);
            }
        }

        mSurfaceTexture.updateTexImage();// 更新image，会调用onFrameAvailable方法
    }

    @Override
    protected void onResume() {

        super.onResume();

        CameraUtil.getInstance().openCamera(cameraId);

        if (cameraId == CAMERA_FACING_BACK) {
            mCameraOr = CameraUtil.getInstance().getCameraOr(this);
        } else {
            mCameraOr = 360 - CameraUtil.getInstance().getCameraOr(this);
        }

//        Log.e(TAG, "onResume: mCameraOr --> " + mCameraOr);

        mGLSurfaceView.onResume();

        initFacepp();

    }

    private void initFacepp() {

        if (mFacepp == null)
            mFacepp = new Facepp();

        String init = mFacepp.init(this, ConUtil.getFileContent(this, R.raw.megviifacepp_0_4_7_model));
        Log.e(TAG, "initFacepp: " + init);

        Facepp.FaceppConfig faceppConfig = mFacepp.getFaceppConfig();

        faceppConfig.interval = detection_interval;

        faceppConfig.minFaceSize = min_face_size;

        int left = 0;

        int top = 0;

        mPreviewWidth = CameraUtil.getInstance().mPreviewWidth;

        mPreviewHeight = CameraUtil.getInstance().mPreviewHeight;

        faceppConfig.roi_left = left;

        faceppConfig.roi_top = top;

        faceppConfig.roi_right = mPreviewWidth;

        faceppConfig.roi_bottom = mPreviewHeight;

        faceppConfig.one_face_tracking = 0;

        String[] array = getResources().getStringArray(R.array.trackig_mode_array);

        String trackModel = "Normal";

        if (trackModel.equals(array[0]))

            faceppConfig.detectionMode = Facepp.FaceppConfig.DETECTION_MODE_TRACKING;

        else if (trackModel.equals(array[1]))

            faceppConfig.detectionMode = Facepp.FaceppConfig.DETECTION_MODE_TRACKING_ROBUST;

        else if (trackModel.equals(array[2]))

            faceppConfig.detectionMode = Facepp.FaceppConfig.DETECTION_MODE_TRACKING_FAST;

        mFacepp.setFaceppConfig(faceppConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraUtil.getInstance().closeCamera();
    }


    int rotation;

    /**
     * @param data   原始数据流
     * @param camera
     */
    @Override
    public void onPreviewFrame(final byte[] data, Camera camera) {

        if (mSensorEventUtil == null) {
            mSensorEventUtil = new SensorEventUtil(FaceppActivity.this);
        }

        int orientation = mSensorEventUtil.orientation;
//        Log.e(TAG, "onPreviewFrame: orientation: " + orientation );
        if (orientation == 0)
            rotation = mCameraOr;
        else if (orientation == 1)
            rotation = 0;
        else if (orientation == 2)
            rotation = 180;
        else if (orientation == 3)
            rotation = 360 - mCameraOr;
        /**
         * 修正图像旋转度
         */

//        Log.e(TAG, "onPreviewFrame: rotation: " + rotation);

        setConfig(rotation);

        final Facepp.Face[] faces = mFacepp.detect(data, mPreviewWidth, mPreviewHeight, Facepp.IMAGEMODE_NV21);

        if (null != faces && faces.length > 0) {

            for (Facepp.Face face : faces) {

                Rect faceRect = new Rect();

                Rect rect = face.rect;

//                Log.e(TAG, "onPreviewFrame: rect" + rect);
                rectPoints.add(polarCoordinates(rect));

                PointF[] points = face.points;

                Arrays.sort(points, new Comparator<PointF>() {
                    @Override
                    public int compare(PointF o1, PointF o2) {
                        return (int) (o1.x - o2.x);
                    }
                });

//                Log.e(TAG, "onPreviewFrame1: " + Arrays.toString(points) );
                faceRect.top = (int) Math.floor(points[0].x);
                faceRect.bottom = (int) Math.ceil(points[points.length - 1].x);

                Arrays.sort(points, new Comparator<PointF>() {
                    @Override
                    public int compare(PointF o1, PointF o2) {
                        return (int) (o1.y - o2.y);
                    }
                });

//                Log.e(TAG, "onPreviewFrame2: " + Arrays.toString(points) );
                faceRect.left = (int) Math.floor(points[0].y);
                faceRect.right = (int) Math.ceil(points[points.length - 1].y);

//                Log.e(TAG, "onPreviewFrame: faceRect" + faceRect);
                faceRects.add(polarCoordinates(faceRect));
            }

            hasFaces = true;
        } else {
            hasFaces = false;
        }


        mGLSurfaceView.requestRender();

        if (hasFaces) {
//            Log.e(TAG, "onPreviewFrame: 有人脸");
            faceDetect(data, camera);
        } else {
//            Log.e(TAG, "onPreviewFrame: 没有人脸");
        }
    }

    private void faceDetect(final byte[] data, final Camera camera) {

        if (!isDeteccFace) {
//            Log.e(TAG, "faceDetect: 检测结束");
            isDeteccFace = true;
        } else {
//            Log.e(TAG, "faceDetect: 正在检测。。。。。");
            return;
        }

        new Thread() {

            @Override
            public void run() {

                mStartTime = System.currentTimeMillis();

                Camera.Size previewSize = camera.getParameters().getPreviewSize();

                YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);

                final ByteArrayOutputStream stream = new ByteArrayOutputStream();

                yuvImage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, stream);

                Bitmap bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
//                    Log.e(TAG, "run: " + bitmap);
                Log.e(TAG, "run: 开始流程");

                bitmap = rotaingImageView(-90, bitmap);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                compressImg(stream, bitmap);

                FaceDetectImpl.faceDetectForByte(FaceppActivity.this, stream.toByteArray(), 0, "gender,age,smiling,glass,headpose,facequality,blur", new FaceCallBack<FaceDetectBean>() {
                    @Override
                    public void onSuccess(FaceDetectBean body) {

                        Log.e(TAG, "onSuccess: 检测人脸" + body);

                        if (null != body) {

                            mFaces = body.getFaces();

                            if (null != mFaces && mFaces.size() > 0) {

                                for (final FaceDetectBean.FacesBean faceBean : mFaces) {
                                    Log.e(TAG, "onSuccess: 有人脸，查找人脸集合");

                                    if (mFacesets != null && mFacesets.size() > 0) {
                                        faceSearch(faceBean);
                                    } else
                                        FaceSetImpl.getFaceSets(FaceppActivity.this, null, new FaceCallBack<GetFaceSetsBean>() {

                                            @Override
                                            public void onSuccess(GetFaceSetsBean body) {

                                                Log.e(TAG, "onSuccess: 查找到faceset集合 ： " + body);

                                                mFacesets = body.getFacesets();

                                                faceSearch(faceBean);
                                            }

                                            @Override
                                            public void onFaild(String body) {

                                                Log.e(TAG, "onFaild: 网络异常，没有获取到人脸集合 " + body);

                                                isDeteccFace = false;

                                            }
                                        });
                                }
                            } else {
                                Log.e(TAG, "onSuccess: 人脸为空");
                                isDeteccFace = false;
                            }
                        } else {
                            Log.e(TAG, "onSuccess: 人脸检测失败");
                            isDeteccFace = false;
                        }
                    }

                    @Override
                    public void onFaild(String body) {
                        Log.e(TAG, "onFaild: 人脸检测失败" + body);
                        isDeteccFace = false;
                    }
                });
            }
        }.start();

    }

    private void faceSearch(final FaceDetectBean.FacesBean faceBean) {

        if (null != mFacesets && mFacesets.size() > 0) {

            for (final GetFaceSetsBean.FacesetsBean faceset : mFacesets) {

                final String faceset_token = faceset.getFaceset_token();

                Log.e(TAG, "onSuccess: 有人脸集合，检测人脸集合中是否有相似人脸");

                FaceSearchImpl.faceSearchForToken(FaceppActivity.this, faceBean.getFace_token(), null, null, faceset_token, 1, new FaceCallBack<FaceSearchBean>() {
                    @Override
                    public void onSuccess(final FaceSearchBean faceSearchBean) {

                        Log.e(TAG, "onSuccess: 人脸查询成功" + faceSearchBean);

                        FaceSearchBean.ThresholdsBean thresholds = faceSearchBean.getThresholds();
                        double confidence = faceSearchBean.getResults().get(0).getConfidence();
                        String face_token = faceSearchBean.getResults().get(0).getFace_token();
                        if (!(confidence < thresholds.get_$1e3()) && confidence > thresholds.get_$1e5()) {

                            Message obtain = Message.obtain();
                            Log.e(TAG, "onSuccess: lastFaceID --> " + lastFaceID);
                            Log.e(TAG, "onSuccess: face_token --> " + face_token);
                            Log.e(TAG, "onSuccess: time --> " + (System.currentTimeMillis() - againBeginTime));
                            if (!TextUtils.isEmpty(lastFaceID) && lastFaceID.equals(face_token) && (System.currentTimeMillis() - againBeginTime) < againTime) {
                                obtain.obj = new IsCompareFaceBean(faceBean, face_token, false);
                            } else {
                                obtain.obj = new IsCompareFaceBean(faceBean, face_token, true);
                            }
                            mHandler.sendMessage(obtain);

                            Log.e(TAG, "onSuccess: 登陆成功");
                        } else {

                            Message obtain = Message.obtain();
                            obtain.obj = new IsCompareFaceBean(faceBean, face_token, false);
                            mHandler.sendMessage(obtain);

                            Log.e(TAG, "onSuccess: 登录失败");
                        }
                    }

                    @Override
                    public void onFaild(String body) {

                        Message obtain = Message.obtain();
                        obtain.obj = new IsCompareFaceBean(faceBean, "", false);
                        mHandler.sendMessage(obtain);

                        Log.e(TAG, "onFaild: 人脸查询失败" + body);
                    }
                });

            }

        } else {
            faceRegist(faceBean);
        }
    }

    private void faceRegist(final FaceDetectBean.FacesBean faceBean) {

        FaceDetectBean.FacesBean.AttributesBean attributes = faceBean.getAttributes();

        if (null != attributes) {

            FaceDetectBean.FacesBean.AttributesBean.HeadposeBean headpose = attributes.getHeadpose();

            if (null != headpose && Math.abs(headpose.getYaw_angle()) <= 30 && Math.abs(headpose.getPitch_angle()) <= 30 && Math.abs(headpose.getRoll_angle()) <= 30) {
                Log.e(TAG, "onSuccess: 偏移量正常，检测是否符合人脸对比");

                if (attributes.getFacequality().getValue() > attributes.getFacequality().getThreshold() && attributes.getFacequality().getValue() >= 80) {

                    Log.e(TAG, "run: 符合人脸对比要求，开始查找是否和人脸集合");
                    if (null != mFacesets && mFacesets.size() > 0) {

                        for (final GetFaceSetsBean.FacesetsBean faceSetBean : mFacesets) {

                            FaceSetImpl.getDetailForFaceToken(FaceppActivity.this, faceSetBean.getFaceset_token(), new FaceCallBack<FaceSetDetailBean>() {

                                @Override
                                public void onSuccess(FaceSetDetailBean body) {

                                    Log.e(TAG, "getDetailForFaceToken.onSuccess: " + body);

                                    if (body.getFace_count() <= 1000) {

                                        FaceSetImpl.addFaceByFaceToken(FaceppActivity.this, faceBean.getFace_token(), faceSetBean.getFaceset_token(), new FaceCallBack<String>() {

                                            @Override
                                            public void onSuccess(String body) {

                                                Log.e(TAG, "onSuccess: 注册成功" + body.toString());
//                                            isDeteccFace = false;
                                            }

                                            @Override
                                            public void onFaild(String body) {

//                                            isDeteccFace = false;
                                                Log.e(TAG, "onFaild: " + body);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFaild(String body) {

//                                isDeteccFace = false;
                                    Log.e(TAG, "getDetailForFaceToken.onFaild: " + body);
                                }
                            });
                        }

                    } else
                        FaceSetImpl.faceSetCreat(FaceppActivity.this, "帅哥", "" + System.currentTimeMillis(), "faceRegistSet", faceBean.getFace_token(), "最帅的那个", 1, new FaceCallBack<String>() {
                            @Override
                            public void onSuccess(String body) {
                                Log.e(TAG, "onSuccess: 注册成功 " + body);
                                isDeteccFace = false;
                            }

                            @Override
                            public void onFaild(String body) {
                                isDeteccFace = false;
                                Log.e(TAG, "onFaild: 注册失败 " + body);
                            }
                        });

                } else {
                    Log.e(TAG, "onSuccess: 人脸相似度较低,不符合人脸对比要求");
                    isDeteccFace = false;
                }
            } else {
                Log.e(TAG, "onSuccess: 偏移量过大");
                isDeteccFace = false;
            }

        } else {
            Log.e(TAG, "onSuccess: faceBean.getAttributes()为空");
            isDeteccFace = false;
        }
    }

    private void polarCoordinates(int left, int top, int width, int height) {

        float[] rectPoint = new float[3 * 4];

        int count = 0;
        for (int i = 0; i < rectPoint.length; i++) {
            switch (i % 3) {
                case 0:
                    switch (count % 4) {
                        case 0:
                            rectPoint[i] = left;
                            break;
                        case 1:
                            rectPoint[i] = left;
                            break;
                        case 2:
                            rectPoint[i] = left + width;
                            break;
                        case 3:
                            rectPoint[i] = left + width;
                            break;
                    }
                    break;
                case 1:
                    switch (count % 4) {
                        case 0:
                            rectPoint[i] = top;
                            break;
                        case 1:
                            rectPoint[i] = top + height;
                            break;
                        case 2:
                            rectPoint[i] = top + height;
                            break;
                        case 3:
                            rectPoint[i] = top;
                            break;
                    }
                    break;
                case 2:
                    rectPoint[i] = 0f;
                    count++;
                    break;
            }
        }

//        Log.e(TAG, "polarCoordinates: 联网请求到的数据" + Arrays.toString(rectPoint));
    }

    /**
     * 将face++计算的人脸框转换成系统界面上的人脸坐标
     *
     * @param rect
     * @return
     */
    private float[] polarCoordinates(Rect rect) {

        float[] rectPoint = new float[3 * 4];

        int count = 0;

        for (int i = 0; i < rectPoint.length; i++) {
            switch (i % 3) {
                case 0:
                    switch (count % 4) {
                        case 0:
                            rectPoint[i] = mPreviewHeight - rect.bottom;
                            break;
                        case 1:
                            rectPoint[i] = mPreviewHeight - rect.bottom;
                            break;
                        case 2:
                            rectPoint[i] = mPreviewHeight - rect.top;
                            break;
                        case 3:
                            rectPoint[i] = mPreviewHeight - rect.top;
                            break;
                    }
                    break;
                case 1:
                    switch (count % 4) {
                        case 0:
                            rectPoint[i] = mPreviewWidth - rect.right;
                            break;
                        case 1:
                            rectPoint[i] = mPreviewWidth - rect.left;
                            break;
                        case 2:
                            rectPoint[i] = mPreviewWidth - rect.left;
                            break;
                        case 3:
                            rectPoint[i] = mPreviewWidth - rect.right;
                            break;
                    }
                    break;
                case 2:
                    rectPoint[i] = 0f;
                    count++;
                    break;
            }
        }

//        Log.e(TAG, "polarCoordinates: " + Arrays.toString(rectPoint));
        return rectPoint;

    }

    private void compressImg(ByteArrayOutputStream stream, Bitmap bitmap) {
        Log.e(TAG, "compressImg: 压缩图片");
        int i = 100;
        while (stream.size() > 1024 * 1024 / 4) {
            i -= 5;
            if (i < 0) {
                break;
            }
            stream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, i, stream);
//            Log.e(TAG, "compressImg: i = " + i + " ; stream.size = " + stream.size());
        }

        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * surfacetexture 获取到帧数据时回调
     *
     * @param surfaceTexture
     */
    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
//        mGLSurfaceView.requestRender();
    }

    /*
   * 旋转图片
   * @param angle
   * @param bitmap
   * @return Bitmap
   */
    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.postRotate(angle);
//        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    private void setConfig(int rotation) {
        Facepp.FaceppConfig faceppConfig = mFacepp.getFaceppConfig();
        if (faceppConfig.rotation != rotation) {
//            Log.e(TAG, "setConfig: rotation : " + rotation);
            faceppConfig.rotation = rotation;
            mFacepp.setFaceppConfig(faceppConfig);
        }
    }

}
