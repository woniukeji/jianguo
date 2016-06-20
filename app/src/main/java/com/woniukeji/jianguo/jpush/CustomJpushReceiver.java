package com.woniukeji.jianguo.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.woniukeji.jianguo.eventbus.MessageEvent;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.main.PushMessageActivity;
import com.woniukeji.jianguo.mine.SignActivity;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * Created by invinjun on 2016/4/27.
 */
    public class CustomJpushReceiver extends BroadcastReceiver {
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
        @Override
        public void onReceive(Context context, Intent intent) {
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

            }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//                System.out.println("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
                System.out.println("收到了ACTION_MESSAGE_RECEIVED通知");
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                System.out.println("收到了ACTION_NOTIFICATION_RECEIVED通知");
                MessageEvent messageEvent=new MessageEvent();
                EventBus.getDefault().post(messageEvent);
                // 在这里可以做些统计，或者做些其他工作
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                System.out.println("用户点击打开了通知");
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                context.startActivity(new Intent(context, PushMessageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

        }
    }
