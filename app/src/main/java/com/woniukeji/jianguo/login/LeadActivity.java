package com.woniukeji.jianguo.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.PicassoLoader;
import com.woniukeji.jianguo.utils.SPUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.page.OnPageClickListener;
import cn.lightsky.infiniteindicator.page.Page;

public class LeadActivity extends Activity implements OnPageClickListener,ViewPager.OnPageChangeListener {

    @InjectView(R.id.indicator_default_circle) InfiniteIndicator indicatorDefaultCircle;
    @InjectView(R.id.btn_enter) Button btnEnter;
    private InfiniteIndicator mAnimCircleIndicator;
    private ArrayList<Page> pageViews = new ArrayList<>();
    private int[] drawables = new int[]{R.mipmap.lead1, R.mipmap.lead2, R.mipmap.lead3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        ButterKnife.inject(this);


        mAnimCircleIndicator = (InfiniteIndicator) findViewById(R.id.indicator_default_circle);
        mAnimCircleIndicator.setImageLoader(new PicassoLoader());

        mAnimCircleIndicator.setPosition(InfiniteIndicator.IndicatorPosition.Center_Bottom);
        mAnimCircleIndicator.setOnPageChangeListener(this);
        initBannerData();
        mAnimCircleIndicator.isStopScrollWhenTouch();
        mAnimCircleIndicator.scrollOnce();

    }



    @Override
    public void onPageClick(int position, Page page) {

    }

    private void initBannerData() {
        for (int i = 0; i < 3; i++) {
            pageViews.add(new Page(String.valueOf(String.valueOf(i)), drawables[i]));
        }
        mAnimCircleIndicator.addPages(pageViews);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAnimCircleIndicator.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimCircleIndicator.start();
//        mAnimCircleIndicator.start();
    }

    @Override
    protected void onStop() {
        mAnimCircleIndicator.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.btn_enter)
    public void onClick() {
        SPUtils.setParam(LeadActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "1");
        startActivity(new Intent(LeadActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position==2||position==5||position==8||position==11){
            btnEnter.setVisibility(View.VISIBLE);
            mAnimCircleIndicator.stop();
        } else{
            btnEnter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position==2||position==5||position==8||position==11){
            btnEnter.setVisibility(View.VISIBLE);
            mAnimCircleIndicator.stop();
        } else{
            btnEnter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
