package com.woniukeji.jianguo.http;


import com.woniukeji.jianguo.base.Constants;
import com.woniukeji.jianguo.entity.HttpResult;
import com.woniukeji.jianguo.entity.JobDetails;
import com.woniukeji.jianguo.entity.JobInfo;
import com.woniukeji.jianguo.entity.User;
import com.woniukeji.jianguo.utils.DateUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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

        private static final int DEFAULT_TIMEOUT = 5;

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
     *
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
//    /**
//     *获取首页数据
//     *@author invinjun
//     *created at 2016/6/15 11:30
//     * @param
//     * @param
//     * @param subscriber
//     */
//    public void getHomeData(Subscriber <Goods> subscriber ){
//        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
//        methodInterface.getHomeInfo(only)
//                .map(new HttpResultFunc<Goods>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }
    public void Login(Subscriber<User> subscriber ,String phone,String password){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.Login(only,phone,password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
//
//    /**
//     * 获取超市分类
//     */
//    public void getMarketClass(Subscriber<Market> subscriber,String type){
//        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
//        methodInterface.getMarketClass(only,type)
//                .map(new HttpResultFunc<Market>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }
//    /**
//     * 获取超市分类
//     */
//    public void getGoods(Subscriber<Goods> subscriber,String loginId,String categoryId){
//        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
//        methodInterface.getGoods(only,loginId,categoryId)
//                .map(new HttpResultFunc<Goods>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

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
    public void getJobDetail(Subscriber<JobInfo> subscriber, String loginId, String jobId, String merchantId){
        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
        methodInterface.getJobDetail(only,loginId,jobId,merchantId,"0")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
