package com.zjxfyb.whf.facecomparedemo.utils;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by whf on 2017/7/10.
 */

public class CameraUtil {

    private static final String TAG = CameraUtil.class.getSimpleName();
    private static CameraUtil instance;
    private int screenWidth;
    private int screenHeight;
    public int mPreviewWidth;
    public int mPreviewHeight;
    private Camera.CameraInfo mInfo;

    private CameraUtil() {
    }

    private int or = 90;

    private Camera mCamera;

    public static CameraUtil getInstance() {
        if (instance == null) {
            synchronized (CameraUtil.class) {
                if (instance == null) {
                    instance = new CameraUtil();
                }
            }
        }

        return instance;
    }

    public void openCamera(int cameraId) {

        if (mCamera == null) {
            mCamera = Camera.open(cameraId);
        }

        mInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, mInfo);
        setCameraParams();

    }

    /**
     * prepear
     *
     * @param textureView
     */
    public void prepear(SurfaceTexture textureView) {
        try {
            if (mCamera != null) {
                mCamera.setPreviewTexture(textureView);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startDetect(Camera.PreviewCallback activity) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(activity);
        }
    }

    public void closeCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    public void getScreenSize(Activity activity) {
        screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = ScreenUtil.getScreenContentHeight(activity);

        setCameraParams();
    }

    private void setCameraParams() {
        Camera.Parameters parameters = mCamera.getParameters();

        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(supportedPreviewSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size o1, Camera.Size o2) {
                return o1.width - o2.width;
            }
        });

        mPreviewWidth = supportedPreviewSizes.get(supportedPreviewSizes.size() - 1).width;
        mPreviewHeight = supportedPreviewSizes.get(supportedPreviewSizes.size() - 1).height;
//        List<Camera.Size> sizes = new ArrayList<>();
//        for (Camera.Size size : supportedPreviewSizes) {
//
//            if (size.height <= screenWidth) {
//                sizes.add(size);
//            }
//        }

//        Collections.sort(sizes, new Comparator<Camera.Size>() {
//            @Override
//            public int compare(Camera.Size o1, Camera.Size o2) {
//                return o1.width - o2.width;
//            }
//        });
//        mPreviewWidth = sizes.get(sizes.size() - 1).width;
//        mPreviewHeight = sizes.get(sizes.size() - 1).height;

        parameters.setPreviewSize(mPreviewWidth, mPreviewHeight);
        mCamera.setParameters(parameters);
    }

    public RelativeLayout.LayoutParams getParams() {

        float scale = mPreviewHeight * 1.0f / mPreviewWidth;

        int width = screenWidth;
        int height = (int) (screenWidth / scale);
       /* if (height > screenHeight){
            height = screenHeight;
            width = (int) (screenHeight * scale);
        }*/

//        Log.e(TAG, "getParams: width:" + width);
//        Log.e(TAG, "getParams: height:" + height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        return params;
    }

    public int getCameraOr(Activity activity) {

        int orientation = activity.getWindowManager().getDefaultDisplay().getOrientation();
        int temp = 0;
        switch (orientation) {
            case Surface.ROTATION_0:
                temp = 0;
                break;
            case Surface.ROTATION_90:
                temp = 90;
                break;
            case Surface.ROTATION_180:
                temp = 180;
                break;
            case Surface.ROTATION_270:
                temp = 270;
                break;
        }

        if (mInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            or = (mInfo.orientation + temp) % 360;
            or = (360 - or) % 360;
        } else {
            or = (360 + mInfo.orientation - temp) % 360;
        }

        return or;
    }

    public void autoFocus() {

        if (mCamera != null) {

            mCamera.cancelAutoFocus();
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    Log.i(TAG, "onAutoFocus: " + success);
                }
            });
        }
    }
}
