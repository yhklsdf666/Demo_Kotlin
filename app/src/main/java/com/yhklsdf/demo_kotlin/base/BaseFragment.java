package com.yhklsdf.demo_kotlin.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment{

    protected T presenter;
    Unbinder unbinder;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(initLayout(), container,false);
        unbinder = ButterKnife.bind(this,view);
        presenter = createPresenter();
        presenter.attachView((V)this);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
    }

    protected abstract T createPresenter();

    protected abstract void initView();

    protected abstract int initLayout();
}
