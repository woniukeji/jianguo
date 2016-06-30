package com.woniukeji.jianmerchant.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.BaseCallback;
import com.woniukeji.jianmerchant.entity.CodeCallback;
import com.woniukeji.jianmerchant.entity.SmsCode;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.CommonUtils;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;

/**
 * A login screen that offers login via email/password.
 */
public class ChangPssActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_merchant_name) TextView title;
    @InjectView(R.id.phoneNumber) EditText phoneNumber;
    @InjectView(R.id.btn_get_code) Button btnGetCode;
    @InjectView(R.id.phoneCode) EditText phoneCode;
    @InjectView(R.id.passWord1) EditText passWord1;
    @InjectView(R.id.passWord2) EditText passWord2;
    @InjectView(R.id.phone_sign_in_button) Button phoneSignInButton;
    @InjectView(R.id.email_login_form) LinearLayout emailLoginForm;
    private String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";
    private BroadcastReceiver smsReceiver;
    private IntentFilter filter2;

    SmsCode smsCode;

    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler=new Myhandler(this);
    private Context context= ChangPssActivity.this;
    private static class Myhandler extends Handler{
        private WeakReference<Context> reference;
        public Myhandler(Context context){
            reference=new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChangPssActivity registActivity= (ChangPssActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    registActivity.saveToSP(user.getData());
                    Intent intent=new Intent(registActivity,MainActivity.class);
//                    intent.putExtra("user",user);
                    registActivity.startActivity(intent);
                    registActivity.finish();
                    break;
                case 1:
                    String ErrorMessage= (String) msg.obj;
                    Toast.makeText(registActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    registActivity.smsCode= (SmsCode) msg.obj;
                    if (registActivity.smsCode.getIs_tel().equals("1")){
                        registActivity.showShortToast("验证码已经发送，请注意查收");
                    }else{
                        registActivity.showShortToast("您的手机号还没有注册，请先注册");
                    }

                    break;
                case 3:
                    String sms= (String) msg.obj;
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
        title.setText("密码找回");
        phoneSignInButton.setText("确认修改");

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(ChangPssActivity.this);
    }

    private void saveToSP(User user) {
        SPUtils.setParam(context, Constants.LOGIN_INFO,Constants.SP_PASSWORD,user.getT_user_login().getPassword());
    }

    @OnClick({R.id.img_back, R.id.btn_get_code, R.id.phone_sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_get_code:
                String tel=phoneNumber.getText().toString();
                boolean isOK= CommonUtils.isMobileNO(tel);
                if (isOK){
//                    showShortToast("正在发送验证码，请稍后");
                    GetSMS getSMS =new GetSMS(tel);
                    getSMS.execute();
                }else{
                    showLongToast("请输入正确的手机号");
                }

                break;
            case R.id.phone_sign_in_button:
                String phone=phoneNumber.getText().toString();
                String pass=passWord2.getText().toString();
                if (CheckStatus()){
                    UserRegisterPhone(phone, MD5Util.MD5(pass));
                }
                break;
        }
    }

    private boolean CheckStatus() {

        if (phoneNumber.getText().toString().equals("")){
            showShortToast("手机号不能为空");
            return false;
        }else if(!CommonUtils.isMobileNO(phoneNumber.getText().toString().trim())){
            showShortToast("手机号码格式不正确");
            return false;
        }
        else if (passWord1.getText().toString().equals("")){
            showShortToast("密码不能为空");
            return false;
        }
        else if (passWord2.getText().toString().equals("")){
            showShortToast("请再次输入密码");
            return false;
        }
        else if (passWord1.getText().toString().trim().length()<6){
            showShortToast("您的密码太短了");
            return false;
        }
        else if (!passWord1.getText().toString().trim().equals(passWord2.getText().toString().trim())){
            showShortToast("您两次输入的密码不同！");
            return false;
        }
        else if (phoneCode.getText().toString().equals("")){
            showShortToast("验证码不能为空");
            return false;
        }
        else if (smsCode==null){
            showShortToast("验证码不正确");
            return false;
        }
        else if (smsCode.getCode().equals("")
                ||!phoneCode.getText().toString().trim().equals(smsCode.getText())){
            showShortToast("验证码不正确");
            return false;
        }
        return true;
    }

    /**
     * 手机验证码Task
     */
    public class GetSMS extends AsyncTask<Void, Void, User> {

        private final String tel;
        SweetAlertDialog pDialog = new SweetAlertDialog(ChangPssActivity.this, SweetAlertDialog.PROGRESS_TYPE);

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
            pDialog.setTitleText("登陆中...");
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
                    .url(Constants.REC_SMS)
                    .addParams("tel", tel)
                    .addParams("only", only)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new CodeCallback() {

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message=new Message();
                            message.obj=e.toString();
                            message.what=MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(SmsCode response, int id) {
                            Message message=new Message();
                            message.obj=response;
                            message.what=MSG_PHONE_SUCCESS;
                            mHandler.sendMessage(message);
                        }

                    });
        }


    }


        /**
         * UserRegisterPhone
         * 修改密码
         * @param phone
         * @param s
         */
        public void UserRegisterPhone(String phone, String s) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.CHANGE_PASSWORD)
                    .addParams("only", only)
                    .addParams("tel", phone)
                    .addParams("password", s)
                    .build()
                    .connTimeOut(30000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new BaseCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message=new Message();
                            message.obj=e.getMessage();
                            message.what=MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean response, int id) {
                            if (response.getCode().equals("200")){
                                Message message=new Message();
                                message.obj=response;
                                message.what=MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            }else {
                                Message message=new Message();
                                message.obj=response.getMessage();
                                message.what=MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }
