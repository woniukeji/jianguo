package com.woniukeji.jianguo.wallte;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.Balance;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.BaseCallback;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;

public class DrawMoneyActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.img_alipay) ImageView imgAlipay;
    @InjectView(R.id.tv_alipay) TextView tvAlipay;
    @InjectView(R.id.tv_ali_is_bind) TextView tvAliIsBind;
    @InjectView(R.id.rl_alipay) RelativeLayout rlAlipay;
    @InjectView(R.id.img_yinlian) ImageView imgYinlian;
    @InjectView(R.id.tv_yinlian) TextView tvYinlian;
    @InjectView(R.id.tv_yinlian_is_bind) TextView tvYinlianIsBind;
    @InjectView(R.id.rl_yinlian) RelativeLayout rlYinlian;
    @InjectView(R.id.tv_money_title) TextView tvMoneyTitle;
    @InjectView(R.id.et_money_sum) EditText etMoneySum;
    @InjectView(R.id.btn_post) Button btnPost;
    @InjectView(R.id.img_go01) ImageView imgGo01;
    @InjectView(R.id.rb_ailipay) RadioButton rbAilipay;
    @InjectView(R.id.img_go02) ImageView imgGo02;
    @InjectView(R.id.rb_yinlian) RadioButton rbYinlian;
    private Balance balance;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private Handler mHandler = new Myhandler(this);
    private String type="";
    private int loginid;
    private double blanceMoney;

    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
               DrawMoneyActivity activity = (DrawMoneyActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    SPUtils.setParam(activity.context, Constants.USER_INFO,Constants.USER_PAY_PASS,activity.newPassWord);
//                    SPUtils.setParam(activity, Constants.USER_INFO,Constants.USER_PAY_PASS,activity.newPassWord);
                    String Message = (String) msg.obj;
                    Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();
                    activity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(activity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

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
        setContentView(R.layout.activity_draw_money);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        rbYinlian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   rbAilipay.setChecked(false);
                   type="1";
               }
            }
        });
        rbAilipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rbYinlian.setChecked(false);
                    type="0";
                }
            }
        });

    }

    @Override
    public void initData() {
        Intent balanceIntent = getIntent();
        balance = (Balance) balanceIntent.getSerializableExtra("balance");

        /**
         * id : 1
         * login_id : 4
         * name : 谢军
         * money : 50.0
         * zhifubao : 0
         * yinhang : 0
         * kahao : 0
         * pay_password : 0
         */
         blanceMoney=balance.getData().getT_user_money().getMoney();

        if (!balance.getData().getT_user_money().getYinhang().equals("0")) {
            imgGo02.setVisibility(View.GONE);
            rbYinlian.setVisibility(View.VISIBLE);
            rbYinlian.setChecked(true);
            tvYinlianIsBind.setText(balance.getData().getT_user_money().getYinhang());
            type="1";

        }
        if(!balance.getData().getT_user_money().getZhifubao().equals("0")){
            rbAilipay.setChecked(true);
            rbAilipay.setVisibility(View.VISIBLE);
            imgGo01.setVisibility(View.GONE);
            tvAliIsBind.setText(balance.getData().getT_user_money().getName());
            type="0";
        }
        if (!balance.getData().getT_user_money().getYinhang().equals("0")&&!balance.getData().getT_user_money().getZhifubao().equals("0")){
            rbAilipay.setChecked(true);
            rbYinlian.setChecked(false);
            type="0";
        }
        loginid = (int) SPUtils.getParam(DrawMoneyActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
    }

    @Override
    public void addActivity() {

    }

    @OnClick({R.id.img_back, R.id.rl_alipay, R.id.rl_yinlian, R.id.btn_post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_alipay:
                startActivity(new Intent(DrawMoneyActivity.this, BindAliActivity.class));
                break;
            case R.id.rl_yinlian:
                startActivity(new Intent(DrawMoneyActivity.this, BindYinlianActivity.class));
                break;
            case R.id.btn_post:
                //先判断支付宝和银行卡是否绑定过，再判断选中的是哪个，最后判断金额是否正确
                if (type.equals("")) {
                       showShortToast("请先绑定支付宝或银联卡！");
                    return;
                }else if(etMoneySum.getText().toString()==null||etMoneySum.getText().toString().equals("")){
                    showShortToast("请输入取现金额");
                    return;
            }else if(blanceMoney<Double.valueOf(etMoneySum.getText().toString())){
                    showShortToast("提现金额不能大于当前余额");
                    return;
                }
                PostTask postTask=new PostTask(String.valueOf(loginid),type,etMoneySum.getText().toString());
                postTask.execute();
                break;
        }
    }



    /**
     * 提现Task
     */
    public class PostTask extends AsyncTask<Void, Void, Void> {

        private final String id;
        private final String type;
        private final String money;
//        private final String kahao;
        SweetAlertDialog pDialog = new SweetAlertDialog(DrawMoneyActivity.this, SweetAlertDialog.PROGRESS_TYPE);

        PostTask(String id, String type,String money) {
            this.id = id;
            this.type = type;
            this.money = money;
        }

        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            UserRegisterPhone();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("保存中...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(final Void user) {
            pDialog.dismiss();
        }

        /**
         * UserRegisterPhone
         * 修改密码
         */
        public void UserRegisterPhone() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_DRAW_MONEY)
                    .addParams("only", only)
                    .addParams("status", type)
                    .addParams("type", type)
                    .addParams("login_id", id)
                    .addParams("money", money)
                    .build()
                    .connTimeOut(30000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new BaseCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.getMessage();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }


                        @Override
                        public void onResponse(BaseBean response) {
                            if (response.getCode().equals("200")) {
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.obj = response.getMessage();
                                message.what = MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }

                        }


                    });
        }
    }
}
