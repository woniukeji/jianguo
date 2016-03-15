package com.woniukeji.jianguo.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;

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


    @OnClick({R.id.img_back, R.id.changePassword, R.id.refresh, R.id.about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.changePassword:
                break;
            case R.id.refresh:
                break;
            case R.id.about:
                break;
        }
    }
}
