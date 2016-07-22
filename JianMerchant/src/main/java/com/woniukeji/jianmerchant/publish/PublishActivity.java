package com.woniukeji.jianmerchant.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.TabEntity;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.fragment.PublishPartJobFragment;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.SPUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class PublishActivity extends BaseActivity {

    @InjectView(R.id.img_back)
    ImageView imgBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.img_share)
    ImageView imgShare;
    @InjectView(R.id.tl_new)
    CommonTabLayout tlNew;
    @InjectView(R.id.vp_publish_partjob)
    ViewPager vpPublishPartjob;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = PublishActivity.this;

    private ArrayList<CustomTabEntity> tabList = new ArrayList<>();
    private String[] tabContents = {"创建新兼职", "历史纪录", "模板"};

    private ArrayList<PublishPartJobFragment> fragmentsList = new ArrayList<>();
    private String[] fragmentsType = {"cjxjz","lsjl","mb"};



    @OnClick({R.id.img_back, R.id.tv_title, R.id.img_share, R.id.tl_new, R.id.vp_publish_partjob})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_title:
                break;
            case R.id.img_share:
                break;
            case R.id.tl_new:
                break;
            case R.id.vp_publish_partjob:
                break;
        }
    }


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PublishActivity registActivity = (PublishActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    registActivity.saveToSP(user.getData());
                    Intent intent = new Intent(registActivity, MainActivity.class);
//                    intent.putExtra("user",user);
                    registActivity.startActivity(intent);
                    registActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(registActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(registActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_publish_new);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("发布兼职");
        imgShare.setVisibility(View.GONE);
        for (int i = 0; i < tabContents.length; i++) {
            fragmentsList.add(PublishPartJobFragment.newInstance(fragmentsType[i]));
        }
        vpPublishPartjob.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentsList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentsList.size();
            }
        });
        vpPublishPartjob.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tlNew.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < tabContents.length; i++) {
            tabList.add(new TabEntity(tabContents[i],0,0));
        }
        tlNew.setTabData(tabList);
        tlNew.setCurrentTab(0);
        tlNew.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpPublishPartjob.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


    }

    @Override
    public void initListeners() {

    }

    //    @OnClick({R.id.img_back, R.id.btn_history, R.id.btn_modle, R.id.btn_new})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.img_back:
//                finish();
//                break;
//            case R.id.btn_history:
//                Intent intent=new Intent(PublishActivity.this,HistoryJobActivity.class);
//                intent.putExtra("type","0");
//                startActivity(intent);
//                break;
//            case R.id.btn_modle:
//                Intent intent1=new Intent(PublishActivity.this,HistoryJobActivity.class);
//                intent1.putExtra("type","1");
//                startActivity(intent1);
//                break;
//            case R.id.btn_new:
//                Intent intent2=new Intent(PublishActivity.this,PublishDetailActivity.class);
//                intent2.putExtra("type","new");
//                startActivity(intent2);
//                break;
//        }
//    }
    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(PublishActivity.this);
    }

    private void saveToSP(User user) {
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getT_user_login().getPassword());
    }


}
