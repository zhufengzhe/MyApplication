package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

/**
 * Created by ddw on 2018/1/6.
 */

public class PermissionUtil {


    /**
     * 查找权限是否已经授权
     *
     * @param activity    所在的activity
     * @param permissions 权限组
     */
    @RequiresApi(api = Build.VERSION_CODES.M)

    public static boolean checkPermission(Activity activity, String[] permissions, int requestCode) {

        boolean isSuccess = true;

        for (String s : permissions) {

            if (activity.checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED) {

                requestPermission(new String[]{s}, activity, requestCode);
                isSuccess = false;
            }
        }

        return isSuccess;
    }

    /**
     * 请求权限
     *
     * @param permissions 权限组
     * @param activity    所在的activity
     */
    public static void requestPermission(String[] permissions, Activity activity, int requestCode) {

        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

}
