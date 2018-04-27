package com.zjxfyb.whf.facecomparedemo.imageloader;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by ddw on 2017/12/14.
 * <p>
 * 图片加载类
 * <p>
 * 1.从网络中加载图片
 * <p>
 * 2.提供图片二级缓存
 * <p>
 * 3.下载队列
 * <p>
 * 4.线程池
 * <p>
 * <p>
 * 流程：
 * <p>
 * 1.初始化下载线程池和缓存线程池以及相关缓存机制 后台轮询线程
 */

public class ImageLoader {

    private static final int LOAD_NET_IMAGE = 123;

    private static final int LOAD_DISK_IMAGE = 321;

    private static final String TAG = ImageLoader.class.getSimpleName();

    /**
     * 单例索引
     */
    private static ImageLoader mInstance;

    /**
     * 下载线程池
     */
    private ThreadPoolExecutor mDownloadPool;
    /**
     * 缓存线程池
     */
    private ThreadPoolExecutor mDiskloadPool;
    /**
     * 下载请求队列
     */
    private LinkedList<TaskInfo> mDownloadLinked;
    /**
     * 缓存请求队列
     */
    private LinkedList<TaskInfo> mDiskLinked;
    /**
     * 后台轮询线程句柄
     */
    private Handler mBackPoolThreadHandler;
    /**
     * 后台轮询线程
     */
    private Thread mBackPoolThread;

    private Handler mUiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            ImageInfo info = (ImageInfo) msg.obj;
            ImageView imageView = info.getImageView();
            if (imageView.getTag() != null && imageView.getTag().equals(info.getTag())) {

                if (info.getBitmap() != null) {
                    imageView.setImageBitmap(info.getBitmap());
                } else {
                    Toast.makeText(imageView.getContext(), "图片请求失败，地址 --> " + info.getTag(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public enum Type {
        FIFO, LIFO
    }

    private Type mType = Type.LIFO;

    /**
     * 控制下载线程池的信号量
     */
    private Semaphore mDownloadSemaphore = new Semaphore(ThreadPoolFactory.corePoolSize);

    /**
     * 控制本地加载线程池的信号量
     */
    private Semaphore mDiskSemaphore = new Semaphore(ThreadPoolFactory.corePoolSize);

    /**
     * 控制后台轮询线程句柄的的信号量
     */
    private Semaphore mBackPoolThreadHandlerSemaphore = new Semaphore(0);

    private ImageLoader() {

        /**
         *  初始化线程池和队列
         */

        mDownloadPool = ThreadPoolFactory.createThreadPool(ThreadPoolFactory.LOAD_NET_IMAGE_THREADPOOL);

        mDiskloadPool = ThreadPoolFactory.createThreadPool(ThreadPoolFactory.LOAD_DISK_IMAGE_THREADPOOL);

        mDownloadLinked = new LinkedList<>();

        mDiskLinked = new LinkedList<>();

        initBackPoolThread();
    }

    private void initBackPoolThread() {

        mBackPoolThread = new Thread() {
            @Override
            public void run() {

                Looper.prepare();

                if (mBackPoolThreadHandler == null)

                    mBackPoolThreadHandler = new Handler(Looper.myLooper()) {
                        @Override
                        public void handleMessage(Message msg) {

                            try {

                                Log.i(TAG, "handleMessage: 请求信号量");
                                //请求信号量
                                if (LOAD_NET_IMAGE == msg.what)
                                    mDownloadSemaphore.acquire();
                                else
                                    mDiskSemaphore.acquire();

                                Future<?> submit = null;

                                if (LOAD_NET_IMAGE == msg.what) {

                                    Callable callable = getTask(LOAD_NET_IMAGE);

                                    if (callable != null)

                                        submit = mDownloadPool.submit(callable);

                                } else if (LOAD_DISK_IMAGE == msg.what) {

                                    Callable callable = getTask(LOAD_DISK_IMAGE);

                                    if (callable != null)

                                        submit = mDiskloadPool.submit(callable);
                                }

                                if (null != submit) {

                                    submit.get();

                                }

                            } catch (InterruptedException e) {

                                e.printStackTrace();

                            } catch (ExecutionException e) {
                                Log.i(TAG, "handleMessage: 在后台轮询线程中捕获到子任务抛出的异常，异常信息 --> " + e.getMessage());
                                e.printStackTrace();

                            } finally {
                                /**
                                 *  放置在释放信号量之前发生了一场  导致没有释放掉信号量
                                 */
                                //释放信号量
                                if (LOAD_NET_IMAGE == msg.what)
                                    mDownloadSemaphore.release();
                                else
                                    mDiskSemaphore.release();
                                Log.i(TAG, "handleMessage: 释放信号量");
                            }

                        }
                    };
                Log.i(TAG, "run: 初始化后台线程");
                mBackPoolThreadHandlerSemaphore.release();
                Looper.loop();
            }
        };

        mBackPoolThread.start();
    }

    private synchronized Callable getTask(int loadNetImage) {

        TaskInfo mTask = null;

        if (LOAD_NET_IMAGE == loadNetImage) {

            if (Type.FIFO == mType) {
                mTask = mDownloadLinked.removeFirst();
            } else {
                mTask = mDownloadLinked.removeLast();
            }
            Log.i(TAG, "getTask: 获取到一个下载线程， url --> " + mTask.getUrl());
        } else if (LOAD_DISK_IMAGE == loadNetImage) {

            if (Type.FIFO == mType) {
                mTask = mDiskLinked.removeFirst();
            } else {
                mTask = mDiskLinked.removeLast();
            }
            Log.i(TAG, "getTask: 获取到一个本地线程， key --> " + ((LoadDiskImageCallable) mTask.getCallable()).getKey());
        }

        return mTask != null ? mTask.getCallable() : null;
    }

    /**
     * 将请求添加到请求队列中
     */
    public void addTask(String url, ImageView imageView) {

//        Log.i(TAG, "addTask: ");

        if (TextUtils.isEmpty(url)) {

            throw new NullPointerException("图片url为空！！");
        }

        String key = Utils.getMd5(url);

        //imageview的tag为urlmd5值加上时间戳
        String tag = key + System.currentTimeMillis();

        //给imageview设置一个tag
        if (null != imageView)
            imageView.setTag(tag);

        //首先判断内存中是否存在

        Bitmap bitmap = LruCacheUtil.getInstance().getBitmap(key);

        //如果不存在就查看是否存在本地文件
        if (null != bitmap) {

            if (null != imageView) {

                Log.i(TAG, "addTask: 内存中有图片");
                ImageInfo info = new ImageInfo(imageView, tag, bitmap, LoadedFrom.MEMORY_CACHE);
                Message msg = Message.obtain();
                msg.obj = info;
                mUiHandler.sendMessage(msg);
            }

        } else {

            if (null != imageView) {

                if (imageView.getTag() != null) {

                    removeTask(imageView);
                }
            }

            File dir = new File(Environment.getExternalStorageDirectory() + "/diskDir");

            if (!dir.exists()) {
                dir.mkdir();            }

            File file = new File(dir, "/" + key + ".png");

            //决定是启用下载线程池还是缓存线程池
            if (file.exists()) {

                buildDiskImageLoader(imageView, mUiHandler, key, tag);

            } else {

                if (url.startsWith("http")) {

                    buildNetImageLoader(url, imageView, mUiHandler, key, tag);

                } else {

                    buildDiskImageLoader(imageView, mUiHandler, key, tag);
                }
            }
        }

    }

    /**
     * 构建一个网络加载图片的请求
     *
     * @param url
     * @param imageView
     * @param uiHandler
     * @param key
     * @param tag
     */
    private void buildNetImageLoader(String url, ImageView imageView, Handler uiHandler, String key, String tag) {
        Log.i(TAG, "buildNetImageLoader: 构建一个网络加载图片的请求");
        DownloadImageCallable callable = new DownloadImageCallable(url, imageView, uiHandler, key, tag);
        putDownLoadCallable2Task(url, imageView, callable);
    }

    /**
     * 构建一个本地加载图片的请求
     *
     * @param imageView
     * @param uiHandler
     * @param key
     * @param tag
     */
    private void buildDiskImageLoader(ImageView imageView, Handler uiHandler, String key, String tag) {
        Log.i(TAG, "buildDiskImageLoader: 构建一个本地加载图片的请求");
        LoadDiskImageCallable callable = new LoadDiskImageCallable(imageView, uiHandler, key, tag);
        putDiskCallable2Task(imageView, callable);
    }

    /**
     * 将任务提交到队列中，并发送一个消息
     *
     * @param imageView
     * @param callable
     */
    private synchronized void putDiskCallable2Task(ImageView imageView, LoadDiskImageCallable callable) {

        TaskInfo info = new TaskInfo(callable, "", imageView);

        if (mDiskLinked.contains(info)) {

            TaskInfo taskInfo = mDiskLinked.get(mDiskLinked.indexOf(info));

            Log.i(TAG, "removeTask: 已存在下载任务，移除该任务, key --> " + callable.getKey());

            mDiskLinked.remove(taskInfo);

            mDiskLinked.add(info);

        } else {

            Log.i(TAG, "putDownLoadCallable2Task: 添加任务, key --> " + callable.getKey());

            mDiskLinked.add(info);

        }

        if (mBackPoolThreadHandler == null) {

            try {

                mBackPoolThreadHandlerSemaphore.acquire();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        Log.i(TAG, "putDiskCallable2Task: 发送消息，有新任务加入");
        mBackPoolThreadHandler.sendEmptyMessage(LOAD_DISK_IMAGE);
    }

    /**
     * 将任务提交到队列中，并发送一个消息
     *
     * @param url
     * @param imageView
     * @param callable
     */
    private synchronized void putDownLoadCallable2Task(String url, ImageView imageView, DownloadImageCallable callable) {

        TaskInfo info = new TaskInfo(callable, url, imageView);

        if (mDownloadLinked.contains(info)) {

            TaskInfo taskInfo = mDownloadLinked.get(mDownloadLinked.indexOf(info));
            Log.i(TAG, "putDownLoadCallable2Task : 已存在下载任务，移除该任务, url --> " + taskInfo.getUrl());
            mDownloadLinked.remove(taskInfo);

            mDownloadLinked.add(info);

        } else {

            Log.i(TAG, "putDownLoadCallable2Task: 添加任务, url --> " + info.getUrl());

            mDownloadLinked.add(info);
        }

        if (mBackPoolThreadHandler == null) {

            try {

                mBackPoolThreadHandlerSemaphore.acquire();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "putDownLoadCallable2Task: 发送消息，有新任务加入");
        mBackPoolThreadHandler.sendEmptyMessage(LOAD_NET_IMAGE);
    }

    /**
     * 加载url
     * 使用build模式的作用是为了将url和imageview与imageloder分开，虽然imageloader的主要作用是将两者结合起来，保证是一对一的
     * 但是若是请求过多的时候无法确保两者是同步的  除非是放在同一个方法里面，使用build是为了将两着分开，在build里面使两者一一对应起来，而build含有imageloader的引用，可以使用imageloader的添加task的方法直接将两者打包并存放在队列中，这样就保证了两者是一一对应而不会错乱
     * 其次最重要的是可以使imageloader的配置更加丰富，不仅限于默认的配置  还可以在builder里面设置然后应用于imageloader
     *
     * @param url
     * @return
     */
    public static ImageLoaderBuilder load(String url) {

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("图片url地址为空");
        }

        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }

        return new ImageLoaderBuilder(mInstance, url);
    }


    public synchronized void removeTask(ImageView v) {

        TaskInfo info = new TaskInfo(null, "", v);

        if (mDownloadLinked.contains(info)) {
            TaskInfo taskInfo = mDownloadLinked.get(mDownloadLinked.indexOf(info));
            Log.i(TAG, "removeTask: 已存在下载任务，移除该任务, url --> " + taskInfo.getUrl());
            mDownloadLinked.remove(taskInfo);
        }

        if (mDiskLinked.contains(info)) {
            TaskInfo taskInfo = mDiskLinked.get(mDiskLinked.indexOf(info));
            Log.i(TAG, "removeTask: 已存在下载任务，移除该任务, url --> " + taskInfo.getUrl());
            mDiskLinked.remove(taskInfo);
        }
    }
}
