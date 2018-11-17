package com.yhklsdf.demo_kotlin.mediamvp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.adapter.RVPicturesAdapter;
import com.yhklsdf.demo_kotlin.base.BaseFragment;
import com.yhklsdf.demo_kotlin.bean.Picture;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShowPicturesFragment extends BaseFragment<ShowPicturesContract.View, ShowPicturesPresenter<ShowPicturesContract.View>> implements ShowPicturesContract.View {

    RVPicturesAdapter mPicturesRVAdapter;
    @BindView(R.id.rv_show_pictures_container)
    RecyclerView rvShowPicturesContainer;
    @BindView(R.id.srl_show_picture_refresh)
    SmartRefreshLayout srlShowPictureRefresh;
    List<Picture> mPictures = new ArrayList<>();

    @Override
    protected ShowPicturesPresenter<ShowPicturesContract.View> createPresenter() {
        return new ShowPicturesPresenter<>();
    }

    @Override
    protected void initView() {
        FlexboxLayoutManager manager = new FlexboxLayoutManager(getActivity());
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);
        manager.setJustifyContent(JustifyContent.SPACE_BETWEEN);

        rvShowPicturesContainer.setLayoutManager(manager);
        mPicturesRVAdapter = new RVPicturesAdapter(mPictures);
        rvShowPicturesContainer.setAdapter(mPicturesRVAdapter);

        presenter.rxRequestPictures(mPictures);
        srlShowPictureRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (srlShowPictureRefresh != null) {
                    presenter.rxRequestPictures(mPictures);
                    srlShowPictureRefresh.finishLoadMore();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (srlShowPictureRefresh != null) {
                    presenter.rxRequestPictures(mPictures);
                    srlShowPictureRefresh.finishRefresh();
                }
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_show_pictures;
    }

    @Override
    public void notifyDataChanged() {
//        mList更新了，但是mList指向了新的引用，所以调用notifyDataSetChanged无效
//        mPictures.clear();
//        mPictures.addAll(pictures);
        mPicturesRVAdapter.notifyDataSetChanged();
    }
}
