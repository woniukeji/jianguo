/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.woniukeji.jianmerchant.talk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.talk.bean.Emojicon;
import com.woniukeji.jianmerchant.talk.bean.Faceicon;
import com.woniukeji.jianmerchant.talk.bean.Message;
import com.woniukeji.jianmerchant.talk.emoji.DisplayRules;
import com.woniukeji.jianmerchant.talk.leanmessage.ChatManager;
import com.woniukeji.jianmerchant.talk.leanmessage.ImTypeMessageEvent;
import com.woniukeji.jianmerchant.talk.widget.KJChatKeyboard;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * 聊天主界面
 */
public class ChatActivity extends KJActivity {

    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0x1;
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.chat_listview) ListView chatListview;
    @InjectView(R.id.chat_msg_input_box) KJChatKeyboard chatMsgInputBox;

    private KJChatKeyboard box;
    private ListView mRealListView;

    List<AVIMMessage> datas = new ArrayList<AVIMMessage>();
    private ChatAdapter adapter;
    private Context context = ChatActivity.this;
    private AVIMConversation conv;
    private Context mContext=ChatActivity.this;
    private int loginId;
    private String mConversationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        loginId = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        Intent intent = this.getIntent();
         mConversationId = intent.getStringExtra("mConversationId");
        queryConvById(mConversationId);

    }

    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    public void onEvent(ImTypeMessageEvent event) {
        if (event.message.getConversationId().equals(mConversationId)){
            datas.add(event.message);
            adapter.notifyDataSetChanged();
        }
    }

    private void queryConvById(String conid) {
        AVIMClient client = ChatManager.getInstance().getImClient();
        //登录成功
        conv = client.getConversation(conid);
        conv.getAttribute(Constants.CREAT_NAME);
         if(conv.getCreator().equals(String.valueOf(loginId))){
             tvTitle.setText((String)conv.getAttribute(Constants.OTHER_NAME));
        }else{
             tvTitle.setText((String)conv.getAttribute(Constants.CREAT_NAME));
         }

        int limit = 20;// limit 取值范围 1~1000 之内的整数
        // 不使用 limit 默认返回 20 条消息
        conv.queryMessages(limit, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> messages, AVIMException e) {
                if (e == null) {
                    //成功获取最新10条消息记录
                    datas.addAll(messages);
                    adapter.notifyDataSetChanged();
                    mRealListView.setSelection(mRealListView.getBottom());
                    mRealListView.setSelection(datas.size() - 1);
                }
            }
        });
//                    AVIMConversationQuery query = client.getQuery();
//                    query.whereEqualTo("objectId",mConversationId);
//                    query.findInBackground(new AVIMConversationQueryCallback(){
//                        @Override
//                        public void done(List<AVIMConversation> convs, AVIMException e){
//                            if(e==null){
//                                if(convs!=null && !convs.isEmpty()){
//                                    //convs.get(0) 就是想要的conversation
//
//                                }
//                            }
//                        }
//                    });
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    public void initWidget() {
        super.initWidget();
//        TextView textview= (TextView) findViewById(R.id.title_text);
//        textview.setText("聊天");
        box = (KJChatKeyboard) findViewById(R.id.chat_msg_input_box);
        mRealListView = (ListView) findViewById(R.id.chat_listview);
        mRealListView.setSelector(android.R.color.transparent);
        int loginId = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        String img = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_IMG, "");
        String nickname = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_NICK, "无名字");
        initMessageInputToolBox(String.valueOf(loginId), nickname, img);
        initListView();


    }

    private void initMessageInputToolBox(final String loginid, final String nickname, final String img) {
        box.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                AVIMTextMessage message = new AVIMTextMessage();
//                datas.add(message);
//                adapter.refresh(datas);
//                sendWelcomeMessage("42",loginid,nickname,img,content);
//                createReplayMsg(message);
                //                createReplayMsg(message);

//                message.setFrom(loginid);

                sendMessage("42", loginid, nickname, img, content);
            }

            @Override
            public void selectedFace(Faceicon content) {
                Message message = new Message(Message.MSG_TYPE_FACE, Message.MSG_STATE_SUCCESS,
                        "Tom", "avatar", "Jerry", "avatar", content.getPath(), true, true, new
                        Date());
//                datas.add(message);
                adapter.refresh(datas);
//                createReplayMsg(message);
            }

            @Override
            public void selectedEmoji(Emojicon emoji) {
                box.getEditTextBox().append(emoji.getValue());
            }

            @Override
            public void selectedBackSpace(Emojicon back) {
                DisplayRules.backspace(box.getEditTextBox());
            }

            @Override
            public void selectedFunction(int index) {
                switch (index) {
                    case 0:
                        goToAlbum();
                        break;
                    case 1:
                        ViewInject.toast("跳转相机");
                        break;
                }
            }
        });

        List<String> faceCagegory = new ArrayList<>();
//        File faceList = FileUtils.getSaveFolder("chat");
        File faceList = new File("");
        if (faceList.isDirectory()) {
            File[] faceFolderArray = faceList.listFiles();
            for (File folder : faceFolderArray) {
                if (!folder.isHidden()) {
                    faceCagegory.add(folder.getAbsolutePath());
                }
            }
        }

        box.setFaceData(faceCagegory);
        mRealListView.setOnTouchListener(getOnTouchListener());
    }

    private void sendMessage(String s, String loginid, String nickname, String img, String content) {

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("userid", loginid);
        attributes.put("touserid", s);
        attributes.put("nickname", nickname);
        attributes.put("avatar", img);
        attributes.put("type", 0);
        AVIMTextMessage message = new AVIMTextMessage();
        message.setText(content);
        message.setAttrs(attributes);
        datas.add(message);
        adapter.notifyDataSetChanged();
        mRealListView.setSelection(datas.size() - 1);
        conv.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (null != e) {
                    LogUtils.e("message", "fasong:" + e.getMessage());
                }
                LogUtils.e("message", "fasong:");
            }
        });
    }


    private void initListView() {
        byte[] emoji = new byte[]{
                (byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81
        };
        Message message = new Message(Message.MSG_TYPE_TEXT,
                Message.MSG_STATE_SUCCESS, "\ue415", "avatar", "Jerry", "avatar",
                new String(emoji), false, true, new Date(System.currentTimeMillis()
                - (1000 * 60 * 60 * 24) * 8));
//        Message message1 = new Message(Message.MSG_TYPE_TEXT,
//                Message.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar",
//                "以后的版本支持链接高亮喔:http://www.kymjs.com支持http、https、svn、ftp开头的链接",
//                true, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 8));
//        Message message2 = new Message(Message.MSG_TYPE_PHOTO,
//                Message.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar",
//                "http://static.oschina.net/uploads/space/2015/0611/103706_rpPc_1157342.png",
//                false, true, new Date(
//                System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 7));
//        Message message6 = new Message(Message.MSG_TYPE_TEXT,
//                Message.MSG_STATE_FAIL, "Tom", "avatar", "Jerry", "avatar",
//                "test send fail", true, false, new Date(
//                System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 6));
//        Message message7 = new Message(Message.MSG_TYPE_TEXT,
//                Message.MSG_STATE_SENDING, "Tom", "avatar", "Jerry", "avatar",
//                "<a href=\"http://kymjs.com\">自定义链接</a>也是支持的", true, true, new Date(System.currentTimeMillis()
//                - (1000 * 60 * 60 * 24) * 6));

//        datas.add(message);
//        datas.add(message1);
//        datas.add(message2);
//        datas.add(message6);
//        datas.add(message7);

        adapter = new ChatAdapter(this, datas, getOnChatItemClickListener());
        mRealListView.setAdapter(adapter);

    }

//    private void createReplayMsg(Message message) {
//        final Message reMessage = new Message(message.getType(), Message.MSG_STATE_SUCCESS, "Tom",
//                "avatar", "Jerry", "avatar", message.getType() == Message.MSG_TYPE_TEXT ? "返回:"
//                + message.getContent() : message.getContent(), false,
//                true, new Date());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000 * (new Random().nextInt(3) + 1));
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            datas.add(reMessage);
//                            adapter.refresh(datas);
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && box.isShow()) {
            box.hideLayout();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 跳转到选择相册界面
     */
    private void goToAlbum() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    REQUEST_CODE_GETIMAGE_BYSDCARD);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    REQUEST_CODE_GETIMAGE_BYSDCARD);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD) {
            Uri dataUri = data.getData();
            if (dataUri != null) {
                File file = FileUtils.uri2File(aty, dataUri);
                Message message = new Message(Message.MSG_TYPE_PHOTO, Message.MSG_STATE_SUCCESS,
                        "Tom", "avatar", "Jerry",
                        "avatar", file.getAbsolutePath(), true, true, new Date());
//                datas.add(message);
                adapter.refresh(datas);
            }
        }
    }

    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                box.hideLayout();
                box.hideKeyboard(aty);
                return false;
            }
        };
    }

    /**
     * @return 聊天列表内存点击事件监听器
     */
    private OnChatItemClickListener getOnChatItemClickListener() {
        return new OnChatItemClickListener() {
            @Override
            public void onPhotoClick(int position) {
                KJLoger.debug(datas.get(position).getContent() + "点击图片的");
                ViewInject.toast(aty, datas.get(position).getContent() + "点击图片的");
            }

            @Override
            public void onTextClick(int position) {
            }

            @Override
            public void onFaceClick(int position) {
            }
        };
    }



    /**
     * 聊天列表中对内容的点击事件监听
     */
    public interface OnChatItemClickListener {
        void onPhotoClick(int position);

        void onTextClick(int position);

        void onFaceClick(int position);
    }

    public void sendWelcomeMessage(final String UserId, final String toUserId, final String nickName, final String img, final String text) {
        Map<String, Object> attrs = new HashMap<>();
//        attrs.put(ConversationType.TYPE_KEY, ConversationType.Single.getValue());
        ChatManager.getInstance().getImClient().createConversation(Arrays.asList(toUserId), "", attrs, false, true, new AVIMConversationCreatedCallback() {

            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (e == null) {
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("userid", UserId);
                    attributes.put("touserid", toUserId);
                    attributes.put("nickname", nickName);
                    attributes.put("avatar", img);
                    attributes.put("type", 0);
                    AVIMTextMessage message = new AVIMTextMessage();
                    message.setText(text);
                    message.setAttrs(attributes);
                    avimConversation.sendMessage(message, null);
                }
            }
        });
    }

}
