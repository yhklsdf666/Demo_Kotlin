package com.yhklsdf.demo_kotlin.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.adapter.VPMediaAdapter;

import java.util.Objects;

import butterknife.BindView;

public class MediaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        ViewPager mediaViewpager = view.findViewById(R.id.media_viewpager);
        TabLayout mediaTabLayout = view.findViewById(R.id.media_tab_layout);
//        替换actionbar --- 有问题
//        Toolbar mediaToolbar = view.findViewById(R.id.media_toolbar);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Objects.requireNonNull(getActivity()).setActionBar(mediaToolbar);
//        }
        mediaViewpager.setAdapter(new VPMediaAdapter(getChildFragmentManager()));
        mediaTabLayout.setupWithViewPager(mediaViewpager);
        return view;
    }
}
