package com.yhklsdf.demo_kotlin.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.bean.PictureBean;

import java.util.List;


public class VPPictureBrowsingAdapter extends PagerAdapter{

    private Context context;
    private List<PictureBean> pictures;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    public VPPictureBrowsingAdapter(Context context, List<PictureBean> pictures) {
        this.context = context;
        this.pictures = pictures;
        cacheView = new SparseArray<>(pictures.size());
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (containerTemp == null) {
            containerTemp = container;
        }
        View view = cacheView.get(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_picture_browsing, container, false);
            view.setTag(position);
            final PhotoView imageView = view.findViewById(R.id.pv_picture_browsing_picture);
            final PhotoViewAttacher photoViewAttacher = imageView.getAttacher();
            Glide.with(context).load(pictures.get(position).getUrl()).into(imageView);
            photoViewAttacher.update();
            photoViewAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
            cacheView.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
