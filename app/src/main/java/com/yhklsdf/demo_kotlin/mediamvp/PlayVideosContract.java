package com.yhklsdf.demo_kotlin.mediamvp;

import android.provider.MediaStore;

import com.yhklsdf.demo_kotlin.base.BasePresenter;
import com.yhklsdf.demo_kotlin.base.IBaseModel;
import com.yhklsdf.demo_kotlin.base.IBaseView;
import com.yhklsdf.demo_kotlin.bean.VideoBean;

import java.util.List;

import io.reactivex.Observable;


public class PlayVideosContract {

    interface View extends IBaseView {
        void notifyDataChanged();
    }

    interface Model extends IBaseModel {
        Observable<String> rxRequestVideos(String url, List<VideoBean> videos);
    }

    //此处一定要是static
    abstract static class Present<V> extends BasePresenter<V> {
        abstract void rxRequestVideos(String url, List<VideoBean> videos);
    }
}
