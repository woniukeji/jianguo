package com.woniukeji.jianguo.activity.wallte;

import android.content.Context;
import android.content.Intent;
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

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.BaseCallback;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;

public class BindYinlianActivity extends BaseActivity {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.et_yinlian_name) EditText etYinlianName;
    @BindView(R.id.et_bank_name) EditText etBankName;
    @BindView(R.id.ll_bank) LinearLayout llBank;
    @BindView(R.id.et_bank_number) EditText etBankNumber;
    @BindView(R.id.btn_save_yinlian) Button btnSaveYinlian;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private Handler mHandler = new Myhandler(this);
    private Context context = BindYinlianActivity.this;
    private int merchartid;
    private String password;
    private String newPassWord;
    private int loginid;

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
            BindYinlianActivity activity = (BindYinlianActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    SPUtils.setParam(activity.context, Constants.USER_INFO,Constants.USER_PAY_PASS,activity.newPassWord);
                    String Message = (String) msg.obj;
                    Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();
                    Intent intent=activity.getIntent();
                    intent.putExtra("TYPE","1");
                    activity.setResult(RESULT_OK,intent);
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
        setContentView(R.layout.activity_bind_yinlian);
        ButterKnife.bind(this);
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
          loginid = (int) SPUtils.getParam(BindYinlianActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
//        password = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_PAY_PASS,"");

    }

    @Override
    public void addActivity() {

    }


    @OnClick({R.id.img_back, R.id.btn_save_yinlian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.btn_save_yinlian:
                if (etYinlianName.getText().toString()==null||etYinlianName.getText().toString().equals("")){
                    showShortToast("请输入真实姓名");
                    return;
                }else if(etBankName.getText().toString()==null||etBankName.getText().toString().equals("")){
                    showShortToast("请输入开户银行");
                    return;
                }else if(etBankNumber.getText().toString()==null||etBankNumber.getText().toString().equals("")){
                    showShortToast("请输入银行卡号");
                    return;
                }
                postTask postTask=new postTask(String.valueOf(loginid),etYinlianName.getText().toString(),"0"
                        ,etBankName.getText().toString(),etBankNumber.getText().toString());
                postTask.execute();
                break;
        }
    }

    /**
     * 保存阿里信息Task
     */
    public class postTask extends AsyncTask<Void, Void, Void> {

        private final String id;
        private final String name;
        private final String zhifubao;
        private final String yinlian;
        private final String kahao;
        SweetAlertDialog pDialog = new SweetAlertDialog(BindYinlianActivity.this, SweetAlertDialog.PROGRESS_TYPE);

        postTask(String id, String name,String zhifubao,String yinlian,String kahao) {
            this.id = id;
            this.name = name;
            this.zhifubao = zhifubao;
            this.yinlian = yinlian;
            this.kahao = kahao;
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
                    .url(Constants.POST_ALIPAY_INFO)
                    .addParams("only", only)
                    .addParams("type", "1")
                    .addParams("login_id", id)
                    .addParams("name", name)
                    .addParams("zhifubao", zhifubao)
                    .addParams("yinhang", yinlian)
                    .addParams("kahao", kahao)
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
}
