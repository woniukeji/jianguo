package com.woniukeji.jianguo.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.umeng.analytics.MobclickAgent;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.widget.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.app_bg);//通知栏所需颜色
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        setContentView();
        bind = ButterKnife.bind(this);
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
        bind.unbind();
        //可以取消同一个tag的
        OkHttpUtils.getInstance().cancelTag(this);//取消以Activity.this作为tag的请求
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
        TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
    }
    public void showLongToast(String text) {
        TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
    }
/**
*showShortToast
*@param text toast的文字
*@param type 类型 包括警告、错误、成功、失败、默认样式
*@author invinjun
*created at 2016/8/22 9:07
*/
    public void showShortToast(String text,int type) {
        switch (type){
            case TastyToast.DEFAULT:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
                break;
            case TastyToast.WARNING:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                break;
            case TastyToast.ERROR:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                break;
            case TastyToast.SUCCESS:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                break;
            case TastyToast.INFO:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                break;
            default:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
                break;
        }
    }
    /**
     *showLongToast
     *@param text toast的文字
     *@param type 类型 包括警告、错误、成功、失败、默认样式
     *@author invinjun
     *created at 2016/8/22 9:07
     */
    public void showLongToast(String text,int type) {
        switch (type){
            case TastyToast.DEFAULT:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                break;
            case TastyToast.WARNING:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_LONG, TastyToast.WARNING);
                break;
            case TastyToast.ERROR:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                break;
            case TastyToast.SUCCESS:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                break;
            case TastyToast.INFO:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_LONG, TastyToast.WARNING);
                break;
            default:
                TastyToast.makeText(getApplicationContext(), text, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                break;
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
