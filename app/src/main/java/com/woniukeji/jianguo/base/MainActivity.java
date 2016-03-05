package com.woniukeji.jianguo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.entity.TabEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 */
public class MainActivity extends BaseActivity {

    @InjectView(R.id.tabHost) CommonTabLayout tabHost;
    @InjectView(R.id.mainPager) ViewPager mainPager;
    private ViewPagerAdapter adapter;
    private String[] titles = {"首页", "兼职", "果聊","我"};

    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect,
            R.mipmap.tab_more_unselect,
            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select,
            R.mipmap.tab_more_select ,
            R.mipmap.tab_guo_talk_select,
            R.mipmap.tab_about_me_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);

    }

    @Override
    public void initViews() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        tabHost = (CommonTabLayout) findViewById(R.id.tabHost);
        mainPager = (ViewPager) findViewById(R.id.mainPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(adapter);
        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabhost();
    }
    private void tabhost() {
        tabHost.setTabData(mTabEntities);
//        mTabWidget.setIconHeight();
        tabHost.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                        mainPager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    mTabWidget.showMsg(0, random.nextInt(100) + 1);
//                    UnreadMsgUtils.show(tl_2.getMsgView(0), random.nextInt(100) + 1);
                }
            }
        });
        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    tabHost.setCurrentTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mainPager.setCurrentItem(0);
    }


    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
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
                    return new FragmentText();           //直播榜
                case 1:
                    return new FragmentText();          //话题榜
                case 2:
                    return new FragmentText();
                case 3:
                    return new FragmentText();  //用户榜
            }
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
