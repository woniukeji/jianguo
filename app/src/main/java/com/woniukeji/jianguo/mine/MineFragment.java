package com.woniukeji.jianguo.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.eventbus.HeadImgEvent;
import com.woniukeji.jianguo.eventbus.LoginEvent;
import com.woniukeji.jianguo.eventbus.QuickLoginEvent;
import com.woniukeji.jianguo.eventbus.TalkMessageEvent;
import com.woniukeji.jianguo.login.LoginActivity;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.setting.FeedBackActivity;
import com.woniukeji.jianguo.setting.PereferenceActivity;
import com.woniukeji.jianguo.setting.SettingActivity;
import com.woniukeji.jianguo.utils.GlideCircleTransform;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.utils.UpDialog;
import com.woniukeji.jianguo.wallte.WalletActivity;
import com.woniukeji.jianguo.widget.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

public class MineFragment extends BaseFragment {
    @BindView(R.id.title_bar) TextView titleBar;
    @BindView(R.id.img_head) ImageView imgHead;
    @BindView(R.id.iv_setting) ImageView ivSetting;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.school) TextView school;
    @BindView(R.id.phone) TextView phone;
    @BindView(R.id.lin_info) LinearLayout linInfo;
    @BindView(R.id.account1) RelativeLayout account1;
    @BindView(R.id.hobby) RelativeLayout hobby;
    @BindView(R.id.or_img) ImageView orImg;
    @BindView(R.id.point_img) ImageView pointImg;
    @BindView(R.id.ll_money) LinearLayout llMoney;
    @BindView(R.id.ll_real_name) LinearLayout llRealName;
    @BindView(R.id.ll_wallte_realname) LinearLayout llWallteRealname;
    @BindView(R.id.credit) RelativeLayout credit;
    @BindView(R.id.account) RelativeLayout account;
    @BindView(R.id.rl_evaluation) RelativeLayout rlEvaluation;
    @BindView(R.id.ll_collect) RelativeLayout llCollect;
    @BindView(R.id.rl_point) RelativeLayout rlPoint;
    @BindView(R.id.rl_feedback) RelativeLayout rlFeedback;
    @BindView(R.id.rl_setting) RelativeLayout rlSetting;
    @BindView(R.id.ll_guanli) RelativeLayout llGuanli;
    @BindView(R.id.about) RelativeLayout about;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context context = getActivity();
    private int status;
    private int loginId;


    @OnClick({R.id.about,R.id.iv_setting,R.id.hobby,R.id.ll_guanli, R.id.ll_money,  R.id.ll_real_name, R.id.credit, R.id.rl_evaluation, R.id.ll_collect, R.id.rl_point, R.id.rl_feedback, R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.hobby:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), PereferenceActivity.class));
                break;
            case R.id.about:
//                startActivity(new Intent(getActivity(), PereferenceActivity.class));
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.ll_guanli:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                break;
            case R.id.credit:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                Intent intentRe = new Intent(getActivity().getApplicationContext(), ResumeActivity.class);
                startActivity(intentRe);
                break;
            case R.id.rl_evaluation:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                Intent intentEvluation = new Intent(getActivity().getApplicationContext(), EvaluationActivity.class);
                startActivity(intentEvluation);
                break;
            case R.id.ll_collect:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
//                Intent intentColl = new Intent(getActivity().getApplicationContext(), CollectActivity.class);,暂改收藏简直无商家
                Intent intentColl = new Intent(getActivity().getApplicationContext(), CollTionActivity.class);
                startActivity(intentColl);
                break;
            case R.id.rl_point:
                break;
            case R.id.rl_feedback:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                Intent intentFeed = new Intent(getActivity().getApplicationContext(), FeedBackActivity.class);
                startActivity(intentFeed);
                break;
            case R.id.rl_setting:
                Intent intentSet = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                startActivity(intentSet);
                break;
            case R.id.ll_money:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                if (status == 1 || status == 0) {//未认证 不可以查询信息
                    Toast.makeText(getActivity(), "请先实名认证", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentWallte = new Intent(getActivity().getApplicationContext(), WalletActivity.class);
                    startActivity(intentWallte);
                }

                break;
            case R.id.ll_real_name:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(getActivity().getApplicationContext(), AuthActivity.class);
                startActivity(intent);
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
            MainActivity mainActivity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    Intent intent = new Intent(mainActivity, MainActivity.class);
//                    intent.putExtra("user", user);
                    mainActivity.startActivity(intent);
                    mainActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_mine, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this, view);
        return view;


    }
    @Override
    public int getContentViewId() {
        return R.layout.activity_mine;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    public void onEvent(QuickLoginEvent event) {
        if (event.isQuickLogin){
          initData(true);
        }
    }

    public void onEvent(HeadImgEvent event) {
        Glide.with(getActivity()).load(event.ImgUrl)
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new GlideCircleTransform(getActivity()))
                .into(imgHead);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i("fragment", "mine:onstart");
    }

    public void initData(boolean init) {
        if (init) {
            String nick = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_NAME, "");
            String schoolStr = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_SCHOOL, "");
            String tel = (String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TEL, "");
            String img = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_IMG, "");
            status = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_STATUS, 0);
            loginId = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, 0);
            if (schoolStr.equals("")) {
                school.setText("未填写");
            } else {
                school.setText(schoolStr);
            }
            if (nick.equals("")) {
                name.setText("未填写");
            } else {
                name.setText(nick);
            }
            phone.setText(tel);
            if (img != null && !img.equals("")) {
                Glide.with(getActivity()).load(img)
                        .placeholder(R.mipmap.icon_head_defult)
                        .error(R.mipmap.icon_head_defult)
                        .transform(new GlideCircleTransform(getActivity()))
                        .into(imgHead);
            }
        } else {
            Glide.with(getActivity()).load("http//null")
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(imgHead);
        }
        if (loginId == 0) {
            name.setText("登录/注册");
            Glide.with(getActivity()).load("http//null")
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(imgHead);
            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }else {
            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initData(true);
        LogUtils.i("fragment", "mine:onresum");
    }

    @Override
    public void onDestroy() {
        LogUtils.i("fragment", "mine:ondestroy");
        super.onDestroy();
    }



}
