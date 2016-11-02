package com.woniukeji.jianguo.activity.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.activity.login.ChangePhoneActivity;
import com.woniukeji.jianguo.activity.login.ForgetPassActivity;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.utils.UpDialog;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img) ImageView img;
    @BindView(R.id.changePassword) RelativeLayout changePassword;
    @BindView(R.id.refresh) RelativeLayout refresh;
    @BindView(R.id.or_img) ImageView orImg;
    @BindView(R.id.change) RelativeLayout change;
    @BindView(R.id.change_phone) RelativeLayout changePhone;
    @BindView(R.id.btn_logout) Button btnLogout;
    private int version;
    private String apkurl;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

    }

    @Override
    public void initViews() {
        tvTitle.setText("设置");

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        version = (int) SPUtils.getParam(SettingActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_VERSION, 0);
        apkurl = (String) SPUtils.getParam(SettingActivity.this, Constants.LOGIN_INFO, Constants.LOGIN_APK_URL, "");
    }

    @Override
    public void addActivity() {
//        ActivityManager.getActivityManager().addActivity(SettingActivity.this);
    }


    @OnClick({R.id.img_back, R.id.change, R.id.refresh, R.id.change_phone,R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                    break;
            case R.id.change_phone:
                startActivity(new Intent(SettingActivity.this, ChangePhoneActivity.class));
                break;

             case R.id.change:
                  startActivity(new Intent(SettingActivity.this, ForgetPassActivity.class));
                 finish();
                  break;
                    case R.id.refresh:
                        if (version > getVersion()) {//大于当前版本升级
                            new SweetAlertDialog(SettingActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("检测到新版本，是否更新？")
                                    .setConfirmText("确定")
                                    .setCancelText("取消")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            UpDialog upDataDialog = new UpDialog(SettingActivity.this,apkurl);
                                            upDataDialog.setCanceledOnTouchOutside(false);
                                            upDataDialog.setCanceledOnTouchOutside(false);
                                            upDataDialog.show();

                                        }
                                    }).show();


                        } else {
                    new SweetAlertDialog(SettingActivity.this, SweetAlertDialog.SUCCESS_TYPE)
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
            case R.id.btn_logout:
                new SweetAlertDialog(SettingActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                                if(LCChatKit.getInstance()!=null){
                                    LCChatKit.getInstance().close(new AVIMClientCallback() {
                                        @Override
                                        public void done(AVIMClient avimClient, AVIMException e) {
                                        }
                                    });
                                }

                                JPushInterface.stopPush(SettingActivity.this);
//                ActivityManager.getActivityManager().finishAllActivity();
                                SPUtils.deleteParams(SettingActivity.this);
                                btnLogout.setVisibility(View.GONE);
                                sendEvent();
//                                TalkMessageEvent talkMessageEvent = new TalkMessageEvent();
//                                talkMessageEvent.isLogin = false;
//                                EventBus.getDefault().post(talkMessageEvent);
                                finish();
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
    /**
     * 发送退出登录消息 清空聊天界面消息记录
     *聊天界面会判断conversation是否为空
     * 不会接受该消息
     */
    private void sendEvent() {
        LCIMIMTypeMessageEvent event = new LCIMIMTypeMessageEvent();
        event.messageNull = true;
        EventBus.getDefault().post(event);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion() {
        try {
            PackageManager manager = SettingActivity.this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(SettingActivity.this.getPackageName(), 0);
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


}
