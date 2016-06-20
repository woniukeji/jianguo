package com.woniukeji.jianmerchant.affordwages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.woniukeji.jianmerchant.R;
import com.woniukeji.jianmerchant.base.BaseActivity;
import com.woniukeji.jianmerchant.entity.AffordUser;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.User;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FinishActivity extends BaseActivity {


    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.tv_finish_pay_content) TextView tvFinishPayContent;
    @InjectView(R.id.btn_continue_pay) Button btnContinuePay;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = FinishActivity.this;
    AffordUser.ListTUserInfoEntity user;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick({R.id.img_back, R.id.btn_continue_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_continue_pay:
//                startActivity(new Intent(this,CalculateActivity.class));
                setResult(RESULT_OK);
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
            FinishActivity activity = (FinishActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
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
        setContentView(R.layout.activity_pay_finish);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        Intent intent=getIntent();
        String sum=intent.getStringExtra("sum");
        tvFinishPayContent.setText("结算成功，还有"+sum+"人未结算");
//        Picasso.with(FinishActivity.this).load(user.getName_image()).transform(new CropCircleTransfermation()).error(R.drawable.default_head).into(imgHead);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {

    }

//    @OnClick({R.id.img_back, R.id.btn_confirm_change})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.img_back:
//                finish();
//                break;
//            case R.id.btn_confirm_change:
////                Intent intent=getIntent();
////                user.setReal_money(Double.parseDouble(etPayWages.getText().toString().trim()));
////                user.setNote(etNote.getText().toString().trim());
////                intent.putExtra("user",user);
////                intent.putExtra("position",position);
////                setResult(RESULT_OK,intent);
////                finish();
//                break;
//        }
//    }
}
