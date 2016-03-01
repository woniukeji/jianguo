package com.woniukeji.jianguo.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initViews();
        initListeners();
        initData();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public abstract void setContentView();

    public abstract void initViews();

    public abstract void initListeners();

    public abstract void initData();

    //常用适配或提示方法
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dipValue + 0.5f);
    }
    public void showToast(String text) {
        Toast.makeText(BaseActivity.this,text,Toast.LENGTH_SHORT).show();
    }
}
