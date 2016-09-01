package com.woniukeji.jianguo.activity.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.utils.ActivityManager;

import butterknife.ButterKnife;
import butterknife.BindView;

public class EvaluationActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("评价");
    }

    @Override
    public void initListeners() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(EvaluationActivity.this);
    }

    @Override
    public void onClick(View view) {

    }


}
