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

import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.eventbus.HeadImgEvent;
import com.woniukeji.jianguo.eventbus.TalkMessageEvent;
import com.woniukeji.jianguo.login.LoginActivity;
import com.woniukeji.jianguo.login.QuickLoginActivity;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.CropCircleTransfermation;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.wallte.WalletActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

public class MineFragment extends BaseFragment {
    @InjectView(R.id.title_bar) TextView titleBar;
    @InjectView(R.id.img_head) ImageView imgHead;
    @InjectView(R.id.name) TextView name;
    @InjectView(R.id.school) TextView school;
    @InjectView(R.id.phone) TextView phone;
    @InjectView(R.id.lin_info) LinearLayout linInfo;
    @InjectView(R.id.account1) RelativeLayout account1;
    @InjectView(R.id.or_img) ImageView orImg;
    @InjectView(R.id.point_img) ImageView pointImg;
    @InjectView(R.id.ll_money) LinearLayout llMoney;
    @InjectView(R.id.ll_real_name) LinearLayout llRealName;
    @InjectView(R.id.ll_wallte_realname) LinearLayout llWallteRealname;
    @InjectView(R.id.credit) RelativeLayout credit;
    @InjectView(R.id.rl_evaluation) RelativeLayout rlEvaluation;
    @InjectView(R.id.ll_collect) RelativeLayout llCollect;
    @InjectView(R.id.rl_point) RelativeLayout rlPoint;
    @InjectView(R.id.rl_feedback) RelativeLayout rlFeedback;
    @InjectView(R.id.rl_setting) RelativeLayout rlSetting;
    @InjectView(R.id.btn_logout) Button btnLogout;
    @InjectView(R.id.refresh) RelativeLayout refresh;
    @InjectView(R.id.ll_guanli) RelativeLayout llGuanli;
    @InjectView(R.id.about) RelativeLayout about;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context context = getActivity();
    private int status;
    private int loginId;
    private int version;
    private String apkurl;


    @OnClick({R.id.about,R.id.ll_guanli, R.id.refresh, R.id.btn_logout, R.id.ll_money, R.id.account1, R.id.ll_real_name, R.id.credit, R.id.rl_evaluation, R.id.ll_collect, R.id.rl_point, R.id.rl_feedback, R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh:
                if (version > getVersion()) {//大于当前版本升级
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("检测到新版本，是否更新？")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    SweetAlertDialog downLoadDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                                    downLoadDialog.setTitleText("正在下载新版本");
                                    downLoadDialog.show();
                                    downLoadTask downLoadTask = new downLoadTask(downLoadDialog);
                                    downLoadTask.execute();
                                }
                            }).show();


                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("已经是最新版本了")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                }

                break;
            case R.id.about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.ll_guanli:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), QuickLoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), SignActivity.class));
                break;
            case R.id.credit:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), QuickLoginActivity.class));
                    return;
                }
                Intent intentRe = new Intent(getActivity().getApplicationContext(), ResumeActivity.class);
                startActivity(intentRe);
                break;
            case R.id.rl_evaluation:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), QuickLoginActivity.class));
                    return;
                }
                Intent intentEvluation = new Intent(getActivity().getApplicationContext(), EvaluationActivity.class);
                startActivity(intentEvluation);
                break;
            case R.id.ll_collect:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), QuickLoginActivity.class));
                    return;
                }
//                Intent intentColl = new Intent(getActivity().getApplicationContext(), CollectActivity.class);,暂改收藏简直无商家
                Intent intentColl = new Intent(getActivity().getApplicationContext(), CollTionActivity.class);
                startActivity(intentColl);
                break;
            case R.id.rl_point:
                break;
            case R.id.rl_feedback:
                Intent intentFeed = new Intent(getActivity().getApplicationContext(), FeedBackActivity.class);
                startActivity(intentFeed);
                break;
            case R.id.rl_setting:
                Intent intentSet = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                startActivity(intentSet);
                break;
            case R.id.ll_money:
                if (loginId == 0) {
                    startActivity(new Intent(getActivity(), QuickLoginActivity.class));
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
                    startActivity(new Intent(getActivity(), QuickLoginActivity.class));
                    return;
                }
                Intent intent = new Intent(getActivity().getApplicationContext(), AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.account1:
                startActivity(new Intent(getActivity(), LoginActivity.class));
//                startActivity(new Intent(getActivity(), QuickLoginActivity.class));
                break;
            case R.id.btn_logout:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定要退出吗?")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                                sweetAlertDialog.dismiss();
//                                暂时关闭果聊
//                                ChatManager chatManager = ChatManager.getInstance();
//                                chatManager.closeWithCallback(new AVIMClientCallback() {
//                                    @Override
//                                    public void done(AVIMClient avimClient, AVIMException e) {
//                                    }
//                                });
                                JPushInterface.stopPush(getActivity());
//                ActivityManager.getActivityManager().finishAllActivity();
                                SPUtils.deleteParams(getActivity());
                                initData(false);
                                account1.setVisibility(View.VISIBLE);
                                btnLogout.setVisibility(View.GONE);
                                TalkMessageEvent talkMessageEvent = new TalkMessageEvent();
                                talkMessageEvent.isLogin = false;
                                EventBus.getDefault().post(talkMessageEvent);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                                sDialog.dismiss();
                            }
                        })
                        .show();

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
        ButterKnife.inject(this, view);
        return view;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void onEvent(HeadImgEvent event) {
        Picasso.with(getActivity()).load(event.ImgUrl)
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new CropCircleTransfermation())
                .into(imgHead);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i("fragment", "mine:onstart");
    }

    public void initData(boolean init) {
        version = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.LOGIN_VERSION, 0);
        apkurl = (String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.LOGIN_APK_URL, "");
        if (init) {
            String nick = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_NAME, "");
            String schoolStr = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_SCHOOL, "");
            String tel = (String) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TEL, "");
            String img = (String) SPUtils.getParam(getActivity(), Constants.USER_INFO, Constants.SP_IMG, "");
            status = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_STATUS, 0);
            loginId = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, 0);

            if (loginId == 0) {
                btnLogout.setVisibility(View.GONE);
                account1.setVisibility(View.VISIBLE);
            }
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
                Picasso.with(getActivity()).load(img)
                        .placeholder(R.mipmap.icon_head_defult)
                        .error(R.mipmap.icon_head_defult)
                        .transform(new CropCircleTransfermation())
                        .into(imgHead);
            }
        } else {
            Picasso.with(getActivity()).load("http//null")
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new CropCircleTransfermation())
                    .into(imgHead);
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

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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


    public class downLoadTask extends AsyncTask<Void, Void, Void> {
        private SweetAlertDialog sweetAlertDialog;

        downLoadTask(SweetAlertDialog sweetAlertDialog) {
            this.sweetAlertDialog = sweetAlertDialog;
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
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(File response, int id) {
                            sweetAlertDialog.dismissWithAnimation();
                            openFile(response);
                        }

//                        @Override
//                        public void inProgress(float progress) {
////                            LogUtils.e("e",progress*100+"");
////                            sweetAlertDialog.getProgressHelper().setProgress(progress);
////                            sweetAlertDialog.getProgressHelper().setCircleRadius((int)progress*100);
//                        }
//
//                        @Override
//                        public void onError(Call call, Exception e) {
//
//                        }
//
//
//                        @Override
//                        public void onResponse(File file) {
//                            sweetAlertDialog.dismissWithAnimation();
//                            openFile(file);
//
//                        }
                    });
        }
    }

}
