package com.haibin.qiaqia.main;

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
import com.haibin.qiaqia.R;
import com.haibin.qiaqia.base.BaseActivity;
import com.haibin.qiaqia.cart.CartFragment;
import com.haibin.qiaqia.entity.TabEntity;
import com.haibin.qiaqia.home.HomeFragment;
import com.haibin.qiaqia.utils.ActivityManager;
import com.haibin.qiaqia.utils.LogUtils;
import com.haibin.qiaqia.widget.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private String[] titles = {"首页", "购物车",  "我的"};//"果聊",
    private int[] mIconUnselectIds = {
            R.mipmap.shouye,
            R.mipmap.gouwuche,
            R.mipmap.wode};
    //R.mipmap.tab_guo_talk_unselect,
    private int[] mIconSelectIds = {
            R.mipmap.shouye_on,
            R.mipmap.gouwuche_on,
            R.mipmap.wode_on};
    // R.mipmap.tab_guo_talk_select,
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private long exitTime;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
//    ArrowDownloadButton button;
    private Handler mHandler = new Myhandler(this);
    private Context context = MainActivity.this;
    private ImageView imgeMainLead;
    private int clickTime=0;
    private String apkurl;

    int b = 0;
    private RelativeLayout up_dialog;

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
//                    BaseBean<CityCategory> obj = (BaseBean<CityCategory>) msg.obj;
//                    CityJobTypeEvent cityJobTypeEvent=new CityJobTypeEvent();
//                    cityJobTypeEvent.cityCategory=obj.getData();
//                   EventBus.getDefault().post(cityJobTypeEvent);
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
//        button = (ArrowDownloadButton)findViewById(R.id.arrow_download_button);
//        up_dialog= (RelativeLayout) findViewById(R.id.up_dialog);
//        int version = (int) SPUtils.getParam(MainActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_VERSION, 0);
//        apkurl = (String) SPUtils.getParam(MainActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_APK_URL, "");
//        if (version > getVersion()) {//大于当前版本升级
//            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
//                    .setTitleText("检测到新版本，是否更新？")
//                    .setConfirmText("确定")
//                    .setCancelText("取消")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            sDialog.dismissWithAnimation();
////                            SweetAlertDialog downLoadDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
////                            downLoadDialog.setTitleText("正在下载新版本");
////                            downLoadDialog.show();
//                            up_dialog.setVisibility(View.VISIBLE);
//                            button.startAnimating();
//                            downLoadTask downLoadTask = new downLoadTask();
//                            downLoadTask.execute();
//                        }
//                    }).show();
//        }
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

    public class downLoadTask extends AsyncTask<Void, Void, Void> {
        private SweetAlertDialog sweetAlertDialog;

        downLoadTask(SweetAlertDialog sweetAlertDialog) {
            this.sweetAlertDialog = sweetAlertDialog;
        }
        downLoadTask() {

        }
        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getCitys();
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
        public void getCitys() {

            OkHttpUtils
                    .get()
                    .url(apkurl)
                    .build()
                    .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "jianguoApk")//
                    {
                        @Override
                        public void inProgress( float progress) {
                            Message message=new Message();
                            message.what=2;
                            float tem=progress*100;
                             b = (int)tem;
                            message.arg1=b;
                            int i = (int) Math.round(progress+0.5);
//                             mHandler.sendMessage(message);
                            LogUtils.e("mes", progress+"pro"+b+"mes"+i);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    button.setProgress(b);
                                }
                            });
//                            loadingView.setImageBitmap(
//                                    BitmapFactory.decodeResource(getResources(), R.drawable.icon_chat_photo));
//                            sweetAlertDialog.getProgressHelper().setProgress(progress);
//                            sweetAlertDialog.getProgressHelper().setCircleRadius((int)progress*100);
                        }

                        @Override
                        public void onError(Call call, Exception e) {

                        }


                        @Override
                        public void onResponse(File file) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    up_dialog.setVisibility(View.GONE);
                                }
                            });

                            openFile(file);

                        }
                    });
        }
    }
    private void openFile(File file) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion() {
        try {
            PackageManager manager = MainActivity.this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(MainActivity.this.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
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
    }

    @Override
    public void initData() {

//        int loginId = (int) SPUtils.getParam(MainActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
//            int First = (int) SPUtils.getParam(MainActivity.this, Constants.LOGIN_INFO, Constants.SP_FIRST, 0);
//        if (First==0){
            imgeMainLead.setVisibility(View.GONE);
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
//    public void onEvent(QuickLoginEvent event) {
//        if (event.isQuickLogin){
//            tabHost.setCurrentTab(0);
//            mainPager.setCurrentItem(0);
//        }
//    }


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
                    return new CartFragment();          //话题榜
                case 2:
                    return new CartFragment();  //用户榜
                default:
                    return new CartFragment();
            }

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
