package com.yhklsdf.demo_kotlin.mediamvp;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.yhklsdf.demo_kotlin.api.ApiService;
import com.yhklsdf.demo_kotlin.bean.RecycleViewItemBean;
import com.yhklsdf.demo_kotlin.bean.VideoBean;
import com.yhklsdf.demo_kotlin.gson.History;
import com.yhklsdf.demo_kotlin.utils.okHttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Month;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IPlayVideosImpl implements PlayVideosContract.Model {
    private int i = 1;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://apicloud.mob.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    private Calendar mCalendar = Calendar.getInstance();
    private int month = mCalendar.get(Calendar.MONTH) + 1;
    private int day = mCalendar.get(Calendar.DAY_OF_MONTH);
    private String m = month < 10 ? "0" + month : String.valueOf(month);
    private String d = day < 10 ? "0" + day : String.valueOf(day);

    private History mHistory;

    @SuppressLint("CheckResult")
    @Override
    public Observable<String> rxRequestVideos(final String url, final List<RecycleViewItemBean> videos) {
        return Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        try {
                            Document jsoup = Jsoup.connect(url + i++).get();

                            Elements elements = jsoup.select("#content > div:nth-child(2) > div > div > div.item");
                            for (Element element : elements) {
                                VideoBean video = new VideoBean();
                                video.setVideoUrl("https:" + element.select("a > div").attr("data-mp4"));
                                video.setPlaceholderPicture(element.select("a > div > img").attr("src"));
                                videos.add(new RecycleViewItemBean(video, 1));
                            }

                            if (mHistory == null) {
                                String response = okHttpUtils.handleReturn("http://apicloud.mob.com/appstore/history/query?key=292e01a8067e4&day=" + m + d);
                                mHistory = new Gson().fromJson(response, History.class);
                                if (mHistory != null && "success".equals(mHistory.getMsg())) {
                                    for (History.ResultBean resultBean : mHistory.getResult()) {
                                        videos.add(new RecycleViewItemBean(resultBean, 3));
                                    }
                                }
                            }
                            emitter.onNext("success");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
