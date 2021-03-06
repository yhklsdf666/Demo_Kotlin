package com.yhklsdf.demo_kotlin.mediamvp;

import android.annotation.SuppressLint;

import com.yhklsdf.demo_kotlin.bean.PictureBean;
import com.yhklsdf.demo_kotlin.bean.RecycleViewItemBean;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ShowPicturesPresenter<V extends ShowPicturesContract.View> extends ShowPicturesContract.Present<V>{

    private ShowPicturesContract.model mShowPicturesModel = new IShowPicturesImpl();

    @SuppressLint("CheckResult")
    @Override
    void rxRequestPictures(List<RecycleViewItemBean> pictures, String url) {
        mShowPicturesModel.rxRequestPictures(pictures,url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if ("success".equals(s) && mViewRef.get() != null) {
                            Collections.shuffle(pictures);
                            mViewRef.get().dismissLAR();
                            mViewRef.get().notifyDataChanged();
                        }
                    }
                });
    }
}
