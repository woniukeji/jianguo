package com.haibin.qiaqia.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;
import com.haibin.qiaqia.base.Constants;
import com.haibin.qiaqia.entity.User;
import com.haibin.qiaqia.http.HttpMethods;
import com.haibin.qiaqia.http.ProgressSubscriber;
import com.haibin.qiaqia.http.SubscriberOnNextListener;
import com.haibin.qiaqia.main.MainActivity;
import com.haibin.qiaqia.utils.MD5Util;
import com.haibin.qiaqia.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPassWordActivity extends BaseActivity {

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.textView1) TextView textView1;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.tv_sms_login) TextView tvSmsLogin;
    @BindView(R.id.imageView2) ImageView imageView2;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.tv_go_register) TextView tvGoRegister;
    @BindView(R.id.activity_login) RelativeLayout activityLogin;
    private SubscriberOnNextListener<User> loginSubListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        //处理接口返回的数据
        loginSubListener=new SubscriberOnNextListener<User>() {

            @Override
            public void onNext(User user) {
                if (user.getCode().equals("200")){
                    Toast.makeText(LoginPassWordActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    SPUtils.setParam(LoginPassWordActivity.this, Constants.USER_LOGIN,Constants.LOGIN_PHONE,user.getData().getIUserLogin().getPhone());
                    SPUtils.setParam(LoginPassWordActivity.this, Constants.USER_LOGIN,Constants.LOGIN_PASSWORD,user.getData().getIUserLogin().getPassword());
                    SPUtils.setParam(LoginPassWordActivity.this, Constants.USER_LOGIN,Constants.LOGIN_STATUS,user.getData().getIUserLogin().getStatus());
                    SPUtils.setParam(LoginPassWordActivity.this, Constants.USER_LOGIN,Constants.LOGIN_TYPE,1);
                    SPUtils.setParam(LoginPassWordActivity.this, Constants.USER_INFO,Constants.INFO_IMG,user.getData().getIUserInfo().getNameImage());
                    SPUtils.setParam(LoginPassWordActivity.this, Constants.USER_INFO,Constants.INFO_ID,user.getData().getIUserInfo().getLoginId());
                    SPUtils.setParam(LoginPassWordActivity.this, Constants.USER_INFO,Constants.INFO_NAME,user.getData().getIUserInfo().getName());
                    startActivity(new Intent(LoginPassWordActivity.this, MainActivity.class));
                    finish();
                }else
                    Toast.makeText(LoginPassWordActivity.this,user.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }

    //进行网络请求
    private void postRegister() {

    }

    @OnClick({R.id.tv_sms_login, R.id.btn_login, R.id.tv_go_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sms_login:
                break;
            case R.id.btn_login:
                if (checkStatus()){
                    HttpMethods.getInstance().Login(new ProgressSubscriber<User>(loginSubListener,LoginPassWordActivity.this),phone.getText().toString().trim(),
                            MD5Util.MD5( password.getText().toString().trim()));
                }
                break;
            case R.id.tv_go_register:
                startActivity(new Intent(LoginPassWordActivity.this,RegiterActivity.class));
                break;
        }
    }
    private boolean checkStatus() {
        if (phone.getText().toString().trim()==null||phone.getText().toString().trim().equals("")){
            showShortToast("请输入手机号码");
            return false;
        }else if (password.getText().toString().trim().length()==10){
            showShortToast("密码不能为空");
            return false;
        }
        return true;
    }
}
