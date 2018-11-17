package com.yhklsdf.demo_kotlin.base;

import java.lang.ref.WeakReference;

public class BasePresenter<T> {

    public WeakReference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {
        mViewRef.clear();
    }
}
