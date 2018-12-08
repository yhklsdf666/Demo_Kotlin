package com.yhklsdf.demo_kotlin.mediamvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.king.view.counterview.CounterView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.adapter.RVAdapter;
import com.yhklsdf.demo_kotlin.base.BaseFragment;
import com.yhklsdf.demo_kotlin.bean.RecycleViewItemBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Thinkad
 */
public class PlayVideosFragment extends BaseFragment<PlayVideosContract.View, PlayVideosContract.Present<PlayVideosContract.View>> implements PlayVideosContract.View {

    @BindView(R.id.rv_play_videos_container)
    RecyclerView rvPlayVideosContainer;
    @BindView(R.id.srl_play_videos_refresh)
    SmartRefreshLayout srlPlayVideosRefresh;
    @BindView(R.id.tv_play_video_month)
    CounterView tvPlayVideoMonth;
    @BindView(R.id.tv_play_video_day)
    CounterView tvPlayVideoDay;
    @BindView(R.id.ll_play_video_date)
    LinearLayout llPlayVideoDate;
    private List<RecycleViewItemBean> mVideos = new ArrayList<>();
    private boolean isFirstLoad = true;
    private RVAdapter mVideosAdapter;
    private static PlayVideosFragment intance;
    private static Bundle sBundle;
    private String url;

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
        initDate();
        url = sBundle.getString("url");
        mVideosAdapter = new RVAdapter(mVideos);
        rvPlayVideosContainer.setAdapter(mVideosAdapter);
        rvPlayVideosContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (isFirstLoad) {
            presenter.rxRequestVideos(url, mVideos);
            srlPlayVideosRefresh.autoRefresh();
            isFirstLoad = false;
        }
        srlPlayVideosRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.rxRequestVideos(url, mVideos);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.rxRequestVideos(url, mVideos);
            }
        });
        rvPlayVideosContainer.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {
                    Toast.makeText(mContext, "滑到顶部", Toast.LENGTH_SHORT).show();
                } else if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(mContext, "滑到底部", Toast.LENGTH_SHORT).show();
                } else if (dy > 0) {
                    if (llPlayVideoDate.getVisibility() == View.VISIBLE) {
                        llPlayVideoDate.setVisibility(View.GONE);
                    }
                } else if (dy < 0) {
                    if (llPlayVideoDate.getVisibility() == View.GONE) {
                        llPlayVideoDate.setVisibility(View.VISIBLE);
                        initDate();
                    }
                }
            }
        });
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        tvPlayVideoMonth.showAnimation(0, month, month * 100);
        tvPlayVideoDay.showAnimation(0, day, day * 100);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_play_videos;
    }

    @Override
    public void notifyDataChanged() {
        mVideosAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLAR() {
        srlPlayVideosRefresh.finishRefresh();
        srlPlayVideosRefresh.finishLoadMore();
    }
}
