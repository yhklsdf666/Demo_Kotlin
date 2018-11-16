package com.yhklsdf.demo_kotlin.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.db.Picture;
import com.yhklsdf.demo_kotlin.utils.ImageLoader;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PicturesRVAdapter extends RecyclerView.Adapter<PicturesRVAdapter.viewHolder> {

    private List<Picture> mPicturesList;
    private Context mContext;
    private ImageLoader mImageLoader;

    public PicturesRVAdapter(List<Picture> picturesList) {
        mImageLoader = ImageLoader.getInstance(mContext);
        mPicturesList = picturesList;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.iv_item_RV_pictures);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_pictures_fragment_recycleview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Glide.with(mContext).load(mPicturesList.get(i).getUrl()).into(viewHolder.mImageView);
//        mImageLoader.loadImage(viewHolder.mImageView, mPicturesList.get(i));
        ViewGroup.LayoutParams params = viewHolder.mImageView.getLayoutParams();
        if(params instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexBoxParams = (FlexboxLayoutManager.LayoutParams) params;
            flexBoxParams.setFlexGrow(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return mPicturesList.size();
    }
}
