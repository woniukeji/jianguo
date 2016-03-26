package com.woniukeji.jianmerchant.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.CommonUtils;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A Register screen that offers Register via email/password.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.phoneNumber) EditText phoneNumber;
    @InjectView(R.id.password) EditText password;
    @InjectView(R.id.sign_in_button) Button signInButton;
    @InjectView(R.id.register_in_button) Button registerInButton;
    @InjectView(R.id.wechat) ImageView wechat;
    @InjectView(R.id.qq) ImageView qq;
    @InjectView(R.id.email_login_form) LinearLayout emailLoginForm;
    @InjectView(R.id.login_form) LinearLayout loginForm;
    @InjectView(R.id.login_bg) ImageView loginBg;
    @InjectView(R.id.forget_pass) TextView forgetPass;
    @InjectView(R.id.quick_login) TextView quickLogin;

    private Context context=LoginActivity.this;
    private UserLoginTask mAuthTask = null;
    // UI references.
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_AUTH_COMPLETE = 2;
    private int MSG_AUTH_ERROR = 3;
    private int MSG_AUTH_CANCEL = 4;
    private int MSG_LOGIN = 5;
    private Handler handler = new Handler() {
        // 处理子线程给我们发送的消息。
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    //每次登录后保存用户信息
                        saveToSP(user.getData());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                        showLongToast("登录成功");
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(LoginActivity.this, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void saveToSP(User user) {
        SPUtils.setParam(context, Constants.LOGIN_INFO,Constants.SP_WQTOKEN,user.getT_user_login().getQqwx_token()!=null?user.getT_user_login().getQqwx_token():"");
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_TEL,user.getT_user_login().getTel()!=null?user.getT_user_login().getTel():"");
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_PASSWORD,user.getT_user_login().getPassword()!=null?user.getT_user_login().getPassword():"");
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_USERID,user.getT_user_login().getId());
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_STATUS,user.getT_user_login().getStatus());
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_QNTOKEN,user.getT_user_login().getQiniu());

        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_NICK,user.getT_user_info().getNickname()!=null?user.getT_user_info().getNickname():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_NAME,user.getT_user_info().getName()!=null?user.getT_user_info().getName():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_IMG,user.getT_user_info().getName_image()!=null?user.getT_user_info().getName_image():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_SCHOOL,user.getT_user_info().getSchool()!=null?user.getT_user_info().getSchool():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_CREDIT,user.getT_user_info().getCredit());
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_INTEGRAL,user.getT_user_info().getIntegral());
//        final ChatManager chatManager = ChatManager.getInstance();
//        if (!TextUtils.isEmpty(String.valueOf(user.getT_user_login().getId()))) {
//            chatManager.setupManagerWithUserId(this, String.valueOf(user.getT_user_login().getId()));
//        }
//        ChatManager.getInstance().openClient(new AVIMClientCallback() {
//            @Override
//            public void done(AVIMClient avimClient, AVIMException e) {
//                if (null == e) {
////                    finish();
////                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                    startActivity(intent);
//                } else {
//                    showShortToast(e.toString());
//                }
//            }
//        });
    }

    @Override
    public void setContentView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
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

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(LoginActivity.this);
    }


//    private void authorize(Platform plat) {
//        Toast.makeText(LoginActivity.this, "正在登录，请稍后...", Toast.LENGTH_SHORT).show();
////        if (plat.isValid()) {
////            String userId = plat.getDb().getUserId();
////            if (!TextUtils.isEmpty(userId)) {
////                Register(true, plat.getName(), userId, null);
////                return;
////            }
////        }
//        plat.setPlatformActionListener(this);
//        plat.SSOSetting(false);
//        plat.showUser(null);
//    }

    /*
         * 演示执行第三方登录/注册的方法
         * <p>
         * 这不是一个完整的示例代码，需要根据您项目的业务需求，改写登录/注册回调函数
         *
         * @param platformName 执行登录/注册的平台名称，如：SinaWeibo.NAME
         */
    private void Register( String plat, String token, HashMap<String, Object> userInfo) {
        String sex = null;
        String nickname = null;
        String nameimage = null;
// 接下来执行您要的操作
            Iterator iterator = userInfo.entrySet().iterator();
            //QQ Wechat字段名不同分别获取
            if (plat.equals("Wechat")) {
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if (entry.getKey().equals("nickname")) {
                        nickname = (String) entry.getValue();
                    } else if (entry.getKey().equals("headimgurl")) {
                        nameimage = (String) entry.getValue();
                    } else if (entry.getKey().equals("sex")) {
                        sex = entry.getValue().toString();
                    }
                }
            } else {//QQ Wechat字段名不同分别获取
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if (entry.getKey().equals("nickname")) {
                        nickname = (String) entry.getValue();
                    } else if (entry.getKey().equals("figureurl_qq_1")) {
                        nameimage = (String) entry.getValue();
                    } else if (entry.getKey().equals("gender")) {
                        if (entry.getValue().equals("男")) {
                            sex = "1";
                        } else {
                            sex = "0";
                        }
                    }
                }

            }
            String only1 = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            UserLoginTask userLoginTask = new UserLoginTask( only1, token, nickname, nameimage, sex);
            userLoginTask.execute();
        }

//    @Override
//    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
//        if (action == Platform.ACTION_USER_INFOR) {
//            Register( platform.getName(), platform.getDb().getUserId(), hashMap);
//        }
//    }
//
//    @Override
//    public void onError(Platform platform, int i, Throwable throwable) {
//        handler.sendEmptyMessage(MSG_AUTH_COMPLETE);
//    }

//    @Override
//    public void onCancel(Platform platform, int i) {
//        handler.sendEmptyMessage(MSG_AUTH_COMPLETE);
//    }

    @OnClick({R.id.sign_in_button, R.id.register_in_button, R.id.wechat, R.id.qq,R.id.forget_pass, R.id.quick_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                String phone = phoneNumber.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (CheckStatus()) {
                    PhoneLoginTask phoneLoginTask = new PhoneLoginTask(phone, MD5Util.MD5(pass));
                    phoneLoginTask.execute();
                }
                break;

            case R.id.forget_pass:
                startActivity(new Intent(LoginActivity.this,ChangPssActivity.class));
                break;
//            case R.id.quick_login:
//                startActivity(new Intent(LoginActivity.this,QuickLoginActivity.class));
//                break;
        }
    }

    private boolean CheckStatus() {
        if (!CommonUtils.isMobileNO(phoneNumber.getText().toString().trim())) {
            showShortToast("手机号码格式不正确");
            return false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            showShortToast("手机号不能为空");
            return false;
        } else if (password.getText().toString().equals("")) {
            showShortToast("密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    public class UserLoginTask extends AsyncTask<Void, Void, User> {

        private final String only;
        private final String token;
        private final String nickname;
        private final String nameimage;
        private final String sex;

        UserLoginTask( String only, String token, String nickname, String nameimage, String sex) {
            this.only = only;
            this.sex = sex;
            this.token = token;
            this.nickname = nickname;
            this.nameimage = nameimage;
        }

        @Override
        protected User doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                    AuthWQ();
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
            mAuthTask = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

        /**
         * login
         * 授权过的weixin qq 用户直接通过token登陆
         */
        public void Login() {
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
                            handler.sendMessage(message);
                        }


                        @Override
                        public void onResponse(BaseBean user) {
                            if (user.getCode().equals("200")){
                                SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_TYPE,"1");
                                Message message = new Message();
                                message.obj = user;
                                message.what = MSG_USER_SUCCESS;
                                handler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.obj = user.getMessage();
                                message.what = MSG_USER_FAIL;
                                handler.sendMessage(message);
                            }

                        }

                    });
        }
        /**
         * authWQ
         * 未授权的weixin qq用户
         */
        public void AuthWQ() {
            OkHttpUtils
                    .get()
                    .url(Constants.LOGIN_WQ)
                    .addParams("only", only)
                    .addParams("sex", sex)
                    .addParams("nickname", nickname)
                    .addParams("token", token)
                    .addParams("nameimage", nameimage)
                    .build()
                    .connTimeOut(30000)
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
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean user) {
                            if (user.getCode().equals("200")){
                                SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_TYPE,"1");
                                Message message = new Message();
                                message.obj = user;
                                message.what = MSG_USER_SUCCESS;
                                handler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.obj = user.getMessage();
                                message.what = MSG_USER_FAIL;
                                handler.sendMessage(message);
                            }
                        }

                    });
        }
    }

    public class PhoneLoginTask extends AsyncTask<Void, Void, User> {

        private final String tel;
        private final String passWord;
        SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);

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
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("登录中...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(final User user) {
            mAuthTask = null;
            pDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            pDialog.dismiss();
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
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean user) {
                            if (user.getCode().equals("200")){
                                SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_TYPE,"0");
                                Message message = new Message();
                                message.obj = user;
                                message.what = MSG_USER_SUCCESS;
                                handler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.obj = user.getMessage();
                                message.what = MSG_USER_FAIL;
                                handler.sendMessage(message);
                            }
                        }

                    });
        }


    }
}

