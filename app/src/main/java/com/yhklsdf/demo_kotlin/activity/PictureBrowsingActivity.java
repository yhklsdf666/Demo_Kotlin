package com.yhklsdf.demo_kotlin.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.adapter.GlideApp;
import com.yhklsdf.demo_kotlin.adapter.VPPictureBrowsingAdapter;
import com.yhklsdf.demo_kotlin.anim.DepthPageTransformer;
import com.yhklsdf.demo_kotlin.bean.PictureBean;
import com.yhklsdf.demo_kotlin.utils.Md5Util;
import com.yhklsdf.demo_kotlin.view.ViewPagerFixed;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Thinkad
 */
public class PictureBrowsingActivity extends AppCompatActivity {

    @BindView(R.id.vp_picture_browsing)
    ViewPagerFixed vpPictureBrowsing;
    @BindView(R.id.tv_picture_browsing_count)
    TextView tvPictureBrowsingCount;
    @BindView(R.id.tv_picture_browsing_save)
    TextView tvPictureBrowsingSave;

    private List<PictureBean> mPictures = new ArrayList<>();
    private int position;
    private VPPictureBrowsingAdapter mAdapter;
    private int page;

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_browsing);
        ButterKnife.bind(this);
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (!granted) {
                        Toast.makeText(this, "未授予权限将无法下载图片！", Toast.LENGTH_SHORT).show();
                    }
                });


        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        mPictures = intent.getParcelableArrayListExtra("pictures");

        mAdapter = new VPPictureBrowsingAdapter(this, mPictures);
        vpPictureBrowsing.setAdapter(mAdapter);
        vpPictureBrowsing.setCurrentItem(position);
        tvPictureBrowsingCount.setText((position + 1) + "/" + mPictures.size());
        vpPictureBrowsing.setPageTransformer(true, new DepthPageTransformer());
        vpPictureBrowsing.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                tvPictureBrowsingCount.setText((i + 1) + "/" + mPictures.size());
                page = i;
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
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), "/yhklsdf/image/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = Md5Util.getMD5(mPictures.get(page).getUrl()) + ".jpg";
            File file = new File(dir, fileName.substring(1, fileName.length()));
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            GlideApp.with(this).asBitmap().load(mPictures.get(page).getUrl().replace("__340", "_640")).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    resource.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    Toast.makeText(PictureBrowsingActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
                    try {
                        os.flush();
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Uri uri = Uri.fromFile(file);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
