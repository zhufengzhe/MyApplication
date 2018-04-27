package com.zjxfyb.whf.facecomparedemo.netUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zjxfyb.whf.facecomparedemo.R;
import com.zjxfyb.whf.facecomparedemo.base.MyApp;


/**
 * Created by ddw on 2017/9/3.
 */

public class ToaskUtil {

    private static String TAG = "ToaskUtil";

    public static void showTopToast(final String msg) {
        Context context = MyApp.getInstance().getApplicationContext();
        final Toast toast = new Toast(context);
        View view = View.inflate(context, R.layout.toast_layout, null);
        ((TextView) view.findViewById(R.id.tv_msg)).setText(msg);
        toast.setView(view);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }

    public static void showTopToask(Context context, String msg) {
        Toast toast = new Toast(context);
        View view = View.inflate(context, R.layout.toast_layout, null);
        ((TextView) view.findViewById(R.id.tv_msg)).setText(msg);
        toast.setView(view);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showBottomToask(Context context, String msg, boolean b) {
        Toast toast = new Toast(context);
        View view = View.inflate(context, R.layout.toast_layout, null);
        view.setBackground(null);
        view.setPadding(40, 10, 40, 40);
//        view.findViewById(R.id.img).setVisibility(View.GONE);
        TextView textView = (TextView) view.findViewById(R.id.tv_msg);
        textView.setText(msg);
        textView.setLines(1);
        if (b) {
            textView.setTextColor(Color.RED);
        }
        textView.setTextColor(Color.BLUE);
        textView.setTextSize(36);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
