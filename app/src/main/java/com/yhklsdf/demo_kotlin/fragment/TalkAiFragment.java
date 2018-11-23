package com.yhklsdf.demo_kotlin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.google.gson.Gson;
import com.yhklsdf.demo_kotlin.R;
import com.yhklsdf.demo_kotlin.activity.MainActivity;
import com.yhklsdf.demo_kotlin.adapter.RVTalkAiAdapter;
import com.yhklsdf.demo_kotlin.bean.MsgBean;
import com.yhklsdf.demo_kotlin.gson.TuLingGet;
import com.yhklsdf.demo_kotlin.gson.TuLingPost;
import com.yhklsdf.demo_kotlin.utils.okHttpUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TalkAiFragment extends Fragment {

    private String url = "http://openapi.tuling123.com/openapi/api/v2";
    private List<MsgBean> msgList = new ArrayList<>();
    private TuLingPost mPost = new TuLingPost();
    private TuLingGet mGet;
    private EditText editText;
    RVTalkAiAdapter mRVTalkAiAdapter;
    RecyclerView recyclerView;

    private Handler mMyHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    notifyNewMessage();
                    break;
                default:
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talk_ai, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        mRVTalkAiAdapter = new RVTalkAiAdapter(msgList);
        if (msgList.isEmpty()) {
            msgList.add(new MsgBean("你好呀，我是Jenny", MsgBean.TYPE_RECEIVE));
        }
        recyclerView.setAdapter(mRVTalkAiAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        editText = view.findViewById(R.id.editText);
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        Button button = view.findViewById(R.id.chatroom_send_et_normal);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                if (!"".equals(msg)) {
                    MsgBean msgBean = new MsgBean(msg, MsgBean.TYPE_SENT);
                    msgList.add(msgBean);
                    notifyNewMessage();
                    editText.setText("");
                    mPost.setText(msg);
                    String postGson = mPost.toString();
                    sendMessage(postGson);
                }
            }
        });

        return view;
    }

    private static final String TAG = "TalkAiFragment";

    private void sendMessage(String json) {
        okHttpUtils.okHttpPostJson(url, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonGet = response.body().string();
                mGet = new Gson().fromJson(jsonGet, TuLingGet.class);
                MsgBean newMsg = new MsgBean(mGet.getResults().get(0).getValues().getText(), MsgBean.TYPE_RECEIVE);
                msgList.add(newMsg);
                mMyHandle.sendEmptyMessage(0);
            }
        });
    }

    public void notifyNewMessage() {
        //每当有新消息时，刷新recycleview中的显示
        mRVTalkAiAdapter.notifyItemInserted(msgList.size() - 1);
        //将recycleview定位到最后一行
        recyclerView.scrollToPosition(msgList.size() - 1);
    }
}
