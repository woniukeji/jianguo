package com.woniukeji.jianguo.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.eventbus.CityJobTypeEvent;
import com.woniukeji.jianguo.eventbus.LoginEvent;
import com.woniukeji.jianguo.eventbus.QuickLoginEvent;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.FragmentText;
import com.woniukeji.jianguo.entity.TabEntity;
import com.woniukeji.jianguo.mine.MineFragment;
import com.woniukeji.jianguo.partjob.PartJobFragment;
import com.woniukeji.jianguo.setting.PereferenceActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import cn.leancloud.chatkit.event.LCIMUnReadCountEvent;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

/**
 *
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tabHost) CommonTabLayout tabHost;
    @BindView(R.id.mainPager) ViewPager mainPager;
    private ViewPagerAdapter adapter;
    private String[] titles = {"首页", "兼职","果聊", "我的"};//"果聊",
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect,
            R.mipmap.tab_partjob_unselect,
            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_unselect};
    //R.mipmap.tab_guo_talk_unselect,
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select,
            R.mipmap.tab_partjob_select,
            R.mipmap.tab_guo_talk_select,
            R.mipmap.tab_about_me_select};
    // R.mipmap.tab_guo_talk_select,
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private long exitTime;
    private Handler mHandler = new Myhandler(this);
    private Context context = MainActivity.this;

    int msgCount = 0;

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
//                    int pro=msg.arg1;
//                    activity. button.setProgress((int)pro);
//                    activity.loadingView.setPercent(pro);
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
//          loadingView = (CircleLoadingView) findViewById(R.id.loading);
        ButterKnife.bind(this);
        initSystemBar(this);
        Intent intent=this.getIntent();
        boolean login=intent.getBooleanExtra("login",false);
        if (login){
            LoginEvent loginEvent=new LoginEvent();
            loginEvent.login=true;
           EventBus.getDefault().post(loginEvent);
        }
    }

    public void onEvent(LCIMUnReadCountEvent event) {
        if (event.unReadCount!=0&& tabHost.getCurrentTab()!=2) {
            tabHost.showMsg(2,event.unReadCount);
            tabHost.setMsgMargin(2, -7, 5);
        }
    }
    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     * 当前选中的是果聊界面的时候不显示消息数量
     * 否则消息数量自加
     */
    public void onEvent(LCIMIMTypeMessageEvent messageEvent) {

        if (tabHost.getCurrentTab()==2) {
            tabHost.hideMsg(2);
            msgCount=0;
        }else {
            msgCount++;
            tabHost.showMsg(2,msgCount);
            tabHost.setMsgMargin(2, -7, 5);
        }
    }
    public static void initSystemBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(activity, true);

        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);

        tintManager.setStatusBarTintEnabled(true);

// 使用颜色资源

        tintManager.setStatusBarTintResource(R.color.app_bg);

    }





    @TargetApi(19)

    private static void setTranslucentStatus(Activity activity, boolean on) {

        Window win = activity.getWindow();

        WindowManager.LayoutParams winParams = win.getAttributes();

        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        if (on) {

            winParams.flags |= bits;

        } else {

            winParams.flags &= ~bits;

        }

        win.setAttributes(winParams);

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
//        imgeMainLead=(ImageView)findViewById(R.id.img_main_lead);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(adapter);
        mainPager .setOffscreenPageLimit(2);
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
        String hobby = (String) SPUtils.getParam(MainActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_HOBBY, "1");
        if (hobby.equals("0")){
            startActivity(new Intent(MainActivity.this, PereferenceActivity.class));
        }
//        if (First==0){
//            imgeMainLead.setVisibility(View.VISIBLE);
//            imgeMainLead.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   if(clickTime==0){
//                        imgeMainLead.setBackgroundResource(R.mipmap.img_three);
//                    }else if(clickTime==1){
//                        imgeMainLead.setBackgroundResource(R.mipmap.img_four);
//                    }else if(clickTime==2){
//                        imgeMainLead.setBackgroundResource(R.mipmap.img_four);
//                        imgeMainLead.setVisibility(View.GONE);
//                        SPUtils.setParam(MainActivity.this, Constants.LOGIN_INFO, Constants.SP_FIRST, 1);
//                    }
//                    clickTime++;
//                }
//            });
//        }

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(MainActivity.this);
    }

    /**
     * 处理推送过来的消息
     * 首页tab显示维度消息
     */
//    public void onEvent(ImTypeMessageEvent event) {
//        tabHost.showDot(2);
//    }
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

    }
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
                    return new LCIMConversationListFragment();//果聊
//                return new MineFragment();  //用户榜
                case 3:
                    return new MineFragment();  //用户榜

            }
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();

            } else {
                Intent i= new Intent(Intent.ACTION_MAIN);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                i.addCategory(Intent.CATEGORY_HOME);

                startActivity(i);
//                finish();
//                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
