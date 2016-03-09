package com.woniukeji.jianguo.login;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Response;

public class SplashActivity extends BaseActivity {

    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context=SplashActivity.this;
    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity splashActivity = (SplashActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    splashActivity.saveToSP(user.getData());
                    Intent intent = new Intent(splashActivity, MainActivity.class);
                    splashActivity.startActivity(intent);
                    splashActivity.finish();
                    break;
                case 1:
                    splashActivity.startActivity(new Intent(splashActivity,LoginActivity.class));
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(splashActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    splashActivity.finish();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(splashActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
    private void saveToSP(User user) {
        SPUtils.setParam(context,Constants.SP_LOGIN,Constants.SP_WQTOKEN,user.getT_user_login().getQqwx_token()!=null?user.getT_user_login().getQqwx_token():"");
        SPUtils.setParam(context,Constants.SP_LOGIN,Constants.SP_TEL,user.getT_user_login().getTel()!=null?user.getT_user_login().getTel():"");
        SPUtils.setParam(context,Constants.SP_LOGIN,Constants.SP_PASSWORD,user.getT_user_login().getPassword()!=null?user.getT_user_login().getPassword():"");
        SPUtils.setParam(context,Constants.SP_LOGIN,Constants.SP_USERID,user.getT_user_login().getId());
        SPUtils.setParam(context,Constants.SP_LOGIN,Constants.SP_STATUS,user.getT_user_login().getStatus());
        SPUtils.setParam(context,Constants.SP_LOGIN,Constants.SP_QNTOKEN,user.getT_user_login().getQiniu());

        SPUtils.setParam(context,Constants.SP_USER,Constants.SP_NICK,user.getT_user_info().getNickname()!=null?user.getT_user_info().getNickname():"");
        SPUtils.setParam(context,Constants.SP_USER,Constants.SP_NAME,user.getT_user_info().getName()!=null?user.getT_user_info().getName():"");
        SPUtils.setParam(context,Constants.SP_USER,Constants.SP_IMG,user.getT_user_info().getName_image()!=null?user.getT_user_info().getName_image():"");
        SPUtils.setParam(context,Constants.SP_USER,Constants.SP_SCHOOL,user.getT_user_info().getSchool()!=null?user.getT_user_info().getSchool():"");
        SPUtils.setParam(context,Constants.SP_USER,Constants.SP_CREDIT,user.getT_user_info().getCredit());
        SPUtils.setParam(context,Constants.SP_USER,Constants.SP_INTEGRAL,user.getT_user_info().getIntegral());
    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        // 在当前的界面变为用户可见的时候调用的方法
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                       chooseActivity();
                    };
                }.start();
            super.onStart();
    }

    /**
     * chooseActivity
     * 根据保存的登陆信息 跳转不同界面
     */
    private void chooseActivity() {
        String loginType= (String) SPUtils.getParam(context,Constants.SP_LOGIN,Constants.SP_TYPE,"2");
        if (loginType.equals("2")){
            startActivity(new Intent(context,LoginActivity.class));
            finish();
        }else if(loginType.equals("1")){
            String token= (String) SPUtils.getParam(context,Constants.SP_LOGIN,Constants.SP_WQTOKEN,"");
            QWLoginTask QWLoginTask = new QWLoginTask(token);
            QWLoginTask.execute();

        }else {
            String phone= (String) SPUtils.getParam(context,Constants.SP_LOGIN,Constants.SP_TEL,"");
            String pass= (String) SPUtils.getParam(context,Constants.SP_LOGIN,Constants.SP_PASSWORD,"");
            PhoneLoginTask phoneLoginTask = new PhoneLoginTask(phone, pass);
            phoneLoginTask.execute();
        }
    }

    @Override
    public void onClick(View view) {

    }

    public class QWLoginTask extends AsyncTask<Void, Void, Void> {

        private final String token;


        QWLoginTask(String token) {
            this.token = token;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
                    QWLogin();
            return null;
        }
        /**
         * login
         * 授权过的weixin qq 用户直接通过token登陆
         */
        public void QWLogin() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.LOGIN_WQ)
                    .addParams("token", token)
                    .addParams("only", only)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<User>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean user = new Gson().fromJson( string, new TypeToken<BaseBean<User>>(){}.getType());
                            return user;
                        }
                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean user) {
                            if (user.getCode().equals("200")){
                                SPUtils.setParam(context,Constants.SP_LOGIN,Constants.SP_TYPE,"1");
                                Message message = new Message();
                                message.obj = user;
                                message.what = MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.obj = user.getMessage();
                                message.what = MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }

    }
    public class PhoneLoginTask extends AsyncTask<Void, Void, User> {

        private final String tel;
        private final String passWord;

        PhoneLoginTask(String phoneNum, String passWord) {
            this.tel = phoneNum;
            this.passWord = passWord;
        }

        @Override
        protected User doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                PhoneLogin();
            } catch (Exception e) {
                return null;
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final User user) {
        }

        @Override
        protected void onCancelled() {
        }

        /**
         * phoneLogin
         */
        public void PhoneLogin() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.LOGIN_PHONE)
                    .addParams("only", only)
                    .addParams("tel", tel)
                    .addParams("password", passWord)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<User>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean user = new Gson().fromJson( string, new TypeToken<BaseBean<User>>(){}.getType());
                            return user;
                        }
                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean user) {
                            if (user.getCode().equals("200")){
                                SPUtils.setParam(context,Constants.SP_LOGIN,Constants.SP_TYPE,"0");
                                Message message = new Message();
                                message.obj = user;
                                message.what = MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.obj = user.getMessage();
                                message.what = MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }


    }
}
