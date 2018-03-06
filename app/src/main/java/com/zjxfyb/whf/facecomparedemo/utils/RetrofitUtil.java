package com.zjxfyb.whf.facecomparedemo.utils;

import android.content.Context;
import android.util.Log;

import com.zjxfyb.whf.facecomparedemo.conts.Contents;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by whf on 2017/7/7.
 */

public class RetrofitUtil {

    private static final String TAG = "RetrofitUtil";

    private static RetrofitUtil instance;
    private Retrofit.Builder mBuilder;

    private RetrofitUtil() {

    }

    private Context mContext;

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil();
                }
            }
        }
        return instance;
    }

    public RetrofitUtil creatRetrofit() {

        mBuilder = new Retrofit.Builder()
                .baseUrl(Contents.RegistBASEURL);

        return this;
    }

    public RetrofitUtil addConverterFactory(Converter.Factory factory){

        if (mBuilder != null){
            mBuilder.addConverterFactory(factory);
        }

        return this;
    }

    public RetrofitUtil client(boolean isHttps){

        if (mBuilder != null){
            if (isHttps) {
                mBuilder.client(getHttpsClient());
            }else {
                mBuilder.client(getHttpClient());
            }
        }

        return this;
    }

    public Retrofit build(){

        if (mBuilder != null){
            Retrofit build = mBuilder.build();
            return build;
        }

        return null;
    }

    public Retrofit getStringRetrofit(Context context) {
        mContext = context;
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Contents.BASEURL)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getHttpsClient())
                .build();
        return mRetrofit;
    }

    public Retrofit getObjectRetrofit(Context context) {
        mContext = context;
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Contents.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getHttpsClient())
                .build();
        return mRetrofit;
    }

    /**
     *  https访问
     * @return
     */
    private OkHttpClient getHttpsClient() {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                //设置网络拦截器
                .addInterceptor(new LoggingInterceptor())
                //添加安全认证证书
                .sslSocketFactory(getSLLContext().getSocketFactory())
                .build();
        return httpClient;
    }

    /**
     *  http访问
     * @return
     */
    private OkHttpClient getHttpClient() {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                //设置网络拦截器
                .addInterceptor(new LoggingInterceptor())
                .build();
        return httpClient;
    }

    /**
     *  加载安全证书
     * @return
     */
    private SSLContext getSLLContext() {
        SSLContext sslContext = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream certificate = mContext.getAssets().open("gdroot-g2.crt");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            String certificateAlias = Integer.toString(0);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
            sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;
    }

    /**
     *  网络访问拦截器
     */
    class LoggingInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "multipart/form-data; boundary=" + getBoundary())
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("connection", "Keep-Alive")
                    .addHeader("accept", "*/*")
                    .addHeader("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)")
                    .build();

            long t1 = System.nanoTime();
            Log.d(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            okhttp3.Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d(TAG, String.format("Received response for %s in %.1fms%n%sconnection=%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers(), chain.connection()));

            return response;

        }

    }

    /**
     *  随机生成post请求体中的data分隔符
     * @return
     */
    private String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }

        return sb.toString();
    }
}
