package com.woniukeji.jianguo.partjob;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.JobDetails;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.main.HomeJobAdapter;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.CropCircleTransfermation;
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

public class MerchantActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.list) FixedRecyclerView list;
    @InjectView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    RelativeLayout mHeader;
    RelativeLayout mRlInfo;
    ImageView mImgHead;
    TextView mTvUserCount;
    TextView mTvJobCount;
    TextView mTvFansCount;
    TextView mTvPart3;
    TextView mTvPart4;

    private HomeJobAdapter adapter;
    private View headerView;
    private List<Jobs.ListTJob> jobList = new ArrayList<Jobs.ListTJob>();
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private Handler mHandler = new Myhandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void onClick(View view) {

    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MerchantActivity merchantActivity = (MerchantActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<Jobs> jobs = (BaseBean<Jobs>) msg.obj;
                    jobs.getData().getList_t_job();
                    jobList.addAll(jobs.getData().getList_t_job());
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(merchantActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(merchantActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_merchant);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        LayoutInflater layoutInflater = getLayoutInflater();
        headerView = layoutInflater.inflate(R.layout.merchant_header_view, null, false);
        mHeader = (RelativeLayout)headerView.findViewById(R.id.header);
        mRlInfo = (RelativeLayout) headerView.findViewById(R.id.rl_info);
        mImgHead = (ImageView) headerView.findViewById(R.id.img_head);
        mTvUserCount = (TextView) headerView.findViewById(R.id.tv_user_count);
        mTvJobCount = (TextView) headerView.findViewById(R.id.tv_job_count);
        mTvFansCount = (TextView) headerView.findViewById(R.id.tv_fans_count);
        mTvPart3 = (TextView) headerView.findViewById(R.id.tv_part3);
        mTvPart4 = (TextView) headerView.findViewById(R.id.tv_part4);
        adapter = new HomeJobAdapter(jobList, this);
        adapter.addHeaderView(headerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//设置布局管理器
        list.setLayoutManager(mLayoutManager);
//设置adapter
        list.setAdapter(adapter);
//设置Item增加、移除动画
        list.setItemAnimator(new DefaultItemAnimator());
//添加分割线
//        recycleList.addItemDecoration(new RecyclerView.ItemDecoration() {
//        });
//        recycleList.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        JobDetails.TMerchantEntity Merchant = (JobDetails.TMerchantEntity) intent.getSerializableExtra("merchant");
        if (Merchant != null) {
            tvTitle.setText(Merchant.getName());
            Picasso.with(MerchantActivity.this).load(Merchant.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new CropCircleTransfermation())
                    .into(mImgHead);
        }
        GetTask getTask = new GetTask("0");
        getTask.execute();
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(MerchantActivity.this);
    }

    @OnClick({R.id.img_back})
    public void onClick() {
        finish();
    }



    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String type;

        GetTask(String type) {
            this.type = type;
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
                    .url(Constants.GET_JOB)
                    .addParams("only", only)
                    .addParams("hot", type)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Jobs>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Jobs>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
                                message.what = MSG_GET_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }
}