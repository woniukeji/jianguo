package com.woniukeji.jianguo.login;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.woniukeji.jianguo.R;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements PlatformActionListener, View.OnClickListener {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    @InjectView(R.id.login_progress) ProgressBar loginProgress;
    @InjectView(R.id.phoneNumber) AutoCompleteTextView phoneNumber;
    @InjectView(R.id.password) EditText password;
    @InjectView(R.id.sign_in_button) Button signInButton;
    @InjectView(R.id.register_in_button) TextView registerInButton;
    @InjectView(R.id.wechat) Button wechat;
    @InjectView(R.id.qq) Button qq;
    @InjectView(R.id.email_login_form) LinearLayout emailLoginForm;
    @InjectView(R.id.login_form) ScrollView loginForm;

    private UserLoginTask mAuthTask = null;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Handler handler = new Handler() {

        // 处理子线程给我们发送的消息。
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;
                default:
                    break;
            }
        }

        ;
    };
    private int MSG_USERID_FOUND = 0;
    private int MSG_AUTH_COMPLETE = 1;
    private String platform;
    private int MSG_AUTH_ERROR = 2;
    private int MSG_AUTH_CANCEL = 3;
    private int MSG_LOGIN = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        //初始化SDK
        ShareSDK.initSDK(this);

    }


    private void authorize(Platform plat) {
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                handler.sendEmptyMessage(MSG_USERID_FOUND);
//                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(true);
        plat.showUser(null);
    }
    /*
         * 演示执行第三方登录/注册的方法
         * <p>
         * 这不是一个完整的示例代码，需要根据您项目的业务需求，改写登录/注册回调函数
         *
         * @param platformName 执行登录/注册的平台名称，如：SinaWeibo.NAME
         */

    private void login(String plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        handler.sendMessage(msg);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_COMPLETE);
            login(platform.getName(), platform.getDb().getUserId(), hashMap);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        handler.sendEmptyMessage(MSG_AUTH_COMPLETE);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        handler.sendEmptyMessage(MSG_AUTH_COMPLETE);
    }

    @OnClick({R.id.sign_in_button, R.id.register_in_button, R.id.wechat, R.id.qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                break;
            case R.id.register_in_button:
                break;
            case R.id.wechat: {
                authorize(new Wechat(this));
            }
            break;
            case R.id.qq: {
                authorize(new QQ(this));
            }
                break;
        }
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
//            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
//            showProgress(false);
        }
    }
}

