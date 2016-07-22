package com.woniukeji.jianguo.http;


import com.woniukeji.jianguo.entity.Balance;
import com.woniukeji.jianguo.entity.HttpResult;
import com.woniukeji.jianguo.entity.RxCityCategory;
import com.woniukeji.jianguo.entity.RxJobDetails;
import com.woniukeji.jianguo.entity.User;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by invinjun on 2016/6/1.
 */

public interface MethodInterface {
        @POST("I_user_login_Insert_Servlet")
        Observable<User> Register(@Query("only") String only, @Query("phone") String phone, @Query("password") String password, @Query("sms_code") String sms_code);
/**
*获取短信验证码
*/
        @GET("I_SMS_Servlet")
        Observable<HttpResult<String>> getSMS(@Query("only") String only, @Query("phone") String phone);
/**
*手机号码密码登陆
*/
        @GET("I_user_login_Login_Servlet")
        Observable<User> Login(@Query("only") String only, @Query("phone") String phone, @Query("password") String password);
/**
*更换手机号码
*@param login_id
*@param tel
 *@param sms_code
*@author invinjun
*created at 2016/7/19 10:57
*/
        @POST("T_user_login_ChangeTel_Servlet")
        Observable<HttpResult<String>> changeTel(@Query("only") String only,@Query("login_id") String login_id,@Query("tel") String tel,@Query("sms_code") String sms_code);

/**
*兼职详情获取
*/
        @GET("T_Job_info_Select_JobId_Servlet")
        Observable<RxJobDetails> getJobDetail(@Query("only") String only, @Query("login_id") String login_id, @Query("job_id") String job_id, @Query("merchant_id") String merchant_id, @Query("alike") String alike);
/**
*拉取城市和兼职类型（兼职列表界面使用）
*/
        @GET("T_Job_Area_City_List_User_Servlet")
        Observable<RxCityCategory> getCityCategory(@Query("only") String only, @Query("login_id") String login_id);
/**
*微信绑定账户接口
*/
        @POST("T_user_wx_Insert_Servlet")
        Observable<HttpResult<String>> postWX(@Query("only") String only, @Query("login_id") String login_id,@Query("openid") String openid,@Query("nickname") String nickname
        ,@Query("sex") String sex,@Query("province") String provice,@Query("city") String city,@Query("headimgurl") String headimgurl,
        @Query("unionid") String unionid );


        @GET("T_user_money_LoginId_Servlet")
        Observable<Balance> getWallet(@Query("only") String only, @Query("login_id") String login_id);

}
