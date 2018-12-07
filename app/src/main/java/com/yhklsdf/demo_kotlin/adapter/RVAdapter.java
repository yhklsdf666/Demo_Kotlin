package com.yhklsdf.demo_kotlin.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.activity.PictureBrowsingActivity;
import com.yhklsdf.demo_kotlin.bean.MsgBean;
import com.yhklsdf.demo_kotlin.bean.PictureBean;
import com.yhklsdf.demo_kotlin.bean.RecycleViewItemBean;
import com.yhklsdf.demo_kotlin.bean.VideoBean;
import com.yhklsdf.demo_kotlin.utils.ImageLoaderUtil;
import com.yhklsdf.demo_kotlin.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RecycleViewItemBean> mDataList;
    private Context mContext;
    private ImageLoaderUtil mImageLoader;
    private int position;

    public enum ITEM_TYPE {
        TYPE_PICTURES,
        TYPE_VIDEOS,
        TYPE_TALK
    }

    public RVAdapter(List<RecycleViewItemBean> dataList) {
        mImageLoader = ImageLoaderUtil.getInstance(mContext);
        mDataList = dataList;
    }

    class viewHolder1 extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private List<PictureBean> pictures = new ArrayList<>();

        public viewHolder1(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.iv_item_RV_pictures);

            ViewGroup.LayoutParams params = mImageView.getLayoutParams();
            if (params instanceof FlexboxLayoutManager.LayoutParams) {
                FlexboxLayoutManager.LayoutParams flexBoxParams = (FlexboxLayoutManager.LayoutParams) params;
                flexBoxParams.setFlexGrow(1.0f);
            }

            mImageView.setOnClickListener(v -> {
                        Intent intent = new Intent(mContext, PictureBrowsingActivity.class);
                        RxBus.getInstance().send(mDataList);RxBus.getInstance().send(position);
                        intent.putParcelableArrayListExtra("pictures", (ArrayList<? extends Parcelable>) pictures);
                        intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    }
            );
        }

        public void bind(int i) {
            pictures.clear();
            for (int j = 0;j < mDataList.size();j++) {
                pictures.add((PictureBean) mDataList.get(j).getData());
            }
            PictureBean picture = (PictureBean) mDataList.get(i).getData();
            GlideApp.with(mContext).asBitmap().load(picture.getUrl()).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    mImageView.setMaxWidth(resource.getWidth());
                    mImageView.setMaxHeight(resource.getHeight());
                    return false;
                }
            }).into(mImageView);
//        mImageLoader.loadImage(viewHolder.mImageView, mDataList.get(i));
        }
    }

    class viewHolder2 extends RecyclerView.ViewHolder {
        JzvdStd vpItemRVVideos;
        public viewHolder2(@NonNull View itemView) {

            super(itemView);
            mContext = itemView.getContext();
            vpItemRVVideos = itemView.findViewById(R.id.vp_item_RV_videos);
        }

        public void bind(int i) {
            VideoBean video = (VideoBean) mDataList.get(i).getData();
            vpItemRVVideos.setUp(video.getVideoUrl(), "", Jzvd.SCREEN_WINDOW_LIST);
            Glide.with(mContext).load(video.getPlaceholderPicture()).into(vpItemRVVideos.thumbImageView);
        }
    }

    class viewHolder3 extends RecyclerView.ViewHolder {
        private RelativeLayout leftLayout;
        private TextView leftMsg;

        private RelativeLayout rightLayout;
        private TextView rightMsg;

        public viewHolder3(@NonNull View itemView) {
            super(itemView);
            leftLayout =  itemView.findViewById(R.id.left_layout);
            leftMsg =  itemView.findViewById(R.id.left_msg);

            rightLayout =  itemView.findViewById(R.id.right_layout);
            rightMsg =  itemView.findViewById(R.id.right_msg);
        }

        public void bind(int i) {
            MsgBean msg = (MsgBean) mDataList.get(i).getData();
            //如果是收到的消息，则显示左边的消息布局，将右边的隐藏
            if (msg.getType() == MsgBean.TYPE_RECEIVE) {
                leftLayout.setVisibility(View.VISIBLE);
                rightLayout.setVisibility(View.GONE);
                leftMsg.setText(msg.getContent());

            } else if (msg.getType() == MsgBean.TYPE_SENT) {
                rightLayout.setVisibility(View.VISIBLE);
                leftLayout.setVisibility(View.GONE);
                rightMsg.setText(msg.getContent());
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        if (i == ITEM_TYPE.TYPE_PICTURES.ordinal()) {
            return new viewHolder1(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_pictures_fragment_recycleview, viewGroup, false));
        } else if (i == ITEM_TYPE.TYPE_VIDEOS.ordinal()) {
            return new viewHolder2(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_videos_fragment_recycleview, viewGroup, false));
        } else {
            return new viewHolder3(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_ai_msg, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        position = i;
        if (viewHolder instanceof viewHolder1) {
            viewHolder1 vh = (viewHolder1) viewHolder;
            vh.bind(i);
        } else if (viewHolder instanceof viewHolder2) {
            viewHolder2 vh = (viewHolder2) viewHolder;
            vh.bind(i);
        } else {
            viewHolder3 vh = (viewHolder3) viewHolder;
            vh.bind(i);
        }
    }

    /**
     * 0 --- 图片
     * 1 --- 视频
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position).getDataType() == 0) {
            return ITEM_TYPE.TYPE_PICTURES.ordinal();
        } else if (mDataList.get(position).getDataType() == 1) {
            return ITEM_TYPE.TYPE_VIDEOS.ordinal();
        } else {
            return ITEM_TYPE.TYPE_TALK.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
