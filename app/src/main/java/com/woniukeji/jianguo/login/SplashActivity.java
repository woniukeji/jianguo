package com.woniukeji.jianguo.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.base.BaseActivity;
import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.BaseBean;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.leanmessage.ChatManager;
import com.woniukeji.jianguo.main.MainActivity;
import com.woniukeji.jianguo.utils.ActivityManager;
import com.woniukeji.jianguo.utils.DateUtils;
import com.woniukeji.jianguo.utils.LogUtils;
import com.woniukeji.jianguo.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.ShareSDK;
import okhttp3.Call;
import okhttp3.Response;

public class SplashActivity extends BaseActivity implements AMapLocationListener {

    @InjectView(R.id.img_splash) ImageView imgSplash;
    private int MSG_USER_SUCCESS = 0;
    private int MSG_USER_FAIL = 1;
    private int MSG_PHONE_SUCCESS = 2;
    private int MSG_REGISTER_SUCCESS = 3;
    private Handler mHandler = new Myhandler(this);
    private Context context = SplashActivity.this;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String mCityId ="0";
    private String mCityName ="";


    private static class Myhandler extends Handler {
        private WeakReference<Context> reference;

        public Myhandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity splashActivity = (SplashActivity) reference.get();
            switch (msg.what) {
                case 0:
                    BaseBean<User> user = (BaseBean<User>) msg.obj;
                    splashActivity.saveToSP(user.getData());
                    Intent intent = new Intent(splashActivity, MainActivity.class);
                    splashActivity.startActivity(intent);
                    splashActivity.finish();
                    break;
                case 1:
                    //如果本地的登录信息登录失败 则删除本地缓存的用户信息，并跳转到首页
                    splashActivity.startActivity(new Intent(splashActivity, MainActivity.class));
                    SPUtils.deleteParams(splashActivity);
                    String ErrorMessage = (String) msg.obj;
                    Toast.makeText(splashActivity, ErrorMessage, Toast.LENGTH_SHORT).show();
                    splashActivity.finish();
                    break;
                case 2:
                    break;
                case 3:
                    String sms = (String) msg.obj;
                    Toast.makeText(splashActivity, sms, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setContentView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
    }

    @Override
    public void initViews() {
        //初始化SDK
        ShareSDK.initSDK(this);

//        Picasso.with(context).load(R.mipmap.splash).into(imgSplash);
    }

    @Override
    public void initListeners() {
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        locationClient.setLocationListener(this);
        //初始化定位参数
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
//设置是否只定位一次,默认为false
        locationOption.setOnceLocation(true);

        if(locationOption.isOnceLocationLatest()){
            locationOption.setOnceLocationLatest(true);
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
//如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        }

//设置是否强制刷新WIFI，默认为强制刷新
        locationOption.setWifiActiveScan(true);
//设置是否允许模拟位置,默认为false，不允许模拟位置
        locationOption.setMockEnable(false);
//设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(2000);
//给定位客户端对象设置定位参数
        locationClient.setLocationOption(locationOption);
//启动定位
        locationClient.startLocation();
//        AMapLocationListener mAMapLocationListener = new AMapLocationListener(){
//            @Override
//            public void onLocationChanged(AMapLocation amapLocation) {
//                if (amapLocation != null) {
//                    if (amapLocation.getErrorCode() == 0) {
//                        //定位成功回调信息，设置相关消息
//                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        amapLocation.getLatitude();//获取纬度
//                        amapLocation.getLongitude();//获取经度
//                        amapLocation.getAccuracy();//获取精度信息
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(amapLocation.getTime());
//                        df.format(date);//定位时间
//                        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                        amapLocation.getCountry();//国家信息
//                        amapLocation.getProvince();//省信息
//                        amapLocation.getCity();//城市信息
//                        amapLocation.getDistrict();//城区信息
//                        amapLocation.getStreet();//街道信息
//                        amapLocation.getStreetNum();//街道门牌号信息
//                        amapLocation.getCityCode();//城市编码
//                        amapLocation.getAdCode();//地区编码
//                        amapLocation.getAoiName();//获取当前定位点的AOI信息
//                        } else {
//                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                        Log.e("AmapError","location Error, ErrCode:"
//                                + amapLocation.getErrorCode() + ", errInfo:"
//                        + amapLocation.getErrorInfo());
//                        }
//                    }
//                }
//        };
        // 设置定位模式为高精度模式
//        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        // 设置定位监听
//        locationClient.setLocationListener(this);
//        // 设置定位参数
//        locationClient.setLocationOption(locationOption);
//        // 启动定位
//        locationClient.startLocation();
    }

    @Override
    public void initData() {

    }

    @Override
    public void addActivity() {
        ActivityManager.getActivityManager().addActivity(SplashActivity.this);
    }

    /**
    *获取到位置信息
    *@author invinjun
    *created at 2016/7/1 15:31
    */
    //以下为后者的举例：

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                if (mCityName ==null|| mCityName.equals("")){
                    mCityName =aMapLocation.getProvince();//省信息
                }
                SPUtils.setParam(context, Constants.USER_INFO, Constants.USER_LOCATION_CODE, aMapLocation.getCityCode());
                SPUtils.setParam(context, Constants.USER_INFO, Constants.USER_LOCATION_NAME, aMapLocation.getCity().substring(0,aMapLocation.getCity().length()-1));
            } else {
                SPUtils.setParam(context, Constants.USER_INFO, Constants.USER_LOCATION_CODE, "010");
                SPUtils.setParam(context, Constants.USER_INFO, Constants.USER_LOCATION_NAME, "北京");
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
    private void saveToSP(User user) {
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_WQTOKEN, user.getT_user_login().getQqwx_token() != null ? user.getT_user_login().getQqwx_token() : "");
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_TEL, user.getT_user_login().getTel() != null ? user.getT_user_login().getTel() : "");
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_PASSWORD, user.getT_user_login().getPassword() != null ? user.getT_user_login().getPassword() : "");
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_USERID, user.getT_user_login().getId());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_STATUS, user.getT_user_login().getStatus());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_QNTOKEN, user.getT_user_login().getQiniu());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_RESUMM, user.getT_user_login().getResume());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.LOGIN_APK_URL, user.getApk_url());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.LOGIN_VERSION, user.getVersion());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.LOGIN_CONTENT, user.getContent());
        SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.LOGIN_HOBBY, user.getT_user_login().getHobby());
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_NICK, user.getT_user_info().getNickname() != null ? user.getT_user_info().getNickname() : "");
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_NAME, user.getT_user_info().getName() != null ? user.getT_user_info().getName() : "");
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_IMG, user.getT_user_info().getName_image() != null ? user.getT_user_info().getName_image() : "");
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_SCHOOL, user.getT_user_info().getSchool() != null ? user.getT_user_info().getSchool() : "");
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_CREDIT, user.getT_user_info().getCredit());
        SPUtils.setParam(context, Constants.USER_INFO, Constants.SP_INTEGRAL, user.getT_user_info().getIntegral());
        SPUtils.setParam(context, Constants.USER_INFO, Constants.USER_SEX, user.getT_user_info().getUser_sex());
        LogUtils.e("jpush","userid"+user.getT_user_login().getId());
        //暂时关闭果聊功能
        final ChatManager chatManager = ChatManager.getInstance();
        if (!TextUtils.isEmpty(String.valueOf(user.getT_user_login().getId()))) {
            //登陆leancloud服务器 给极光设置别名
            chatManager.setupManagerWithUserId(this, String.valueOf(user.getT_user_login().getId()));
            LogUtils.e("jpush","调用jpush");
            if (JPushInterface.isPushStopped(getApplicationContext())){
                JPushInterface.resumePush(getApplicationContext());
            }
            JPushInterface.setAlias(getApplicationContext(),"jianguo"+user.getT_user_login().getId(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    LogUtils.e("jpush",s+",code="+i);
                }
            });
        }
        ChatManager.getInstance().openClient(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                } else {
                    showShortToast(e.toString());
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        // 在当前的界面变为用户可见的时候调用的方法
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
             chooseActivity();
            }

            ;
        }.start();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * chooseActivity
     * 根据保存的登陆信息 跳转不同界面
     */
    private void chooseActivity() {
        String loginType = (String) SPUtils.getParam(context, Constants.LOGIN_INFO, Constants.SP_TYPE, "2");
        if (mCityId.equals("0")||mCityName.equals("")){
            //如果定位失败，则获取上次登陆保存在sp的地理位置信息
            mCityId = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_LOCATION_CODE, "0");
            mCityName = (String) SPUtils.getParam(context, Constants.USER_INFO, Constants.USER_LOCATION_NAME, "");
        }

        if (loginType.equals("2")){
            startActivity(new Intent(context, LeadActivity.class));
            finish();
        }else if(loginType.equals("1")){
            startActivity(new Intent(context, MainActivity.class));
            finish();

        }else {
            String phone= (String) SPUtils.getParam(context,Constants.LOGIN_INFO,Constants.SP_TEL,"");
            String pass= (String) SPUtils.getParam(context,Constants.LOGIN_INFO,Constants.SP_PASSWORD,"");
            PhoneLogin(phone, pass,mCityId,mCityName);
        }
    }

    @Override
    public void onClick(View view) {

    }


        /**
         * login
         * 授权过的weixin qq 用户直接通过token登陆
         */
        public void QWLogin(String token) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.LOGIN_WQ)
                    .addParams("token", token)
                    .addParams("only", only)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<User>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean user = new Gson().fromJson(string, new TypeToken<BaseBean<User>>() {
                            }.getType());
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
                            if (user.getCode().equals("200")) {
                                SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_TYPE, "1");
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
         * phoneLogin
         * @param phone
         * @param pass
         */
        public void PhoneLogin( String phone, String pass, String cityid,String cityName) {
            String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
            OkHttpUtils
                    .get()
                    .url(Constants.LOGIN_PHONE)
                    .addParams("only", only)
                    .addParams("tel", phone)
                    .addParams("password", pass)
                    .addParams("city_id", cityid)
                    .addParams("city_name", cityName)
                    .build()
                    .connTimeOut(60000)
                    .readTimeOut(20000)
                    .writeTimeOut(20000)
                    .execute(new Callback<BaseBean<User>>() {
                        @Override
                        public BaseBean parseNetworkResponse(Response response,int id) throws Exception {
                            String string = response.body().string();
                            BaseBean user = new Gson().fromJson(string, new TypeToken<BaseBean<User>>() {
                            }.getType());
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
                            if (user.getCode().equals("200")) {
                                SPUtils.setParam(context, Constants.LOGIN_INFO, Constants.SP_TYPE, "0");
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
}
