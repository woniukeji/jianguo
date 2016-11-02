package com.woniukeji.jianguo.activity.partjob;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.JobInfo;
import com.woniukeji.jianguo.http.BackgroundSubscriber;
import com.woniukeji.jianguo.http.HttpMethods;
import com.woniukeji.jianguo.http.ProgressSubscriber;
import com.woniukeji.jianguo.http.SubscriberOnNextListener;
import com.woniukeji.jianguo.activity.login.LoginActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.GlideCircleTransform;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.Mdialog;
import com.woniukeji.jianguo.widget.SharePopupWindow;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;

public class JobDetailActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView img_share;
    @BindView(R.id.user_head) ImageView userHead;
    @BindView(R.id.img_pass) ImageView imgPass;

    @BindView(R.id.business_name) TextView businessName;
    @BindView(R.id.tv_wage) TextView tvWage;
    @BindView(R.id.tv_hiring_count) TextView tvHiringCount;
//    @BindView(R.id.tv_enroll_num) TextView tvEnrollNum;
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
    @BindView(R.id.cirimg_work) ImageView cirimgWork;
    @BindView(R.id.tv_company_name) TextView tvCompanyName;
    @BindView(R.id.tv_jobs_count) TextView tvJobsCount;
    @BindView(R.id.tv_contact_company) TextView tvContactCompany;
    @BindView(R.id.tv_collection) TextView tvCollection;
    @BindView(R.id.tv_signup) TextView tvSignup;
    @BindView(R.id.tv_more) TextView tvMore;
    @BindView(R.id.ll_require) LinearLayout llRequire;
    @BindView(R.id.rl_require) RelativeLayout rlRequire;
    @BindView(R.id.ll_welfare) LinearLayout llWelfare;
    @BindView(R.id.id_require) TagFlowLayout idRequire;
    @BindView(R.id.id_welfare) TagFlowLayout idWelfare;

    private Handler mHandler = new Myhandler(this);
    private Context mContext = JobDetailActivity.this;
    private int loginId;
    private int jobid;
    private String resume;
    private String sex;
    private boolean loadMore = false;
    private SubscriberOnNextListener<JobInfo> subscriberOnNextListener;
    private SubscriberOnNextListener<String> attentionSubscriberOnNextListener;
    private SubscriberOnNextListener<Void>  voidSubscriberOnNextListener;
    private TagAdapter welfareAdapter;
    private TagAdapter requireAdapter;
    private JobInfo mJobInfo;
    private List<String> limitBeens = new ArrayList<>();
    private List<String> welfareBeens = new ArrayList<>();
    private String strStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

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
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(jobDetailActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
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
        requireAdapter= new TagAdapter<String>(limitBeens) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_grid_require, idRequire, false);
                tv.setText(s);
                return tv;
            }
        };
        idRequire.setAdapter(requireAdapter);
        welfareAdapter= new TagAdapter<String>(welfareBeens) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_grid_lable,idWelfare, false);
                tv.setText(s);
                return tv;
            }
        };
        idWelfare.setAdapter(welfareAdapter);
    }

    @Override
    public void initListeners() {
        subscriberOnNextListener = new SubscriberOnNextListener<JobInfo>() {
            @Override
            public void onNext(JobInfo jobInfo) {
                mJobInfo = jobInfo;
                fillData(jobInfo);
            }
        };

        attentionSubscriberOnNextListener = new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                Drawable drawable = getResources().getDrawable(R.drawable.icon_collection_check);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvCollection.setCompoundDrawables(null, drawable, null, null);
                showShortToast("收藏成功", TastyToast.SUCCESS);
            }
        };
        voidSubscriberOnNextListener=new SubscriberOnNextListener<Void>() {
            @Override
            public void onNext(Void aVoid) {
             //浏览兼职接口成功
            }
        };
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        jobid = intent.getIntExtra("job", 0);
        strStatus = intent.getStringExtra("jobStatus");
        loginId = (int) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
        resume = (String) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_RESUMM, "");
        sex = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_SEX, "");
        HttpMethods.getInstance().getJobDetailNew(new ProgressSubscriber<JobInfo>(subscriberOnNextListener, this), String.valueOf(loginId), String.valueOf(jobid));
        HttpMethods.getInstance().postLook(new BackgroundSubscriber<Void>(voidSubscriberOnNextListener,this),String.valueOf(loginId),String.valueOf(jobid));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(JobDetailActivity.this);
    }


    @OnClick({R.id.img_share, R.id.tv_more, R.id.img_back, R.id.tv_location_detail, R.id.iv_company, R.id.tv_contact_company, R.id.tv_collection, R.id.tv_signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                loadingMore();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                shareJob();
                break;
            case R.id.tv_location_detail:
                break;
            case R.id.iv_company:
                contact_tel();
                break;
            case R.id.tv_contact_company:
                ContactCompany();
                break;
            case R.id.tv_collection:
                if (loginId == 0) {
                    showShortToast("请先登录");
                    startActivity(new Intent(JobDetailActivity.this, LoginActivity.class));
                    return;
                }
                HttpMethods.getInstance().postAttention(new ProgressSubscriber<String>(attentionSubscriberOnNextListener,this),String.valueOf(loginId),"0",String.valueOf(mJobInfo.getId()));
                break;
            case R.id.tv_signup:
                CheckSignRequire();
                break;
        }
    }

    private void contact_tel() {
        if (loginId == 0) {
            showShortToast("请先登录");
            startActivity(new Intent(JobDetailActivity.this, LoginActivity.class));
            return;
        }
        String tel=mJobInfo.getMerchantTel();
        if (tel==null||tel.equals("")){
            showShortToast("该商家暂无电话");
            return;
        }
        Mdialog mdialog=new Mdialog(mContext,tel);
        mdialog.show();
    }

    /**
     * 工作详情加载更多
     */
    private void loadingMore() {
        if (loadMore) {
            tvWorkContent.setMaxLines(2);
            tvWorkRequire.setMaxLines(2);
            loadMore = false;
            tvMore.setText("");
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_more);
            tvMore.setCompoundDrawablesWithIntrinsicBounds(null,null,null,drawable);
        } else {
            tvWorkContent.setMaxLines(100);
            tvWorkRequire.setMaxLines(100);
            loadMore = true;
            tvMore.setText("收起");
            tvMore.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    /**
     * 在线咨询商家
     * @author invinjun
     * created at 2016/8/18 15:54
     */
    private void ContactCompany() {
        if (loginId == 0) {
            showShortToast("请先登录");
            startActivity(new Intent(JobDetailActivity.this, LoginActivity.class));
            return;
        }
        LCChatKit.getInstance().open(String.valueOf(loginId), new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    finish();
                    Intent intent = new Intent(JobDetailActivity.this, LCIMConversationActivity.class);
                    intent.putExtra(LCIMConstants.PEER_ID, String.valueOf(mJobInfo.getMerchant_LogId())); //String.valueOf(t_job_info.getId())
                    intent.putExtra("job_name",mJobInfo.getJob_name());
                    startActivity(intent);
                } else {
                    Toast.makeText(JobDetailActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 报名信息校验
     *
     * @author invinjun
     * created at 2016/8/18 15:53
     */
    private void CheckSignRequire() {
        if (loginId == 0) {
            showShortToast("报名前请先登录");
            startActivity(new Intent(JobDetailActivity.this, LoginActivity.class));
            return;
        }
        resume = (String) SPUtils.getParam(mContext, Constants.LOGIN_INFO, Constants.SP_RESUMM, "");
        if (resume.equals("0")) {
            showShortToast("报名前先完善资料");
            return;
        }
        sex = (String) SPUtils.getParam(mContext, Constants.USER_INFO, Constants.USER_SEX, "");
        if (mJobInfo == null || mJobInfo.getLimit_sex().equals("30") || mJobInfo.getLimit_sex().equals("0")) {
            if (sex.equals("1")) {
                showShortToast("您的性别不符");
                return;
            }
        }
        if (mJobInfo.getLimit_sex().equals("31") || mJobInfo.getLimit_sex().equals("1")) {
            if (sex.equals("0")) {
                showShortToast("您的性别不符");
                return;
        }
        }
        int sum=mJobInfo.getFinallySum();//总人数
        if (sum<=10){
            sum=sum+5;
        }else if(sum>10){
            sum= (int) (sum*1.4);
        }
        if (mJobInfo.getUser_count() >= sum) {
            showShortToast("该兼职已报满，再看看其它的吧！");
            return;
        }
        if (mJobInfo.getStatus() != 0) {
            showShortToast("该兼职已报满，再看看其它的吧！");
            return;
        }


        SignUpPopuWin signUpPopuWin=new SignUpPopuWin(mContext,mHandler,jobid,mJobInfo);
        signUpPopuWin.showShareWindow();
        Rect rect = new Rect();
        JobDetailActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight =JobDetailActivity.this. getWindow().getDecorView().getHeight();
        signUpPopuWin.showAtLocation(JobDetailActivity.this.getWindow().getDecorView(),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, winHeight-rect.bottom);
        showShortToast("每天至多可以报名三个兼职！");
    }

    /**
     * 分享兼职
     *
     * @author invinjun
     * created at 2016/8/18 15:49
     */
    private void shareJob() {
        SharePopupWindow share = new SharePopupWindow(JobDetailActivity.this, mHandler,String.valueOf(jobid),mJobInfo,tvWorkDate.getText().toString(),tvWage.getText().toString());
        share.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        share.showAtLocation(JobDetailActivity.this.getLayoutInflater().inflate(R.layout.activity_job_detail, null),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void fillData(JobInfo jobInfo) {
        tvWorkLocation.setText(jobInfo.getAddress());
        businessName.setText(jobInfo.getJob_name());
        String date = DateUtils.getTime(Long.valueOf(jobInfo.getStart_date()), Long.valueOf(jobInfo.getStop_date()));
        tvWorkDate.setText(date);
        String time = DateUtils.getHm(Long.parseLong(jobInfo.getStart_time())) + "-" + DateUtils.getHm(Long.parseLong(jobInfo.getStop_time()));
        String setTime = jobInfo.getSet_time();
        tvWorkTime.setText(time);
        tvCollectionSites.setText(jobInfo.getSet_place());
        tvCollectionTime.setText(setTime);
        tvWage.setText(jobInfo.getJob_money());
     if (strStatus!=null&&!strStatus.equals("")){
         if (!strStatus.contains("招满")){
             SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(strStatus);
             ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.red1));
             spannableStringBuilder.setSpan(foregroundColorSpan, 2, spannableStringBuilder.length()-3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
             tvHiringCount.setText(spannableStringBuilder);
         }else {
             tvHiringCount.setText(strStatus);
         }
     }

        if (jobInfo.getStatus() != 0) {
            tvSignup.setText("已报满");
            tvSignup.setBackgroundResource(R.color.gray);
            tvSignup.setClickable(false);
        } else {
            if (jobInfo.getIsEnroll().equals("1")) {
                tvSignup.setText("已报名");
                tvSignup.setBackgroundResource(R.color.gray);
                tvSignup.setClickable(false);
            }
        }

        //期限（0=月结，1=周结，2=日结，3=小时结）
        if (jobInfo.getIsFavorite().equals("0")) {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_collection_normal);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvCollection.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_collection_check);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvCollection.setCompoundDrawables(null, drawable, null, null);
        }

        if (jobInfo.getLimit_sex().equals("0")) {
            tvSex.setText("女");
        } else if (jobInfo.getLimit_sex().equals("1")) {
            tvSex.setText("男");
        } else if (jobInfo.getLimit_sex().equals("30")) {
            tvSex.setText("女");
        } else if (jobInfo.getLimit_sex().equals("31")) {
            tvSex.setText("男");
        } else if (jobInfo.getLimit_sex().equals("2")) {
            tvSex.setText("男女不限");//性别限制（0=只招女，1=只招男，2=不限男女）
        } else if (jobInfo.getLimit_sex().equals("3")) {
            tvSex.setText("男女各限");//性别限制（0=只招女，1=只招男，2=不限男女）
        }
        tvWorkContent.setText(jobInfo.getWork_content());
        tvWorkRequire.setText(jobInfo.getWork_require());
        tvReleaseDate.setText(jobInfo.getReg_date().substring(0, 10) + " 发布");
        //商家信息
        tvCompanyName.setText(jobInfo.getMerchant_name());
        if (jobInfo.getMerchant_name().contains("合作商家")){
            imgPass.setVisibility(View.GONE);
            tvJobsCount.setVisibility(View.VISIBLE);
            tvPayMethod.setText("线下结算");
        }else {
            imgPass.setVisibility(View.VISIBLE);
            tvJobsCount.setVisibility(View.GONE);
            tvPayMethod.setText(jobInfo.getMode());
        }
        if (jobInfo.getLimit() != null && jobInfo.getLimit().size() > 0) {
            limitBeens.addAll(jobInfo.getLimit());
            requireAdapter.notifyDataChanged();
        } else {
            llRequire.setVisibility(View.GONE);
        }
        if (jobInfo.getWelfare() != null && jobInfo.getWelfare().size() > 0) {
            welfareBeens.addAll(jobInfo.getWelfare());
            welfareAdapter.notifyDataChanged();
        } else {
            llWelfare.setVisibility(View.GONE);
        }

        Glide.with(JobDetailActivity.this).load(jobInfo.getMerchant_image())
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new GlideCircleTransform(JobDetailActivity.this))
                .crossFade()
                .into(cirimgWork);
        Glide.with(JobDetailActivity.this).load(jobInfo.getJob_image())
                .placeholder(R.mipmap.icon_head_defult)
                .error(R.mipmap.icon_head_defult)
                .transform(new GlideCircleTransform(JobDetailActivity.this))
                .crossFade()
                .into(userHead);
    }
}


