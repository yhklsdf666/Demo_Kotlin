package com.yhklsdf.demo_kotlin.mediamvp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.base.BaseFragment;
import com.yhklsdf.demo_kotlin.bean.VideoBean;

import java.util.List;

import butterknife.BindView;

public class PlayVideosFragment extends BaseFragment<PlayVideosContract.View, PlayVideosContract.Present<PlayVideosContract.View>> implements PlayVideosContract.View {

    @BindView(R.id.rv_play_videos_container)
    RecyclerView rvPlayVideosContainer;
    @BindView(R.id.srl_play_videos_refresh)
    SmartRefreshLayout srlPlayVideosRefresh;
    private List<VideoBean> mVideos;
    private static PlayVideosFragment intance;
    private static Bundle sBundle;

    @SuppressLint("ValidFragment")
    private PlayVideosFragment() { }

    public static synchronized PlayVideosFragment getIntance(Bundle bundle) {
        if (intance == null) {
            intance = new PlayVideosFragment();
            sBundle = bundle;
        }
        return intance;
    }

    @Override
    protected PlayVideosContract.Present<PlayVideosContract.View> createPresenter() {
        return new PlayVideosPresenter<>();
    }

    @Override
    protected void initView() {
        String url = sBundle.getString("url");

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_play_videos;
    }

    @Override
    public void notifyDataChanged() {

    }
}
