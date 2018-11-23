package com.yhklsdf.demo_kotlin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.bean.MsgBean;

import java.util.ArrayList;
import java.util.List;

public class RVTalkAiAdapter extends RecyclerView.Adapter<RVTalkAiAdapter.ViewHolder> {
    private List<MsgBean> msgList = new ArrayList<>();

    public RVTalkAiAdapter(List<MsgBean> msgList) {
        this.msgList = msgList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout leftLayout;
        private TextView leftMsg;

        private RelativeLayout rightLayout;
        private TextView rightMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout =  itemView.findViewById(R.id.left_layout);
            leftMsg =  itemView.findViewById(R.id.left_msg);

            rightLayout =  itemView.findViewById(R.id.right_layout);
            rightMsg =  itemView.findViewById(R.id.right_msg);
        }
    }

    /**
     * 此处不变，将子项布局加载出来，然后创建一个ViewHolder实例，并把加载出来的布局传入到构造函数中
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_ai_msg, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    /**
     * 对子项进行赋值，会在每个子项被滚动到屏幕内的时候执行，可以通过position参数和list得到当前项的实例，然后在将数据设置到ViewHolder的...各项即可。
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MsgBean msg = msgList.get(i);
        //如果是收到的消息，则显示左边的消息布局，将右边的隐藏
        if (msg.getType() == MsgBean.TYPE_RECEIVE) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent());

        } else if (msg.getType() == MsgBean.TYPE_SENT) {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
