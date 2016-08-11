package com.woniukeji.jianguo.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by invinjun on 2016/6/4.
 */

public class BackgroundSubscriber<T> extends Subscriber<T>{

    private SubscriberOnNextListener subscriberOnNextListener;
    private Context mContext;
    public BackgroundSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mContext=context;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
//        Toast.makeText(mContext, "获取数据成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override


    public void onNext(T t) {
        subscriberOnNextListener.onNext(t);

    }
}
