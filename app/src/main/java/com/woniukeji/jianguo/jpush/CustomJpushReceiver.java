package com.woniukeji.jianguo.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.woniukeji.jianguo.entity.PushType;
import com.woniukeji.jianguo.eventbus.MessageEvent;
import com.woniukeji.jianguo.activity.main.MainActivity;
import com.woniukeji.jianguo.activity.main.PushMessageActivity;
import com.woniukeji.jianguo.activity.main.WebViewActivity;
import com.woniukeji.jianguo.activity.mine.AuthActivity;
import com.woniukeji.jianguo.activity.mine.SignUpActivity;
import com.woniukeji.jianguo.activity.partjob.JobDetailActivity;
import com.woniukeji.jianguo.activity.wallte.WalletActivity;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * Created by invinjun on 2016/4/27.
 */
    public class CustomJpushReceiver extends BroadcastReceiver {
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "mExtras";
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
                Bundle extras = intent.getExtras();
                String type = extras.getString(JPushInterface.EXTRA_EXTRA);//类型 0=报名，1=钱包，2=实名
                Gson gson=new Gson();
                PushType pushType = gson.fromJson(type, PushType.class);
            if (null!=pushType&&null!=pushType.getType()){
                if (pushType.getType().equals("0")) {
                    context.startActivity(new Intent(context, SignUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (pushType.getType().equals("1")) {
                    context.startActivity(new Intent(context, WalletActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (pushType.getType().equals("2")) {
                    context.startActivity(new Intent(context, AuthActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (pushType.getType().equals("3")) {
                    context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (pushType.getType().equals("4")) {
                    Intent intent1=new Intent(context, WebViewActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.putExtra("url",pushType.getHtml_url());
                    context.startActivity(intent1);
                } else if (pushType.getType().equals("5")) {
                    Intent intent2=new Intent(context, JobDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.putExtra("job",pushType.getJob_id());
                    context.startActivity(intent2);
                }else{
                    context.startActivity(new Intent(context, PushMessageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }else
                context.startActivity(new Intent(context, PushMessageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
    }
