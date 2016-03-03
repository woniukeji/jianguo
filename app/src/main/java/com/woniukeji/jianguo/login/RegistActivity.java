package com.woniukeji.jianguo.login;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;

/**
 * A login screen that offers login via email/password.
 */
public class RegistActivity extends BaseActivity {

    private String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";
    private BroadcastReceiver smsReceiver;
    private IntentFilter filter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        // Set up the login form.

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

}

