package com.woniukeji.jianmerchant.entity;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMMessage;

import java.util.List;

/**
 * Created by lzw on 14-9-26.
 */
public class Conversation {
  private AVIMMessage lastMessage;
  private String conversationId;
  private int unreadCount;

  public AVIMMessage getLastMessage() {
    return lastMessage;
  }

  public long getLastModifyTime() {
    if (lastMessage != null) {
      return lastMessage.getTimestamp();
    }



    return 0;
  }



  public void setLastMessage(AVIMMessage lastMessage) {
    this.lastMessage = lastMessage;
  }

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }

  public int getUnreadCount() {
    return unreadCount;
  }

  public void setUnreadCount(int unreadCount) {
    this.unreadCount = unreadCount;
  }

  public static abstract class MultiRoomsCallback {
    public abstract void done(List<Room> roomList, AVException exception);
  }
}
