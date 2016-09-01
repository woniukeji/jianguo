package com.woniukeji.jianguo.base;



import android.content.Context;
import android.support.multidex.MultiDex;

import com.woniukeji.jianguo.eventbus.CustomUserProvider;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.leancloud.chatkit.LCChatKit;
import okhttp3.OkHttpClient;

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

        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance(getApplicationContext()));

        LCChatKit.getInstance().init(getApplicationContext(), "AtwJtfIJPKQFtti8D3gNjMmb-gzGzoHsz","spNrDrtGWAXP633DkMMWT65B");

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//方法数过多 导致
    }

    private void init(){
        JPushInterface.init(getApplicationContext());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);


}
public static Application getInstance(){
        return instance;
        }
        }
