package com.yhklsdf.demo_kotlin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.yhklsdf.demo_kotlin.bean.Picture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PicturesShowFragment extends Fragment {

    @BindView(R.id.srl_show_picture_refresh)
    SmartRefreshLayout srlShowPictureRefresh;
    Unbinder unbinder;
    private List<Picture> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RVPicturesAdapter mPicturesRVAdapter;
    private String url = "https://pixabay.com/en/editors_choice/?media_type=photo&pagi=";
    private int i = 1;

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (srlShowPictureRefresh != null) {
                        srlShowPictureRefresh.finishLoadMore();
                        srlShowPictureRefresh.finishRefresh();
                    }
                    mPicturesRVAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_pictures, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_show_pictures_container);
        FlexboxLayoutManager manager = new FlexboxLayoutManager(getActivity());
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);
        manager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        initList();
        mPicturesRVAdapter = new RVPicturesAdapter(list);
        recyclerView.setAdapter(mPicturesRVAdapter);
        unbinder = ButterKnife.bind(this, view);

        srlShowPictureRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                initList();
                srlShowPictureRefresh.finishRefresh(3000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlShowPictureRefresh.finishLoadMore(3000);
            }
        });
        return view;
    }

    private void initList() {
        new Thread(new Runnable() {
            private static final String TAG = "PicturesShowFragment";
            @Override
            public void run() {
                try {
                    Document jsoup = Jsoup.connect(url + i++).get();
                    Elements elements = jsoup.select("#content > div:nth-child(2) > div > div > div.item");
                    for (Element element : elements) {
                        Picture picture = new Picture();
                        picture.setUrl(element.select("a > img").attr("src"));
                        Log.d(TAG, "run: " + element.attr("style"));
                        list.add(picture);
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
//            mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            }
//        })
}