package com.yhklsdf.demo_kotlin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.adapter.VPPictureBrowsingAdapter;
import com.yhklsdf.demo_kotlin.anim.DepthPageTransformer;
import com.yhklsdf.demo_kotlin.bean.PictureBean;
import com.yhklsdf.demo_kotlin.view.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureBrowsingActivity extends AppCompatActivity {

    @BindView(R.id.vp_picture_browsing)
    ViewPagerFixed vpPictureBrowsing;
    @BindView(R.id.tv_picture_browsing_count)
    TextView tvPictureBrowsingCount;
    @BindView(R.id.tv_picture_browsing_save)
    TextView tvPictureBrowsingSave;

    private List<PictureBean> mPictures;
    private int position;
    private VPPictureBrowsingAdapter mAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_browsing);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        mPictures =  intent.getParcelableArrayListExtra("pictures");
        position = intent.getIntExtra("position", 0);

        mAdapter = new VPPictureBrowsingAdapter(this, mPictures);
        vpPictureBrowsing.setAdapter(mAdapter);
        vpPictureBrowsing.setCurrentItem(position);
        tvPictureBrowsingCount.setText((position + 1) + "/" + mPictures.size());
        vpPictureBrowsing.setPageTransformer(true, new DepthPageTransformer());
        vpPictureBrowsing.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                tvPictureBrowsingCount.setText((i + 1) + "/" + mPictures.size());
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick(R.id.tv_picture_browsing_save)
    public void onViewClicked() {

    }
}
