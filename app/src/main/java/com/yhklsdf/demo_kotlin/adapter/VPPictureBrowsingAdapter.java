package com.yhklsdf.demo_kotlin.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
            //加载小图
            RequestBuilder<Drawable> thumbnailRequest = Glide.with(context)
                    .load(pictures.get(position).getUrl());
            String url = pictures.get(position).getUrl().replace("__340", "_640");
            //正式加载大图
            GlideApp.with(context).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                    GlideApp.with(context).load(pictures.get(position).getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView); 执行后会重新创建Fragment？
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    //做一些操作，例如停止刷新之类
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(thumbnailRequest).into(imageView);
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
