package com.yhklsdf.demo_kotlin.mediamvp;

import com.yhklsdf.demo_kotlin.base.BasePresenter;
import com.yhklsdf.demo_kotlin.base.IBaseModel;
import com.yhklsdf.demo_kotlin.base.IBaseView;
import com.yhklsdf.demo_kotlin.bean.Picture;
import java.util.List;
import io.reactivex.Observable;


public class ShowPicturesContract {

    interface model extends IBaseModel {
        Observable<String> rxRequestPictures(List<Picture> pictures);
    }

    interface View extends IBaseView {
        void notifyDataChanged();
    }

    abstract static class Present<T> extends BasePresenter<T> {
        abstract void rxRequestPictures(List<Picture> pictures);
    }
}
