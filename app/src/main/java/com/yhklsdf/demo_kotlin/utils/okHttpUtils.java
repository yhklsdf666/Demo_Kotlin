package com.yhklsdf.demo_kotlin.utils;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class okHttpUtils {
    private static OkHttpClient client = new OkHttpClient();

    private okHttpUtils() { }

    public static OkHttpClient getInstance() {
        return client;
    }

    public static void handleResponse(String url, okhttp3.Callback callback) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public static String handleReturn(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        return Objects.requireNonNull(client.newCall(request).execute().body()).string();
    }

    //文件下载
    public static void okHttpDownLoad(String url,File file) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response){
                InputStream is = null;
                OutputStream os = null;
                try {
                    long total = response.body().contentLength();
                    is = new BufferedInputStream(response.body().byteStream());
                    os = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] flush = new byte[1024];
                    int len;
                    long current = 0;
                    while ((len = is.read(flush)) != -1) {
                        current += len;
                        os.write(flush,0,len);
                    }
                    os.flush();
                    FileCloseUtil.close(os,is);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    FileCloseUtil.close(os,is);
                }
            }
        });
    }

    public static void okHttpPostJson(String url, String json,okhttp3.Callback callback) {
//        File file = new File(Environment.getExternalStorageDirectory() + "/ic_launcher.png");
//        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
//        multipartBody.addFormDataPart("filename", "png");
//        multipartBody.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));

        RequestBody responseBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        Request request = new Request.Builder().url(url).post(responseBody).build();
        client.newCall(request).enqueue(callback);
    }
}
