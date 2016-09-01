package com.woniukeji.jianguo.activity.mine;

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
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.base.FragmentText;
import com.woniukeji.jianguo.entity.TabEntity;
import com.woniukeji.jianguo.utils.ActivityManager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;

public class CollectActivity extends BaseActivity {

    @BindView(R.id.tl_6) CommonTabLayout tl6;
    @BindView(R.id.mainPager) ViewPager mainPager;
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"收藏的兼职", "关注的商家"};
    private int[] mIconUnselectIds = {

            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_unselect};
    private int[] mIconSelectIds = {

            R.mipmap.tab_guo_talk_select,
            R.mipmap.tab_about_me_select};
    private ViewPagerAdapter adapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("关注与收藏");
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
            public void onPageScrollStateChanged(int state) {

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
        ActivityManager.getActivityManager().addActivity(CollectActivity.this);
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
                    return new CollectionFragment();           //直播榜
                case 1:
                    return new AttentionFragment();          //话题榜
            }
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
