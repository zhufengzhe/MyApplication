package com.zjxfyb.whf.facecomparedemo.netUtils;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ddw on 2018/4/14.
 */

public class NetWorkUtil {

    private static final String TAG = NetWorkUtil.class.getSimpleName();

    /**
     *  get请求
     * @param baseurl 接口根地址
     * @param urlpath 路径地址
     * @param map 参数map
     * @param observer 回调
     * @param yClass 要将请求结果转变成的bean类
     * @param <Y> 要将请求结果转变成的bean类
     */
    public static <Y> void Get(String baseurl, String urlpath, Map<String, String> map, NetObserver<Y,String> observer, Class<Y> yClass) {
        NetApi netApi = RetrofitUtil.getInstance().setFactory(ScalarsConverterFactory.create()).build(baseurl).create(NetApi.class);
        Observable<String> observable = netApi.getApi(urlpath, map);
        observer.setClass(yClass);
        setObserver(observable, observer);
    }

    /**
     *  get请求
     * @param baseurl 接口根地址
     * @param urlpath 路径地址
     * @param map 参数map
     */
    public static Observable<String> GetObservable(String baseurl, String urlpath, Map<String, String> map) {
        NetApi netApi = RetrofitUtil.getInstance().setFactory(ScalarsConverterFactory.create()).build(baseurl).create(NetApi.class);
        Observable<String> observable = netApi.getApi(urlpath, map);
        return observable;
//        setObserver(observable, observer);
    }


    /**
     * 普通的表单提交
     * @param baseurl 接口根地址
     * @param urlpath 路径地址
     * @param map 参数map
     * @param observer 回调
     * @param yClass 要将请求结果转变成的bean类
     * @param <Y> 要将请求结果转变成的bean类
     */
    public static <Y> void PostForm(String baseurl, String urlpath, Map<String, Object> map, NetObserver<Y,String> observer, Class<Y> yClass) {
        NetApi netApi = RetrofitUtil.getInstance().setFactory(ScalarsConverterFactory.create()).build(baseurl).create(NetApi.class);
        Observable<String> observable = netApi.postFormApi(urlpath, map);
        observer.setClass(yClass);
        setObserver(observable, observer);
    }

    /**
     *  有上传文件操作用此方法
     * @param baseurl 接口根地址
     * @param urlpath 路径地址
     * @param map 参数map
     * @param observer 回调
     * @param yClass 要将请求结果转变成的bean类
     * @param <Y> 要将请求结果转变成的bean类
     */
    public static <Y> void PostFile(String baseurl, String urlpath, Map<String, RequestBody> map, NetObserver<Y,String> observer, Class<Y> yClass) {
        NetApi netApi = RetrofitUtil.getInstance().setFactory(ScalarsConverterFactory.create()).build(baseurl).create(NetApi.class);
        Observable<String> observable = netApi.postFileApi(urlpath, map);
        observer.setClass(yClass);
        setObserver(observable, observer);
    }

    /**
     *  post请求返回的数据是bitmap的用此方法
     * @param baseurl 接口根地址
     * @param urlpath 路径地址
     * @param body 参数body
     * @param observer 回调
     * @param yClass 要将请求结果转变成的bean类
     * @param <Y> 要将请求结果转变成的bean类
     */
    public static <Y> void Post(String baseurl, String urlpath, RequestBody body, NetObserver<Y, ResponseBody> observer, Class<Y> yClass) {
        NetApi netApi = RetrofitUtil.getInstance().setFactory(ScalarsConverterFactory.create()).build(baseurl).create(NetApi.class);
        Observable<ResponseBody> observable = netApi.postApi(urlpath, body);
        observer.setClass(yClass);
        setObserver(observable, observer);
    }

    public static <Y,T> void setObserver(Observable<T> observable, NetObserver<Y, T> observer) {
        if (observable != null && observer != null) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    public static Observable<String> PostObservable(String baseurl, String urlpath, Map<String, RequestBody> map) {
        NetApi netApi = RetrofitUtil.getInstance().setFactory(ScalarsConverterFactory.create()).build(baseurl).create(NetApi.class);
        Observable<String> observable = netApi.postFileApi(urlpath, map);
        return observable;
    }
}
