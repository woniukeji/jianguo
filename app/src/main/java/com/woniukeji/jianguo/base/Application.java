package com.woniukeji.jianguo.base;


import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.woniukeji.jianguo.leanmessage.MessageHandler;

/**
 * Created by invinjun on 2016/3/2.
 */
public class Application extends android.app.Application {
    private boolean isGPS=false;//是否已经定位选择过城市
    private static Application instance;

    public boolean isGPS() {
        return isGPS;
    }

    public void setGPS(boolean GPS) {
        isGPS = GPS;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UmengConfig();
        instance = this;
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"AtwJtfIJPKQFtti8D3gNjMmb-gzGzoHsz","spNrDrtGWAXP633DkMMWT65B");
        MessageHandler msgHandler = new MessageHandler(this);
        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, msgHandler);

//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false);
    }
    public void UmengConfig(){


    }
    public static Application getInstance(){
        return instance;
    }
}
