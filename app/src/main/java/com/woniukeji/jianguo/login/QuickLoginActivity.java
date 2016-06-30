package com.woniukeji.jianguo.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
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

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CodeCallback;
import com.woniukeji.jianguo.entity.SmsCode;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.eventbus.QuickLoginEvent;
import com.woniukeji.jianguo.eventbus.TalkMessageEvent;
import com.woniukeji.jianguo.leanmessage.ChatManager;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.CommonUtils;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A Register screen that offers Register via email/password.
 */
public class QuickLoginActivity extends BaseActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    @InjectView(R.id.phoneNumber) EditText phoneNumber;
    @InjectView(R.id.phone_code) EditText phoneCode;
    @InjectView(R.id.sign_in_button) Button signInButton;
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView title;
    @InjectView(R.id.btn_get_code) Button btnGetCode;
    @InjectView(R.id.icon_pass) ImageView iconPass;
//    SmsCode smsCode;
    @InjectView(R.id.cb_rule) CheckBox cbRule;
    @InjectView(R.id.tv_rule) TextView tvRule;
    @InjectView(R.id.user_rule) LinearLayout userRule;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = QuickLoginActivity.this;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            QuickLoginActivity quickLoginActivity = (QuickLoginActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
//                    BaseBean<User> user = msg.;
                    quickLoginActivity.saveToSP(user.getData());
                    quickLoginActivity.showShortToast(user.getMessage());
                    QuickLoginEvent quickLoginEvent=new QuickLoginEvent();
                    quickLoginEvent.isQuickLogin=true;
                    EventBus.getDefault().post(quickLoginEvent);
                    Intent intent = new Intent(QuickLoginActivity.this, MainActivity.class);
                    quickLoginActivity.startActivity(intent);
                    quickLoginActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(quickLoginActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
//                    quickLoginActivity.smsCode = (SmsCode) msg.obj;
                    quickLoginActivity.showShortToast("验证码已经发送，请注意查收");
//                    if (quickLoginActivity.smsCode.getIs_tel().equals("1")){
//                        quickLoginActivity.showShortToast("验证码已经发送，请注意查收");
//                    }else{
//                        quickLoginActivity.showShortToast("该手机号码已经注册，不能重复注册");
//                    }

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(quickLoginActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login_quick);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        title.setText("登录");
        createLink(tvRule);
    }

    @Override
    public void initListeners() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(QuickLoginActivity.this);
    }

    /**
     * 创建一个超链接
     */
    private void createLink(TextView tv) {
        // 创建一个 SpannableString对象
        SpannableString sp = new SpannableString("我已阅读并同意《兼果用户协议》");
        // 设置超链接
        sp.setSpan(new URLSpan("http://101.200.205.243:8080/user_agreement.jsp"), 7, 15,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_bg)), 7, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
        tv.setTextSize(12);
        //设置TextView可点击
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void saveToSP(User user) {
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_WQTOKEN, user.getT_user_login().getQqwx_token() != null ? user.getT_user_login().getQqwx_token() : "");
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_TEL, user.getT_user_login().getTel() != null ? user.getT_user_login().getTel() : "");
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getT_user_login().getPassword() != null ? user.getT_user_login().getPassword() : "");
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, user.getT_user_login().getId());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_STATUS, user.getT_user_login().getStatus());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, user.getT_user_login().getQiniu());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.LOGIN_APK_URL, user.getApk_url());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.LOGIN_VERSION, user.getVersion());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.LOGIN_CONTENT, user.getContent());


        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_NICK, user.getT_user_info().getNickname() != null ? user.getT_user_info().getNickname() : "");
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_NAME, user.getT_user_info().getName() != null ? user.getT_user_info().getName() : "");
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_IMG, user.getT_user_info().getName_image() != null ? user.getT_user_info().getName_image() : "");
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_SCHOOL, user.getT_user_info().getSchool() != null ? user.getT_user_info().getSchool() : "");
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_CREDIT, user.getT_user_info().getCredit());
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_INTEGRAL, user.getT_user_info().getIntegral());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_RESUMM, user.getT_user_login().getResume());
        SPUtils.setParam(context, Constants.USER_INFO, Constants.USER_SEX, user.getT_user_info().getUser_sex());


// 暂时关闭果聊功能
//        final ChatManager chatManager = ChatManager.getInstance();
        if (!TextUtils.isEmpty(String.valueOf(user.getT_user_login().getId()))) {
            if (JPushInterface.isPushStopped(getApplicationContext())){
                JPushInterface.resumePush(getApplicationContext());
            }
            //登陆leancloud服务器 给极光设置别名
//                        chatManager.setupManagerWithUserId(this, String.valueOf(user.getT_user_login().getId()));
                        JPushInterface.setAlias(getApplicationContext(), "jianguo"+user.getT_user_login().getId(), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {

                                LogUtils.e("jpush",s+",code="+i);
                            }
            });
        }
//        ChatManager.getInstance().openClient(new AVIMClientCallback() {
//            @Override
//            public void done(AVIMClient avimClient, AVIMException e) {
//                if (null == e) {
//                    TalkMessageEvent talkMessageEvent=new TalkMessageEvent();
//                    talkMessageEvent.isLogin=true;
//                    EventBus.getDefault().post(talkMessageEvent);
//                } else {
//                    showShortToast(e.toString());
//                }
//            }
//        });

    }


    @OnClick({R.id.sign_in_button, R.id.img_back, R.id.btn_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                String tel = phoneNumber.getText().toString().trim();
                String sms = phoneCode.getText().toString().trim();
                if (CheckStatus()) {
                    PhoneLogin(tel,sms);
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_get_code:
                String phone = phoneNumber.getText().toString();
                boolean isOK = phone.length()==11;
                if (isOK) {
                    time.start();
                    CheckPhone(phone);
                } else {
                    showLongToast("请输入正确的手机号");
                }

                break;
        }
    }

    private boolean CheckStatus() {
        if (phoneNumber.getText().toString().trim().length()!=11) {
            showShortToast("手机号码格式不正确");
            return false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            showShortToast("手机号不能为空");
            return false;
        } else if (phoneCode.getText().toString().equals("")) {
            showShortToast("验证码不能为空");
            return false;
        }
        else if (!cbRule.isChecked()) {
            showShortToast("请阅读并确认《兼果用户协议》");
            return false;
        }
        return true;
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnGetCode.setClickable(false);
            btnGetCode.setBackgroundColor(Color.GRAY);
            btnGetCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btnGetCode.setText("验证码");
            btnGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_login));
            btnGetCode.setClickable(true);
        }
    }

        /**
         * phoneLogin
         * @param tel
         * @param sms
         */
        public void PhoneLogin(String tel, String sms) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.REGISTER_PHONE)
                    .addParams("only", only)
                    .addParams("tel", tel)
                    .addParams("sms_code", sms)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<User>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean user = new Gson().fromJson(string, new TypeToken<BaseBean<User>>() {
                            }.getType());
                            return user;
                        }

                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean user,int id) {
                            if (user.getCode().equals("200")) {
                                SPUtils.setParam(QuickLoginActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = user;
                                message.what = MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = user.getMessage();
                                message.what = MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }



        /**
         * login
         * 检查手机号是否存在
         * @param tel
         */
        public void CheckPhone(String tel) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_SMS)
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

