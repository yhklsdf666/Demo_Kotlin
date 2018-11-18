package com.yhklsdf.demo_kotlin.mediamvp;

import android.util.Log;

import com.yhklsdf.demo_kotlin.bean.PictureBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class IShowPicturesImpl implements ShowPicturesContract.model {
    private int i = 1;

    @Override
    public Observable<String> rxRequestPictures(final List<PictureBean> pictures, final String url) {
        return Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        final String TAG = "PicturesShowFragment";
                        try {
                            Document jsoup = Jsoup.connect(url + i++).get();
                            Elements elements = jsoup.select("#content > div:nth-child(2) > div > div > div.item");
                            for (int i = 0; i < 20; i++) {
                                PictureBean picture = new PictureBean();
                                picture.setUrl(elements.get(i).select("a > img").attr("src"));
                                Log.d(TAG, "run: " + elements.get(i).attr("style"));
                                pictures.add(picture);
                            }
                            emitter.onNext("success");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
