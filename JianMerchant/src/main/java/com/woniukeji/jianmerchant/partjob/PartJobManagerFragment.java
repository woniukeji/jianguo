package com.woniukeji.jianmerchant.partjob;

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
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseFragment;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PartJobManagerFragment extends BaseFragment implements PartJobManagerAdapter.RecyCallBack{

    private static String params1;
    @InjectView(R.id.img_renwu) ImageView imgRenwu;
    @InjectView(R.id.rl_null) RelativeLayout rlNull;
    @InjectView(R.id.list) FixedRecyclerView list;
    @InjectView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_DELETE_SUCCESS = 5;
    private int MSG_DELETE_FAIL = 6;
    private Handler mHandler = new Myhandler(this.getActivity());
    private Context mContext = this.getActivity();
    private List<Model.ListTJobEntity> modleList=new ArrayList<>();
    private PartJobManagerAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int merchant_id;
    private int type=1;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void RecyOnClick(int job_id, int merchant_id, int position) {

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
                 if ( refreshLayout.isRefreshing()){
                     refreshLayout.setRefreshing(false);
                 }
                    if (msg.arg1==0){
                        modleList.clear();
                    }
                BaseBean<Model> modelBaseBean= (BaseBean<Model>) msg.obj;
                modleList.addAll(modelBaseBean.getData().getList_t_job());
                adapter.notifyDataSetChanged();
                    break;
                case 1:
                    if ( refreshLayout.isRefreshing()){
                        refreshLayout.setRefreshing(false);
                    }
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
    public static PartJobManagerFragment newInstance(int pid) {
        //通过Bundle保存数据
        Bundle args = new Bundle();
        args.putInt(params1, pid);
        PartJobManagerFragment fragment = new PartJobManagerFragment();
        //将Bundle设置为fragment的参数
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(params1);
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
        adapter = new PartJobManagerAdapter(modleList, getActivity(),type,this);
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
                GetTask getTask=new GetTask(String.valueOf(merchant_id),String.valueOf(type),"0");
                getTask.execute();
            }
        });
        merchant_id= (int) SPUtils.getParam(getActivity(),Constants.USER_INFO,Constants.USER_MERCHANT_ID,0);


    }

    @Override
    public void onResume() {
        super.onResume();
        GetTask getTask=new GetTask(String.valueOf(merchant_id),String.valueOf(type),"0");
        getTask.execute();
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String merchantId;
        private final String status;
        private final String count;

        GetTask(String merId,String status,String count) {
            this.merchantId = merId;
            this.status = status;
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
                    .url(Constants.GET_PART_JOB_PUBLISH)
                    .addParams("only", only)
                    .addParams("merchant_id", merchantId)
                    .addParams("count", count)
                    .addParams("status", status)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Model>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Model>>() {
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
                                message.arg1= Integer.parseInt(count);
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
