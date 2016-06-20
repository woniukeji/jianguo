package com.woniukeji.jianguo.mine;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityBannerEntity;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.eventbus.AttentionCollectionEvent;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CollTionActivity extends BaseActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.img_renwu) ImageView imgRenwu;
    @InjectView(R.id.list) FixedRecyclerView list;

    @InjectView(R.id.rl_null) RelativeLayout rlNull;
    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CollectionAdapter adapter;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_DELETE_SUCCESS = 5;
    private int MSG_DELETE_FAIL = 6;
    public List<Jobs.ListTJobEntity> jobList = new ArrayList<Jobs.ListTJobEntity>();
    private Handler mHandler = new Myhandler(CollTionActivity.this);
    private Context mContext = CollTionActivity.this;
    private int loginId;
    private int delePosition;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;
        private List<CityBannerEntity.ListTBannerEntity> banners;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    BaseBean<Jobs> jobs = (BaseBean<Jobs>) msg.obj;
                    jobList.addAll(jobs.getData().getList_t_job());
                    adapter.notifyDataSetChanged();
                    if (jobs.getData().getList_t_job().size() > 0) {
                        rlNull.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    rlNull.setVisibility(View.VISIBLE);
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(mContext, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(mContext, sms, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    BaseBean<CityBannerEntity> cityBannerEntityBaseBean = (BaseBean<CityBannerEntity>) msg.obj;
                    banners = cityBannerEntityBaseBean.getData().getList_t_banner();
                    break;
                case 5:
                    jobList.remove(delePosition);
                    adapter.notifyDataSetChanged();
                    break;
                case 6:
                    String mes = (String) msg.obj;
                    Toast.makeText(mContext, mes, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 处理长按事件
     */
    public void onEvent(AttentionCollectionEvent event) {
        if (event.listTJob != null) {
            delePosition = event.position;
            DeleteTask deleteTask = new DeleteTask(String.valueOf(loginId), String.valueOf(event.listTJob.getId()));
            deleteTask.execute();
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    @Override
    public void setContentView() {
        setContentView(R.layout.fragment_collection);
//        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initViews() {
        initview();
    }

    @Override
    public void initListeners() {
       imgBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    @Override
    public void initData() {
        loginId = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        GetTask getTask = new GetTask(String.valueOf(loginId));
        getTask.execute();
    }

    @Override
    public void addActivity() {

    }


    private void initview() {
        tvTitle.setText("我的收藏");
        adapter = new CollectionAdapter(jobList, mContext);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
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
//                mContext, DividerItemDecoration.VERTICAL_LIST));
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//            }
//        });
    }


    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String loginId;

        GetTask(String loginId) {
            this.loginId = loginId;
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
                    .url(Constants.GET_ATTENT)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
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

    public class DeleteTask extends AsyncTask<Void, Void, Void> {
        private final String loginId;
        private final String id;

        DeleteTask(String loginId, String id) {
            this.loginId = loginId;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                DeleteCollAtten();
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
        public void DeleteCollAtten() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.DELETE_COLLATTEN)
                    .addParams("only", only)
                    .addParams("login_id", loginId)
                    .addParams("id", id)
                    .addParams("type", "1")
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
                            message.what = MSG_DELETE_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
                                Message message = new Message();
                                message.what = MSG_DELETE_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_DELETE_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }
}
