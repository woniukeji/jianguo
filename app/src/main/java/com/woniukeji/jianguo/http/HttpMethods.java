package com.woniukeji.jianguo.http;


import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.Balance;
import com.woniukeji.jianguo.entity.CityBannerEntity;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.entity.HttpResult;
import com.woniukeji.jianguo.entity.JobInfo;
import com.woniukeji.jianguo.entity.Jobs;
import com.woniukeji.jianguo.entity.ListTJobEntity;
import com.woniukeji.jianguo.entity.PushMessage;
import com.woniukeji.jianguo.entity.RxCityCategory;
import com.woniukeji.jianguo.entity.RxJobDetails;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.utils.DateUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.leancloud.chatkit.LCChatKitUser;
import dalvik.bytecode.OpcodeInfo;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by invinjun on 2016/6/1.
 */

public class HttpMethods {

    public static final String BASE_URL = Constants.JIANGUO_USING;
        private static final int DEFAULT_TIMEOUT = 10;
        private Retrofit retrofit;
        private MethodInterface methodInterface;


        //构造方法私有
        private HttpMethods() {
            //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        methodInterface = retrofit.create(MethodInterface.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCode() != 200) {
                throw new ApiException(httpResult.getMessage());
            }
            return httpResult.getData();
        }
    }
    /**
     * 用户注册
     * @param subscriber 由调用者传过来的观察者对象
     * @param phone 手机号
     * @param password 密码
     * @param sms 验证码
     */

    public void  toRegister(Subscriber<User> subscriber, String phone, String password, String sms){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.Register(only, phone,password,sms)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void getSms(Subscriber<String> subscriber ,String phone){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getSMS(only,phone)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

/**
     *更换手机号
     */
    public void postChangeTel(Subscriber<String> subscriber,String loginId,String tel,String sms){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.changeTel(only,loginId,tel,sms)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     *获取兼职详情（工作详情界面）
     */
    public void getJobDetail(Subscriber<RxJobDetails> subscriber, String loginId, String jobId){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getJobDetail(only,loginId,jobId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     *获取兼职详情（工作详情界面new）
     */
    public void getJobDetailNew(Subscriber<JobInfo> subscriber, String loginId, String jobId){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getJobDetailNew(only,loginId,jobId)
                .map(new HttpResultFunc<JobInfo>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     *获取兼职详情（工作详情界面）
     */
    public void getCityCategory(Subscriber<CityCategory> subscriber){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getCityCategory(only)
                .map(new HttpResultFunc<CityCategory>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
    *绑定微信账户
    *@param
    *@author invinjun
    *created at 2016/7/22 11:53
    */
  public void bindWX(Subscriber<String> subscriber,String loginid,String openid,String nickname,String sex,String province,String city,String imgurl,String unionid ){
      String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
      methodInterface.postWX(only,loginid, openid,nickname,sex,province,city,imgurl,unionid)
              .map(new HttpResultFunc())
              .subscribeOn(Schedulers.io())
              .unsubscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(subscriber);
  }
    /**
    *钱包数据
    *@param
    *@param
    *@author invinjun
    *created at 2016/7/26 16:46
    */
    public void getWallte(Subscriber<Balance> subscriber,String loginid){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getWallet(only,loginid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

/**
*报名接口
*@param
*@param
*@author invinjun
*created at 2016/7/26 16:46
*/
    public void MpostSign(Subscriber<String> subscriber,String loginid,String jobid){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.postSign(only,loginid,jobid)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
    *推送信息获取
    *@param
    *@param
    *@author invinjun
    *created at 2016/7/26 16:46
    */
    public void getPush(Subscriber<PushMessage> subscriber, String loginid){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getPush(only,loginid)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
    *提交实名信息
    */
    public void postReal(Subscriber<String> subscriber, String loginid,String front,String behind,String name,String id,String sex){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.postRealName(only,loginid,front,behind,name,id,sex)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
    *提交用户资料
    */
    public void postResum(Subscriber<String> subscriber, String loginid,String name,String nickname,String school,String height,
                          String student,String name_image,String intoschool_date,String birth_date,String shoe_size,String clothing_size,String sex){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.postResume(only,loginid,name,nickname,school,height,student,name_image,intoschool_date,birth_date,shoe_size,clothing_size,sex)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
    *获取果聊用户资料
    */
    public void getTalkUser(Subscriber<List<LCChatKitUser>> lcChatKitUserSubscriber, String loginid){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getTalkUser(only,loginid)
                .map(new HttpResultFunc<List<LCChatKitUser>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lcChatKitUserSubscriber);
    }
    /**
     *提交收藏兼职请求
     */
    public void postAttention(Subscriber<String> subscriber, String loginid,String follow,String collection){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.postAttention(only,loginid,follow,collection)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     *提交提现申请
     */
    public void postMoney(Subscriber<String> subscriber, String loginid,String sms,String type,String money){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.postMoney(only,loginid,sms,type,money)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
    *手机号存在发送验证码（提现、忘记密码，修改密码）
    */
    public void checkSms(Subscriber<String> subscriber,String tel){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.checkSms(only,tel)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     *获取城市和banner
     */
    public void getCityBanner(Subscriber<CityBannerEntity> subscriber){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getCityBanner(only)
                .map(new HttpResultFunc<CityBannerEntity>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
/**
*获取热门兼职列表
*/    
    public void getHotJobs(Subscriber<Jobs> subscriber,String cityid,String count){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        String hot="1";
        methodInterface.getHotJobs(only,hot,cityid,count)
                .map(new HttpResultFunc<Jobs>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
/**
*浏览兼职记录
*@param subscriber
*@param loginid
 *@param jobid
*@author invinjun
*created at 2016/8/30 10:25
*/
    public void postLook(Subscriber<Void> subscriber, String loginid,String jobid){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.postLook(only,loginid,jobid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
