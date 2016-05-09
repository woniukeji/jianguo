package com.woniukeji.jianmerchant.talk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.talk.leanmessage.ChatManager;
import com.woniukeji.jianmerchant.talk.leanmessage.ImTypeMessageEvent;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.FixedRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;


public class TalkFragment extends BaseFragment {
    @InjectView(R.id.text) TextView text;
    @InjectView(R.id.button) Button button;
    @InjectView(R.id.talk_const_rv) FixedRecyclerView talkConstRv;
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    private List<AVIMConversation> conversations = new ArrayList<AVIMConversation>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ConversationAdapter itemAdapter;
    private AVIMClient client;
    private String avatarUrl;
    private int loginId;
    private AVIMConversationQuery query;


    public static TalkFragment newInstance(String param1, String param2) {
        TalkFragment fragment = new TalkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.i("fragment", "talk:onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_talk, container, false);
        ButterKnife.inject(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        imgBack.setVisibility(View.GONE);
        tvTitle.setText("果聊");
        avatarUrl = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.USER_IMG, "");
        loginId = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, 0);

//        conversationManager = ConversationManager.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        talkConstRv.setLayoutManager(layoutManager);
        itemAdapter = new ConversationAdapter(conversations, getActivity(),loginId);
        talkConstRv.setAdapter(itemAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //查询对话
        client = ChatManager.getInstance().getImClient();
//        LogUtils.e("leancloudMes","client对象="+ client.toString());
//        query=ChatManager.getInstance().getConversationQuery();
//        LogUtils.e("leancloudMes","query对象1="+ query.toString());
//        getConversation();
//        sendMessageToJerryFromTom();
        query = client.getQuery();
        query.limit(100);
        query.setQueryPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e==null){
                    conversations.clear();
                    conversations.addAll(list);
                    itemAdapter.notifyDataSetChanged();
                    LogUtils.e("leancloudMes","talk查询NETWORK_ONLY成功="+list.size());
                }else {
                    LogUtils.e("leancloudMes","talk查询消息NETWORK_ONLY="+e.getMessage());
                }
            }
        });
   }
        public void getConversation(){
             AVIMConversationQuery query1 =ChatManager.getInstance().getConversationQuery();
            query1.setQueryPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query1.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e==null){
                    conversations.clear();
                    conversations.addAll(list);
                    itemAdapter.notifyDataSetChanged();
                    LogUtils.e("leancloudMes","talk查询消息列表成功="+list.size());
                }else {
//                    getConversation();
                    LogUtils.e("leancloudMes","talk查询消息列表失败="+e.getMessage());
                }
            }
        });
    }
    public void sendMessageToJerryFromTom() {
        // Tom 用自己的名字作为clientId，获取AVIMClient对象实例
        AVIMClient tom = AVIMClient.getInstance(String.valueOf(loginId));
        // 与服务器连接
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建与Jerry之间的对话
                    client.getQuery().findInBackground(new AVIMConversationQueryCallback() {
                        @Override
                        public void done(List<AVIMConversation> list, AVIMException e) {
                            if (e==null){
                                conversations.clear();
                                conversations.addAll(list);
                                itemAdapter.notifyDataSetChanged();
                                LogUtils.e("leancloudMes","loginId成功="+list.size());
                            }else {
//                                getConversation();
                                LogUtils.e("leancloudMes","loginId失败="+e.getMessage());
                            }
                        }
                    });
                }else{
                    LogUtils.e("leancloudMes","登陆失败="+e.getMessage());
                }
            }
        });
    }
//        LogUtils.e("leancloudMes","query对象2="+ query.toString());
//                                   query.limit(20);
//                                   query.setQueryPolicy(AVQuery.CachePolicy.IGNORE_CACHE);
//                                   query.findInBackground(new AVIMConversationQueryCallback() {
//                                       @Override
//                                       public void done(List<AVIMConversation> convs, AVIMException e) {
//                                           if (e == null) {
//                                               conversations.clear();
//                                               conversations.addAll(convs);
//                                               itemAdapter.notifyDataSetChanged();
//                                               String con = client.toString();
////                                               sendWelcomeMessage("13","img");
//                                               LogUtils.e("leancloudMes", "获取conversitaion成功"+convs.size());
//                                               //convs就是获取到的conversation列表
//                                               //注意：按每个对话的最后更新日期（收到最后一条消息的时间）倒序  排列
//                                           }else{
////                                               sendWelcomeMessage("13","img");
//                                               LogUtils.e("leancloudMes","错误信息="+ e.getMessage());
//                                           }
//
//                                       }
//        });



    /**
     * 处理推送过来的消息
     * 此处应该判断本地已经有的converid 和新推动过来的消息的convid对比 加入对应数据中更新
     */
    public void onEvent(ImTypeMessageEvent event) {
//        event.message.getConversationId();
//        text.setText(event.message.getContent());
        query.limit(20);
        query.setQueryPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> convs, AVIMException e) {
                if (e == null) {
                    conversations.clear();
                    conversations.addAll(convs);
                    itemAdapter.notifyDataSetChanged();
                    String con = client.toString();
                    LogUtils.e("conv", con);
                    //convs就是获取到的conversation列表
                    //注意：按每个对话的最后更新日期（收到最后一条消息的时间）倒序  排列
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i("fragment", "talk:onAttach");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.i("fragment", "talk:onDetach");

        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
        LogUtils.i("fragment", "talk:ondestory");
    }


    public void sendWelcomeMessage(final String toUserId, final String img) {
        Map<String, Object> attrs = new HashMap<>();
//        attrs.put(ConversationType.TYPE_KEY, ConversationType.Single.getValue());
        ChatManager.getInstance().getImClient().createConversation(Arrays.asList(toUserId), "", attrs, false, true, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (e == null) {
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("userid", String.valueOf(loginId));
                    attributes.put("touserid", toUserId);
                    attributes.put("nickname", "呵呵");
                    attributes.put("avatar", img);
                    attributes.put("type", 0);
                    AVIMTextMessage message = new AVIMTextMessage();
                    message.setText("创建一个对话" + System.currentTimeMillis());
                    message.setAttrs(attributes);
                    avimConversation.sendMessage(message, null);
                    LogUtils.e("leancloudMes","成功信息="+ "fasongchengg");
                }else
                    LogUtils.e("leancloudMes","错误信息="+ e.getMessage());
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
