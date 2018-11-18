package com.yhklsdf.demo_kotlin.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.activity.PictureBrowsingActivity;
import com.yhklsdf.demo_kotlin.bean.PictureBean;
import com.yhklsdf.demo_kotlin.utils.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

public class RVPicturesAdapter extends RecyclerView.Adapter<RVPicturesAdapter.viewHolder> {

    private List<PictureBean> mPicturesList;
    private Context mContext;
    private Context viewGroupA;
    private ImageLoaderUtil mImageLoader;

    public RVPicturesAdapter(List<PictureBean> picturesList) {
        mImageLoader = ImageLoaderUtil.getInstance(mContext);
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
        viewGroupA = viewGroup.getContext();
        return new viewHolder(LayoutInflater.from(viewGroupA)
                .inflate(R.layout.item_pictures_fragment_recycleview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Glide.with(mContext).load(mPicturesList.get(i).getUrl()).into(viewHolder.mImageView);
//        mImageLoader.loadImage(viewHolder.mImageView, mPicturesList.get(i));
        ViewGroup.LayoutParams params = viewHolder.mImageView.getLayoutParams();
        if (params instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexBoxParams = (FlexboxLayoutManager.LayoutParams) params;
            flexBoxParams.setFlexGrow(1.0f);
        }

        viewHolder.mImageView.setOnClickListener(v -> {
                    Intent intent = new Intent(viewGroupA, PictureBrowsingActivity.class);
                    intent.putParcelableArrayListExtra("pictures", (ArrayList<? extends Parcelable>) mPicturesList);
                    intent.putExtra("position", i);
                    viewGroupA.startActivity(intent);
                }
        );
    }

    @Override
    public int getItemCount() {
        return mPicturesList.size();
    }
}
