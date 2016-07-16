package com.woniukeji.jianguo.setting;

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
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.leanmessage.ChatManager;
import com.woniukeji.jianguo.login.LoginActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.utils.UpDialog;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img) ImageView img;
    @InjectView(R.id.changePassword) RelativeLayout changePassword;
    @InjectView(R.id.refresh) RelativeLayout refresh;
    @InjectView(R.id.or_img) ImageView orImg;
    @InjectView(R.id.about) RelativeLayout about;
    @InjectView(R.id.btn_logout) Button btnLogout;
    private int version;
    private String apkurl;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);

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


    @OnClick({R.id.img_back, R.id.changePassword, R.id.refresh, R.id.about,R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.changePassword:
//                startActivity(new Intent(SettingActivity.this, ChangPssActivity.class));
//                finish();
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
                ChatManager chatManager = ChatManager.getInstance();
                chatManager.closeWithCallback(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                    }
                });
                ActivityManager.getActivityManager().finishAllActivity();
                SPUtils.deleteParams(SettingActivity.this);
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.about:

                break;
        }




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
