package com.zjxfyb.whf.facecomparedemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.megvii.facepp.sdk.Facepp;
import com.megvii.licensemanager.sdk.LicenseManager;
import com.zjxfyb.whf.facecomparedemo.api.FaceSetImpl;
import com.zjxfyb.whf.facecomparedemo.base.BaseActivity;
import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.conts.Constant;
import com.zjxfyb.whf.facecomparedemo.modle.GetFaceSetsBean;
import com.zjxfyb.whf.facecomparedemo.utils.ConUtil;

import java.util.List;

/**
 * Created by whf on 2017/7/10.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int CAMERA_CODE = 666;
    private final String TAG = this.getClass().getSimpleName();

    private int requestCode = 666;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.face_detect).setOnClickListener(this);
        findViewById(R.id.img_detect).setOnClickListener(this);
//        verifySDK();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.face_detect:
                requestPermission();
                break;
            case R.id.img_detect:
                startActivity(new Intent(this, ChroseActivity.class));
                break;
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
        } else {
            startActivity(new Intent(this, FaceppActivity.class));
            Log.e(TAG, "requestPermission: 权限请求成功");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "请允许权限", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(this, FaceppActivity.class));
                        Log.e(TAG, "requestPermission: 权限请求成功");
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    private void verifySDK() {

        if (Facepp.getSDKAuthType(ConUtil.getFileContent(this, R.raw
                .megviifacepp_0_4_7_model)) == 2) {// 非联网授权
            Log.e(TAG, "verifySDK: 离线SDK认证成功");
            return;
        }

        final LicenseManager licenseManager = new LicenseManager(this);
        licenseManager.setExpirationMillis(Facepp.getApiExpirationMillis(this, ConUtil.getFileContent(this, R.raw
                .megviifacepp_0_4_7_model)));

        String uuid = ConUtil.getUUIDString(this);
        long apiName = Facepp.getApiName();

        licenseManager.setAuthTimeBufferMillis(0);

        licenseManager.takeLicenseFromNetwork(uuid, Constant.KEY, Constant.SECRET, apiName,
                LicenseManager.DURATION_30DAYS, "Landmark", "1", true, new LicenseManager.TakeLicenseCallback() {
                    @Override
                    public void onSuccess() {

                        Log.e(TAG, "onSuccess: 离线SDK认证成功");
                    }

                    @Override
                    public void onFailed(int i, byte[] bytes) {
                        String lastError = licenseManager.getLastError();
                        Log.e(TAG, "onFailed: " + lastError);
                    }
                });
    }


    /**
     * 删除人脸库操作
     */
    private void deleateFaceSets(final Context context) {

        FaceSetImpl.getFaceSets(context, null, new FaceCallBack<GetFaceSetsBean>() {

            @Override
            public void onSuccess(GetFaceSetsBean body) {

                Log.e(TAG, "onSuccess: 查找到faceset集合 ： " + body);

                List<GetFaceSetsBean.FacesetsBean> facesets = body.getFacesets();

                for (GetFaceSetsBean.FacesetsBean bean : facesets) {

                    FaceSetImpl.deleteFaceForFaceSetToken(context, bean.getFaceset_token(), 0, new FaceCallBack<String>() {
                        @Override
                        public void onSuccess(String body) {
                            Log.e(TAG, "onSuccess: 删除人脸库成功 " + body);
                        }

                        @Override
                        public void onFaild(String body) {
                            Log.e(TAG, "onSuccess: 删除人脸库失败 " + body);
                        }
                    });
                }

            }

            @Override
            public void onFaild(String body) {

                Log.e(TAG, "onFaild: 网络异常，没有获取到人脸集合 " + body);


            }
        });
    }
}
