package com.woniukeji.jianguo.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import rx.Subscriber;

/**
 * Created by invinjun on 2016/6/4.
 */

public class NoProgressSubscriber<T> extends Subscriber<T>{

    private SubscriberOnNextListener subscriberOnNextListener;
    private Context mContext;
    private ProgressDialog progressDialog;
    public NoProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context,ProgressDialog progressDialog) {
        this.subscriberOnNextListener = subscriberOnNextListener;
        mContext=context;
        this.progressDialog=progressDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
//        progressDialog=new ProgressDialog(mContext);
//        progressDialog.setMessage("正在加载...");
//        progressDialog.show();
    }

    @Override
    public void onCompleted() {
        progressDialog.dismiss();
//        Toast.makeText(mContext, "获取数据成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        TastyToast.makeText(mContext,  e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);

//        Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onNext(T t) {
        subscriberOnNextListener.onNext(t);

    }
}
