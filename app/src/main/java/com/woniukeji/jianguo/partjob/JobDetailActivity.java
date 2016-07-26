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

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.JobDetails;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.entity.RealName;
import com.woniukeji.jianguo.entity.RxJobDetails;
import com.woniukeji.jianguo.http.HttpMethods;
import com.woniukeji.jianguo.http.ProgressSubscriber;
import com.woniukeji.jianguo.http.SubscriberOnNextListener;
import com.woniukeji.jianguo.login.LoginActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.CropCircleTransfermation;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.CircleImageView;
import com.woniukeji.jianguo.widget.SharePopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import okhttp3.Call;
import okhttp3.Response;

public class JobDetailActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView img_share;
    @BindView(R.id.user_head) ImageView userHead;
    @BindView(R.id.business_name) TextView businessName;
    @BindView(R.id.tv_wage) TextView tvWage;
    @BindView(R.id.tv_hiring_count) TextView tvHiringCount;
    @BindView(R.id.tv_enroll_num) TextView tvEnrollNum;
    @BindView(R.id.tv_release_date) TextView tvReleaseDate;
    @BindView(R.id.tv_work_location) TextView tvWorkLocation;
    @BindView(R.id.tv_location_detail) TextView tvLocationDetail;
    @BindView(R.id.tv_work_date) TextView tvWorkDate;
    @BindView(R.id.tv_work_time) TextView tvWorkTime;
    @BindView(R.id.tv_collection_sites) TextView tvCollectionSites;
    @BindView(R.id.tv_collection_time) TextView tvCollectionTime;
    @BindView(R.id.tv_sex) TextView tvSex;
    @BindView(R.id.tv_pay_method) TextView tvPayMethod;
    @BindView(R.id.tv_other) TextView tvOther;
    @BindView(R.id.tv_notic) TextView tvNotic;
    @BindView(R.id.tv_work_content) TextView tvWorkContent;
    @BindView(R.id.rl_work) RelativeLayout rlWork;
    @BindView(R.id.tv_work_require) TextView tvWorkRequire;
    @BindView(R.id.tv_worker) TextView tvWorker;
    @BindView(R.id.cirimg_work) CircleImageView cirimgWork;
    @BindView(R.id.tv_company_name) TextView tvCompanyName;
    @BindView(R.id.tv_jobs_count) TextView tvJobsCount;
    @BindView(R.id.rl_company) RelativeLayout rlCompany;
    @BindView(R.id.tv_contact_company) TextView tvContactCompany;
    @BindView(R.id.tv_collection) TextView tvCollection;
    @BindView(R.id.tv_signup) TextView tvSignup;
    @BindView(R.id.tv_more) TextView tvMore;

    private int MSG_GET_SUCCESS = 0;
    private int MSG_GET_FAIL = 1;
    private int MSG_POST_SUCCESS = 5;
    private int MSG_POST_FAIL = 6;
    private Handler mHandler = new Myhandler(this);
    private Context mContext = JobDetailActivity.this;
    private int loginId;
    private int jobid;
    private String resume;
    private String sex;
    private Jobs.ListTJobEntity job;
    private String money;
    private boolean loadMore=false;
    RxJobDetails.DataBean.TJobInfoBean t_job_info;
    RxJobDetails.DataBean.TMerchantBean t_merchant;
    private SubscriberOnNextListener<RxJobDetails> subscriberOnNextListener;
    private int merchantid;

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

                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    BaseBean<RealName> realNameBaseBean = (BaseBean<RealName>) msg.obj;
                    jobDetailActivity.showShortToast("获取实名信息成功");
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    jobDetailActivity.tvSignup.setText("已报名");
                    jobDetailActivity.tvSignup.setBackgroundResource(R.color.gray);
                    jobDetailActivity.tvSignup.setClickable(false);
                    break;
                case 5:
//                    String msg1 = (String) msg.obj;
//                    Toast.makeText(jobDetailActivity, msg1, Toast.LENGTH_SHORT).show();
                    Drawable drawable=jobDetailActivity.getResources().getDrawable(R.drawable.icon_collection_check);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    jobDetailActivity.tvCollection.setCompoundDrawables(null,drawable,null,null);
                    jobDetailActivity.showShortToast("收藏成功");
                    break;
                case 6:
                    String msg2 = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, "收藏失败"+msg2, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }


    }



    @Override
    public void setContentView() {
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("工作详情");
        img_share.setVisibility(View.VISIBLE);
    }

    @Override
    public void initListeners() {
        subscriberOnNextListener=new SubscriberOnNextListener<RxJobDetails>() {
            @Override
            public void onNext(RxJobDetails jobInfo) {
                fillData(jobInfo);
            }
        };
    }

    @Override
    public void initData() {
        Intent intent= getIntent();
        job= (Jobs.ListTJobEntity) intent.getSerializableExtra("jobbean");
        jobid= intent.getIntExtra("job",0);
        merchantid=  intent.getIntExtra("merchant",0);
        money=  intent.getStringExtra("money");
        String count=intent.getStringExtra("count");
        String mername=intent.getStringExtra("mername");
        loginId = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        resume = (String) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_RESUMM, "");
        sex = (String)SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_SEX, "");
        tvWage.setText(money);
        tvHiringCount.setText(count);
        businessName.setText(mername);
        HttpMethods.getInstance().getJobDetail(new ProgressSubscriber<RxJobDetails>(subscriberOnNextListener,this),String.valueOf(loginId),String.valueOf(jobid),String.valueOf(merchantid));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(JobDetailActivity.this);
    }


    @OnClick({R.id.img_share,R.id.tv_more,R.id.img_back, R.id.tv_location_detail, R.id.rl_company, R.id.tv_contact_company, R.id.tv_collection, R.id.tv_signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                if (loadMore){
                    tvWorkContent.setMaxLines(2);
                    tvWorkRequire.setMaxLines(2);
                    loadMore=false;
                    tvMore.setText("");
                    Drawable drawable= getResources().getDrawable(R.mipmap.icon_more);
                    tvMore.setCompoundDrawablesWithIntrinsicBounds (null,
                            null, null,drawable );
                }else {
                    tvWorkContent.setMaxLines(20);
                    tvWorkRequire.setMaxLines(20);
                    loadMore=true;
                    tvMore.setText("收起");
                    tvMore.setCompoundDrawablesWithIntrinsicBounds (null,
                            null, null, null);
                }

                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                SharePopupWindow share = new SharePopupWindow(JobDetailActivity.this, mHandler,String.valueOf(jobid),job,t_job_info,tvWorkDate.getText().toString(),tvWage.getText().toString());
                share.showShareWindow();
                // 显示窗口 (设置layout在PopupWindow中显示的位置)
                share.showAtLocation(JobDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_job_detail, null),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_location_detail:
                break;
            case R.id.rl_company:

//                Intent intent=new Intent(this,MerchantActivity.class);
//                intent.putExtra("merchant",merchantInfo);
//                startActivity(intent);
                break;
            case R.id.tv_contact_company:



                if (loginId==0){
                    showShortToast("请先登录");
                    startActivity(new Intent(JobDetailActivity.this, LoginActivity.class));
                    return;
                }
//                String tel=t_job_info.getTel();
//                if (tel==null||tel.equals("")){
//                    showShortToast("该商家暂无电话");
//                    return;
//                }
//                Mdialog mdialog=new Mdialog(mContext,tel);
//                mdialog.show();
                        LCChatKit.getInstance().open("Tom", new AVIMClientCallback() {
                            @Override
                            public void done(AVIMClient avimClient, AVIMException e) {
                                if (null == e) {
                                    finish();
                                    Intent intent = new Intent(JobDetailActivity.this, LCIMConversationActivity.class);
                                    intent.putExtra(LCIMConstants.PEER_ID, "77");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(JobDetailActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                final int Id=t_merchant.getId();
//                Intent intent=new Intent(JobDetailActivity.this, ChatActivity.class);
//                intent.putExtra("merchantId",Id);
//                startActivity(intent);

//                             String.valueOf(Id);
//                                final String toUserId="42";
//                                Map<String, Object> attrs = new HashMap<>();
//                                attrs.put(Constants.CREAT_NAME, "嘿嘿");
//                                attrs.put(Constants.CREAT_IMG, "嘿嘿");
//                                attrs.put(Constants.OTHER_IMG, t_merchant.getName_image());
//                                attrs.put(Constants.OTHER_NAME, t_merchant.getName());
//                                attrs.put(Constants.C_TYPE, 0);
//                                ChatManager.getInstance().getImClient().createConversation(Arrays.asList(toUserId), "嘿嘿", attrs, false, true, new AVIMConversationCreatedCallback() {
//                                        @Override
//                                        public void done(AVIMConversation avimConversation, AVIMException e) {
//                                                if (e == null) {
//                                                        Map<String, Object> attributes = new HashMap<String, Object>();
//                                                        attributes.put("userid", String.valueOf(loginId));
//                                                        attributes.put("touserid", toUserId);
//                                                        attributes.put("nickname", "嘿嘿");
//                                                        attributes.put("avatar", "嘿嘿");
//                                                        attributes.put("type", 0);
//                                                        AVIMTextMessage message = new AVIMTextMessage();
//                                                        message.setText("出来聊会天吧！");
//                                                        message.setAttrs(attributes);
//                                                        avimConversation.sendMessage(message, null);
//
//
////                                                    finish();
//                                                            }else {
//                                                        String mes = e.getMessage();
//                                                        mes.trim();
//                                                    }
//                                            }
//
//                                   });
                break;
            case R.id.tv_collection:
                if (loginId==0){
                    showShortToast("请先登录");
                    startActivity(new Intent(JobDetailActivity.this, LoginActivity.class));
                    return;
                }
                PostAttTask postAttTask=new PostAttTask(String.valueOf(loginId),"0",String.valueOf(t_job_info.getJob_id()));
                postAttTask.execute();


                break;
            case R.id.tv_signup:
                    if (loginId==0){
                        showShortToast("报名前请先登录");
                        startActivity(new Intent(JobDetailActivity.this, LoginActivity.class));
                        return;
                    }
                resume = (String) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_RESUMM, "");
                if (resume.equals("0")){
                    showShortToast("报名前先完善资料");
                    return;
//                    startActivity(new Intent(JobDetailActivity.this, QuickLoginActivity.class));
                }

                sex = (String)SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_SEX, "");
                        if (t_job_info==null||t_job_info.getLimit_sex()==30||t_job_info.getLimit_sex()==0){
                    if (sex.equals("1")){
                        showShortToast("您的性别不符");
                        return;
                    }
                }
                if (t_job_info.getLimit_sex()==31||t_job_info.getLimit_sex()==1){
                    if (sex.equals("0")){
                        showShortToast("您的性别不符");
                        return;
                    }
                }
                if (job.getCount()>=job.getSum()){
                        showShortToast("该兼职已报满，再看看其它的吧！");
                        return;
                }
                SignUpPopuWin signUpPopuWin=new SignUpPopuWin(mContext,mHandler,jobid,t_job_info,tvPayMethod.getText().toString(),money);
                signUpPopuWin.showShareWindow();
                Rect rect = new Rect();
                JobDetailActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                int winHeight =JobDetailActivity.this. getWindow().getDecorView().getHeight();
                signUpPopuWin.showAtLocation(JobDetailActivity.this.getWindow().getDecorView(),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, winHeight-rect.bottom);
                showShortToast("每天最多可以报名三个兼职！");
//                shareBoard.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0,winHeight-rect.bottom);
                break;
        }
    }
    private void fillData(RxJobDetails jobInfo) {
        t_job_info = jobInfo.getData().getT_job_info();
        t_merchant = jobInfo.getData().getT_merchant();
        tvWorkLocation.setText(t_job_info.getAddress());
        if (t_job_info!=null){
            if (t_job_info.getStart_date()!=null&&t_job_info.getStop_date()!=null){
                String date = DateUtils.getTime(Long.valueOf(t_job_info.getStart_date()),Long.valueOf( t_job_info.getStop_date()));
                tvWorkDate.setText(date);
            }
            String time = DateUtils.getHm(Long.parseLong(t_job_info.getStart_time()))+"-"+DateUtils.getHm(Long.parseLong(t_job_info.getStop_time()));
            String setTime =t_job_info.getSet_time();

            tvWorkTime.setText(time);
            tvCollectionSites.setText(t_job_info.getSet_place());
            tvCollectionTime.setText(setTime);
            if (job!=null){
                if (job.getStatus()!=0){
                    tvSignup.setText("该兼职已过期");
                    tvSignup.setBackgroundResource(R.color.gray);
                    tvSignup.setClickable(false);
                }else {
                    if (t_job_info.getIs_enroll().equals("1")){
                        tvSignup.setText("已报名");
                        tvSignup.setBackgroundResource(R.color.gray);
                        tvSignup.setClickable(false);
                    }
                }
                //期限（1=月结，2=周结，3=日结，4=小时结）
                if (job.getMode()==0){
                    tvPayMethod.setText("月结");
                }else if(job.getMode()==1){
                    tvPayMethod.setText("周结");
                }else if(job.getMode()==2){
                    tvPayMethod.setText("日结");
                }else {
                    tvPayMethod.setText("旅行");
                }
            }
            if (t_job_info.getIs_collection().equals("0")){
                Drawable drawable=getResources().getDrawable(R.drawable.icon_collection_normal);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvCollection.setCompoundDrawables(null,drawable,null,null);
            }else{
                Drawable drawable=getResources().getDrawable(R.drawable.icon_collection_check);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvCollection.setCompoundDrawables(null,drawable,null,null);
            }

            if (t_job_info.getLimit_sex() == 0) {
                tvSex.setText("女");
            } else if (t_job_info.getLimit_sex() == 1) {
                tvSex.setText("男");
            } else if (t_job_info.getLimit_sex() == 30) {
                tvSex.setText("女");
            }else if (t_job_info.getLimit_sex() == 31) {
                tvSex.setText("男");
            }else
                tvSex.setText("男女不限");//性别限制（0=只招女，1=只招男，2=不限男女）

            if (t_job_info.getOther()==null||t_job_info.getOther().equals("null")||t_job_info.getOther().equals("")){
                tvOther.setText("暂无");
            }else{
                tvOther.setText(t_job_info.getOther());
            }

            tvWorkContent.setText(t_job_info.getWork_content());
            tvWorkRequire.setText(t_job_info.getWork_require());
            tvReleaseDate.setText(job.getRegedit_time().substring(0,10)+" 发布");
            //商家信息

            tvCompanyName.setText(t_merchant.getName());
            if (t_merchant.getJob_count()==0){
                tvJobsCount.setText("多个职位在招");
            }else {
                tvJobsCount.setText(t_merchant.getJob_count()+"个职位在招");
            }

//        tvHiringCount.setText(merchantInfo.getJob_count());
            Picasso.with(JobDetailActivity.this).load(t_merchant.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new CropCircleTransfermation())
                    .into(cirimgWork);
            Picasso.with(JobDetailActivity.this).load(job.getName_image())
                    .placeholder(R.mipmap.icon_head_defult)
                    .error(R.mipmap.icon_head_defult)
                    .transform(new CropCircleTransfermation())
                    .into(userHead);

        }

    }

    public class PostAttTask extends AsyncTask<Void, Void, Void> {
        private final String login_id;
        private final String follow;
        private final String collection;

        PostAttTask(String login_id,String follow,String collection) {
            this.follow = follow;
            this.collection = collection;
            this.login_id = login_id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                PostAtten();
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
        public void PostAtten() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_ATTENT)
                    .addParams("only", only)
                    .addParams("login_id", login_id)
                    .addParams("follow", follow)
                    .addParams("collection", collection)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<JobDetails>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean baseBean = new Gson().fromJson(string, new TypeToken<BaseBean<JobDetails>>() {
                            }.getType());
                            return baseBean;
                        }

                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_POST_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean baseBean,int id) {
                            if (baseBean.getCode().equals("200")) {
//                                SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean;
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
