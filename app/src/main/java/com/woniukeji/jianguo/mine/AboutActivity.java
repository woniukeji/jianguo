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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.img_back, R.id.img_good, R.id.img_bad})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_good:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.woniukeji.jianguo");
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.img_bad:
                Intent intent1 = new Intent();
                intent1.setAction("android.intent.action.VIEW");
                Uri content_url1 = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.woniukeji.jianguo");
                intent1.setData(content_url1);
                startActivity(intent1);
                break;
        }
    }
}
