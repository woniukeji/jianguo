package cn.leancloud.chatkit.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by invinjun on 16/8/17.
 * 当用户进入某个具体的对话后，发送消息通知对话列表不要显示未读消息数，当退出的时候恢复消息数量显示
 */
public class LCIMTalkingConversationIdEvent {
  public boolean exitConversation;
  public String conversationId;
}
