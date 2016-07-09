package com.woniukeji.jianmerchant.affordwages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.LazyStringArrayList;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.eventbus.PayPassWordEvent;
import com.woniukeji.jianmerchant.eventbus.UserWagesEvent;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.LogUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.FixedRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

public class CalculateActivity extends BaseActivity implements CalculateAdapter.deleteCallBack {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.list) FixedRecyclerView list;
    @InjectView(R.id.tv_job_name) TextView tvJobName;
    @InjectView(R.id.tv_job_date) TextView tvJobDate;
    @InjectView(R.id.ll_jobname) LinearLayout llJobname;
    @InjectView(R.id.tv_title_sum) TextView tvTitleSum;
    @InjectView(R.id.tv_job_wages) TextView tvJobWages;
    @InjectView(R.id.btn_change_wages) Button btnChangeWages;
    @InjectView(R.id.ll_jobinfo) LinearLayout llJobinfo;
    @InjectView(R.id.ch_all) CheckBox chAll;
    @InjectView(R.id.btn_pay_wages) Button btnPayWages;
    @InjectView(R.id.tv_wages_sum) TextView tvWagesSum;
    @InjectView(R.id.tv_choose_sum) TextView tvChooseSum;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_DELETE_SUCCESS = 2;
    private int MSG_DELETE_FAIL = 3;
    private int MSG_POST_SUCCESS = 5;
    private int MSG_POST_FAIL = 6;
    private Handler mHandler = new Myhandler(this);
    private Context mContext = CalculateActivity.this;
    private CalculateAdapter adapter;
    private int merchantid;
    private List<Boolean> isSelected=new ArrayList<>();
    private List<AffordUser.ListTUserInfoEntity> userList = new ArrayList<>();
//  private List<HashMap<AffordUser.ListTUserInfoEntity,Boolean>> userList = new ArrayList<>();
    private int mPosition;
    private SweetAlertDialog sweetAlertDialog;
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    private String jobid;
    private String jobNid = "0";
    private String moneyStr;
    private String jobName;
    private double money;
    private boolean noData;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.btn_change_wages, R.id.ch_all, R.id.btn_pay_wages,R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_wages:
                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("修改应发工资")
                        .setInputType("number")
                        .setContentEdit("请输入应发工资数目")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                              money = Double.parseDouble(sweetAlertDialog.getEditContent());
                             tvJobWages.setText(money+str);
                             for (int i=0; i<userList.size();i++){
                                 userList.get(i).setReal_money(money);
                             }
                                Calculate();
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
                break;
            case R.id.btn_pay_wages:
                Mdialog mdialog=new Mdialog(mContext);
                mdialog.show();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }




    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CalculateActivity activity = (CalculateActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<AffordUser> modleBaseBean = (BaseBean<AffordUser>) msg.obj;
                    List<AffordUser.ListTUserInfoEntity> tempList = null;
                    if (msg.arg1!=0){
                        tempList = modleBaseBean.getData().getList_t_user_info();
                        for (int i = 0; i <tempList.size() ; i++) {
                            activity.isSelected.add(activity.userList.size()+i,false);
                        }
                    }else {
                        tempList = modleBaseBean.getData().getList_t_user_info();
                        activity.userList.clear();
                        for (int i = 0; i <tempList.size() ; i++) {
                            activity.isSelected.add(i,false);
                        }
                    }
                    if (tempList.size()<10){
                        activity.noData=true;
                    }else {
                        activity.noData=false;
                    }
                    activity.userList.addAll(tempList);
                    for (int i=0; i<activity.userList.size();i++){
                        activity.userList.get(i).setReal_money(activity.money);
                    }
                    activity.tvTitleSum.setText("总计"+modleBaseBean.getData().getUser_sum()+"人");
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
                case 5:
                    BaseBean Bean = (BaseBean) msg.obj;
                    String sum=Bean.getSum();
                    Toast.makeText(activity, "结算成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(activity,FinishActivity.class);
                    intent.putExtra("sum",sum);
                    activity.startActivityForResult(intent,0);
//                    activity.finish();
                    break;
                case 6:
                    String sms6 = (String) msg.obj;
                    Toast.makeText(activity, sms6, Toast.LENGTH_SHORT).show();
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
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        jobName = intent.getStringExtra("name");
        moneyStr = intent.getStringExtra("money");
        str=moneyStr.substring(moneyStr.indexOf("/"));
        money= Double.parseDouble(moneyStr.substring(0,moneyStr.indexOf("/")));
        jobid = intent.getStringExtra("jobid");
        jobNid = intent.getStringExtra("jobNid");
        if (jobNid==null||jobNid.equals("null")){
            jobNid="0";
        }
    }

    @Override
    public void initViews() {
        tvTitle.setText("结算");
        tvJobName.setText(jobName);
        tvJobWages.setText(moneyStr);
        adapter = new CalculateAdapter(userList, isSelected,this, String.valueOf(money), this);
        mLayoutManager = new LinearLayoutManager(this);
//设置布局管理器
        list.setLayoutManager(mLayoutManager);
//设置adapter
        list.setAdapter(adapter);
//设置Item增加、移除动画
        list.setItemAnimator(new DefaultItemAnimator());
//添加分割线

        sweetAlertDialog = new SweetAlertDialog(CalculateActivity.this, SweetAlertDialog.PROGRESS_TYPE);
    }

    @Override
    public void initListeners() {
        chAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (int i = 0; i < isSelected.size(); i++) {
                        isSelected.set(i,true);
                    }
                }else {
                    for (int i = 0; i < isSelected.size(); i++) {
                        isSelected.set(i,false);
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initData() {
        merchantid = (int) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_MERCHANT_ID, 0);
        GetTask getTask = new GetTask("0");
        getTask.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (userList.size() > 5 && lastVisibleItem == userList.size()&&!noData) {
                    noData=true;//防止数据还未取回的时候重新触发数据接口
                    GetTask getTask = new GetTask(String.valueOf(lastVisibleItem));
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
    public void  onEvent(UserWagesEvent event){
        Calculate();
    }

    private void Calculate() {
        int sum = 0;
        int person=0;
        for (int i = 0; i <userList.size() ; i++) {
            if (isSelected.get(i)){
                sum+= userList.get(i).getReal_money();
                person++;
            }
        }
        tvWagesSum.setText("合计："+sum+"元");
        tvChooseSum.setText("选中"+person+"人");
    }

    public void  onEventMainThread(PayPassWordEvent event){

        if (event.isCorrect){
            if (!sweetAlertDialog.isShowing()){
                sweetAlertDialog.setTitleText("结算提交中，请稍后");
                sweetAlertDialog.getProgressHelper().setBarColor(R.color.app_bg);
                sweetAlertDialog.show();
            }
            Map  map=new HashMap();
            List tempList=new ArrayList();
            for (int i = 0; i <userList.size() ; i++) {
                if (isSelected.get(i)){
                    tempList.add(userList.get(i));
                    LogUtils.e("json",userList.get(i).getReal_money()+"元");
                }
            }
            map.put("list_t_wages_Bean",tempList);
            String json=new Gson().toJson(map);
            PostTask postTask= new PostTask(jobid,jobNid,json
            );

            postTask.execute();
        }
    }
//    @Override
//    public void deleOnClick(int job_id, int merchant_id, int position) {
//        mPosition = position;
//        DeleteTask deleteTask = new DeleteTask(String.valueOf(job_id), String.valueOf(merchant_id));
//        deleteTask.execute();
//    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public class GetTask extends AsyncTask<Void, Void, Void> {
        private String count = "0";

        GetTask(String mCount) {
            this.count = mCount;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                getJobUsers();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            sweetAlertDialog.dismissWithAnimation();
        }

        /**
         * postInfo
         */
        public void getJobUsers() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.GET_PAY_LIST)
                    .addParams("only", only)
                    .addParams("job_id", jobid)
                    .addParams("nv_job_id", jobNid)
                    .addParams("count", count)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<AffordUser>>() {
                        @Override
                        public BaseBean<AffordUser> parseNetworkResponse(Response response, int id) throws Exception {
                            String string = response.body().string();
                            LogUtils.e("calculate",count);
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<AffordUser>>() {
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
                        public void onResponse(BaseBean<AffordUser> response, int id) {
                            if (response.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = response;
                                message.arg1= Integer.parseInt(count);
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

    public class PostTask extends AsyncTask<Void, Void, Void> {
        private  String jobid;
        private  String loginid;
        private  String houle;
        private  String jobnid="";
        private String remarks="";
        private  String json;
        PostTask(String jobid, String jobnid,String json) {
            this.jobid = jobid;
            this.json=json;
            if (jobnid!=null){
                this.jobnid=jobnid;
            }

        }
        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                PostJobWages();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!sweetAlertDialog.isShowing()){

                sweetAlertDialog.setTitleText("结算提交中，请稍后");
                sweetAlertDialog.getProgressHelper().setBarColor(R.color.app_bg);
                sweetAlertDialog.show();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (sweetAlertDialog.isShowing()){
                sweetAlertDialog.dismiss();
            }
        }

        /**
         */
        public void PostJobWages() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
             OkHttpUtils
                        .post()
                        .url(Constants.POST_PART_JOB_WAGES)
//                        .content(json)
                        .addParams("only", only)
//                        .addParams("hould_money", houle)
//                        .addParams("real_money", real)
                        .addParams("nv_job_id", jobnid)
                        .addParams("job_id", jobid)
                        .addParams("json", json)
                        .build()
                        .connTimeOut(50000)
                        .readTimeOut(300000)
                        .writeTimeOut(300000)
                     .execute(new Callback<BaseBean<AffordUser>>() {
                         @Override
                         public BaseBean<AffordUser> parseNetworkResponse(Response response, int id) throws Exception {
                             String string = response.body().string();
//                             LogUtils.e("calculate",count);
                             BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<AffordUser>>() {
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
                         public void onResponse(BaseBean<AffordUser> response, int id) {
                             if (response.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                 Message message = new Message();
                                 message.obj = response;
//                                 message.arg1= Integer.parseInt(count);
                                 message.what = MSG_POST_SUCCESS;
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


    @Override
    public void deleOnClick(String money, AffordUser.ListTUserInfoEntity user, int position) {
        Intent intent = new Intent(mContext, ChangeActivity.class);
        intent.putExtra("money",money);
        intent.putExtra("user", user);
        intent.putExtra("position", position);
//        startActivity(intent);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){//修改界面返回值
            if (resultCode==RESULT_OK){
                int position= data.getIntExtra("position",0);
                AffordUser.ListTUserInfoEntity user= (AffordUser.ListTUserInfoEntity) data.getSerializableExtra("user");
                userList.set(position,user);
                Calculate();
                LogUtils.e("json","修改"+user.getReal_money());
                adapter.notifyDataSetChanged();
            }
        }else {
            if (resultCode==RESULT_OK){
                GetTask getTask = new GetTask("0");
                getTask.execute();
            }
        }




    }
}
