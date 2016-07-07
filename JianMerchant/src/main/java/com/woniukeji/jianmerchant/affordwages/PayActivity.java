package com.woniukeji.jianmerchant.affordwages;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.utils.ActivityManager;
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
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Response;

public class PayActivity extends BaseActivity implements CalculateAdapter.deleteCallBack{

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.list) FixedRecyclerView list;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_DELETE_SUCCESS = 2;
    private int MSG_DELETE_FAIL = 3;
    private Handler mHandler = new Myhandler(this);
    private Context mContext = PayActivity.this;
    private CalculateAdapter adapter;
    private int merchantid;
    private String type;
    private List<AffordUser.ListTUserInfoEntity> userList=new ArrayList<>();
    private int mPosition;
    private SweetAlertDialog sweetAlertDialog;
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;

    @OnClick(R.id.img_back)
    public void onClick() {
    }

    @Override
    public void deleOnClick(String money, AffordUser.ListTUserInfoEntity user, int position) {

    }


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PayActivity activity = (PayActivity) reference.get();
            switch (msg.what) {
                case 0:
                        BaseBean<AffordUser> modleBaseBean = (BaseBean<AffordUser>) msg.obj;
                        activity.userList.addAll(modleBaseBean.getData().getList_t_user_info());
                        activity.adapter.notifyDataSetChanged();

                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    BaseBean baseBean = (BaseBean) msg.obj;
                    activity.userList.remove(activity.mPosition);
                    activity.adapter.notifyDataSetChanged();
                    activity.showShortToast("刪除模板成功！");
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
        setContentView(R.layout.activity_pay_wages);
        ButterKnife.inject(this);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
    }

    @Override
    public void initViews() {
            tvTitle.setText("结算");

//        adapter = new CalculateAdapter(userList, this,type,this);
         mLayoutManager = new LinearLayoutManager(this);
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
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        merchantid= (int) SPUtils.getParam(mContext,Constants.USER_INFO,Constants.USER_MERCHANT_ID,0);
//        GetTask getTask=new GetTask("0");
//        getTask.execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (userList.size() > 5 && lastVisibleItem == userList.size()+1) {
                    GetTask getTask=new GetTask(String.valueOf(lastVisibleItem));
                    getTask.execute();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(this);

    }

    @Override
    public void onClick(View view) {

    }
//    @Override
//    public void deleOnClick(int job_id, int merchant_id,int position) {
//        mPosition=position;
//        DeleteTask deleteTask=new DeleteTask(String.valueOf(job_id),String.valueOf(merchant_id));
//        deleteTask.execute();
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class GetTask extends AsyncTask<Void, Void, Void> {
          private String count="0";
          GetTask(String mCount){
              this.count=mCount;
          }
        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getHistoryJob();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            refreshLayout.setRefreshing(true);

//             sweetAlertDialog=new SweetAlertDialog(mContext,SweetAlertDialog.PROGRESS_TYPE);
//             sweetAlertDialog.setTitleText("请稍后");
//             sweetAlertDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            sweetAlertDialog.dismissWithAnimation();
        }

        /**
         * postInfo
         */
        public void getHistoryJob() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_PART_HISTORY)
                    .addParams("only", only)
                    .addParams("type", type)
                    .addParams("count", count)
                    .addParams("merchant_id",String.valueOf(merchantid))
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<Model>>() {
                        @Override
                        public BaseBean<Model> parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<Model>>() {
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
                        public void onResponse(BaseBean<Model> response, int id) {
                            if (response.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = response;
                                message.what = MSG_GET_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = MSG_GET_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }
                        }



                     );
        }
    }

    public class DeleteTask extends AsyncTask<Void, Void, Void> {
        private final String jobid;
        private final String merchantid;

        DeleteTask(String jobid,String merchantid) {
            this.jobid = jobid;
            this.merchantid = merchantid;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                deleteJobModel();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         *刪除兼職模板
         */
        public void deleteJobModel() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.DELETE_JOB_MODEL)
                    .addParams("only", only)
                    .addParams("merchant_id", merchantid)
                    .addParams("job_id", jobid)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_DELETE_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean response, int id) {
                            if (response.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = response;
                                message.what = MSG_DELETE_SUCCESS;
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
}
