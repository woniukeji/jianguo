package com.haibin.qiaqia.fruitvegetables;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FruitVegetableActivity extends BaseActivity {
    @BindView(R.id.vp) ViewPager vp;

    private final String[] mTitles = new String[]{
            "水果", "蔬菜"
    };
    private MyPagerAdapter mAdapter;
    private RelativeLayout rl_click;
    @BindView(R.id.sliding_tab) SlidingTabLayout slidingTab;
    @BindView(R.id.activity_fruit_vegetable) RelativeLayout activityFruitVegetable;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_fruit_vegetable);
        ButterKnife.bind(this);

        rl_click = (RelativeLayout) findViewById(R.id.rl_click);


    }

    @Override
    public void initViews() {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        slidingTab.setViewPager(vp, mTitles);



    }

    @Override
    public void initListeners() {
        rl_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }



    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return FruitFragment.getInstance("1");
            }else{
                return  FruitFragment.getInstance("2");
            }
        }
        }
    }
