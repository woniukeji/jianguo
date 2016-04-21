package com.woniukeji.jianmerchant.partjob;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.affordwages.CalculateActivity;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.JobDetails;
import com.woniukeji.jianmerchant.entity.Model;
import com.woniukeji.jianmerchant.publish.PublishDetailActivity;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.woniukeji.jianmerchant.widget.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class JobItemDetailActivity extends BaseActivity {


    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_merchant_name) TextView tvMerchantName;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_type) ImageView imgType;
    @InjectView(R.id.tv_manager_name) TextView tvManagerName;
    @InjectView(R.id.tv_wages) TextView tvWages;
    @InjectView(R.id.tv_date) TextView tvDate;
    @InjectView(R.id.ll_publish_time) LinearLayout llPublishTime;
    @InjectView(R.id.tv_chakan_browse) TextView tvChakanBrowse;
    @InjectView(R.id.ll_browse) LinearLayout llBrowse;
    @InjectView(R.id.tv_human) TextView tvHuman;
    @InjectView(R.id.tv_count) TextView tvCount;
    @InjectView(R.id.ll_publisher) LinearLayout llPublisher;
    @InjectView(R.id.ll_limit_sex) LinearLayout llLimitSex;
    @InjectView(R.id.tv_no_limit) TextView tvNoLimit;
    @InjectView(R.id.tv_work_location) TextView tvWorkLocation;
    @InjectView(R.id.tv_location_detail) TextView tvLocationDetail;
    @InjectView(R.id.tv_work_date) TextView tvWorkDate;
    @InjectView(R.id.tv_work_time) TextView tvWorkTime;
    @InjectView(R.id.tv_collection_sites) TextView tvCollectionSites;
    @InjectView(R.id.tv_collection_time) TextView tvCollectionTime;
    @InjectView(R.id.tv_sex) TextView tvSex;
    @InjectView(R.id.tv_pay_method) TextView tvPayMethod;
    @InjectView(R.id.tv_other) TextView tvOther;
    @InjectView(R.id.tv_notic) TextView tvNotic;
    @InjectView(R.id.tv_notic6) TextView tvNotic6;
    @InjectView(R.id.tv_work_content) TextView tvWorkContent;
    @InjectView(R.id.rl_work) RelativeLayout rlWork;
    @InjectView(R.id.tv_notic7) TextView tvNotic7;
    @InjectView(R.id.tv_work_require) TextView tvWorkRequire;
    @InjectView(R.id.tv_worker) TextView tvWorker;
    @InjectView(R.id.cirimg_work) CircleImageView cirimgWork;
    @InjectView(R.id.tv_company_name) TextView tvCompanyName;
    @InjectView(R.id.tv_jobs_count) TextView tvJobsCount;
    @InjectView(R.id.rl_company) RelativeLayout rlCompany;
    @InjectView(R.id.btn_change) Button btnChange;
    @InjectView(R.id.btn_finish) Button btnFinish;
    @InjectView(R.id.btn_down) Button btnDown;
    @InjectView(R.id.btn_no_limit) Button btnNoLimit;
    @InjectView(R.id.btn_boy) Button btnBoy;
    @InjectView(R.id.btn_girl) Button btnGirl;
    private JobDetails.TMerchantEntity merchantInfo;
    private JobDetails.TJobInfoEntity jobinfo;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_POST_SUCCESS = 5;
    private int MSG_POST_FAIL = 6;
    private Handler mHandler = new Myhandler(this);
    private Context mContext = JobItemDetailActivity.this;
    private int loginId;
    private String img;
    private String name;
    private Model.ListTJobEntity modleJob;
    private int jobid;
    BaseBean<JobDetails> jobDetailsBaseBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick({R.id.img_back, R.id.btn_boy, R.id.btn_girl,R.id.btn_no_limit, R.id.btn_change, R.id.btn_finish, R.id.btn_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_boy:
                Intent boyIntent=new Intent(this,FilterActivity.class);
                boyIntent.putExtra("jobid",jobinfo.getId());
                startActivity(boyIntent);
                break;
            case R.id.btn_girl:
                Intent girgleIntent=new Intent(this,FilterActivity.class);
                girgleIntent.putExtra("jobid",jobinfo.getNv_job_id());
                startActivity(girgleIntent);
                break;
            case R.id.btn_no_limit:
                Intent Intent=new Intent(this,FilterActivity.class);
                Intent.putExtra("jobid",String.valueOf(jobid));
                startActivity(Intent);
                break;
            case R.id.btn_change:
                Intent changeIntent=new Intent(this,PublishDetailActivity.class);
                changeIntent.putExtra("jobid",String.valueOf(jobid));
                changeIntent.putExtra("type","change");
                startActivity(changeIntent);

                break;
            case R.id.btn_finish:
                PostActionTask postFinishTask=new PostActionTask(String.valueOf(loginId),String.valueOf(jobid),"9");
                postFinishTask.execute();
                break;
            case R.id.btn_down:
                if(modleJob.getStatus()==3){
                    Intent intent=new Intent(mContext, CalculateActivity.class);
                    intent.putExtra("money",tvWages.getText().toString());
                    intent.putExtra("name",tvMerchantName.getText().toString());
                    intent.putExtra("jobid",String.valueOf(jobinfo.getJob_id()));
                    intent.putExtra("jobNid",String.valueOf(jobinfo.getNv_job_id()));
//                    intent.putExtra("")
                    startActivity(intent);
                }else{
                    PostActionTask postDownTask=new PostActionTask(String.valueOf(loginId),String.valueOf(jobid),"13");
                    postDownTask.execute();
                }

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
            JobItemDetailActivity jobDetailActivity = (JobItemDetailActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    if (null!=jobDetailActivity.pDialog){
//                        jobDetailActivity.pDialog.dismiss();
//                    }
                    jobDetailActivity.jobDetailsBaseBean = (BaseBean) msg.obj;
                    jobDetailActivity.jobinfo = jobDetailActivity.jobDetailsBaseBean.getData().getT_job_info();
                    jobDetailActivity.merchantInfo = jobDetailActivity.jobDetailsBaseBean.getData().getT_merchant();
                    jobDetailActivity.fillData();
                    break;
                case 1:
//                    if (null!=jobDetailActivity.pDialog){
//                        jobDetailActivity.pDialog.dismiss();
//                    }
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
//                    if (null!=jobDetailActivity.pDialog){
//                        jobDetailActivity.pDialog.dismiss();
//                    }
//                    BaseBean<RealName> realNameBaseBean = (BaseBean<RealName>) msg.obj;
//                    jobDetailActivity.showShortToast("获取实名信息成功");
//                    jobDetailActivity.setInf(realNameBaseBean.getData());
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    String signMessage = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, signMessage, Toast.LENGTH_SHORT).show();
//                    jobDetailActivity.tvSignup.setText("已报名");
//                    jobDetailActivity.tvSignup.setBackgroundResource(R.color.gray);
                    break;
                case 5:
                    String Message = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, Message, Toast.LENGTH_SHORT).show();
//                    jobDetailActivity.btnDown.setVisibility(View.GONE);
//                    jobDetailActivity.btnChange.setVisibility(View.GONE);
//                    jobDetailActivity.btnFinish.setVisibility();
                    break;
                case 6:
                    String msg2 = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, "收藏失败" + msg2, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }

    private void fillData() {

        if (modleJob.getStatus()==6){
            btnFinish.setVisibility(View.GONE);
            btnChange.setVisibility(View.GONE);
            btnDown.setText("已下架");
        }else if(modleJob.getStatus()==5){
            btnFinish.setVisibility(View.GONE);
            btnChange.setVisibility(View.GONE);
        }else if(modleJob.getStatus()==4){
            btnFinish.setVisibility(View.GONE);
            btnChange.setVisibility(View.GONE);
            btnDown.setText("去评价");
        }else if(modleJob.getStatus()==3){
            btnFinish.setVisibility(View.GONE);
            btnChange.setVisibility(View.GONE);
            btnDown.setText("去结算");
        }

        tvTitle.setText(modleJob.getName());
        tvMerchantName.setText(modleJob.getName());
        tvWorkLocation.setText(jobinfo.getAddress());
        tvManagerName.setText(modleJob.getMerchant_id_name());


        String date = DateUtils.getTime(Long.valueOf(jobinfo.getStart_date()), Long.valueOf(jobinfo.getStop_date()));
        tvWorkDate.setText(date);
        tvDate.setText(date);
        tvWorkTime.setText(DateUtils.getHm(Long.parseLong(jobinfo.getStart_time())) + "-" + DateUtils.getHm(Long.parseLong(jobinfo.getStop_time())));
        tvCollectionSites.setText(jobinfo.getSet_place());
        tvCollectionTime.setText(jobinfo.getSet_time());
        tvJobsCount.setText(modleJob.getCount() + "/" + modleJob.getSum());
        if (modleJob.getTerm() == 0) {//0=月结，1=周结，2=日结，3=小时结，4=次，5=义工
            tvWages.setText(modleJob.getMoney() + "/月");
        } else if (modleJob.getTerm() == 1) {
            tvWages.setText(modleJob.getMoney() + "/周");
        } else if (modleJob.getTerm() == 2) {
            tvWages.setText(modleJob.getMoney() + "/日");
        } else if (modleJob.getTerm() == 3) {
            tvWages.setText(modleJob.getMoney() + "/小时");
        } else if (modleJob.getTerm() == 6) {
            tvWages.setText(modleJob.getMoney() + "/次");
        } else if (modleJob.getTerm() == 5) {
            tvWages.setText("义工");
        }
        tvCount.setText(modleJob.getCount() + "/" + modleJob.getSum());

        if (jobinfo.getLimit_sex() == 0) {
            btnNoLimit.setVisibility(View.VISIBLE);
            llLimitSex.setVisibility(View.GONE);
            btnNoLimit.setText(modleJob.getCount()+"/"+modleJob.getSum());
            tvSex.setText("女");
        } else if (jobinfo.getLimit_sex() == 1) {
            btnNoLimit.setVisibility(View.VISIBLE);
            llLimitSex.setVisibility(View.GONE);
            btnNoLimit.setText(modleJob.getCount()+"/"+modleJob.getSum());
            tvSex.setText("男");
        } else if (jobinfo.getLimit_sex() == 2) {
            btnNoLimit.setVisibility(View.VISIBLE);
            llLimitSex.setVisibility(View.GONE);
            btnNoLimit.setText(modleJob.getCount()+"/"+modleJob.getSum());
            tvSex.setText("男女不限");
        }else {
            btnNoLimit.setVisibility(View.GONE);
            llLimitSex.setVisibility(View.VISIBLE);
            btnBoy.setText(modleJob.getCount()+"/"+modleJob.getSum());
            btnGirl.setText(jobinfo.getNv_count()+"/"+jobinfo.getNv_sum());
            tvSex.setText("男女各需");//性别限制（0=只招女，1=只招男，2=不限男女，30,31，男女各需）
        }

        //期限（1=月结，2=周结，3=日结，4=小时结）
        if (jobinfo.getTerm() == 0) {
            tvPayMethod.setText("月结");
        } else if (jobinfo.getTerm() == 1) {
            tvPayMethod.setText("周结");
        } else if (jobinfo.getTerm() == 2) {
            tvPayMethod.setText("日结");
        } else
            tvPayMethod.setText("小时结");
        if (jobinfo.getOther()==null||jobinfo.getOther().equals("null")||jobinfo.getOther().equals("")){
            tvOther.setText("暂无");
        }else
        tvOther.setText(jobinfo.getOther());
        tvWorkContent.setText(jobinfo.getWork_content());
        tvWorkRequire.setText(jobinfo.getWork_require());

        //商家信息

        tvCompanyName.setText(merchantInfo.getName());

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_partjob_item_detail);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {


    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        modleJob = (Model.ListTJobEntity) intent.getSerializableExtra("job");
        String alike = modleJob.getAlike();
         jobid = modleJob.getId();
        int merchantid = intent.getIntExtra("merchantid", 0);
        loginId = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        GetTask getTask = new GetTask(String.valueOf(loginId), String.valueOf(jobid), String.valueOf(merchantid), alike);
        getTask.execute();

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(JobItemDetailActivity.this);
    }


    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String login_id;
        private final String job_id;
        private final String merchant_id;
        private final String alike;

        GetTask(String login_id, String job_id, String merchant_id, String alike) {
            this.job_id = job_id;
            this.merchant_id = merchant_id;
            this.login_id = login_id;
            this.alike = alike;

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
                    .url(Constants.GET_JOB_DETAIL)
                    .addParams("only", only)
                    .addParams("login_id", login_id)
                    .addParams("job_id", job_id)
                    .addParams("merchant_id", merchant_id)
                    .addParams("alike", alike)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<JobDetails>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<JobDetails>>() {
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
//                              SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
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
    public class PostActionTask extends AsyncTask<Void, Void, Void> {
        private final String loginid;
        private final String jobid;
        private final String offer;

        PostActionTask(String loginid,String jobid,String offer) {
            this.loginid = loginid;
            this.jobid = jobid;
            this.offer = offer;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                postAction();
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
        public void postAction() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_DOWN)
                    .addParams("only", only)
                    .addParams("job_id", jobid)
                    .addParams("offer", offer)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }
    }

}
