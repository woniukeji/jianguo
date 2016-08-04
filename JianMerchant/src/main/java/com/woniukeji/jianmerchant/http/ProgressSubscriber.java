package com.woniukeji.jianmerchant.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/16.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{
    private SubscriberOnNextListenner mSubscriberOnNextListenner;
    private Context context;
    private final ProgressDialogHandler progressDialogHandler;

    public ProgressSubscriber(SubscriberOnNextListenner mSubscriberOnNextListenner, Context context) {
        this.mSubscriberOnNextListenner = mSubscriberOnNextListenner;
        this.context = context;
        progressDialogHandler = new ProgressDialogHandler(context, this, true);

    }

    @Override
    public void onStart() {
        super.onStart();
        showProgress();
    }

    @Override
    public void onCompleted() {
        dismissProgress();
    }

    private void showProgress() {
        if (progressDialogHandler != null) {
            progressDialogHandler.sendEmptyMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.i("onError subscriber", "onError: "+e.getMessage().toString());
        Toast.makeText(context, "请检查网络！", Toast.LENGTH_LONG).show();

        dismissProgress();
    }

    private void dismissProgress() {
        progressDialogHandler.sendEmptyMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG);
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListenner.onNext(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            Log.i("unsubscribe", "解除subscribe");
            this.unsubscribe();
        }
    }

    public interface SubscriberOnNextListenner<T> {
        void onNext(T t);
    }
}
