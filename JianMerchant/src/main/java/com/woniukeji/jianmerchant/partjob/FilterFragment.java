package com.woniukeji.jianmerchant.partjob;

import android.app.job.JobInfo;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.PublishUser;
import com.woniukeji.jianmerchant.eventbus.FilterEvent;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.ExcelUtil;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.woniukeji.jianmerchant.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class FilterFragment extends BaseFragment implements FilterAdapter.RecyCallBack {

    private static String params1 = "type";
    private static String params2 = "jobid";
    @InjectView(R.id.img_renwu) ImageView imgRenwu;
    @InjectView(R.id.rl_null) RelativeLayout rlNull;
    @InjectView(R.id.list) FixedRecyclerView list;
    @InjectView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    @InjectView(R.id.btn_out_info) TextView btnOutInfo;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_POST_SUCCESS = 5;
    private int MSG_POST_FAIL = 6;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context mContext = this.getActivity();
    private List<PublishUser.ListTUserInfoEntity> modleList = new ArrayList<>();
    private int lastVisibleItem;
    private FilterAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int mPosition;
    private int type = 0;
    private String jobid;
    private String jobName;
    private boolean loadOk=false;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.reset(this);
    }


    @Override
    public void RecyOnClick(int loginid, int offer, int position) {
        postAdmit(String.valueOf(loginid), jobid, String.valueOf(offer));
    }

    @OnClick(R.id.btn_out_info)
    public void onClick() {
        exportUser();
    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BaseBean<PublishUser> modelBaseBean = (BaseBean<PublishUser>) msg.obj;
                    int count = msg.arg1;
                    if (count == 0) {
                        modleList.clear();
                    }
                    if (refreshLayout != null && refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    modleList.addAll(modelBaseBean.getData().getList_t_user_info());

                    if (modleList.size() > 0) {//判断录取人数 是否显示图片
                        rlNull.setVisibility(View.GONE);
//                        if (type==1){//导出录取人信息按钮=
                            btnOutInfo.setVisibility(View.VISIBLE);
//                        }
                    } else {
                        rlNull.setVisibility(View.VISIBLE);
                    }

                    adapter.notifyDataSetChanged();
                    loadOk=true;
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(getActivity(), ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(getActivity(), sms, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    break;
                case 5:
                    String me = (String) msg.obj;
                    Toast.makeText(getActivity(), me, Toast.LENGTH_SHORT).show();
                    FilterEvent filterEvent = new FilterEvent();
                    EventBus.getDefault().post(filterEvent);
//                    GetTask getTask=new GetTask(jobid,String.valueOf(type),String.valueOf(lastVisibleItem));
//                    getTask.execute();
//                    modleList.remove(mPosition);
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
    public static FilterFragment newInstance(int type, String jobid) {
        //通过Bundle保存数据
        Bundle args = new Bundle();
        args.putInt(params1, type);
        args.putString(params2, jobid);
        FilterFragment fragment = new FilterFragment();
        //将Bundle设置为fragment的参数
        fragment.setArguments(args);
        return fragment;
    }
    public static FilterFragment newInstance(int type, String jobid,String jobname) {
        //通过Bundle保存数据
        Bundle args = new Bundle();
        args.putInt(params1, type);
        args.putString(params2, jobid);
        args.putString("jobname", jobname);
        FilterFragment fragment = new FilterFragment();
        //将Bundle设置为fragment的参数
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(params1);
        jobid = getArguments().getString(params2);
        jobName = getArguments().getString("jobname");
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admit, container, false);
        ButterKnife.inject(this, view);
        initview();
        return view;

    }

    private void initview() {

        adapter = new FilterAdapter(modleList, getActivity(), type, jobid, this);
        mLayoutManager = new LinearLayoutManager(getActivity());
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
        refreshLayout.setColorSchemeResources(R.color.app_bg);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTask getTask = new GetTask(jobid, String.valueOf(type), "0");
                getTask.execute();
            }
        });
//        merchant_id= (int) SPUtils.getParam(getActivity(),Constants.USER_INFO,Constants.USER_MERCHANT_ID,0);
        GetTask getTask = new GetTask(jobid, String.valueOf(type), "0");
        getTask.execute();

    }

    public void onEvent(FilterEvent filterEvent) {
        GetTask getTask = new GetTask(jobid, String.valueOf(type), "0");
        getTask.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()&&loadOk) {
                    GetTask getTask = new GetTask(jobid, String.valueOf(type), String.valueOf(lastVisibleItem));
                    getTask.execute();
                    refreshLayout.setRefreshing(true);
                    loadOk=false;
                    LogUtils.e("position",lastVisibleItem+"开始"+modleList.size());
                }
//                if (modleList.size() > 5 && lastVisibleItem == modleList.size()) {
//                    GetTask getTask = new GetTask(jobid, String.valueOf(type), String.valueOf(lastVisibleItem));
//                    getTask.execute();
//                    LogUtils.e("position",lastVisibleItem+"开始"+modleList.size());
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    void exportUser() {
      SweetAlertDialog sw=new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        sw.show();
        try {
            ExcelUtil excelUtils = new ExcelUtil(jobName, modleList);
            File file = excelUtils.create();
            ExcelUtil.openFileByOtherApp(getActivity(), file);
            if (sw.isShowing()) {
                sw.dismiss();
            }
        } catch (Exception e) {
            LogUtils.e("导出excel", e.getMessage());
            if (sw.isShowing()) {
                sw.dismiss();
            }
        }
    }

    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String jobid;
        private final String type;
        private final String count;

        GetTask(String merId, String type, String count) {
            this.jobid = merId;
            this.type = type;
            this.count = count;
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
                    .url(Constants.GET_ADMIT_LIST)
                    .addParams("only", only)
                    .addParams("job_id", jobid)
                    .addParams("type", type)
                    .addParams("count", count)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<PublishUser>>() {
                        @Override
                        public BaseBean<PublishUser> parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<PublishUser>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_GET_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean<PublishUser> response, int id) {
                            if (response.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = response;
                                message.arg1 = Integer.parseInt(count);
                                message.what = MSG_GET_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }



                    });
        }
    }


        /**
         * postInfo
         */
        public void postAdmit(String loginid, String jobid, String offer) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_ADMIT)
                    .addParams("only", only)
                    .addParams("job_id", jobid)
                    .addParams("login_id", loginid)
                    .addParams("offer", offer)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<PublishUser>>() {
                        @Override
                        public BaseBean<PublishUser> parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<PublishUser>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean<PublishUser> response, int id) {
                            if (response.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = MSG_POST_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = MSG_POST_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }
                    });
        }


}
