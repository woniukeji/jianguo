package com.woniukeji.jianguo.partjob;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.JobDetails;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.woniukeji.jianguo.widget.time.PickerDateView;
import com.woniukeji.jianguo.widget.time.PickerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Response;

public class SignUpPopuWin extends PopupWindow implements View.OnClickListener {

    private final Context context;
    private final Handler mHandler;
    RelativeLayout mRlRootView;
    TextView mTvInfo;
    TextView mTvWage;
    TextView mTvWorkLocation;
    TextView mTvWorkDate;
    TextView mTvWorkTime;
    TextView mTvCollectionSites;
    TextView mTvCollectionTime;
    TextView mTvSex;
    TextView mTvPayMethod;
    TextView mTvOther;
    Button mTvOk;
    ImageView mImgOff;
    private int MSG_POST_FAIL=1;
    public int MSG_POST_SUCCESS=4;
    private int jobid;
    private JobDetails.TJobInfoEntity jobinfo;
    private String payMethod;
     private String money;
    public SignUpPopuWin(Context context, Handler handler, int id, JobDetails.TJobInfoEntity jobinfo, String toString, String s) {
        this.context = context;
        this.mHandler=handler;
        jobid=id;
        this.payMethod=s;
        this.jobinfo=jobinfo;

    }
    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_signup, null);

        initView(view);
        initListeners();
        initDate();
        // 添加布局
        this.setContentView(view);
        // 设置SharePopupWindow宽度
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SharePopupWindow高度
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置setFocusable可获取焦点
        this.setFocusable(true);
        // 设置setFocusable动画风格
//        this.setAnimationStyle(R.style.AnimBottom);
        //画背景
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置背景
        this.setBackgroundDrawable(dw);


    }
    public void initView(View view) {
            mRlRootView = (RelativeLayout) view.findViewById(R.id.rl_root_view);
            mTvInfo = (TextView) view.findViewById(R.id.tv_info);
            mTvWage = (TextView) view.findViewById(R.id.tv_wage);
            mTvWorkLocation = (TextView) view.findViewById(R.id.tv_work_location);
            mTvWorkDate = (TextView) view.findViewById(R.id.tv_work_date);
            mTvWorkTime = (TextView) view.findViewById(R.id.tv_work_time);
            mTvCollectionSites = (TextView) view.findViewById(R.id.tv_collection_sites);
            mTvCollectionTime = (TextView) view.findViewById(R.id.tv_collection_time);
            mTvSex = (TextView) view.findViewById(R.id.tv_sex);
            mTvPayMethod = (TextView) view.findViewById(R.id.tv_pay_method);
            mTvOther = (TextView) view.findViewById(R.id.tv_other);
            mTvOk = (Button) view.findViewById(R.id.tv_ok);
        mImgOff= (ImageView) view.findViewById(R.id.img_off);

    }
    public void initListeners() {
        mTvOk.setOnClickListener(this);
        mImgOff.setOnClickListener(this);
    }


    private void initDate() {

        if (jobinfo!=null){

            mTvWorkLocation.setText(jobinfo.getAddress());
            String date = DateUtils.getTime(Long.valueOf(jobinfo.getStart_date()),Long.valueOf( jobinfo.getStop_date()));
            String time = DateUtils.getHm(Long.parseLong(jobinfo.getStart_time()))+"-"+DateUtils.getHm(Long.parseLong(jobinfo.getStop_time()));
            String setTime =jobinfo.getSet_time();
            mTvWage.setText(money);
            mTvWorkDate.setText(date);
            mTvWorkTime.setText(time);
            mTvCollectionSites.setText(jobinfo.getSet_place());
            mTvCollectionTime.setText(setTime);


            if (jobinfo.getLimit_sex() == 0) {
                mTvSex.setText("女");
            } else if (jobinfo.getLimit_sex() == 1) {
                mTvSex.setText("男");
            } else if (jobinfo.getLimit_sex() == 30) {
                mTvSex.setText("女");
            }else if (jobinfo.getLimit_sex() == 31) {
                mTvSex.setText("男");
            }else{
                mTvSex.setText("男女不限");//性别限制（0=只招女，1=只招男，2=不限男女）
            }

            //期限（1=月结，2=周结，3=日结，4=小时结）
                mTvPayMethod.setText(payMethod);

            if (jobinfo.getOther()==null||jobinfo.getOther().equals("null")||jobinfo.getOther().equals("")){
                mTvOther.setText("暂无");
            }else {
                mTvOther.setText(jobinfo.getOther());
            }
            }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_off:
                dismiss();
                break;
            case R.id.tv_ok:
                int loginId = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
                GetTask getTask=new GetTask(String.valueOf(loginId),String.valueOf(jobid));
                getTask.execute();
                Message message=new Message();
//                message.obj="报名成功";
//                message.what=4;
//                mHandler.sendMessage(message);

                break;
        }
    }
    public class GetTask extends AsyncTask<Void, Void, Void> {
        private final String login_id;
        private final String job_id;

        GetTask(String login_id,String job_id) {
            this.job_id = job_id;
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
                    .url(Constants.POST_SIGN)
                    .addParams("only", only)
                    .addParams("login_id", login_id)
                    .addParams("job_id", job_id)
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
                            dismiss();
                        }

                        @Override
                        public void onResponse(BaseBean baseBean) {
                            if (baseBean.getCode().equals("200")) {
//                              SPUtils.setParam(AuthActivity.this, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_SUCCESS;
                                mHandler.sendMessage(message);
                                dismiss();
                            } else if(baseBean.getCode().equals("500")){
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_FAIL;
                                mHandler.sendMessage(message);
                                dismiss();
                            }else if(baseBean.getCode().equals("403")){
                                Message message = new Message();
                                message.obj = baseBean.getMessage();
                                message.what = MSG_POST_FAIL;
                                mHandler.sendMessage(message);
                                dismiss();
                            }
                        }
                    });
        }
    }
}
