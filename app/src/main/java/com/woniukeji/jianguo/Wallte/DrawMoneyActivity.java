package com.woniukeji.jianguo.wallte;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.woniukeji.jianguo.entity.CodeCallback;
import com.woniukeji.jianguo.entity.SmsCode;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.utils.BitmapUtils;
import com.woniukeji.jianguo.utils.CommonUtils;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.MD5Coder;
import com.woniukeji.jianguo.utils.QiNiu;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
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
    @InjectView(R.id.tv_sms_title) TextView tvSmsTitle;
    @InjectView(R.id.et_sms) EditText etSms;
    @InjectView(R.id.btn_sms) Button btnSms;
    private Balance balance;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private Handler mHandler = new Myhandler(this);
    private String type = "";
    private int loginid;
    private double blanceMoney;
    private String sms;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

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
                    SmsCode smsCode= (SmsCode) msg.obj;
                    activity.sms=smsCode.getText();
                    activity.showShortToast("验证码已经发送，请注意查收");
                    break;
                case 3:
                    String sms1 = (String) msg.obj;
                    Toast.makeText(activity, sms1, Toast.LENGTH_SHORT).show();
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
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    public void initListeners() {
      etMoneySum.addTextChangedListener(new TextWatcher() {

          @Override
          public void onTextChanged(CharSequence s, int start, int before,
                                    int count) {
              if (s.toString().contains(".")) {
                  if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                      s = s.toString().subSequence(0,
                              s.toString().indexOf(".") + 3);
                      etMoneySum.setText(s);
                      etMoneySum.setSelection(s.length());
                  }
              }
              if (s.toString().trim().substring(0).equals(".")) {
                  s = "0" + s;
                  etMoneySum.setText(s);
                  etMoneySum.setSelection(2);
              }

              if (s.toString().startsWith("0")
                      && s.toString().trim().length() > 1) {
                  if (!s.toString().substring(1, 2).equals(".")) {
                      etMoneySum.setText(s.subSequence(0, 1));
                      etMoneySum.setSelection(1);
                      return;
                  }
              }
          }

          @Override
          public void beforeTextChanged(CharSequence s, int start, int count,
                                        int after) {

          }

          @Override
          public void afterTextChanged(Editable s) {
              // TODO Auto-generated method stub

          }

    });

rbYinlian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbAilipay.setChecked(false);
                    type = "1";
                }
            }
        });
        rbAilipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbYinlian.setChecked(false);
                    type = "0";
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
        blanceMoney = balance.getData().getT_user_money().getMoney();

        if (!balance.getData().getT_user_money().getYinhang().equals("0")) {
            imgGo02.setVisibility(View.GONE);
            rbYinlian.setVisibility(View.VISIBLE);
            rbYinlian.setChecked(true);
            tvYinlianIsBind.setText(balance.getData().getT_user_money().getYinhang());
            type = "1";

        }
        if (!balance.getData().getT_user_money().getZhifubao().equals("0")) {
            rbAilipay.setChecked(true);
            rbAilipay.setVisibility(View.VISIBLE);
            imgGo01.setVisibility(View.GONE);
            tvAliIsBind.setText(balance.getData().getT_user_money().getName());
            type = "0";
        }
        if (!balance.getData().getT_user_money().getYinhang().equals("0") && !balance.getData().getT_user_money().getZhifubao().equals("0")) {
            rbAilipay.setChecked(true);
            rbYinlian.setChecked(false);
            type = "0";
        }
        loginid = (int) SPUtils.getParam(DrawMoneyActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
    }
      public void checkAlipayYinLian(int bindPay){
          if (bindPay==1) {
              imgGo02.setVisibility(View.GONE);
              rbYinlian.setVisibility(View.VISIBLE);
              rbYinlian.setChecked(true);
              rbAilipay.setChecked(false);
              tvYinlianIsBind.setText("已绑定");
              type = "1";

          }
          if (bindPay==0) {
              rbAilipay.setChecked(true);
              rbYinlian.setChecked(false);
              rbAilipay.setVisibility(View.VISIBLE);
              imgGo01.setVisibility(View.GONE);
              tvAliIsBind.setText("已绑定");
              type = "0";
          }
//          if (!balance.getData().getT_user_money().getYinhang().equals("0") && !balance.getData().getT_user_money().getZhifubao().equals("0")) {
//              rbAilipay.setChecked(true);
//              rbYinlian.setChecked(false);
//              type = "0";
//          }
      }
    @Override
    public void addActivity() {

    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnSms.setClickable(false);
            btnSms.setText(millisUntilFinished / 1000 + "秒");
            btnSms.setBackgroundColor(Color.GRAY);
        }

        @Override
        public void onFinish() {
            btnSms.setText("验证码");
            Drawable rBlack;
            if(android.os.Build.VERSION.SDK_INT >= 21){
                rBlack = getResources().getDrawable(R.drawable.button_background_login, getTheme());
            } else {
                rBlack = getResources().getDrawable(R.drawable.button_background_login);
            }
            btnSms.setBackgroundDrawable(rBlack);
            btnSms.setClickable(true);
        }
    }
    @OnClick({R.id.img_back, R.id.rl_alipay, R.id.rl_yinlian, R.id.btn_post,R.id.btn_sms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_sms:
//                if (type.equals("")) {
//                    showShortToast("请先绑定支付宝或银联卡！");
//                    return;
//                } else if (etMoneySum.getText().toString() == null || etMoneySum.getText().toString().equals("")) {
//                    showShortToast("请输入取现金额");
//                    return;
//                } else if (50 >Double.valueOf(etMoneySum.getText().toString())) {
//                    showShortToast("提现金额不能小于50元");
//                    return;
//                }else if (blanceMoney < Double.valueOf(etMoneySum.getText().toString())) {
//                    showShortToast("提现金额不能大于当前余额");
//                    return;
//                }
                time.start();
                String tel= (String) SPUtils.getParam(DrawMoneyActivity.this,Constants.LOGIN_INFO,Constants.SP_TEL,"");
                GetSMS getSMS=new GetSMS(tel);
                getSMS.execute();
                break;
            case R.id.rl_alipay:
                Intent intentali=new Intent(DrawMoneyActivity.this, BindAliActivity.class);
                startActivityForResult(intentali,0);
                break;
            case R.id.rl_yinlian:
                Intent intent=new Intent(DrawMoneyActivity.this, BindYinlianActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.btn_post:
                //先判断支付宝和银行卡是否绑定过，再判断选中的是哪个，最后判断金额是否正确
                if (type.equals("")) {
                    showShortToast("请先绑定支付宝或银联卡！");
                    return;
                } else if (etMoneySum.getText().toString() == null || etMoneySum.getText().toString().equals("")) {
                    showShortToast("请输入取现金额");
                    return;
                } else if (blanceMoney < Double.valueOf(etMoneySum.getText().toString())) {
                    showShortToast("提现金额不能大于当前余额");
                    return;
                }else if (etSms.getText().toString().equals("")) {
                    showShortToast("请输入验证码");
                    return;
                }else if (!etSms.getText().toString().equals(sms)) {
                    showShortToast("验证码不正确");
                    return;
                }else if (Double.valueOf(etMoneySum.getText().toString())<50) {
                    showShortToast("提现金额必须大于50");
                    return;
                }
                PostTask postTask = new PostTask(String.valueOf(loginid), type, etMoneySum.getText().toString());
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

        PostTask(String id, String type, String money) {
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
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.getMessage();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }


                        @Override
                        public void onResponse(BaseBean response,int id) {
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

    /**
     * 手机验证码Task
     */
    public class GetSMS extends AsyncTask<Void, Void, User> {

        private final String tel;

        GetSMS(String phoneNum) {
            this.tel = phoneNum;
        }

        protected User doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            CheckPhone();
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final User user) {
        }

        /**
         * login
         * 检查手机号是否存在
         */
        public void CheckPhone() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.CHECK_PHONE_BLACK)
                    .addParams("tel", tel)
                    .addParams("only", only)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new CodeCallback() {

                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message=new Message();
                            message.obj=e.toString();
                            message.what=MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(SmsCode response,int id) {
                            Message message=new Message();
                            message.obj=response;
                            message.what=MSG_PHONE_SUCCESS;
                            mHandler.sendMessage(message);
                        }


                    });
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // ALI
                checkAlipayYinLian(0);
            }
        }else{
            if (resultCode == RESULT_OK) {
                //yinlian
                checkAlipayYinLian(1);
            }
        }


    }
}
