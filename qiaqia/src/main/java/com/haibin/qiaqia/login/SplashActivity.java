package com.haibin.qiaqia.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.Toast;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;
import com.haibin.qiaqia.base.Constants;
import com.haibin.qiaqia.entity.User;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.main.MainActivity;
import com.haibin.qiaqia.utils.SPUtils;

public class SplashActivity extends BaseActivity {

    private Context context = SplashActivity.this;

    private SubscriberOnNextListener<User> loginSubListener;

    private  Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what==0){

            }
            return false;
        }
    });
    @Override
    public void setContentView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initViews() {
        //初始化SDK
//        ShareSDK.initSDK(this);
//        Picasso.with(context).load(R.mipmap.splash).into(imgSplash);
    }

    @Override
    public void initListeners() {
        //处理接口返回的数据
        loginSubListener=new SubscriberOnNextListener<User>() {

            @Override
            public void onNext(User user) {
                if (user.getCode().equals("200")){
                    Toast.makeText(SplashActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    SPUtils.setParam(SplashActivity.this, Constants.USER_LOGIN,Constants.LOGIN_PHONE,user.getData().getIUserLogin().getPhone());
                    SPUtils.setParam(SplashActivity.this, Constants.USER_LOGIN,Constants.LOGIN_PASSWORD,user.getData().getIUserLogin().getPassword());
                    SPUtils.setParam(SplashActivity.this, Constants.USER_LOGIN,Constants.LOGIN_STATUS,user.getData().getIUserLogin().getStatus());
                    SPUtils.setParam(SplashActivity.this, Constants.USER_LOGIN,Constants.LOGIN_TYPE,1);
                    SPUtils.setParam(SplashActivity.this, Constants.USER_INFO,Constants.INFO_IMG,user.getData().getIUserInfo().getNameImage());
                    SPUtils.setParam(SplashActivity.this, Constants.USER_INFO,Constants.INFO_ID,user.getData().getIUserInfo().getLoginId());
                    SPUtils.setParam(SplashActivity.this, Constants.USER_INFO,Constants.INFO_NAME,user.getData().getIUserInfo().getName());
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }else
                    startActivity(new Intent(SplashActivity.this, LoginPassWordActivity.class));
                    Toast.makeText(SplashActivity.this,user.getMessage(),Toast.LENGTH_LONG).show();
                    finish();
            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
//        ActivityManager.getActivityManager().addActivity(SplashActivity.this);
    }

    private void saveToSP(User user) {
        SPUtils.setParam(SplashActivity.this, Constants.USER_LOGIN,Constants.LOGIN_PHONE,user.getData().getIUserLogin().getPhone());
        SPUtils.setParam(SplashActivity.this, Constants.USER_LOGIN,Constants.LOGIN_PASSWORD,user.getData().getIUserLogin().getPassword());
        SPUtils.setParam(SplashActivity.this, Constants.USER_LOGIN,Constants.LOGIN_STATUS,user.getData().getIUserLogin().getStatus());
        SPUtils.setParam(SplashActivity.this, Constants.USER_LOGIN,Constants.LOGIN_TYPE,1);
        SPUtils.setParam(SplashActivity.this, Constants.USER_INFO,Constants.INFO_IMG,user.getData().getIUserInfo().getNameImage());
        SPUtils.setParam(SplashActivity.this, Constants.USER_INFO,Constants.INFO_ID,user.getData().getIUserInfo().getLoginId());
        SPUtils.setParam(SplashActivity.this, Constants.USER_INFO,Constants.INFO_NAME,user.getData().getIUserInfo().getName());
        //暂时关闭果聊功能
//        final ChatManager chatManager = ChatManager.getInstance();
//        if (!TextUtils.isEmpty(String.valueOf(user.getT_user_login().getId()))) {
//            //登陆leancloud服务器 给极光设置别名
////            chatManager.setupManagerWithUserId(this, String.valueOf(user.getT_user_login().getId()));
//            LogUtils.e("jpush","调用jpush");
//            if (JPushInterface.isPushStopped(getApplicationContext())){
//                JPushInterface.resumePush(getApplicationContext());
//            }
//            JPushInterface.setAlias(getApplicationContext(),"jianguo"+user.getT_user_login().getId(), new TagAliasCallback() {
//                @Override
//                public void gotResult(int i, String s, Set<String> set) {
//
//                    LogUtils.e("jpush",s+",code="+i);
//                }
//            });
//        }
//        ChatManager.getInstance().openClient(new AVIMClientCallback() {
//            @Override
//            public void done(AVIMClient avimClient, AVIMException e) {
//                if (null == e) {
//                } else {
//                    showShortToast(e.toString());
//                }
//            }
//        });
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        // 在当前的界面变为用户可见的时候调用的方法
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseActivity();
            }
        },2000);
    super.onStart();
}
//        new Thread() {
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                runOnUiThread();
//                chooseActivity();
//            }
//
//            ;
//        }.start();


    /**
     * chooseActivity
     * 根据保存的登陆信息 跳转不同界面
     */
    private void chooseActivity() {
        int loginType = (int) SPUtils.getParam(context, Constants.USER_LOGIN, Constants.LOGIN_TYPE, 0);
        if (loginType==0){
            startActivity(new Intent(context, LoginPassWordActivity.class));
            finish();
        }
        else if(loginType==1){
            String phone= (String) SPUtils.getParam(context,Constants.USER_LOGIN,Constants.LOGIN_PHONE,"");
            String password= (String) SPUtils.getParam(context,Constants.USER_LOGIN,Constants.LOGIN_PASSWORD,"");
            HttpMethods.getInstance().Login(new ProgressSubscriber<User>(loginSubListener,SplashActivity.this),phone,
                   password);
        }
    }


}
