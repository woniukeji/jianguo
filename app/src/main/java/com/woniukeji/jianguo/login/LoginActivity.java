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
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.entity.UserCallback;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.MD5Coder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

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
                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(false);
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
         final String sex;
         final String nickname;
         final String nameimage;

        Iterator iterator=userInfo.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry= (Map.Entry) iterator.next();
           if (entry.getKey().equals("nickname")){

           }else if(entry.getKey().equals("figureurl_qq_1")){

           }
           else if(entry.getKey().equals("gender")){

           }
        }


        String time =DateUtils.getDateTime(System.currentTimeMillis());
        String only = MD5Coder.getMD5Code(Constants.ONLY_STR+time+":");
        UserLoginTask userLoginTask=new UserLoginTask(only,userId,"","","");
        userLoginTask.execute();

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

        private final String only;
        private final String token;
        private final String nickname;
        private final String nameimage;
        private final String sex;

        UserLoginTask(String only, String token,String nickname,String nameimage,String sex) {
            this.only = only;
            this.sex = token;
            this.token = nickname;
            this.nickname = nameimage;
            this.nameimage = sex;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
               Login();
            } catch (Exception e) {
                return false;
            }
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
        public void Login(){
            OkHttpUtils
                    .get()
                    .url(Constants.LOGIN_WQ)
                    .addParams("only", only)
                    .addParams("sex", sex)
                    .addParams("nickname", nickname)
                    .addParams("token", token)
                    .addParams("nameimage", nameimage)
                    .build()
                    .execute(new UserCallback()
                    {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(User response) {

                        }

                    });
        }
    }
}

