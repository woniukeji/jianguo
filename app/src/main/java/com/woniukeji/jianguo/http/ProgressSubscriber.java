package com.woniukeji.jianguo.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.listener.ProgressCancelListener;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by invinjun on 2016/6/4.
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{

    private SubscriberOnNextListener subscriberOnNextListener;
    private Context mContext;
    private ProgressDialog progressDialog;
    private String message="请稍后……";

    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mContext=context;
    }
    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context,String message) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mContext=context;
        this.message=message;
    }
    @Override
    public void onStart() {
        super.onStart();
        progressDialog=new ProgressDialog(mContext);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void onCompleted() {
        progressDialog.dismiss();
//      Toast.makeText(mContext, "获取数据成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(mContext, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(mContext, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

    @Override
    public void onNext(T t) {
        subscriberOnNextListener.onNext(t);

    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
