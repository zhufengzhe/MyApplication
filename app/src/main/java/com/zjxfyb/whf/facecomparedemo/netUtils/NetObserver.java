package com.zjxfyb.whf.facecomparedemo.netUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.zjxfyb.whf.facecomparedemo.R;
import com.zjxfyb.whf.facecomparedemo.base.MyApp;

import org.json.JSONException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by ddw on 2018/4/14.
 */

public abstract class NetObserver<Y,T> implements Observer<T> {
    
    
    private Class<Y> mClass;

    public void setClass(Class<Y> aClass) {
        mClass = aClass;
    }

    public Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
     public void onNext(T t) {
        Y y = null;
        if (t instanceof String){
            Gson gson = new Gson();
            y = gson.fromJson((String) t, mClass);
        }else if (mClass == Bitmap.class && t instanceof ResponseBody){
            try {
                byte[] bytes = ((ResponseBody) t).bytes();
                y = (Y) BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
        }

        onSuccess(y);
    }

    public abstract void onSuccess(Y y);

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFaild(e);
    }

    public abstract void onFaild(Throwable e);

    @Override
    public void onComplete() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToaskUtil.showTopToask(MyApp.getInstance().getApplicationContext(),MyApp.getInstance().getApplicationContext().getResources().getString(R.string.connect_error));
                break;
            case CONNECT_TIMEOUT:
                ToaskUtil.showTopToask(MyApp.getInstance().getApplicationContext(),MyApp.getInstance().getApplicationContext().getResources().getString(R.string.connect_timeout));
                break;
            case BAD_NETWORK:
                ToaskUtil.showTopToask(MyApp.getInstance().getApplicationContext(),MyApp.getInstance().getApplicationContext().getResources().getString(R.string.bad_network));
                break;
            case PARSE_ERROR:
                ToaskUtil.showTopToask(MyApp.getInstance().getApplicationContext(),MyApp.getInstance().getApplicationContext().getResources().getString(R.string.parse_error));
                break;
            case UNKNOWN_ERROR:
                ToaskUtil.showTopToask(MyApp.getInstance().getApplicationContext(),MyApp.getInstance().getApplicationContext().getResources().getString(R.string.unknown_error));
            default:
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
