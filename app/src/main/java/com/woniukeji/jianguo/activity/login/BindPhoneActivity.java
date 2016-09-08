package com.woniukeji.jianguo.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;
import com.woniukeji.jianguo.activity.mine.AuthActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CodeCallback;
import com.woniukeji.jianguo.entity.SmsCode;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A Register screen that offers Register via email/password.
 */
public class BindPhoneActivity extends BaseActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    @BindView(R.id.phoneNumber) EditText phoneNumber;
    @BindView(R.id.phone_code) EditText phoneCode;
    @BindView(R.id.sign_in_button) Button signInButton;
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView title;
    @BindView(R.id.btn_get_code) Button btnGetCode;
    @BindView(R.id.icon_pass) ImageView iconPass;
    SmsCode smsCode;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler=new Myhandler(this);
    private Context context= BindPhoneActivity.this;
    private int loginId;
    private String phone;

    private static class Myhandler extends Handler{
        private WeakReference<Context> reference;
        public Myhandler(Context context){
            reference=new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BindPhoneActivity quickLoginActivity= (BindPhoneActivity) reference.get();
            switch (msg.what) {
                case 0:
                    quickLoginActivity.showShortToast("手机号验证成功！");
                    Intent intent=new Intent(quickLoginActivity,AuthActivity.class);
                    SPUtils.setParam(quickLoginActivity,Constants.LOGIN_INFO,Constants.SP_TEL,quickLoginActivity.phone);
                    quickLoginActivity.startActivity(intent);
                    quickLoginActivity.finish();
                    break;
                case 1:
                    String ErrorMessage= (String) msg.obj;
                    Toast.makeText(quickLoginActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    quickLoginActivity.smsCode= (SmsCode) msg.obj;
                    if (quickLoginActivity.smsCode.getIs_tel().equals("0")){
                        quickLoginActivity.showShortToast("验证码已经发送，请注意查收");
                    }else{
                        quickLoginActivity.showShortToast("该手机号码已经注册，不能重复注册");
                    }

                    break;
                case 3:
                    String sms= (String) msg.obj;
                    Toast.makeText(quickLoginActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login_quick);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        title.setText("手机验证");
        signInButton.setText("提交");

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        loginId = (int) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, 0);

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(BindPhoneActivity.this);
    }


    private void saveToSP(User user) {
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_WQTOKEN,user.getT_user_login().getQqwx_token()!=null?user.getT_user_login().getQqwx_token():"");
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_TEL,user.getT_user_login().getTel()!=null?user.getT_user_login().getTel():"");
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_PASSWORD,user.getT_user_login().getPassword()!=null?user.getT_user_login().getPassword():"");
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_USERID,user.getT_user_login().getId());
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_STATUS,user.getT_user_login().getStatus());
        SPUtils.setParam(context,Constants.LOGIN_INFO,Constants.SP_QNTOKEN,user.getT_user_login().getQiniu());

        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_NICK,user.getT_user_info().getNickname()!=null?user.getT_user_info().getNickname():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_NAME,user.getT_user_info().getName()!=null?user.getT_user_info().getName():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_IMG,user.getT_user_info().getName_image()!=null?user.getT_user_info().getName_image():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_SCHOOL,user.getT_user_info().getSchool()!=null?user.getT_user_info().getSchool():"");
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_CREDIT,user.getT_user_info().getCredit());
        SPUtils.setParam(context,Constants.USER_INFO,Constants.SP_INTEGRAL,user.getT_user_info().getIntegral());
    }


    @OnClick({R.id.sign_in_button, R.id.img_back, R.id.btn_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                 phone = phoneNumber.getText().toString().trim();
                String code = phoneCode.getText().toString().trim();
                if (CheckStatus()) {
                    BindPhone(phone);
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_get_code:
                String tel=phoneNumber.getText().toString();
                boolean isOK=tel.length()==11;
                if (isOK){
                    CheckPhone(tel);
                }else{
                    showLongToast("请输入正确的手机号");
                }

                break;
        }
    }

    private boolean CheckStatus() {
        if (phoneNumber.getText().toString().length()!=11) {
            showShortToast("手机号码格式不正确");
            return false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            showShortToast("手机号不能为空");
            return false;
        } else if (phoneCode.getText().toString().equals("")) {
            showShortToast("验证码不能为空");
            return false;
        } else if (smsCode == null) {
            showShortToast("验证码不正确");
            return false;
        } else if (smsCode.getText().equals("")
                || !phoneCode.getText().toString().trim().equals(smsCode.getText())) {
            showShortToast("验证码不正确");
            return false;
        }
        return true;
    }

        /**
         * phoneLogin
         * @param phone
         */
        public void BindPhone(String phone) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.POST_BIND_PHONE)
                    .addParams("only", only)
                    .addParams("login_id", String.valueOf(loginId))
                    .addParams("tel", phone)
                    .build()
                    .connTimeOut(6000)
                    .readTimeOut(2000)
                    .writeTimeOut(2000)
                    .execute(new Callback<BaseBean>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean user = new Gson().fromJson( string, new TypeToken<BaseBean>(){}.getType());
                            return user;
                        }
                        @Override
                        public void onError(Call call, Exception e,int id) {
                            Message message = new Message();
                            message.obj = e.toString();
                            message.what = MSG_USER_FAIL;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(BaseBean user,int id) {
                            if (user.getCode().equals("200")){
                                SPUtils.setParam(BindPhoneActivity.this,Constants.LOGIN_INFO,Constants.SP_TYPE,"0");
                                Message message = new Message();
                                message.obj = user;
                                message.what = MSG_USER_SUCCESS;
                                mHandler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.obj = user.getMessage();
                                message.what = MSG_USER_FAIL;
                                mHandler.sendMessage(message);
                            }
                        }

                    });
        }




        /**
         * login
         * 检查手机号是否存在
         * @param tel
         */
        public void CheckPhone(String tel) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.CHECK_PHONE)
                    .addParams("tel", tel)
                    .addParams("only", only)
                    .build()
                    .connTimeOut(6000)
                    .readTimeOut(2000)
                    .writeTimeOut(2000)
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

