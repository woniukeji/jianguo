package com.woniukeji.jianguo.main;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.DrawMoney;
import com.woniukeji.jianguo.entity.PushMessage;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PushMessageActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView imgShare;
    @BindView(R.id.list) FixedRecyclerView list;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    PushMessageAdapter adapter;
    private int lastVisibleItem;
    private List<PushMessage.DataEntity.ListTPushEntity> listPush=new ArrayList<PushMessage.DataEntity.ListTPushEntity>();
    private LinearLayoutManager mLayoutManager;
    private Handler mHandler = new Myhandler(this);
    private Context context = PushMessageActivity.this;
    private int loginId;


    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PushMessageActivity activity = (PushMessageActivity) reference.get();
            switch (msg.what) {
                case 0:
                    if (activity.refreshLayout.isRefreshing()){
                        activity.refreshLayout.setRefreshing(false);
                    }
                    activity.listPush.clear();
                    PushMessage messageDate = (PushMessage) msg.obj;
                    activity.listPush.addAll( messageDate.getData().getList_t_push());
                    activity.adapter.notifyDataSetChanged();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(activity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_push_message);
        ButterKnife.bind(this);
        tvTitle.setText("消息");
    }

    @Override
    public void initViews() {
        adapter = new PushMessageAdapter(listPush,context);
        mLayoutManager = new LinearLayoutManager(PushMessageActivity.this);
//设置布局管理器
        list.setLayoutManager(mLayoutManager);
//设置adapter
        list.setAdapter(adapter);
//设置Item增加、移除动画
        list.setItemAnimator(new DefaultItemAnimator());
//添加分割线
        list.addItemDecoration(new RecyclerView.ItemDecoration() {
        });
//        list.setItemAnimator(new DefaultItemAnimator());
//        list.addItemDecoration(new DividerItemDecoration(
//                PushMessageActivity.this, DividerItemDecoration.VERTICAL_LIST));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTask getTask = new GetTask(String.valueOf(loginId));
                getTask.execute();
            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        loginId = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
    }

    @Override
    public void addActivity() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetTask getTask = new GetTask(String.valueOf(loginId));
        getTask.execute();
    }

    @Override
    public void onClick(View view) {

    }
    public class GetTask extends AsyncTask<Void, Void, Void> {
//        private final String count;
        private final String loginId;

        GetTask(String loginId) {
            this.loginId = loginId;
//            this.count=count;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getJobs();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * postInfo
         */
        public void getJobs() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.PUSH_MESSAGE)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
//                    .addParams("count", count)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<PushMessage>() {
                        @Override
                        public PushMessage parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            PushMessage baseBean = new Gson().fromJson(string, new TypeToken<PushMessage>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(PushMessage baseBean,int id) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
//                                message.arg1= Integer.parseInt(count);
                                message.what = MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }
}
