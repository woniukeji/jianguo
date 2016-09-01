package com.woniukeji.jianguo.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseFragment;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.CodeCallback;
import com.woniukeji.jianguo.entity.SmsCode;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.eventbus.QuickLoginEvent;
import com.woniukeji.jianguo.activity.main.MainActivity;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.MD5Util;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A Register screen that offers Register via email/password.
 */
public class PasswordLoginFragment extends BaseFragment {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    @BindView(R.id.phoneNumber) EditText phoneNumber;
    @BindView(R.id.btn_get_code) Button btnGetCode;
    @BindView(R.id.icon_pass) ImageView iconPass;
    @BindView(R.id.phone_code) EditText phoneCode;
    @BindView(R.id.cb_rule) CheckBox cbRule;
    @BindView(R.id.tv_rule) TextView tvRule;
    @BindView(R.id.user_rule) LinearLayout userRule;
    @BindView(R.id.sign_in_button) Button signInButton;
    @BindView(R.id.email_login_form) LinearLayout emailLoginForm;
    @BindView(R.id.login_form) LinearLayout loginForm;
    @BindView(R.id.tv_forget_pass) TextView tvForgetPass;


    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(getActivity());
    private Context context = getActivity();
    private TimeCount time;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (time!=null){
            time.cancel();
        }

    }


    private class Myhandler extends Handler {
        private WeakReference<Context> reference;
        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    saveToSP(user.getData());
                    break;
                case 1:
                    String ErrorMessage = (String) msg.obj;
                    TastyToast.makeText(getActivity().getApplicationContext(), ErrorMessage, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    break;
                case 2:
                    TastyToast.makeText(getActivity().getApplicationContext(),  "验证码已经发送，请注意查收", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

//                    quickLoginActivity.smsCode = (SmsCode) msg.obj;
//                    Toast.makeText(getActivity(), "验证码已经发送，请注意查收", Toast.LENGTH_SHORT).show();
//                    if (quickLoginActivity.smsCode.getIs_tel().equals("1")){
//                        quickLoginActivity.showShortToast("验证码已经发送，请注意查收");
//                    }else{
//                        quickLoginActivity.showShortToast("该手机号码已经注册，不能重复注册");
//                    }

                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(getActivity(), sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login_password, container, false);
        ButterKnife.bind(this, view);
        createLink(tvRule);
        return view;

    }
    @Override
    public int getContentViewId() {
        return R.layout.activity_login_password;
    }
    /**
     * 发送消息刷新对话列表界面
     *
     */
    private void sendEvent() {
        LCIMIMTypeMessageEvent event = new LCIMIMTypeMessageEvent();
        EventBus.getDefault().post(event);
    }
    /**
     * 创建一个超链接
     */
    private void createLink(TextView tv) {
        // 创建一个 SpannableString对象
        SpannableString sp = new SpannableString("我已阅读并同意《兼果用户协议》");
        // 设置超链接
        sp.setSpan(new URLSpan("http://101.200.205.243:8080/user_agreement.jsp"), 7, 15,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_bg)), 7, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
        tv.setTextSize(12);
        //设置TextView可点击
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void saveToSP(User user) {
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_WQTOKEN, user.getT_user_login().getQqwx_token() != null ? user.getT_user_login().getQqwx_token() : "");
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TEL, user.getT_user_login().getTel() != null ? user.getT_user_login().getTel() : "");
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getT_user_login().getPassword() != null ? user.getT_user_login().getPassword() : "");
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_USERID, user.getT_user_login().getId());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_STATUS, user.getT_user_login().getStatus());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_QNTOKEN, user.getT_user_login().getQiniu());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.LOGIN_APK_URL, user.getApk_url());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.LOGIN_VERSION, user.getVersion());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.LOGIN_CONTENT, user.getContent());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.LOGIN_HOBBY, user.getT_user_login().getHobby());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
        SPUtils.setParam(getActivity(), Constants.USER_INFO, Constants.SP_NICK, user.getT_user_info().getNickname() != null ? user.getT_user_info().getNickname() : "");
        SPUtils.setParam(getActivity(), Constants.USER_INFO, Constants.SP_NAME, user.getT_user_info().getName() != null ? user.getT_user_info().getName() : "");
        SPUtils.setParam(getActivity(), Constants.USER_INFO, Constants.SP_IMG, user.getT_user_info().getName_image() != null ? user.getT_user_info().getName_image() : "");
        SPUtils.setParam(getActivity(), Constants.USER_INFO, Constants.SP_SCHOOL, user.getT_user_info().getSchool() != null ? user.getT_user_info().getSchool() : "");
        SPUtils.setParam(getActivity(), Constants.USER_INFO, Constants.SP_CREDIT, user.getT_user_info().getCredit());
        SPUtils.setParam(getActivity(), Constants.USER_INFO, Constants.SP_INTEGRAL, user.getT_user_info().getIntegral());
        SPUtils.setParam(getActivity(), Constants.LOGIN_INFO, Constants.SP_RESUMM, user.getT_user_login().getResume());
        SPUtils.setParam(getActivity(), Constants.USER_INFO, Constants.USER_SEX, user.getT_user_info().getUser_sex());


// 暂时关闭果聊功能
//        final ChatManager chatManager = ChatManager.getInstance();
        if (!TextUtils.isEmpty(String.valueOf(user.getT_user_login().getId()))) {
            if (JPushInterface.isPushStopped(getActivity().getApplicationContext())) {
                JPushInterface.resumePush(getActivity().getApplicationContext());
            }
            //登陆leancloud服务器 给极光设置别名
            LCChatKit.getInstance().open(String.valueOf(user.getT_user_login().getId()), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null == e) {
                        QuickLoginEvent quickLoginEvent = new QuickLoginEvent();
                        quickLoginEvent.isQuickLogin = true;
                        EventBus.getDefault().post(quickLoginEvent);
                        sendEvent();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("login",true);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "聊天服务启动失败，稍后请重新登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("login",true);
                        startActivity(intent);
                        getActivity().finish();

                    }
                }
            });
            JPushInterface.setAlias(getActivity().getApplicationContext(), "jianguo" + user.getT_user_login().getId(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {

                    LogUtils.e("jpush", s + ",code=" + i);
                }
            });
        }
//        ChatManager.getInstance().openClient(new AVIMClientCallback() {
//            @Override
//            public void done(AVIMClient avimClient, AVIMException e) {
//                if (null == e) {
//                    TalkMessageEvent talkMessageEvent=new TalkMessageEvent();
//                    talkMessageEvent.isLogin=true;
//                    EventBus.getDefault().post(talkMessageEvent);
//                } else {
////                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
////                    getActivity().showShortToast(e.toString());
//                }
//            }
//        });

    }


    @OnClick({R.id.sign_in_button, R.id.tv_forget_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                String tel = phoneNumber.getText().toString().trim();
                String sms = phoneCode.getText().toString().trim();
                if (CheckStatus()) {
                    PhoneLogin(tel, MD5Util.MD5(sms));
                }
                break;
            case R.id.tv_forget_pass:
                startActivity(new Intent(getActivity(),ForgetPassActivity.class));
                break;
        }
    }

    private boolean CheckStatus() {
        if (phoneNumber.getText().toString().trim().length() != 11) {
            Toast.makeText(getActivity(), "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneCode.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!cbRule.isChecked()) {
            Toast.makeText(getActivity(), "请阅读并确认《兼果用户协议》", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnGetCode.setClickable(false);
            btnGetCode.setBackgroundColor(Color.GRAY);
            btnGetCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btnGetCode.setText("验证码");
            btnGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_login));
            btnGetCode.setClickable(true);
        }
    }

    /**
     * phoneLogin
     *
     * @param tel
     * @param pass
     */
    public void PhoneLogin(String tel, String pass) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.LOGIN_PHONE)
                .addParams("only", only)
                .addParams("tel", tel)
                .addParams("password", pass)
                .build()
                .connTimeOut(6000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new Callback<BaseBean<User>>() {
                    @Override
                    public BaseBean parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        BaseBean user = new Gson().fromJson(string, new TypeToken<BaseBean<User>>() {
                        }.getType());
                        return user;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        message.what = MSG_USER_FAIL;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(BaseBean user, int id) {
                        if (user.getCode().equals("200")) {
                            Message message = new Message();
                            message.obj = user;
                            message.what = MSG_USER_SUCCESS;
                            mHandler.sendMessage(message);
                        } else {
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
     *
     * @param tel
     */
    public void CheckPhone(String tel) {
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        OkHttpUtils
                .get()
                .url(Constants.GET_SMS)
                .addParams("tel", tel)
                .addParams("only", only)
                .build()
                .connTimeOut(6000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new CodeCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message message = new Message();
                        message.obj = e.toString();
                        message.what = MSG_USER_FAIL;
                        mHandler.sendMessage(message);
                    }
                    @Override
                    public void onResponse(SmsCode response, int id) {
                        Message message = new Message();
                        message.obj = response;
                        message.what = MSG_PHONE_SUCCESS;
                        mHandler.sendMessage(message);
                    }
                });
    }

}

