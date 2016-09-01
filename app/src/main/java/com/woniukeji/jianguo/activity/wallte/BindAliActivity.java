package com.woniukeji.jianguo.activity.wallte;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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

public class BindAliActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_confirm) TextView tvConfirm;
    @BindView(R.id.et_alipay_name) EditText etAlipayName;
    @BindView(R.id.ll_change) LinearLayout llChange;
    @BindView(R.id.et_alipay_account) EditText etAlipayAccount;
    @BindView(R.id.btn_change_pay_pass) Button btnChangePayPass;
    @BindView(R.id.ll_changed) LinearLayout llChanged;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private Handler mHandler = new Myhandler(this);
    private Context context = BindAliActivity.this;
    private int merchartid;
    private String password;
    private String newPassWord;
    private int loginid;


    @OnClick({R.id.img_back, R.id.btn_change_pay_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_change_pay_pass:
                if (etAlipayAccount.getText().toString()==null||etAlipayAccount.getText().toString().equals("")){
                    showShortToast("请输支付宝账户");
                    return;
                }else if(etAlipayName.getText().toString()==null||etAlipayName.getText().toString().equals("")){
                    showShortToast("请输入真实姓名");
                    return;
                }

                postTask postTask=new postTask(String.valueOf(loginid),etAlipayName.getText().toString(),etAlipayAccount.getText().toString()
                ,"0","0");
                postTask.execute();
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
            BindAliActivity activity = (BindAliActivity) reference.get();
            switch (msg.what) {
                case 0:
//                    SPUtils.setParam(activity.context, Constants.USER_INFO,Constants.USER_PAY_PASS,activity.newPassWord);
                    String Message = (String) msg.obj;
                    Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();
                    Intent intent=activity.getIntent();
                    intent.putExtra("TYPE","0");
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
        setContentView(R.layout.activity_bind_alipay);
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
        loginid = (int) SPUtils.getParam(BindAliActivity.this, Constants.LOGIN_INFO, Constants.SP_USERID, 0);
//        password = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_PAY_PASS,"");

    }

    @Override
    public void addActivity() {

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
        SweetAlertDialog pDialog = new SweetAlertDialog(BindAliActivity.this, SweetAlertDialog.PROGRESS_TYPE);

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
                    .addParams("type", "0")
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
