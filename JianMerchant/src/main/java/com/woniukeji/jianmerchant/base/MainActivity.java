package com.woniukeji.jianmerchant.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.affordwages.PassWordActivity;
import com.woniukeji.jianmerchant.mine.PersonExitActivity;
import com.woniukeji.jianmerchant.partjob.PartJobManagerActivity;
import com.woniukeji.jianmerchant.publish.PublishActivity;
import com.woniukeji.jianmerchant.talk.leanmessage.ImTypeMessageEvent;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 *
 */
public class MainActivity extends BaseActivity {

    @InjectView(R.id.img_back)
    ImageView imgBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.img_share)
    ImageView imgShare;
    @InjectView(R.id.first_image)
    ImageView firstImage;
    @InjectView(R.id.fbjz)
    LinearLayout fbjz;
    @InjectView(R.id.jzgl)
    LinearLayout jzgl;
    @InjectView(R.id.cwzx)
    LinearLayout cwzx;
    @InjectView(R.id.wd)
    LinearLayout wd;
    @InjectView(R.id.gy)
    LinearLayout gy;
    @InjectView(R.id.rck)
    LinearLayout rck;
    @InjectView(R.id.first_tablelayout)
    TableLayout firstTablelayout;
    @InjectView(R.id.tabHost)
    CommonTabLayout tabHost;
    @InjectView(R.id.mainPager)
    ViewPager mainPager;

    //    @InjectView(R.id.tabHost) CommonTabLayout tabHost;
//    @InjectView(R.id.mainPager) ViewPager mainPager;
//    private ViewPagerAdapter adapter;
//    private String[] titles = {"兼职", "个人"};
////    "消息",
//
//    private int[] mIconUnselectIds = {
//            R.mipmap.tab_home_unselect,
//            R.mipmap.tab_about_me_unselect};
////     R.mipmap.tab_guo_talk_unselect,
//    private int[] mIconSelectIds = {
//            R.mipmap.tab_home_select,
//
//            R.mipmap.tab_about_me_select};
////      R.mipmap.tab_guo_talk_select,
//    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

//    public ViewPager getMainPager() {
//        return mainPager;
//    }

    @Override
    public void initViews() {
        imgBack.setVisibility(View.GONE);
        imgShare.setVisibility(View.VISIBLE);
//        FragmentManager mFragmentManager = getSupportFragmentManager();
//        tabHost = (CommonTabLayout) findViewById(R.id.tabHost);
//        mainPager = (ViewPager) findViewById(R.id.mainPager);
//        adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        mainPager.setAdapter(adapter);
//        for (int i = 0; i < titles.length; i++) {
//            mTabEntities.add(new TabEntity(titles[i], mIconSelectIds[i], mIconUnselectIds[i]));
//        }
//        tabhost();
    }

//    private void tabhost() {
//        tabHost.setTabData(mTabEntities);
////        mTabWidget.setIconHeight();
//        tabHost.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                mainPager.setCurrentItem(position);
//
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//                if (position == 0) {
////                    mTabWidget.showMsg(0, random.nextInt(100) + 1);
////                    UnreadMsgUtils.show(tl_2.getMsgView(0), random.nextInt(100) + 1);
//                }
//            }
//        });
//        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                tabHost.setCurrentTab(position);
//                if (position == 1) {
//                    tabHost.hideMsg(1);
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        mainPager.setCurrentItem(0);
//    }


    @Override
    public void initListeners() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
//        AVObject testObject = new AVObject("TestConversiation");
//        testObject.put("words","测试会话获取异常发送是否有问题");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.e("leancloudMes","success!");
//                }
//            }
//        });
    }

    @Override
    public void initData() {
        final int loginId = (int) SPUtils.getParam(MainActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);

//        ChatManager.getInstance().openClient(new AVIMClientCallback() {
//            @Override
//            public void done(AVIMClient avimClient, AVIMException e) {
//                if (null == e) {
//                    AVObject testObject = new AVObject("TestObject");
//                    testObject.put("words","测试代码");
//                    testObject.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(AVException e) {
//                            if(e == null){
//                                LogUtils.e("leancloudMes","main发送消息成功");
//                            }else
//                                LogUtils.e("leancloudMes","main发送失败"+e.getMessage());
//                        }
//                    });
////                    finish();
////                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                    startActivity(intent);
//                    ChatManager.getInstance().getConversationQuery().findInBackground(new AVIMConversationQueryCallback() {
//                        @Override
//                        public void done(List<AVIMConversation> list, AVIMException e) {
//                            if (e==null){
//                                LogUtils.e("leancloudMes","main查询消息列表成功="+list.size());
//                            }else {
//                                LogUtils.e("leancloudMes","main查询消息列表失败="+e.getMessage());
//                            }
//                        }
//                    });
//                } else {
//                    showShortToast("首页发送异常="+e.toString());
//                }
//            }
//        });

        // 测试 SDK 是否正常工作的代码

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
//        tabHost.showDot(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ButterKnife.reset(this);
        super.onDestroy();
    }


    @OnClick({R.id.img_back, R.id.fbjz, R.id.jzgl, R.id.cwzx, R.id.wd, R.id.gy, R.id.rck})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.fbjz:
                //发布兼职
                startActivity(new Intent(MainActivity.this, PublishActivity.class));
                break;
            case R.id.jzgl:
                //兼职管理
                startActivity(new Intent(MainActivity.this, PartJobManagerActivity.class));
                break;
            case R.id.cwzx:
                //财务中心
                startActivity(new Intent(MainActivity.this, PassWordActivity.class));
                break;
            case R.id.wd:
                //我的
                startActivity(new Intent(MainActivity.this, PersonExitActivity.class));
                break;
            case R.id.gy:
                //关于
                break;
            case R.id.rck:
                break;
        }
    }

//    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
//
//        public ViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    return new PartJobFragment();           //直播榜
//                case 1:
//                    return new MineFragment();
////                    return new TalkFragment();          //话题榜
////                case 2:
////                    return  new MineFragment();
//            }
//            return new FragmentText();
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//    }
}
