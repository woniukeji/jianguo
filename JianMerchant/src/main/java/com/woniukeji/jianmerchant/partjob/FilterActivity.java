package com.woniukeji.jianmerchant.partjob;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.FragmentText;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.utils.ActivityManager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FilterActivity extends BaseActivity {

    @InjectView(R.id.tl_6) CommonTabLayout tl6;
    @InjectView(R.id.mainPager) ViewPager mainPager;
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"已报名", "已录取","已取消"};
    private int[] mIconUnselectIds = {

            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_unselect};
    private int[] mIconSelectIds = {

            R.mipmap.tab_guo_talk_select,
            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_select};
    private ViewPagerAdapter adapter;
    private String jobid;
    private String jobName;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_filter);
        ButterKnife.inject(this);
        Intent intent=getIntent();
        jobid=intent.getStringExtra("jobid");
        jobName=intent.getStringExtra("jobname");
    }

    @Override
    public void initViews() {
        tvTitle.setText("报名录取详情");
          adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(adapter);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tl6.setTabData(mTabEntities);
        tl6.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mainPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
//                mainPager.setCurrentItem(position);
            }
        });
        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tl6.setCurrentTab(position);
//                if (position==2){
//                    tl6.hideMsg(2);
//                }

            }

            @Override
            public void onPageScrollStateChanged(int stateFilterFragment) {

            }
        });
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
        ActivityManager.getActivityManager().addActivity(FilterActivity.this);
    }

    @Override
    public void onClick(View view) {

    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return  FilterFragment.newInstance(0,jobid,jobName+"(已报名)");           //已报名
                case 1:
                    return  FilterFragment.newInstance(1,jobid,jobName+"(已录取)");           //已录取
                case 2:
                    return  FilterFragment.newInstance(2,jobid,jobName+"(已取消)");    //取消
            }
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
