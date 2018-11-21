package com.yhklsdf.demo_kotlin.mediamvp;

import com.yhklsdf.demo_kotlin.base.BasePresenter;
import com.yhklsdf.demo_kotlin.base.IBaseModel;
import com.yhklsdf.demo_kotlin.base.IBaseView;
import com.yhklsdf.demo_kotlin.bean.PictureBean;
import java.util.List;
import io.reactivex.Observable;


public class ShowPicturesContract {

    interface model extends IBaseModel {
        Observable<String> rxRequestPictures(List<PictureBean> pictures, String url);
    }

    interface View extends IBaseView {
        void notifyDataChanged();

        void dismissLAR();
    }

    abstract static class Present<T> extends BasePresenter<T> {
        abstract void rxRequestPictures(List<PictureBean> pictures, String url);
    }
}
