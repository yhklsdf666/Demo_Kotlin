package com.yhklsdf.demo_kotlin.adapter;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.bean.VideoBean;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class RVVideosAdapter extends RecyclerView.Adapter<RVVideosAdapter.viewHolder> {

    private List<VideoBean> mVideos;
    private Context mContext;

    public RVVideosAdapter(List<VideoBean> videos) {
        mVideos = videos;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_videos_fragment_recycleview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.vpItemRVVideos.setUp(mVideos.get(i).getVideoUrl(), "", Jzvd.SCREEN_WINDOW_LIST);
        Glide.with(mContext).load(mVideos.get(i).getPlaceholderPicture()).into(viewHolder.vpItemRVVideos.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        JzvdStd vpItemRVVideos;
        public viewHolder(@NonNull View itemView) {

            super(itemView);
            mContext = itemView.getContext();
            vpItemRVVideos = itemView.findViewById(R.id.vp_item_RV_videos);
        }
    }

}
