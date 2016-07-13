package com.woniukeji.jianguo.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CodeCallback;
import com.woniukeji.jianguo.entity.SmsCode;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.CommonUtils;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.MD5Util;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.utils.TimeCount;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class RegistActivity extends BaseActivity {

    @InjectView(R.id.tv_title) TextView title;
    @InjectView(R.id.phoneNumber) EditText phoneNumber;
    @InjectView(R.id.btn_get_code) Button btnGetCode;
    @InjectView(R.id.phoneCode) EditText phoneCode;
    @InjectView(R.id.passWord1) EditText passWord1;
    @InjectView(R.id.passWord2) EditText passWord2;
    @InjectView(R.id.phone_sign_in_button) Button phoneSignInButton;
    @InjectView(R.id.email_login_form) LinearLayout emailLoginForm;
    @InjectView(R.id.user_rule) LinearLayout userRule;
    @InjectView(R.id.cb_rule) CheckBox cbRule;
    @InjectView(R.id.tv_rule) TextView tvRule;
    private String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";
    private BroadcastReceiver smsReceiver;
    private IntentFilter filter2;

    SmsCode smsCode;

    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context=RegistActivity.this;

    @OnClick(R.id.tv_rule)
    public void onClick() {
    }

    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RegistActivity registActivity = (RegistActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    Toast.makeText(registActivity, "注册成功，请登陆", Toast.LENGTH_SHORT).show();
                    registActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(registActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    registActivity.smsCode = (SmsCode) msg.obj;
                    if (registActivity.smsCode.getIs_tel().equals("1")) {
                        registActivity.showShortToast("该手机号码已经注册，不能重复注册");
                    } else {
                        registActivity.showShortToast("验证码已经发送，请注意查收");
                    }

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(registActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_regist);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        title.setText("注册");
        userRule.setVisibility(View.VISIBLE);
        createLink(tvRule);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(RegistActivity.this);
    }


    /**
     * 创建一个超链接
     */
    private void createLink(TextView tv) {
        // 创建一个 SpannableString对象
        SpannableString sp = new SpannableString("我已阅读并同意《兼果用户协议》");
        // 设置超链接
        sp.setSpan(new URLSpan("http://inke.tv/privacy/privacy.html"), 7, 15,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_bg)), 7, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setTextSize(12);
        tv.setText(sp);
        //设置TextView可点击
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.img_back, R.id.btn_get_code, R.id.phone_sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_get_code:
                String tel = phoneNumber.getText().toString();
                boolean isOK = CommonUtils.isMobileNO(tel);
                if (isOK) {
//                    showShortToast("正在发送验证码，请稍后");
                    new TimeCount(60000, 1000,btnGetCode).start();//构造CountDownTimer对象
                    GetSMS getSMS = new GetSMS(tel);
                    getSMS.execute();
                } else {
                    showLongToast("请输入正确的手机号");
                }

                break;
            case R.id.phone_sign_in_button:
                String phone = phoneNumber.getText().toString();
                String pass = passWord2.getText().toString();
                if (CheckStatus()) {
                    UserRegisterPhone(phone, MD5Util.MD5(pass));
                }
                break;
        }
    }

    private boolean CheckStatus() {

        if (phoneNumber.getText().toString().equals("")) {
            showShortToast("手机号不能为空");
            return false;
        } else if (!CommonUtils.isMobileNO(phoneNumber.getText().toString().trim())) {
            showShortToast("手机号码格式不正确");
            return false;
        } else if (passWord1.getText().toString().equals("")) {
            showShortToast("密码不能为空");
            return false;
        } else if (passWord2.getText().toString().equals("")) {
            showShortToast("请再次输入密码");
            return false;
        } else if (passWord1.getText().toString().trim().length() < 6) {
            showShortToast("您的密码太短了");
            return false;
        } else if (!passWord1.getText().toString().trim().equals(passWord2.getText().toString().trim())) {
            showShortToast("您两次输入的密码不同！");
            return false;
        } else if (phoneCode.getText().toString().equals("")) {
            showShortToast("验证码不能为空");
            return false;
        }
        return true;
    }

    /**
     * 手机验证码Task
     */
    public class GetSMS extends AsyncTask<Void, Void, User> {

        private final String tel;
        SweetAlertDialog pDialog = new SweetAlertDialog(RegistActivity.this, SweetAlertDialog.PROGRESS_TYPE);

        GetSMS(String phoneNum) {
            this.tel = phoneNum;
        }

        protected User doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            CheckPhone();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("中...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(final User user) {
            pDialog.dismiss();
        }

        /**
         * login
         * 检查手机号是否存在
         */
        public void CheckPhone() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.CHECK_PHONE)
                    .addParams("tel", tel)
                    .addParams("only", only)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new CodeCallback() {

                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(SmsCode response,int id) {
                            Message message = new Message();
                            message.obj = response;
                            message.what = MSG_PHONE_SUCCESS;
                            mHandler.sendMessage(message);
                        }


                    });
        }


    }

        /**
         * UserRegisterPhone
         * 通过手机号码注册
         * @param phone
         * @param
         */
        public void UserRegisterPhone(String phone, String pass) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.REGISTER_PHONE)
                    .addParams("only", only)
                    .addParams("tel", phone)
                    .addParams("password", pass)
                    .addParams("sms_code", phoneCode.getText().toString())
                    .build()
                    .connTimeOut(30000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<User>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean user = new Gson().fromJson( string, new TypeToken<BaseBean<User>>(){}.getType());
                            return user;
                        }
                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.getMessage();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean response,int id) {
                            if (response.getCode().equals("200")){
                                SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_TYPE,"0");
                                Message message = new Message();
                                message.obj = response;
                                message.what = MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            }else{
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }
                    });
        }
    }


