package com.woniukeji.jianguo.partjob;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.JobDetails;
import com.woniukeji.jianguo.entity.RealName;
import com.woniukeji.jianguo.leanmessage.ChatManager;
import com.woniukeji.jianguo.talk.ChatActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.CropCircleTransfermation;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.CircleImageView;
import com.woniukeji.jianguo.widget.SharePopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class JobDetailActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView img_share;
    @InjectView(R.id.user_head) ImageView userHead;
    @InjectView(R.id.business_name) TextView businessName;
    @InjectView(R.id.tv_wage) TextView tvWage;
    @InjectView(R.id.tv_hiring_count) TextView tvHiringCount;
    @InjectView(R.id.tv_enroll_num) TextView tvEnrollNum;
    @InjectView(R.id.tv_release_date) TextView tvReleaseDate;
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
    @InjectView(R.id.tv_work_content) TextView tvWorkContent;
    @InjectView(R.id.rl_work) RelativeLayout rlWork;
    @InjectView(R.id.tv_work_require) TextView tvWorkRequire;
    @InjectView(R.id.tv_worker) TextView tvWorker;
    @InjectView(R.id.cirimg_work) CircleImageView cirimgWork;
    @InjectView(R.id.tv_company_name) TextView tvCompanyName;
    @InjectView(R.id.tv_jobs_count) TextView tvJobsCount;
    @InjectView(R.id.rl_company) RelativeLayout rlCompany;
    @InjectView(R.id.tv_contact_company) TextView tvContactCompany;
    @InjectView(R.id.tv_collection) TextView tvCollection;
    @InjectView(R.id.tv_signup) TextView tvSignup;
    private JobDetails.TMerchantEntity merchantInfo;
    private JobDetails.TJobInfoEntity jobinfo;
    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private Handler mHandler = new Myhandler(this);
    private Context mContext = JobDetailActivity.this;
    private int loginId;
    private String img;
    private String name;

    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JobDetailActivity jobDetailActivity = (JobDetailActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    if (null!=jobDetailActivity.pDialog){
//                        jobDetailActivity.pDialog.dismiss();
//                    }
                    BaseBean<JobDetails> jobDetailsBaseBean = (BaseBean) msg.obj;
                    jobDetailActivity.jobinfo = jobDetailsBaseBean.getData().getT_job_info();
                    jobDetailActivity.merchantInfo = jobDetailsBaseBean.getData().getT_merchant();
                    jobDetailActivity.fillData();
                    //手动保存认证状态 防止未重新登录情况下再次进入该界面
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
                    BaseBean<RealName> realNameBaseBean = (BaseBean<RealName>) msg.obj;
                    jobDetailActivity.showShortToast("获取实名信息成功");
//                    jobDetailActivity.setInf(realNameBaseBean.getData());
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    String signMessage = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, signMessage, Toast.LENGTH_SHORT).show();
                    jobDetailActivity.tvSignup.setText("已报名");
                    jobDetailActivity.tvSignup.setBackgroundResource(R.color.gray);
                    break;
                default:
                    break;
            }
        }


    }

    private void fillData() {


        tvWorkLocation.setText(jobinfo.getAddress());
        String date = DateUtils.getTime(jobinfo.getStart_date(), jobinfo.getStop_date());
        String time = DateUtils.getTime(jobinfo.getStart_time(), jobinfo.getStop_time(), "HH:mm");
        String setTime = DateUtils.getTime(jobinfo.getSet_time(), "HH:mm");
        tvWorkDate.setText(date);
        tvWorkTime.setText(time);
        tvCollectionSites.setText(jobinfo.getSet_place());
        tvCollectionTime.setText(setTime);
        if (jobinfo.getLimit_sex() == 0) {
            tvSex.setText("女");
        } else if (jobinfo.getLimit_sex() == 1) {
            tvSex.setText("男");
        } else
            tvSex.setText("男女不限");//性别限制（0=只招女，1=只招男，2=不限男女）
        //期限（1=月结，2=周结，3=日结，4=小时结）
        if (jobinfo.getTerm() == 1) {
            tvPayMethod.setText("月结");
        } else if (jobinfo.getTerm() == 2) {
            tvPayMethod.setText("周结");
        } else if (jobinfo.getTerm() == 3) {
            tvPayMethod.setText("日结");
        } else
            tvPayMethod.setText("小时结");

        tvOther.setText(jobinfo.getOther());

        tvWorkContent.setText(jobinfo.getWork_content());
        tvWorkRequire.setText(jobinfo.getWork_require());

        //商家信息

        tvCompanyName.setText(merchantInfo.getName());
//        tvHiringCount.setText(merchantInfo.getJob_count());
        Picasso.with(JobDetailActivity.this).load(merchantInfo.getName_image())
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new CropCircleTransfermation())
                .into(cirimgWork);
        Picasso.with(JobDetailActivity.this).load(merchantInfo.getName_image())
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new CropCircleTransfermation())
                .into(userHead);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_job_detail);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("兼职详情");
        img_share.setVisibility(View.VISIBLE);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

       Intent intent= getIntent();
        int jobid= intent.getIntExtra("job",0);
        int merchantid=  intent.getIntExtra("merchant",0);
        int money= (int) intent.getDoubleExtra("money",0);
        String count=intent.getStringExtra("count");
        tvWage.setText(String.valueOf(money));
        tvJobsCount.setText(count);

         loginId = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        img = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.SP_IMG, "");
        name = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.SP_NAME, "");

        GetTask getTask=new GetTask(String.valueOf(loginId),String.valueOf(jobid),String.valueOf(merchantid));
        getTask.execute();
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(JobDetailActivity.this);
    }


    @OnClick({R.id.img_share,R.id.img_back, R.id.tv_location_detail, R.id.rl_company, R.id.tv_contact_company, R.id.tv_collection, R.id.tv_signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:

                SharePopupWindow share = new SharePopupWindow(JobDetailActivity.this, mHandler);
                share.showShareWindow();
                // 显示窗口 (设置layout在PopupWindow中显示的位置)
                share.showAtLocation(JobDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_job_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_location_detail:
                break;
            case R.id.rl_company:
                Intent intent=new Intent(this,MerchantActivity.class);
                intent.putExtra("merchant",merchantInfo);
                startActivity(intent);
                break;
            case R.id.tv_contact_company:
                final int Id=merchantInfo.getId();
//                String.valueOf(Id);
                final String toUserId="48";
                Map<String, Object> attrs = new HashMap<>();
                attrs.put(Constants.CREAT_NAME, name);
                attrs.put(Constants.CREAT_IMG, img);
                attrs.put(Constants.OTHER_IMG, merchantInfo.getName_image());
                attrs.put(Constants.OTHER_NAME, merchantInfo.getName());
                attrs.put(Constants.C_TYPE, 0);
                ChatManager.getInstance().getImClient().createConversation(Arrays.asList(toUserId), name, attrs, false, true, new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation avimConversation, AVIMException e) {
                        if (e == null) {
                            Map<String, Object> attributes = new HashMap<String, Object>();
                            attributes.put("userid", String.valueOf(loginId));
                            attributes.put("touserid", toUserId);
                            attributes.put("nickname", name);
                            attributes.put("avatar", img);
                            attributes.put("type", 0);
                            AVIMTextMessage message = new AVIMTextMessage();
                            message.setText("出来聊会天吧！");
                            message.setAttrs(attributes);
                            avimConversation.sendMessage(message, null);

                            Intent intent=new Intent(JobDetailActivity.this, ChatActivity.class);
                            intent.putExtra("mConversationId",avimConversation.getConversationId());
                            startActivity(intent);
//                        finish();
                        }else {
                            String mes = e.getMessage();
                            mes.trim();
                        }
                    }
                });

                break;
            case R.id.tv_collection:
                Drawable drawable=getResources().getDrawable(R.drawable.icon_collection_check);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvCollection.setCompoundDrawables(null,drawable,null,null);
                showShortToast("收藏成功");
                break;
            case R.id.tv_signup:
                SignUpPopuWin signUpPopuWin=new SignUpPopuWin(mContext,mHandler,4);
                signUpPopuWin.showShareWindow();
                Rect rect = new Rect();
                JobDetailActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                int winHeight =JobDetailActivity.this. getWindow().getDecorView().getHeight();
                signUpPopuWin.showAtLocation(JobDetailActivity.this.getWindow().getDecorView(),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, winHeight-rect.bottom);
//                shareBoard.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0,winHeight-rect.bottom);
                break;
        }
    }


    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String login_id;
        private final String job_id;
        private final String merchant_id;

        GetTask(String login_id,String job_id,String merchant_id) {
            this.job_id = job_id;
            this.merchant_id = merchant_id;
            this.login_id = login_id;

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
