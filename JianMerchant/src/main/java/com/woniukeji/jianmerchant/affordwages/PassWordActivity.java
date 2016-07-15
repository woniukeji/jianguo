package com.woniukeji.jianmerchant.affordwages;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.BaseCallback;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;

public class PassWordActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.tv_confirm) TextView tvConfirm;
    @InjectView(R.id.et_pass_one) EditText etPassOne;
    @InjectView(R.id.et_pass_two) EditText etPassTwo;
    @InjectView(R.id.ll_change) LinearLayout llChange;
    @InjectView(R.id.btn_change_pay_pass) Button btnChangePayPass;
    @InjectView(R.id.ll_changed) LinearLayout llChanged;
    @InjectView(R.id.et_pass_old) EditText etPassOld;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private Handler mHandler = new Myhandler(this);
    private Context context = PassWordActivity.this;
    private int merchartid;
    private String password;
    private String newPassWord;

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
            PassWordActivity activity = (PassWordActivity) reference.get();
            switch (msg.what) {
                case 0:
                    SPUtils.setParam(activity.context,Constants.USER_INFO,Constants.USER_PAY_PASS,activity.newPassWord);
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
        setContentView(R.layout.activity_pass_word);
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
//        SPUtils.setParam(context,Constants.USER_INFO,Constants.USER_MERCHANT_ID,user.getT_merchant().getId());
        merchartid = (int) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_MERCHANT_ID, 0);
        password = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_PAY_PASS,"");

    }

    @Override
    public void addActivity() {

    }


    @OnClick({R.id.img_back, R.id.tv_confirm, R.id.btn_change_pay_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.btn_change_pay_pass:
                llChange.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_confirm:
                if (!etPassOld.getText().toString().equals(password)){
                    showShortToast("旧密码错误！");
                    return;
                }
                if (etPassOne.getText().toString().equals(etPassTwo.getText().toString())&&etPassOne.getText().toString()!=null&&!etPassOne.getText().toString().equals("")) {
                    newPassWord=etPassOne.getText().toString();
                    UserRegisterPhone(String.valueOf(merchartid), newPassWord);

                } else
                    showShortToast("两次设置的密码不相同或新密码为空");
                break;
        }
    }

        /**
         * UserRegisterPhone
         * 修改密码
         */
        public void UserRegisterPhone(String id, String passWord) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_PAY_PASSWORD)
                    .addParams("only", only)
                    .addParams("id", id)
                    .addParams("pay_password", passWord)
                    .build()
                    .connTimeOut(30000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new BaseCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Message message = new Message();
                            message.obj = e.getMessage();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean response, int id) {
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
