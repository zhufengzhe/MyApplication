package com.zjxfyb.whf.facecomparedemo.base;

import android.app.Application;
import android.util.Config;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import static android.content.ContentValues.TAG;

/**
 * Created by whf on 2017/7/19.
 */

public class MyApp extends Application {

    private static MyApp instance;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        Log.e(TAG, "onCreate: 初始化leakcanary");
        mRefWatcher = LeakCanary.install(this);
        // Normal app init code...

        if (!Config.DEBUG){
            Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程
        }
    }
    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e(TAG, "捕获异常：" + ex.getMessage());
            ex.printStackTrace();        }
    };
    public static MyApp getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

}
