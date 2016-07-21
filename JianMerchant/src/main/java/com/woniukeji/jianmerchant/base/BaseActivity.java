package com.woniukeji.jianmerchant.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initViews();
        initListeners();
        initData();
        addActivity();
    }

    @Override
    protected void onStart() {
        LogUtils.i("activity",":onstart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LogUtils.i("activity",":onResume");
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.i("activity",":onDestroy");
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }


    public abstract void setContentView();

    public abstract void initViews();

    public abstract void initListeners();

    public abstract void initData();

    public abstract void addActivity();

    //常用适配或提示方法
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dipValue + 0.5f);
    }
    public void showShortToast(String text) {
        Toast.makeText(BaseActivity.this,text,Toast.LENGTH_SHORT).show();
    }
    public void showLongToast(String text) {
        Toast.makeText(BaseActivity.this,text,Toast.LENGTH_LONG).show();
    }
}
