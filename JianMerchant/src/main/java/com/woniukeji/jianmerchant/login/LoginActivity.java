package com.woniukeji.jianmerchant.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.talk.leanmessage.ChatManager;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A Register screen that offers Register via email/password.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private Context context=LoginActivity.this;
    // UI references.
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private EditText phoneNumber;
    private EditText password;
    private Button signInButton;
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
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                        intent.putExtra("user", user);
//                        startActivity(intent);
//                        finish();
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
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_TEL,user.getT_user_login().getTel()!=null?user.getT_user_login().getTel():"");
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_PASSWORD,user.getT_user_login().getPassword()!=null?user.getT_user_login().getPassword():"");
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_USERID,user.getT_user_login().getId());
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_STATUS,user.getT_user_login().getStatus());
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_QNTOKEN,user.getT_user_login().getQiniu());

        SPUtils.setParam(context,Constants.USER_INFO,Constants.USER_NICK,user.getT_merchant().getName()!=null?user.getT_merchant().getName():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.USER_MERCHANT_ID,user.getT_merchant().getId());
        SPUtils.setParam(context,Constants.USER_INFO,Constants.USER_PAY_PASS,user.getT_merchant().getPay_password());
        SPUtils.setParam(context,Constants.USER_INFO,Constants.USER_IMG,user.getT_merchant().getName_image()!=null?user.getT_merchant().getName_image():"");
        final ChatManager chatManager = ChatManager.getInstance();
        if (!TextUtils.isEmpty(String.valueOf(user.getT_user_login().getId()))) {
            chatManager.setupManagerWithUserId(this, String.valueOf(user.getT_user_login().getId()));
            JPushInterface.setAlias(getApplicationContext(), "jianguo"+user.getT_user_login().getId(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                }
            });
        }
        ChatManager.getInstance().openClient(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showShortToast(e.toString());
                }
            }
        });


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
       phoneNumber= (EditText) findViewById(R.id.phoneNumber);
        password= (EditText) findViewById(R.id.password);
        signInButton= (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneNumber.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (CheckStatus()) {
                    PhoneLogin(phone, MD5Util.MD5(pass));
                }
            }
        });
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
//            String only1 = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
//            UserLoginTask userLoginTask = new UserLoginTask( only1, token, nickname, nameimage, sex);
//            userLoginTask.execute();
        }

    @OnClick({R.id.sign_in_button, R.id.register_in_button, R.id.wechat, R.id.qq,R.id.forget_pass, R.id.quick_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
//                String phone = phoneNumber.getText().toString().trim();
//                String pass = password.getText().toString().trim();
//                if (CheckStatus()) {
//                    PhoneLoginTask phoneLoginTask = new PhoneLoginTask(phone, MD5Util.MD5(pass));
//                    phoneLoginTask.execute();
//                }
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
//        if (!CommonUtils.isMobileNO(phoneNumber.getText().toString().trim())) {
//            showShortToast("手机号码格式不正确");
//            return false;
//        }
        if (phoneNumber.getText().toString().equals("")) {
            showShortToast("手机号不能为空");
            return false;
        } else
        if (password.getText().toString().equals("")) {
            showShortToast("密码不能为空");
            return false;
        }
        return true;
    }

    /**
     * phoneLogin
     * @param phone
     * @param s
     */
    public void PhoneLogin(String phone, String s) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.LOGIN)
                .addParams("only", only)
                .addParams("tel", phone)
                .addParams("password", s)
                .build()
                .connTimeOut(60000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean<User>>() {
                    @Override
                    public BaseBean<User> parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        BaseBean user = new Gson().fromJson(string, new TypeToken<BaseBean<User>>() {
                        }.getType());
                        return user;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        message.what = MSG_USER_FAIL;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(BaseBean<User> user, int id) {
                        if (user.getCode().equals("200")) {
                            SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                            Message message = new Message();
                            message.obj = user;
                            message.what = MSG_USER_SUCCESS;
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.obj = user.getMessage();
                            message.what = MSG_USER_FAIL;
                            handler.sendMessage(message);
                        }
                    }




                });
    }
}






