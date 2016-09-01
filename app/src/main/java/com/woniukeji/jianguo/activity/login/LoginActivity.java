package com.woniukeji.jianguo.activity.login;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.adapter.PartJobAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link } subclass.
 */
public class LoginActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.tabHost) SegmentTabLayout tabHost;
    @BindView(R.id.mainPager) ViewPager mainPager;
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_register) TextView tvRegister;
    @BindView(R.id.top) RelativeLayout top;
    private PartJobAdapter adapter;
    private int lastVisibleItem;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = new String[]{
            "手机号快捷登陆", "账号密码登录"
    };
    private MyPagerAdapter mAdapter;
    private Handler mHandler = new Myhandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
                break;
        }
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
                    TastyToast.makeText(mainActivity, sms, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
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
        Unbinder bind = ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void initViews() {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mFragments.add(new QuickLoginFragment());
        mFragments.add(new PasswordLoginFragment());
        mainPager.setAdapter(mAdapter);
        tabHost.setTabData(mTitles);

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mainPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        } else {
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
