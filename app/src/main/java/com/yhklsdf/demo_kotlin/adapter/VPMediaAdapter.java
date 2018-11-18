package com.yhklsdf.demo_kotlin.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.yhklsdf.demo_kotlin.fragment.EmptyFragment;
import com.yhklsdf.demo_kotlin.mediamvp.PlayVideosFragment;
import com.yhklsdf.demo_kotlin.mediamvp.ShowPicturesFragment;

import java.util.ArrayList;
import java.util.List;

public class VPMediaAdapter extends FragmentPagerAdapter {

    private final List<CharSequence> mTitles = new ArrayList<>();
    private final SparseArray<Fragment> mFragments = new SparseArray<>();

    private final static int POSITION_FRAGMENT_PICTURES = 0;
    private final static int POSITION_FRAGMENT_VIDEOS = 1;

    String[] urls = new String[]{
            "https://pixabay.com/en/editors_choice/?media_type=photo&pagi=",
            "https://pixabay.com/en/editors_choice/?media_type=video&pagi="
    };

    public VPMediaAdapter(FragmentManager fm) {
        super(fm);
        // 定义我们想要放两個 fragments as tab，以及他們的位置
        mTitles.add(POSITION_FRAGMENT_PICTURES, "图片");
        mTitles.add(POSITION_FRAGMENT_VIDEOS, "视频");
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("url", urls[i]);
        switch (i) {
            case POSITION_FRAGMENT_PICTURES:
                return ShowPicturesFragment.getIntance(bundle);
            case POSITION_FRAGMENT_VIDEOS:
                return PlayVideosFragment.getIntance(bundle);
        }
        throw new RuntimeException("Unknown type");
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).toString();
    }
}
