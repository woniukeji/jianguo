package com.woniukeji.jianguo.mine;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.AttenCollection;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CityBannerEntity;
import com.woniukeji.jianguo.entity.JobDetails;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.eventbus.AttentionCollectionEvent;
import com.woniukeji.jianguo.main.MainActivity;
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
public class AttentionFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.img_renwu) ImageView imgRenwu;
    @InjectView(R.id.rl_null) RelativeLayout rlNull;
    @InjectView(R.id.list) FixedRecyclerView list;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    public List<JobDetails.TMerchantEntity> jobList = new ArrayList<JobDetails.TMerchantEntity>();
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context mContext = this.getActivity();
    private int loginId;
    private AttentionAdapter adapter;
    private int MSG_DELETE_SUCCESS=5;
    private int MSG_DELETE_FAIL=6;
    private int delePosition;


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;
        private List<CityBannerEntity.ListTBannerEntity> banners;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = (MainActivity) reference.get();
            switch (msg.what) {
                case 0:
                    rlNull.setVisibility(View.GONE);
                    BaseBean<AttenCollection> jobs = (BaseBean<AttenCollection>) msg.obj;
                    jobs.getData().getList_t_merchant();
                    jobList.addAll(jobs.getData().getList_t_merchant());
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    rlNull.setVisibility(View.VISIBLE);
                    String ErrorMessage = (String) msg.obj;
//                    Toast.makeText(mainActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(getActivity(), sms, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), mes, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    public AttentionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.inject(this, view);
        initview();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginId = (int) SPUtils.getParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        GetTask getTask = new GetTask(String.valueOf(loginId));
        getTask.execute();
    }

    private void initview() {
//        tvTitle.setText("兼职");
//        imgBack.setVisibility(View.GONE);
        adapter = new AttentionAdapter(jobList, getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//            }
//        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 处理长按事件
     */
    public void onEvent(AttentionCollectionEvent event) {
        if (event.tMerchantEntity!=null){
            delePosition=event.position;
            DeleteTask deleteTask=new DeleteTask(String.valueOf(loginId),String.valueOf( event.tMerchantEntity.getId()));
            deleteTask.execute();
        }
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
                    .execute(new Callback<BaseBean<AttenCollection>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<AttenCollection>>() {
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

        DeleteTask(String loginId,String id) {
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
                    .addParams("type", "2")
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
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
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
