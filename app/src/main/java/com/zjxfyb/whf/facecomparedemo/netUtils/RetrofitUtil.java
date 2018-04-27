package com.zjxfyb.whf.facecomparedemo.netUtils;

import android.util.Log;


import com.zjxfyb.whf.facecomparedemo.base.MyApp;

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
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by whf on 2017/7/7.
 */

public class RetrofitUtil {

    private static final String TAG = "RetrofitUtil";
    private Converter.Factory mFactory = null;
    private boolean isHttpsRequest;

    private static class Instance {
        private static RetrofitUtil instance = new RetrofitUtil();
    }

    private RetrofitUtil() {
    }

    public static RetrofitUtil getInstance() {
        return Instance.instance;
    }

    public RetrofitUtil setFactory(Converter.Factory factory) {
        mFactory = factory;
        return this;
    }

    public void setHttpsRequest(boolean httpsRequest) {
        isHttpsRequest = httpsRequest;
    }

    public Retrofit build(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(mFactory == null ? GsonConverterFactory.create() : mFactory)
                .client(isHttpsRequest ? getHttpsClient() : getHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    private OkHttpClient getHttpsClient() {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                //设置网络拦截器
                .addInterceptor(new LoggingInterceptor())
                .sslSocketFactory(getSLLContext().getSocketFactory())
                .build();
        return httpClient;
    }

    private OkHttpClient getHttpClient() {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();
        return httpClient;
    }

    private OkHttpClient getHttpClient2() {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .addInterceptor(new LoggingInterceptor())
                .build();
        return httpClient;
    }

    private SSLContext getSLLContext() {
        SSLContext sslContext = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream certificate = MyApp.getInstance().getAssets().open("gdroot-g2.crt");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            String certificateAlias = Integer.toString(0);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
            sslContext = SSLContext.getInstance("TLS");
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
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

    class LoggingInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "multipart/form-data; boundary=" + getBoundary())
//                    .addHeader("Accept-Encoding", "gzip, deflate")
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

    private String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }

        return sb.toString();
    }

}
