package com.haibin.qiaqia.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;
import com.haibin.qiaqia.entity.User;
import com.haibin.qiaqia.http.HttpMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class LoginActivity extends BaseActivity {

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

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }

    //进行网络请求
    private void postRegister() {
        HttpMethods.getInstance().toRegister(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {

            }
        }, "only", "hha", "fd", "");
    }

    @OnClick({R.id.tv_sms_login, R.id.btn_login, R.id.tv_go_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sms_login:
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_go_register:
                startActivity(new Intent(LoginActivity.this,RegiterActivity.class));
                break;
        }
    }
}
