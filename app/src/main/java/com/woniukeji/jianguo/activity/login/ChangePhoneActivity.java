package com.woniukeji.jianguo.activity.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.CodeCallback;
import com.woniukeji.jianguo.entity.SmsCode;
import com.woniukeji.jianguo.http.HttpMethods;
import com.woniukeji.jianguo.http.ProgressSubscriber;
import com.woniukeji.jianguo.http.SubscriberOnNextListener;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import okhttp3.Call;

/**
 * A Register screen that offers Register via email/password.
 */
public class ChangePhoneActivity extends BaseActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView imgShare;
    @BindView(R.id.phoneNumber) EditText phoneNumber;
    @BindView(R.id.btn_get_code) Button btnGetCode;
    @BindView(R.id.icon_pass) ImageView iconPass;
    @BindView(R.id.phone_code) EditText phoneCode;
    @BindView(R.id.sign_in_button) Button signInButton;
    @BindView(R.id.email_login_form) LinearLayout emailLoginForm;
    @BindView(R.id.login_form) LinearLayout loginForm;


    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(ChangePhoneActivity.this);
    private Context context = ChangePhoneActivity.this;
    private TimeCount time;
    private SubscriberOnNextListener<String> changeSubListener;
    private int loginId;

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (time!=null){
            time.cancel();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChangePhoneActivity quickLoginActivity = (ChangePhoneActivity) reference.get();
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    TastyToast.makeText(quickLoginActivity, ErrorMessage, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 2:
                    SmsCode smsCode = (SmsCode) msg.obj;
                    if (smsCode.getIs_tel().equals("1")){
                        quickLoginActivity.showShortToast("验证码已经发送，请注意查收");
                    }else{
                        quickLoginActivity.showShortToast("该手机号码已经注册，请直接登陆！");
                    }

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    TastyToast.makeText(quickLoginActivity, sms, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    public void initListeners() {
        changeSubListener=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                TastyToast.makeText(ChangePhoneActivity.this, "更换手机号成功！", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                SPUtils.setParam(ChangePhoneActivity.this,Constants.LOGIN_INFO, Constants.SP_TEL, phoneNumber.getText().toString().trim());
                finish();
            }
        };



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void initData() {
        loginId = (int) SPUtils.getParam(ChangePhoneActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
    }

    @Override
    public void addActivity() {

    }





    @OnClick({R.id.sign_in_button, R.id.btn_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                String tel = phoneNumber.getText().toString().trim();
                String sms = phoneCode.getText().toString().trim();
                if (CheckStatus()) {
                    HttpMethods.getInstance().postChangeTel(new ProgressSubscriber<String>(changeSubListener,this), String.valueOf(loginId),tel,sms);
//                    PhoneLogin(tel, sms);
                }
                break;
            case R.id.btn_get_code:
                String phone = phoneNumber.getText().toString();
                boolean isOK = phone.length() == 11;
                if (isOK) {
                    time.start();
                    CheckPhone(phone);
                } else {
                    TastyToast.makeText(ChangePhoneActivity.this, "请输入正确的手机号！", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                break;
        }
    }

    private boolean CheckStatus() {
        if (phoneNumber.getText().toString().trim().length() != 11) {
            showShortToast("手机号码格式不正确！");
            return false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            showShortToast("手机号不能为空！");
            return false;
        } else if (phoneCode.getText().toString().equals("")) {
            showShortToast("验证码不能为空！");
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
            if (btnGetCode != null) {
                btnGetCode.setClickable(false);
                btnGetCode.setBackgroundColor(Color.GRAY);
                btnGetCode.setText(millisUntilFinished / 1000 + "秒");
            }
        }

        @Override
        public void onFinish() {
            btnGetCode.setText("验证码");
            btnGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_login));
            btnGetCode.setClickable(true);
        }
    }
    /**
     * login
     * 检查手机号是否存在
     *
     * @param tel
     */
    public void CheckPhone(String tel) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.CHECK_PHONE)
                .addParams("tel", tel)
                .addParams("only", only)
                .build()
                .connTimeOut(6000)
                .readTimeOut(2000)
                .writeTimeOut(2000)
                .execute(new CodeCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        message.what = MSG_USER_FAIL;
                        mHandler.sendMessage(message);
                    }

                    @Override                    public void onResponse(SmsCode response, int id) {
                        Message message = new Message();
                        message.obj = response;
                        message.what = MSG_PHONE_SUCCESS;
                        mHandler.sendMessage(message);
                    }


                });
    }

}

