package com.yhklsdf.demo_kotlin.mediamvp;

import android.util.Log;

import com.yhklsdf.demo_kotlin.bean.Picture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class IShowPicturesImpl implements ShowPicturesContract.model {
    private int i = 1;
    String url = "https://pixabay.com/en/editors_choice/?media_type=photo&pagi=";

    @Override
    public Observable<String> rxRequestPictures(final List<Picture> pictures) {
        return Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        final String TAG = "PicturesShowFragment";
                        try {
                            Document jsoup = Jsoup.connect(url + i++).get();
                            Elements elements = jsoup.select("#content > div:nth-child(2) > div > div > div.item");
                            for (Element element : elements) {
                                Picture picture = new Picture();
                                picture.setUrl(element.select("a > img").attr("src"));
                                Log.d(TAG, "run: " + element.attr("style"));
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
