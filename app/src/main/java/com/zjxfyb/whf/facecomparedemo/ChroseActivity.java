package com.zjxfyb.whf.facecomparedemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zjxfyb.whf.facecomparedemo.api.FaceCompareImpl;
import com.zjxfyb.whf.facecomparedemo.base.BaseActivity;
import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.modle.FaceCompareBean;

import java.io.ByteArrayOutputStream;

;

public class ChroseActivity extends BaseActivity implements View.OnClickListener {

    private static final int IMAGE_SWITCH_CODE1 = 666;
    private static final int IMAGE_SWITCH_CODE2 = 888;
    private static final String TAG = "ChroseActivity";
    private ImageView mImageView, mImageView2;
    private Button mButton;
    private Bitmap mBitmap1, mBitmap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrose);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView2 = (ImageView) findViewById(R.id.imageView2);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mImageView2.setOnClickListener(this);

    }



    private void requestPermission(int requestCode) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        } else {
            checkImage(requestCode);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                requestPermission(IMAGE_SWITCH_CODE1);
                break;
            case R.id.imageView2:
                requestPermission(IMAGE_SWITCH_CODE2);
                break;
            case R.id.button:
                if (mBitmap1 != null && mBitmap2 != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                                mBitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
                                mBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
                                FaceCompareImpl.compareFace(ChroseActivity.this, null, null, stream1.toByteArray(), null, null, null, stream2.toByteArray(), null, new FaceCallBack<FaceCompareBean>() {
                                    @Override
                                    public void onSuccess(FaceCompareBean body) {
                                        Log.i(TAG, "onSuccess: " + body);
                                    }

                                    @Override
                                    public void onFaild(String body) {
                                        Log.i(TAG, "onFaild: " + body);
                                    }
                                });

//                                FaceDetectImpl.faceDetectForByte(ChroseActivity.this, stream1.toByteArray(), 1, "gender,age,smiling,glass,headpose,facequality,blur", new FaceCallBack<FaceDetectBean>() {
//                                    @Override
//                                    public void onSuccess(FaceDetectBean body) {
//                                        Log.e(TAG, "onSuccess: 检测成功 ：" + body );
//                                    }
//
//                                    @Override
//                                    public void onFaild(String body) {
//                                        Log.e(TAG, "onFaild: 检测失败 : " + body );
//                                    }
//                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }
                break;
        }
    }

    private void checkImage(int requestCode) {
        Log.i(TAG, "checkImage: ");
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);

//        // 调用系统的相冊
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                "image/*");
//
//        // 调用剪切功能
//        startActivityForResult(intent, requestCode);


//       // 激活系统图库，选择一张图片
//       Intent intent = new Intent(Intent.ACTION_PICK);
//       intent.setType("image/*");
//       // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
//       startActivityForResult(intent, requestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: 调用成功");
        if (requestCode == IMAGE_SWITCH_CODE1 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                mBitmap1 = BitmapFactory.decodeFile(picturePath);
                mImageView.setImageBitmap(mBitmap1);

        } else if (requestCode == IMAGE_SWITCH_CODE2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            mBitmap2 = BitmapFactory.decodeFile(picturePath);
            mImageView2.setImageBitmap(mBitmap2);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case IMAGE_SWITCH_CODE1:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(ChroseActivity.this, "请允许权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                checkImage(IMAGE_SWITCH_CODE1);
                break;
            case IMAGE_SWITCH_CODE2:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(ChroseActivity.this, "请允许权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                checkImage(IMAGE_SWITCH_CODE2);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

}
