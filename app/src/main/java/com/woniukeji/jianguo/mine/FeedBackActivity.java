package com.woniukeji.jianguo.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.utils.ActivityManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.et_content) EditText etContent;
    @InjectView(R.id.et_contact) EditText etContact;
    @InjectView(R.id.btn_confirm) Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_feed_back);
        ButterKnife.inject(this);
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
        ActivityManager.getActivityManager().addActivity(FeedBackActivity.this);
    }



    @OnClick({R.id.img_back, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_confirm:
                if (etContent.getText().toString().equals("")){
                    showShortToast("请输入反馈内容");
                    return;
                }else if(etContact.getText().toString().equals("")){
                    showShortToast("请输入联系方式");
                    return;
                }
               showShortToast("感谢您的反馈！我们将及时处理");
                finish();
                break;
        }
    }
}
