package com.woniukeji.jianmerchant.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/7/16.
 */
public class ProgressDialogHandler extends Handler {
    private Context context;
    private ProgressCancelListener listener;
    private boolean isUnSubscriber = false;
    private SweetAlertDialog sad = null;


    public ProgressDialogHandler(Context context, ProgressCancelListener listener, boolean isUnSubscriber) {
        this.context = context;
        this.listener = listener;
        this.isUnSubscriber = isUnSubscriber;
    }

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;

        }
        super.handleMessage(msg);
    }

    private void dismissProgressDialog() {
        if (sad.isShowing()) {
            sad.dismiss();
            sad = null;
        }

    }

    private void initProgressDialog() {
        if (sad == null) {
            sad = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        }
        sad.setTitleText("正在加载中...");
        sad.setCancelable(isUnSubscriber);
        sad.setCancelText("取消");

        if (isUnSubscriber) {
            sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    listener.onCancelProgress();
                    sad.dismiss();
                }
            });
        }
        if (!sad.isShowing()) {
            sad.show();
        }
    }
}
