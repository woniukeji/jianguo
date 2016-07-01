package com.woniukeji.jianguo.login;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayfang.dropdownmenu.DropDownMenu;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.partjob.PartJobAdapter;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link } subclass.
 */
public class LoginActivity extends BaseActivity {

    private PartJobAdapter adapter;
    private int lastVisibleItem;
    private Handler mHandler = new Myhandler(this);

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginActivity mainActivity = (LoginActivity) reference.get();
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(mainActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }
    @Override
    public void addActivity() {

    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
//        ButterKnife.inject(this);
//        EventBus.getDefault().register(this);
    }
    @Override
    public void initViews() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
//        EventBus.getDefault().unregister(this);
    }



}
