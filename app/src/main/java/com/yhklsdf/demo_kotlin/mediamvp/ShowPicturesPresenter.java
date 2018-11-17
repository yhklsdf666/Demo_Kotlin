package com.yhklsdf.demo_kotlin.mediamvp;

import android.annotation.SuppressLint;

import com.yhklsdf.demo_kotlin.base.BasePresenter;
import com.yhklsdf.demo_kotlin.bean.Picture;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ShowPicturesPresenter<T extends ShowPicturesContract.View> extends ShowPicturesContract.Present<T>{

    private ShowPicturesContract.model mShowPicturesModel = new IShowPicturesImpl();

    @SuppressLint("CheckResult")
    @Override
    void rxRequestPictures(List<Picture> pictures) {
        mShowPicturesModel.rxRequestPictures(pictures)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if ("success".equals(s)) {
                            mViewRef.get().notifyDataChanged();
                        }
                    }
                });
    }
}
