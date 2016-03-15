package com.woniukeji.jianguo.talk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.woniukeji.jianguo.LeanMessage.ChatManager;
import com.woniukeji.jianguo.LeanMessage.ImTypeMessageEvent;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.FixedRecyclerView;

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
    private List<AVIMConversation> conversations = new ArrayList<AVIMConversation>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ConversationAdapter itemAdapter;
    private AVIMClient client;
    private String avatarUrl;
    private int loginId;


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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_talk, container, false);
        ButterKnife.inject(this, view);
//        conversationManager = ConversationManager.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        talkConstRv.setLayoutManager(layoutManager);
        itemAdapter = new ConversationAdapter(conversations,getActivity());
        talkConstRv.setAdapter(itemAdapter);
        client= ChatManager.getInstance().getImClient();
        AVIMConversationQuery query = client.getQuery();
        query.limit(20);
        query.findInBackground(new AVIMConversationQueryCallback(){
            @Override
            public void done(List<AVIMConversation> convs,AVIMException e){
                if(e==null){
                  conversations.addAll(convs);
                    itemAdapter.notifyDataSetChanged();
                    String con=client.toString();
                    LogUtils.e("conv",con);
                    //convs就是获取到的conversation列表
                    //注意：按每个对话的最后更新日期（收到最后一条消息的时间）倒序排列
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        avatarUrl= (String) SPUtils.getParam(getActivity(), Constants.SP_USER,Constants.SP_IMG,"");
           loginId = (int) SPUtils.getParam(getActivity(), Constants.SP_LOGIN, Constants.SP_USERID, 0);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
//        sendWelcomeMessage("42",avatarUrl);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWelcomeMessage("42",avatarUrl);
            }
        });
    }


    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    public void onEvent(ImTypeMessageEvent event) {
        text.setText(event.message.getContent());
//        if (null != imConversation && null != event &&
//                imConversation.getConversationId().equals(event.conversation.getConversationId())) {
//            itemAdapter.addMessage(event.message);
//            itemAdapter.notifyDataSetChanged();
//            scrollToBottom();
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }



    public void sendWelcomeMessage(final String toUserId, final String img) {
        Map<String, Object> attrs = new HashMap<>();
//        attrs.put(ConversationType.TYPE_KEY, ConversationType.Single.getValue());
        ChatManager.getInstance().getImClient().createConversation(Arrays.asList(toUserId), "", attrs, false, true, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (e == null) {
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("userid",String.valueOf(loginId));
                    attributes.put("touserid",toUserId);
                    attributes.put("nickname","昵称");
                    attributes.put("avatar",img);
                    attributes.put("type",0);
                    AVIMTextMessage message = new AVIMTextMessage();
                    message.setText("创建一个对话"+System.currentTimeMillis());
                    message.setAttrs(attributes);
                    avimConversation.sendMessage(message, null);
                }
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
