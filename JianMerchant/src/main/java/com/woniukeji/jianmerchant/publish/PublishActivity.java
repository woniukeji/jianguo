package com.woniukeji.jianmerchant.publish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import com.woniukeji.jianmerchant.base.Constants;
import com.woniukeji.jianmerchant.base.MainActivity;
import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.BaseCallback;
import com.woniukeji.jianmerchant.entity.CodeCallback;
import com.woniukeji.jianmerchant.entity.SmsCode;
import com.woniukeji.jianmerchant.entity.User;
import com.woniukeji.jianmerchant.utils.ActivityManager;
import com.woniukeji.jianmerchant.utils.CommonUtils;
import com.woniukeji.jianmerchant.utils.DateUtils;
import com.woniukeji.jianmerchant.utils.MD5Util;
import com.woniukeji.jianmerchant.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;

/**
 * A login screen that offers login via email/password.
 */
public class PublishActivity extends BaseActivity {

    @InjectView(R.id.img_back) ImageView imgBack;
    @InjectView(R.id.tv_title) TextView tvTitle;
    @InjectView(R.id.img_share) ImageView imgShare;
    @InjectView(R.id.btn_history) Button btnHistory;
    @InjectView(R.id.btn_modle) Button btnModle;
    @InjectView(R.id.btn_new) Button btnNew;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = PublishActivity.this;


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PublishActivity registActivity = (PublishActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    registActivity.saveToSP(user.getData());
                    Intent intent = new Intent(registActivity, MainActivity.class);
//                    intent.putExtra("user",user);
                    registActivity.startActivity(intent);
                    registActivity.finish();
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(registActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(registActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_publish);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        tvTitle.setText("发布兼职");

    }

    @Override
    public void initListeners() {

    }
    @OnClick({R.id.img_back, R.id.btn_history, R.id.btn_modle, R.id.btn_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_history:
                Intent intent=new Intent(PublishActivity.this,HistoryJobActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.btn_modle:
                Intent intent1=new Intent(PublishActivity.this,HistoryJobActivity.class);
                intent1.putExtra("type","1");
                startActivity(intent1);
                break;
            case R.id.btn_new:
                Intent intent2=new Intent(PublishActivity.this,PublishDetailActivity.class);
                intent2.putExtra("type","new");
                startActivity(intent2);
                break;
        }
    }
    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(PublishActivity.this);
    }

    private void saveToSP(User user) {
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getT_user_login().getPassword());
    }



    /**
     * 手机验证码Task
     */
    public class GetSMS extends AsyncTask<Void, Void, User> {

        private final String tel;
        SweetAlertDialog pDialog = new SweetAlertDialog(PublishActivity.this, SweetAlertDialog.PROGRESS_TYPE);

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
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("登陆中...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(final User user) {
            pDialog.dismiss();
        }

        /**
         * login
         * 检查手机号是否存在
         */
        public void CheckPhone() {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.REC_SMS)
                    .addParams("tel", tel)
                    .addParams("only", only)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new CodeCallback() {

                        @Override
                        public void onError(Call call, Exception e) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(SmsCode response) {
                            Message message = new Message();
                            message.obj = response;
                            message.what = MSG_PHONE_SUCCESS;
                            mHandler.sendMessage(message);
                        }


                    });
        }


    }

    /**
     * 手机注册Task
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, User> {

        private final String tel;
        private final String passWord;
        SweetAlertDialog pDialog = new SweetAlertDialog(PublishActivity.this, SweetAlertDialog.PROGRESS_TYPE);

        UserRegisterTask(String phoneNum, String passWord) {
            this.tel = phoneNum;
            this.passWord = passWord;
        }

        protected User doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            UserRegisterPhone();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("登陆中...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(final User user) {
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
                    .url(Constants.CHANGE_PASSWORD)
                    .addParams("only", only)
                    .addParams("tel", tel)
                    .addParams("password", passWord)
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
                                message.obj = response;
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
