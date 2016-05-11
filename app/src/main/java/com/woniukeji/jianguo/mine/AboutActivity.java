package com.woniukeji.jianguo.mine;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.main.WebViewActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AboutActivity extends Activity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.img_log) ImageView imgLog;
    @InjectView(R.id.img_good) ImageView imgGood;
    @InjectView(R.id.img_bad) ImageView imgBad;
    @InjectView(R.id.ll_img) LinearLayout llImg;
    @InjectView(R.id.bootom) TextView bootom;
    @InjectView(R.id.tv_copration) RelativeLayout tvCopration;
    @InjectView(R.id.tv_leave_opinion) RelativeLayout tvLeaveOpinion;
    @InjectView(R.id.rl_rule) RelativeLayout rlRule;
    @InjectView(R.id.rl_phone) RelativeLayout rlPhone;
    @InjectView(R.id.tv_number) TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.img_back, R.id.tv_leave_opinion, R.id.rl_phone, R.id.rl_rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_phone:

                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvNumber.getText().toString().trim()));
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent2);
                break;
            case R.id.tv_leave_opinion:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.woniukeji.jianguo");
//                intent.setData(content_url);
//                startActivity(intent);
                break;
            case R.id.rl_rule:
                Intent intent1 = new Intent(AboutActivity.this, WebViewActivity.class);
                intent1.putExtra("url", "http://101.200.205.243:8080/user_agreement.jsp");
                startActivity(intent1);
                break;
        }
    }
}