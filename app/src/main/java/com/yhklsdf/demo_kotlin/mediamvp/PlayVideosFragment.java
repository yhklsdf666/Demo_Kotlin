package com.yhklsdf.demo_kotlin.mediamvp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.adapter.RVVideosAdapter;
import com.yhklsdf.demo_kotlin.base.BaseFragment;
import com.yhklsdf.demo_kotlin.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PlayVideosFragment extends BaseFragment<PlayVideosContract.View, PlayVideosContract.Present<PlayVideosContract.View>> implements PlayVideosContract.View {

    @BindView(R.id.rv_play_videos_container)
    RecyclerView rvPlayVideosContainer;
    @BindView(R.id.srl_play_videos_refresh)
    SmartRefreshLayout srlPlayVideosRefresh;
    private List<VideoBean> mVideos = new ArrayList<>();
    private RVVideosAdapter mVideosAdapter;
    private static PlayVideosFragment intance;
    private static Bundle sBundle;
    private String url;

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
        url = sBundle.getString("url");
        mVideosAdapter = new RVVideosAdapter(mVideos);
        rvPlayVideosContainer.setAdapter(mVideosAdapter);
        rvPlayVideosContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter.rxRequestVideos(url, mVideos);

        srlPlayVideosRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.rxRequestVideos(url, mVideos);
                srlPlayVideosRefresh.finishLoadMore();
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_play_videos;
    }

    @Override
    public void notifyDataChanged() {
        mVideosAdapter.notifyDataSetChanged();
    }
}
