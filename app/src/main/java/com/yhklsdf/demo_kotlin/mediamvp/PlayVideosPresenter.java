package com.yhklsdf.demo_kotlin.mediamvp;

import android.annotation.SuppressLint;

import com.yhklsdf.demo_kotlin.base.BasePresenter;
import com.yhklsdf.demo_kotlin.bean.PictureBean;
import com.yhklsdf.demo_kotlin.bean.VideoBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayVideosPresenter<V extends PlayVideosContract.View> extends PlayVideosContract.Present<V> {

    private PlayVideosContract.Model mIPlayVideosModel = new IPlayVideosImpl();

    @SuppressLint("CheckResult")
    @Override
    void rxRequestVideos(String url, List<VideoBean> videos) {
        mIPlayVideosModel.rxRequestVideos(url, videos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if ("success".equals(s) && mViewRef.get() != null) {
                            mViewRef.get().notifyDataChanged();
                        }
                    }
                });
    }
}
