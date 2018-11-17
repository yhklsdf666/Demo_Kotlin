package com.yhklsdf.demo_kotlin.base;

import java.lang.ref.WeakReference;

public class BasePresenter<V> {

    public WeakReference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {
        mViewRef.clear();
    }
}
