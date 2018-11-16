package com.yhklsdf.demo_kotlin.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageLoader {

    private Context mContext;

    private static ImageLoader mImageLoader = null;

    private ExecutorService mExecutorService;

    private LinkedList<ImageTask> mTaskLinkedList = new LinkedList<>();

    private ImageLoader(Context context) {
        mContext = context;
        //new一个线程池
        mExecutorService = Executors.newFixedThreadPool(5);
    }

    public static synchronized ImageLoader getInstance(Context context) {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(context);
        }
        return mImageLoader;
    }

    /**
     * 描述：
     * 首先我们要判断这个url是否已经在下载列表中
     * <p>
     * 添加到队列
     *
     * @param imageView
     * @param url
     */
    public void loadImage(ImageView imageView, String url) {
        if (isTaskExisted(url)) {
            return;
        }
        ImageTask imageTask = new ImageTask(imageView, url);
        //给list加锁
        synchronized (mTaskLinkedList) {
            mTaskLinkedList.add(imageTask);
        }
        //执行
        mExecutorService.execute(imageTask);
    }

    private boolean isTaskExisted(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        synchronized (mTaskLinkedList) {
            for (int i = 0; i < mTaskLinkedList.size(); i++) {
                ImageTask imageTask = mTaskLinkedList.get(i);
                if (imageTask.getUrl() != null && url.equals(imageTask.getUrl())) {
                    return true;
                }
            }
            return false;
        }
    }

    public void removeTask(ImageTask imageTask) {
        mTaskLinkedList.remove(imageTask);
    }
}

/**
 * 进行下载任务的线程
 */
class ImageTask implements Runnable {

    private ImageView imageView;

    private String url;

    private byte[] imageBytes;

    public ImageTask(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            handler.sendEmptyMessage(0);    //开始下载
            Response response = client.newCall(request).execute();
            imageBytes = response.body().bytes();
            handler.sendEmptyMessage(1);    //下载成功
        } catch (IOException e) {
            e.printStackTrace();
            //说明报异常了
            handler.sendEmptyMessage(-1);   //下载失败
        }
    }

    public String getUrl() {
        return url;
    }

    private Handler handler = new android.os.Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: //若是不用OkHttp用原始方法则可以得到实时下载进度
                    break;
                case 1:
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length,options));
                    break;
                case -1:
                    break;
            }
            ImageLoader.getInstance(imageView.getContext()).removeTask(ImageTask.this);
        }
    };
}
