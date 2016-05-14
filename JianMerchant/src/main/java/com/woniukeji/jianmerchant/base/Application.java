package com.woniukeji.jianmerchant.base;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.woniukeji.jianmerchant.talk.leanmessage.LeanchatClientEventHandler;
import com.woniukeji.jianmerchant.talk.leanmessage.MessageHandler;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by invinjun on 2016/3/2.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UmengConfig();
        init();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"AtwJtfIJPKQFtti8D3gNjMmb-gzGzoHsz","spNrDrtGWAXP633DkMMWT65B");
        MessageHandler msgHandler = new MessageHandler(this);
        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, msgHandler);
        AVIMClient.setClientEventHandler(LeanchatClientEventHandler.getInstance());
        AVOSCloud.setDebugLogEnabled(true);
//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false);
    }
    private void init(){
        JPushInterface.init(getApplicationContext());
    }
    public void UmengConfig(){


    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//方法数过多 导致
    }
}
