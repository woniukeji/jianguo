package com.woniukeji.jianguo.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.liulishuo.magicprogresswidget.MagicProgressBar;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.widget.AnimTextView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import cn.dreamtobe.percentsmoothhandler.ISmoothTarget;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

/**
 * Created by invinjun on 2016/4/19.
 */
public class UpDialog extends Dialog {

        private Context context = null;

        private MagicProgressBar progressBar;
        private AnimTextView tvProgress;
        private String apkurl;

        public static UpDialog createBuilder(Context context) {
            return new UpDialog(context);
        }

        public UpDialog(Context context) {
            this(context, R.style.DialogTheme);
            this.context = context;

        }

        public UpDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
            this.context = context;
        }
    public UpDialog(Context context,String apkurl) {
        this(context, R.style.alert_dialog);
        this.context = context;
        this.apkurl=apkurl;
        setDialogContentView();
    }

        public UpDialog(Context context, int theme) {
            super(context, theme);
            this.context = context;

        }

        /**
         * 设置dialog里面的view
         */
        private void setDialogContentView()
        {
            // 加载自己定义的布局
            View view = LayoutInflater.from(context).inflate(R.layout.up_data_dialog, null);
            setContentView(view);
            progressBar = (MagicProgressBar) view.findViewById(R.id.progress_bar);
            tvProgress = (AnimTextView) view.findViewById(R.id.tv_progress);
            downAPK();
        }

        /**
         * 设置ok按钮监听
         */
        public void setOKOnClickListener(View.OnClickListener listener){
        }

        /**
         * 设置显示的文字内容（传入string）
         * @param msg
         */
        public UpDialog setMsg(String msg) {

//            if (null != msgText) {
//                msgText.setText(msg);
//            }
            return this;
        }

        /**
         * 设置显示的文字内容（传入resId）
         */
        public UpDialog setMsg(int resId) {

//            if (null != msgText) {
//                msgText.setText(context.getString(resId));
//            }
            return this;
        }

        /**
         * 设置显示的标题（传入string）
         */
        public UpDialog setAlertTitle(String t) {

            return this;
        }

        /**
         * 设置显示的标题（传入resId）
         */
        public UpDialog setAlertTitle(int resId) {


            return this;

        }
    /**
     * postInfo
     */
    public void downAPK() {
        OkHttpUtils
                .get()
                .url(apkurl)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "jianguoApk")//
                {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        progressBar.setSmoothPercent(progress);
//                        tvProgress.setPercent(progress);
                        tvProgress.setText((int) Math.ceil(progress * 100)+"%");
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(File file, int id) {
                        openFile(file);
                    }
                });
    }
    private float getIncreasedPercent(ISmoothTarget target) {
        float increasedPercent = target.getPercent() + 0.1f;

        return Math.min(1, increasedPercent);
    }
    private void openFile(File file) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}
