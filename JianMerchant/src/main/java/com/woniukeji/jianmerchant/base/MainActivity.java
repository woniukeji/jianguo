package com.woniukeji.jianmerchant.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.mine.MineFragment;
import com.woniukeji.jianmerchant.partjob.PartJobFragment;
import com.woniukeji.jianmerchant.talk.TalkFragment;
import com.woniukeji.jianmerchant.talk.leanmessage.ChatManager;
import com.woniukeji.jianmerchant.talk.leanmessage.ImTypeMessageEvent;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;

import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 */
public class MainActivity extends BaseActivity {

    @InjectView(R.id.tabHost) CommonTabLayout tabHost;
    @InjectView(R.id.mainPager) ViewPager mainPager;
    private ViewPagerAdapter adapter;
    private String[] titles = {"兼职", "消息","个人"};

    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect,
            R.mipmap.tab_guo_talk_unselect,
            R.mipmap.tab_about_me_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select,
            R.mipmap.tab_guo_talk_select,
            R.mipmap.tab_about_me_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
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
                if (position==1){
                    tabHost.hideMsg(1);
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
        tabHost.showDot(1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
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
                    return new PartJobFragment();           //直播榜
                case 1:
                    return new TalkFragment();          //话题榜
                case 2:
                    return  new MineFragment();
            }
            return new FragmentText();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
