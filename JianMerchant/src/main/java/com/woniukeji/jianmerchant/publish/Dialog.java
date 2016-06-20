package com.woniukeji.jianmerchant.publish;

import android.content.Context;
import android.os.Bundle;

import com.woniukeji.jianmerchant.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by invinjun on 2016/4/1.
 */
public class Dialog extends SweetAlertDialog {

    public Dialog(Context context) {
        super(context);
    }

    public Dialog(Context context, int alertType) {
        super(context, alertType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}
