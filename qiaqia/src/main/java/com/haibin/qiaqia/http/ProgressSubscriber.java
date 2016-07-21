package com.haibin.qiaqia.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by invinjun on 2016/6/4.
 */

public class ProgressSubscriber<T> extends Subscriber<T>{

    private SubscriberOnNextListener subscriberOnNextListener;
    private Context mContext;
    private ProgressDialog progressDialog;
    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mContext=context;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialog=new ProgressDialog(mContext);
        progressDialog.show();
    }

    @Override
    public void onCompleted() {
        progressDialog.dismiss();
//        Toast.makeText(mContext, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onNext(T t) {
        subscriberOnNextListener.onNext(t);

    }
}
