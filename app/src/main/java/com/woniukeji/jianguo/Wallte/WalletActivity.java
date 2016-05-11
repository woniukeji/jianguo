package com.woniukeji.jianguo.wallte;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.base.FragmentText;
import com.woniukeji.jianguo.entity.Balance;
import com.woniukeji.jianguo.entity.TabEntity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

public class WalletActivity extends BaseActivity {

    @InjectView(R.id.tl_6) CommonTabLayout tl6;
    @InjectView(R.id.mainPager) ViewPager mainPager;
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.top) RelativeLayout top;
    @InjectView(R.id.tv_money_sum) TextView tvMoneySum;
    @InjectView(R.id.tv_action_get) TextView tvActionGet;
    @InjectView(R.id.rl_wallte_info) RelativeLayout rlWallteInfo;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
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
    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;


        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WalletActivity activity = (WalletActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    if (null != schoolActivity.pDialog) {
//                    schoolActivity.pDialog.dismiss();
//                }
                     activity.balance= (Balance) msg.obj;
                    activity.tvMoneySum.setText(activity.balance.getData().getT_user_money().getMoney()+"");

//                    if (activity.balance.getData().getT_user_money().getPay_password().equals("0")){
//                        activity.startActivity(new Intent(activity,DrawPassActivity.class));
//                        activity.showShortToast("请先设置提现密码！");
//                        activity.finish();
//                    }


                    break;
                case 1:
//                    if (null != authActivity.pDialog) {
//                        authActivity.pDialog.dismiss();
//                    }
                    String ErrorMessage = (String) msg.obj;
                    break;


                default:
                    break;
            }
        }


    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_wallte);
        ButterKnife.inject(this);
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
        tvActionGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WalletActivity.this,DrawMoneyActivity.class);
                intent.putExtra("balance",balance);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {
        int loginId = (int) SPUtils.getParam(WalletActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int loginId = (int) SPUtils.getParam(WalletActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);

        GetTask getTask=new GetTask(String.valueOf(loginId));
        getTask.execute();
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
            return 4;
        }
    }

    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String loginId;

        GetTask(String loginId) {
            this.loginId = loginId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getBalance();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * postInfo
         */
        public void getBalance() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_BALANCE_INFO)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<Balance>() {
                        @Override
                        public Balance parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            Balance baseBean = new Gson().fromJson(string, new TypeToken<Balance>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(Balance baseBean) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
                                message.what = MSG_GET_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }
}
