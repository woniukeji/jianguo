package com.woniukeji.jianguo.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.leanmessage.ChatManager;
import com.woniukeji.jianguo.login.QuickLoginActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img) ImageView img;
    @InjectView(R.id.changePassword) RelativeLayout changePassword;
    @InjectView(R.id.refresh) RelativeLayout refresh;
    @InjectView(R.id.or_img) ImageView orImg;
    @InjectView(R.id.about) RelativeLayout about;
    @InjectView(R.id.btn_logout) Button btnLogout;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);

    }

    @Override
    public void initViews() {
        tvTitle.setText("设置");

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
//        ActivityManager.getActivityManager().addActivity(SettingActivity.this);
    }


    @OnClick({R.id.img_back, R.id.changePassword, R.id.refresh, R.id.about,R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.changePassword:
//                startActivity(new Intent(SettingActivity.this, ChangPssActivity.class));
//                finish();
                break;
            case R.id.refresh:
                showShortToast("已经是最新版本");
                break;
            case R.id.btn_logout:
                ChatManager chatManager = ChatManager.getInstance();
                chatManager.closeWithCallback(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                    }
                });
                ActivityManager.getActivityManager().finishAllActivity();
                SPUtils.deleteParams(SettingActivity.this);
                startActivity(new Intent(SettingActivity.this, QuickLoginActivity.class));
                finish();
                break;
            case R.id.about:

                break;
        }
    }




}
