package com.yhklsdf.demo_kotlin.mediamvp;

import com.yhklsdf.demo_kotlin.bean.RecycleViewItemBean;
import com.yhklsdf.demo_kotlin.bean.VideoBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class IPlayVideosImpl implements PlayVideosContract.Model{
    private int i = 1;

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
                            emitter.onNext("success");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
