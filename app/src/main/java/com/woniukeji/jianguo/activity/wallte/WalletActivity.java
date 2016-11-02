package com.woniukeji.jianguo.activity.wallte;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.base.FragmentText;
import com.woniukeji.jianguo.entity.Balance;
import com.woniukeji.jianguo.entity.TabEntity;
import com.woniukeji.jianguo.http.HttpMethods;
import com.woniukeji.jianguo.http.ProgressSubscriber;
import com.woniukeji.jianguo.http.SubscriberOnNextListener;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.SPUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;

public class WalletActivity extends BaseActivity {

    @BindView(R.id.tl_6) CommonTabLayout tl6;
    @BindView(R.id.mainPager) ViewPager mainPager;
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView imgShare;
    @BindView(R.id.top) RelativeLayout top;
    @BindView(R.id.tv_money_sum) TextView tvMoneySum;
    @BindView(R.id.tv_action_get) TextView tvActionGet;
    @BindView(R.id.rl_wallte_info) RelativeLayout rlWallteInfo;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private SubscriberOnNextListener<Balance> subscriberOnNextListener;
    private Balance balance;
    private Context context = WalletActivity.this;
    private String[] mTitles = {"收入明细", "支出明细"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_unselect};
    private int[] mIconSelectIds = {

            R.mipmap.tab_guo_talk_select,
            R.mipmap.tab_about_me_select};
    private ViewPagerAdapter adapter;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_wallte);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("我的钱包");
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
        tvActionGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (balance.getData().getT_user_money().getPay_status()==1){
                    showShortToast("尚未进行实名认证，不能进行提现操作！");
                    return;
                }else if(balance.getData().getT_user_money().getPay_status()==3){
                    showShortToast("实名认证审核中，不能进行提现操作！");
                    return;
                }else if(balance.getData().getT_user_money().getPay_status()==4){
                    showShortToast("实名认证被拒绝，请重新申请实名认证！");
                    return;
                }
                Intent intent=new Intent(WalletActivity.this,DrawMoneyActivity.class);
                intent.putExtra("balance",balance);
                startActivity(intent);
            }
        });
        subscriberOnNextListener=new SubscriberOnNextListener<Balance>() {
            @Override
            public void onNext(Balance mBalance) {
                balance=mBalance;
                tvMoneySum.setText(balance.getData().getT_user_money().getMoney()+"");

//                if (balance.getData().getT_user_money().getPay_status()!=2){
//                    showShortToast("实名认证尚未通过，不能进行提现操作！");
//                }
            }
        };

    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        int loginId = (int) SPUtils.getParam(WalletActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        HttpMethods.getInstance().getWallte(new ProgressSubscriber<Balance>(subscriberOnNextListener,this), String.valueOf(loginId));
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(WalletActivity.this);
    }

    @Override
    public void onClick(View v) {

    }
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new WallteInFragment();           //直播榜
                case 1:
                    return new WallteOutFragment();          //话题榜
            }
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
