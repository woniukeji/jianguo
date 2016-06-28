package com.woniukeji.jianmerchant.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.partjob.PartJobManagerActivity;


/**
 * Created by invinjun on 2016/4/27.
 */
    public class CustomJpushReceiver extends BroadcastReceiver {
    public static final String MESSAGE_RECEIVED_ACTION = "cn.jpush.android.intent.NOTIFICATION_OPENED";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                   context.startActivity(new Intent(context, PartJobManagerActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
    }
