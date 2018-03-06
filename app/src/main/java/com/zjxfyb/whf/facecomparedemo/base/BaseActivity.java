package com.zjxfyb.whf.facecomparedemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by whf on 2017/7/19.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp.getRefWatcher().watch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MyApp.getRefWatcher().watch(this);
    }
}
