package com.woniukeji.jianguo.login;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.FragmentText;
import com.woniukeji.jianguo.partjob.PartJobAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link } subclass.
 */
public class LoginActivity extends BaseActivity implements OnTabSelectListener {

    @InjectView(R.id.tabHost) SegmentTabLayout tabHost;
    @InjectView(R.id.mainPager) ViewPager mainPager;
    private PartJobAdapter adapter;
    private int lastVisibleItem;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = new String[]{
            "手机号快捷登陆", "账号密码登录"
    };
    private MyPagerAdapter mAdapter;
    private Handler mHandler = new Myhandler(this);

    @Override
    public void onClick(View v) {

    }



    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginActivity mainActivity = (LoginActivity) reference.get();
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(mainActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }

    @Override
    public void addActivity() {

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void initViews() {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mFragments.add(new QuickLoginFragment());
        mFragments.add(new PasswordLoginFragment());
        mainPager.setAdapter(mAdapter);
        tabHost.setTabData( mTitles);

    }

    @Override
    public void initData() {
    }

    @Override
    public void initListeners() {
        tabHost.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                    mainPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
//                mainPager.setCurrentItem(position);
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mainPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }else {
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
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
//        EventBus.getDefault().unregister(this);
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }
}
