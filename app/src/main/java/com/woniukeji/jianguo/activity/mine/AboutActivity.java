package com.woniukeji.jianguo.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.main.WebViewActivity;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends Activity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView imgShare;
    @BindView(R.id.img_log) ImageView imgLog;
    @BindView(R.id.bootom) TextView bootom;
    @BindView(R.id.tv_copration) RelativeLayout tvCopration;
    @BindView(R.id.tv_leave_opinion) RelativeLayout tvLeaveOpinion;
    @BindView(R.id.rl_rule) RelativeLayout rlRule;
    @BindView(R.id.rl_phone) RelativeLayout rlPhone;
    @BindView(R.id.tv_number) TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        TextView tvVersion=(TextView)findViewById(R.id.tv_version);
        tvVersion.setText("版本：" +getVersionName());

    }
    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersionName() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "3.0";
        }
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
