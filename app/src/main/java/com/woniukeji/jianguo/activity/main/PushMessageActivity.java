package com.woniukeji.jianguo.activity.main;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.adapter.PushMessageAdapter;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.PushMessage;
import com.woniukeji.jianguo.http.HttpMethods;
import com.woniukeji.jianguo.http.ProgressSubscriber;
import com.woniukeji.jianguo.http.SubscriberOnNextListener;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.FixedRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

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
    private List<PushMessage.ListTPushEntity> listPush = new ArrayList<PushMessage.ListTPushEntity>();
    private LinearLayoutManager mLayoutManager;
    private Context context = PushMessageActivity.this;
    private int loginId;
    private SubscriberOnNextListener<PushMessage> pushMessageSubscriberOnNextListener;


    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }



    @Override
    public void setContentView() {
        setContentView(R.layout.activity_push_message);
        ButterKnife.bind(this);
        tvTitle.setText("消息");
    }

    @Override
    public void initViews() {
        adapter = new PushMessageAdapter(listPush, context);
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
                HttpMethods.getInstance().getPush(new ProgressSubscriber<PushMessage>(pushMessageSubscriberOnNextListener, PushMessageActivity.this), String.valueOf(loginId));
//
            }
        });
    }

    @Override
    public void initListeners() {
        pushMessageSubscriberOnNextListener = new SubscriberOnNextListener<PushMessage>() {
            @Override
            public void onNext(PushMessage pushMessage) {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
                listPush.clear();
                listPush.addAll(pushMessage.getList_t_push());
                adapter.notifyDataSetChanged();
            }
        };
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
        HttpMethods.getInstance().getPush(new ProgressSubscriber<PushMessage>(pushMessageSubscriberOnNextListener, this), String.valueOf(loginId));
    }

    @Override
    public void onClick(View view) {


    }
}
