package com.woniukeji.jianmerchant.talk.leanmessage;

import android.content.Context;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMFileMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import de.greenrobot.event.EventBus;
/**
 * Created by zhangxiaobo on 15/4/20.
 */
public class MessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

    private Context context;

    public MessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        switch (message.getMessageType()) {
            case AVIMMessageType.TEXT_MESSAGE_TYPE:
                AVIMTextMessage textMsg = (AVIMTextMessage) message;
                sendEvent(message, conversation);
                break;

            case AVIMMessageType.FILE_MESSAGE_TYPE:
                AVIMFileMessage fileMsg = (AVIMFileMessage) message;
                break;

            case AVIMMessageType.VIDEO_MESSAGE_TYPE:
                AVIMImageMessage imageMsg = (AVIMImageMessage) message;
                break;
        }
    }

    /**
     * 因为没有 db，所以暂时先把消息广播出去，由接收方自己处理
     * 稍后应该加入 db
     *
     * @param message
     * @param conversation
     */
    private void sendEvent(AVIMTypedMessage message, AVIMConversation conversation) {
        ImTypeMessageEvent event = new ImTypeMessageEvent();
        event.message = message;
        event.conversation = conversation;
        EventBus.getDefault().post(event);
    }


}
