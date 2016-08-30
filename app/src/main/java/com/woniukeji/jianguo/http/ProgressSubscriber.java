package com.woniukeji.jianguo.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
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
    private ProgressDialogHandler mProgressDialogHandler;
    private Context mContext;
    private ProgressDialog progressDialog;
    private String message="请稍后……";

    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mContext=context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }
    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context,String message) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mContext=context;
        this.message=message;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }
    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
//      Toast.makeText(mContext, "获取数据成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            TastyToast.makeText(mContext, "网络中断，请检查您的网络状态", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        } else if (e instanceof ConnectException) {
            TastyToast.makeText(mContext, "网络中断，请检查您的网络状态", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        } else {
            TastyToast.makeText(mContext, "error:" + e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        dismissProgressDialog();
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
