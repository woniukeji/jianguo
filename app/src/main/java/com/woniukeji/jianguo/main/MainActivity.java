package com.woniukeji.jianguo.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.eventbus.CityJobTypeEvent;
import com.woniukeji.jianguo.eventbus.QuickLoginEvent;
import com.woniukeji.jianguo.leanmessage.ChatManager;
import com.woniukeji.jianguo.leanmessage.ImTypeMessageEvent;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.FragmentText;
import com.woniukeji.jianguo.entity.TabEntity;
import com.woniukeji.jianguo.mine.MineFragment;
import com.woniukeji.jianguo.partjob.PartJobFragment;
import com.woniukeji.jianguo.talk.TalkFragment;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.LocationUtil;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */
public class MainActivity extends BaseActivity {

    @InjectView(R.id.tabHost) CommonTabLayout tabHost;
    @InjectView(R.id.mainPager) ViewPager mainPager;
    private ViewPagerAdapter adapter;
    private String[] titles = {"首页", "兼职",  "我"};//"果聊",

    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect,
            R.mipmap.tab_partjob_unselect,
            R.mipmap.tab_about_me_unselect};
    //R.mipmap.tab_guo_talk_unselect,
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select,
            R.mipmap.tab_partjob_select,
            R.mipmap.tab_about_me_select};
    // R.mipmap.tab_guo_talk_select,
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private long exitTime;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;

    private Handler mHandler = new Myhandler(this);
    private Context context = MainActivity.this;
    private ImageView imgeMainLead;
    private int clickTime=0;


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<CityCategory> obj = (BaseBean<CityCategory>) msg.obj;
                    CityJobTypeEvent cityJobTypeEvent=new CityJobTypeEvent();
                    cityJobTypeEvent.cityCategory=obj.getData();
                   EventBus.getDefault().post(cityJobTypeEvent);
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(activity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    public ViewPager getMainPager() {
        return mainPager;
    }

    @Override
    public void initViews() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        tabHost = (CommonTabLayout) findViewById(R.id.tabHost);
        mainPager = (ViewPager) findViewById(R.id.mainPager);
        imgeMainLead=(ImageView)findViewById(R.id.img_main_lead);
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
                if (position == 2) {
                    tabHost.hideMsg(2);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mainPager.setCurrentItem(0);
    }


    @Override
    public void initListeners() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initData() {

        int loginId = (int) SPUtils.getParam(MainActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
            int First = (int) SPUtils.getParam(MainActivity.this, Constants.LOGIN_INFO, Constants.SP_FIRST, 0);
        if (First==0){
            imgeMainLead.setVisibility(View.VISIBLE);
            imgeMainLead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(clickTime==0){
                        imgeMainLead.setBackgroundResource(R.mipmap.img_three);
                    }else if(clickTime==1){
                        imgeMainLead.setBackgroundResource(R.mipmap.img_four);
                    }else if(clickTime==2){
                        imgeMainLead.setBackgroundResource(R.mipmap.img_four);
                        imgeMainLead.setVisibility(View.GONE);
                        SPUtils.setParam(MainActivity.this, Constants.LOGIN_INFO, Constants.SP_FIRST, 1);
                    }
                    clickTime++;
                }
            });
        }

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(MainActivity.this);
    }

    /**
     * 处理推送过来的消息
     * 首页tab显示维度消息
     */
    public void onEvent(ImTypeMessageEvent event) {
        tabHost.showDot(2);
    }
    public void onEvent(QuickLoginEvent event) {
        if (event.isQuickLogin){
            tabHost.setCurrentTab(0);
            mainPager.setCurrentItem(0);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        timer.schedule(task,2000);
    }
//    Timer timer = new Timer();
//    TimerTask task = new TimerTask(){
//
//        public void run() {
//
//            timer.cancel();
//        }

//    };
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                    return new HomeFragment();           //直播榜
                case 1:
                    return new PartJobFragment();          //话题榜
                case 2:
                    return new MineFragment();  //用户榜

//                case 3:
//                    return new TalkFragment();
            }
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();

            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
