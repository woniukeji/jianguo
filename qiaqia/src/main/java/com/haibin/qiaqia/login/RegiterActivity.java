package com.haibin.qiaqia.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;
import com.haibin.qiaqia.base.Constants;
import com.haibin.qiaqia.entity.BaseBean;
import com.haibin.qiaqia.entity.User;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.main.MainActivity;
import com.haibin.qiaqia.utils.DateUtils;
import com.haibin.qiaqia.utils.MD5Util;
import com.haibin.qiaqia.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class RegiterActivity extends BaseActivity {

    @BindView(R.id.btn_back) ImageView btnBack;
    @BindView(R.id.et_phone) EditText etPhone;
    @BindView(R.id.btn_sms) Button btnSms;
    @BindView(R.id.et_sms) EditText etSms;
    @BindView(R.id.et_ps1) EditText etPs1;
    @BindView(R.id.et_ps2) EditText etPs2;
    @BindView(R.id.img_check) ImageView imgCheck;
    @BindView(R.id.btn_register) Button btnRegister;
    @BindView(R.id.activity_regiter) LinearLayout activityRegiter;
    private SubscriberOnNextListener<User> registerSubListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        ButterKnife.bind(this);
        //处理接口返回的数据
        registerSubListener= new SubscriberOnNextListener<User>() {

            @Override
            public void onNext(User user) {
                if (user.getCode().equals("200")){
                    Toast.makeText(RegiterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    SPUtils.setParam(RegiterActivity.this, Constants.USER_LOGIN,Constants.LOGIN_PHONE,user.getData().getIUserLogin().getPhone());
                    SPUtils.setParam(RegiterActivity.this, Constants.USER_LOGIN,Constants.LOGIN_PASSWORD,user.getData().getIUserLogin().getPassword());
                    SPUtils.setParam(RegiterActivity.this, Constants.USER_LOGIN,Constants.LOGIN_STATUS,user.getData().getIUserLogin().getStatus());
                    SPUtils.setParam(RegiterActivity.this, Constants.USER_LOGIN,Constants.LOGIN_TYPE,1);
                    SPUtils.setParam(RegiterActivity.this, Constants.USER_INFO,Constants.INFO_IMG,user.getData().getIUserInfo().getNameImage());
                    SPUtils.setParam(RegiterActivity.this, Constants.USER_INFO,Constants.INFO_ID,user.getData().getIUserInfo().getLoginId());
                    SPUtils.setParam(RegiterActivity.this, Constants.USER_INFO,Constants.INFO_NAME,user.getData().getIUserInfo().getName());
                    startActivity(new Intent(RegiterActivity.this, MainActivity.class));
                    finish();
                }else
                    Toast.makeText(RegiterActivity.this,user.getMessage(),Toast.LENGTH_LONG).show();

            }
        };
    }

    @Override
    public void setContentView() {

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

    }

    @OnClick({R.id.btn_back, R.id.btn_sms, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_sms:
                if (etPhone.getText().toString().trim()==null||etPhone.getText().toString().trim().equals("")){
                    showShortToast("请输入手机号码");
                    break;
                }else if (etPhone.getText().toString().trim().length()==10){
                    showShortToast("手机号码不正确");
                    break;
                }
//                UserRegisterTask userRegisterTask=new UserRegisterTask(etPhone.getText().toString().trim(),
//                        etPs1.getText().toString().trim(),etSms.getText().toString().trim());
//                userRegisterTask.execute();
                HttpMethods.getInstance().getSms(new ProgressSubscriber<String>(registerSubListener,this),etPhone.getText().toString().trim()
                        );

                break;
            case R.id.btn_register:
                if ( checkStatus()){
//                    UserRegisterTask userRegisterTask1=new UserRegisterTask(etPhone.getText().toString().trim(),
//                            etPs1.getText().toString().trim(),etSms.getText().toString().trim());
//                    userRegisterTask1.execute();
                    HttpMethods.getInstance().toRegister(new ProgressSubscriber<User>(registerSubListener,this),etPhone.getText().toString().trim(),
                            MD5Util.MD5(etPs1.getText().toString().trim()),etSms.getText().toString().trim());
                }

                break;
        }
    }

    private boolean checkStatus() {
        if (etPhone.getText().toString().trim()==null||etPhone.getText().toString().trim().equals("")){
            showShortToast("请输入手机号码");
            return false;
        }else if (etPhone.getText().toString().trim().length()==10){
            showShortToast("手机号码不正确");
            return false;
        }else if (etSms.getText().toString().trim()==null||etSms.getText().toString().trim().equals("")){
            showShortToast("请输入验证码");
            return false;
        }else if (etPs1.getText().toString().trim()==null||etPs1.getText().toString().trim().equals("")||etPs2.getText().toString().trim()==null||etPs2.getText().toString().trim().equals("")){
            showShortToast("请输入密码");
            return false;
        }else if (!etPs1.getText().toString().trim().equals(etPs2.getText().toString().trim())){
            showShortToast("两次输入的密码不相同");
            return false;
        }
        return true;
    }




    /**
     * 手机注册Task
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, User> {

        private final String tel;
        private final String passWord;
        private final  String sms;

        UserRegisterTask(String phoneNum, String passWord ,String sms) {
            this.tel = phoneNum;
            this.passWord = passWord;
            this.sms=sms;
        }

        protected User doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            UserRegisterPhone();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final User user) {
        }

        /**
         * UserRegisterPhone
         * 通过手机号码注册
         */
        public void UserRegisterPhone() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_SMS)
                    .addParams("only", only)
                    .addParams("phone", tel)
//                    .addParams("password", passWord)
//                    .addParams("sms_code", sms)
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
                            message.obj = e.getMessage();
                        }

                        @Override
                        public void onResponse(BaseBean response) {
                            if (response.getCode().equals("")){
                                Message message = new Message();
                                message.obj = response;
                            }else{
                                Message message = new Message();
                                message.obj = response.getMessage();
                            }

                        }

                    });
        }
    }
}
