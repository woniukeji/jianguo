package com.haibin.qiaqia.base;


import android.content.Context;




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
        // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        init();
        instance = this;
        // 初始化参数依次为 this, AppId, AppKey，暂时关闭果聊，第二处splash和quick界面的账户登录
//        AVOSCloud.initialize(this,"AtwJtfIJPKQFtti8D3gNjMmb-gzGzoHsz","spNrDrtGWAXP633DkMMWT65B");
//        MessageHandler msgHandler = new MessageHandler(this);
//        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, msgHandler);

//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //
//        MultiDex.install(this);//方法数过多 导致
    }

    private void init(){
//        JPushInterface.init(getApplicationContext());
    }
    public static Application getInstance(){
        return instance;
    }
}
