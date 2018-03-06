package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.util.Log;
import android.util.SparseArray;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ddw on 2018/1/4.
 */

public class ThreadPoolFactory {


    private static SparseArray<ThreadPoolExecutor> mExecutorSparseArray = new SparseArray<>();
    public static final int LOAD_NET_IMAGE_THREADPOOL = 10010;
    public static final int LOAD_DISK_IMAGE_THREADPOOL = 10086;
    private static long keepAliceTime = 1;
    private static TimeUnit unit = TimeUnit.HOURS;

    private static final String TAG = ThreadPoolFactory.class.getSimpleName();

    private static RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            Log.e(TAG, "rejectedExecution: Runnable --> " + r);
            Log.e(TAG, "rejectedExecution: ThreadPoolExecutor --> " + executor);
        }
    };
    public static int corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;

    public static ThreadPoolExecutor createThreadPool(int poolName) {

        if (mExecutorSparseArray.size() > 0 & null != mExecutorSparseArray.get(poolName)) {
            return mExecutorSparseArray.get(poolName);
        }

        ThreadPoolExecutor executor = null;
        switch (poolName) {
            case LOAD_NET_IMAGE_THREADPOOL:
                executor = new ThreadPoolExecutor(corePoolSize,
                        corePoolSize * 2 + 1,
                        keepAliceTime,
                        unit,
                        new LinkedBlockingQueue<Runnable>(),
                        Executors.defaultThreadFactory(), handler);
                mExecutorSparseArray.put(LOAD_NET_IMAGE_THREADPOOL, executor);
                break;
            case LOAD_DISK_IMAGE_THREADPOOL:
                executor = new ThreadPoolExecutor(corePoolSize,
                        corePoolSize * 2 + 1,
                        keepAliceTime,
                        unit,
                        new LinkedBlockingQueue<Runnable>(),
                        Executors.defaultThreadFactory(), handler);
                mExecutorSparseArray.put(LOAD_NET_IMAGE_THREADPOOL, executor);
                break;
        }

        return executor;
    }
}
