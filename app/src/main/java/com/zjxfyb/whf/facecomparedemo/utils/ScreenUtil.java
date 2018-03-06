package com.zjxfyb.whf.facecomparedemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

/**
 * Created by whf on 2017/7/12.
 */

public class ScreenUtil {

    public static  int SCREEN_HEIGHT;
    public static  int SCREEN_WIDTH;
    private static final String TAG = ScreenUtil.class.getSimpleName();

    public static Point getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        Log.e(TAG, "getScreenSize: widthPixels : " + widthPixels + ", heightPixels : " + heightPixels);
        return new Point(widthPixels, heightPixels);
    }

    public static int getStatusBarHeight(Context context) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        Log.e(TAG, "状态栏-方法1:" + statusBarHeight);
        return statusBarHeight;
    }

    public static int getScreenContentHeight(Activity activity) {
        //屏幕
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        Log.e("WangJ", "屏幕高:" + dm.heightPixels);

        //应用区域
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
//        Log.e("WangJ", "应用区顶部" + outRect1.top);
//        Log.e("WangJ", "应用区高" + outRect1.height());

        //View绘制区域
        Rect outRect2 = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect2);
//        Log.e("WangJ", "View绘制区域顶部-错误方法：" + outRect2.top);   //不能像上边一样由outRect2.top获取，这种方式获得的top是0，可能是bug吧
        int viewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();   //要用这种方法
//        Log.e("WangJ", "View绘制区域顶部-正确方法：" + viewTop);
//        Log.e("WangJ", "View绘制区域高度：" + outRect2.height());
//        Log.e(TAG, "getScreenContentHeight: " + activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight());

        SCREEN_HEIGHT = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
        SCREEN_WIDTH = dm.widthPixels;
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
    }
}
