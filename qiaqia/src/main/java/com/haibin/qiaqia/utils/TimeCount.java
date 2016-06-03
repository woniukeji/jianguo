package com.haibin.qiaqia.utils;

/**
 * Created by invinjun on 2016/3/5.
 */

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 倒计时
 */
public class TimeCount extends CountDownTimer {
    private TextView view;
    public TimeCount(long millisInFuture, long countDownInterval,TextView view) {
        super(millisInFuture, countDownInterval);
        this.view=view;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        view.setClickable(false);
        view.setText(millisUntilFinished / 1000 + "秒");
    }

    @Override
    public void onFinish() {
        view.setText("验证码");
        view.setClickable(true);
    }
}
