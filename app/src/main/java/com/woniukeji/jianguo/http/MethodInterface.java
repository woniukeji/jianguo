package com.woniukeji.jianguo.http;


import com.woniukeji.jianguo.entity.Balance;
import com.woniukeji.jianguo.entity.CityCategory;
import com.woniukeji.jianguo.entity.HttpResult;
import com.woniukeji.jianguo.entity.JobInfo;
import com.woniukeji.jianguo.entity.NameAuth;
import com.woniukeji.jianguo.entity.PushMessage;
import com.woniukeji.jianguo.entity.RxJobDetails;
import com.woniukeji.jianguo.entity.User;
import java.util.List;
import cn.leancloud.chatkit.LCChatKitUser;
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
*@author invinjun
*created at 2016/7/19 10:57
*/
        @POST("T_user_login_ChangeTel_Servlet")
        Observable<HttpResult<String>> changeTel(@Query("only") String only,@Query("login_id") String login_id,@Query("tel") String tel,@Query("sms_code") String sms_code);

/**
*兼职详情获取
*/
        @GET("T_Job_info_Select_Servlet")
        Observable<RxJobDetails> getJobDetail(@Query("only") String only, @Query("login_id") String login_id, @Query("job_id") String job_id);

        @GET("T_JobDetailForUser_Servlet")
        Observable<HttpResult<JobInfo>> getJobDetailNew(@Query("only") String only, @Query("login_id") String login_id, @Query("job_id") String job_id);


        /**
*拉取城市和兼职类型（兼职列表界面使用）
*/
        @GET("T_Job_Area_City_List_User_Servlet")
        Observable<HttpResult<CityCategory>> getCityCategory(@Query("only") String only);
/**
*微信绑定账户接口
*/
        @POST("T_user_wx_Insert_Servlet")
        Observable<HttpResult<String>> postWX(@Query("only") String only, @Query("login_id") String login_id,@Query("openid") String openid,@Query("nickname") String nickname
        ,@Query("sex") String sex,@Query("province") String provice,@Query("city") String city,@Query("headimgurl") String headimgurl,
        @Query("unionid") String unionid );


        @GET("T_user_money_LoginId_Servlet")
        Observable<Balance> getWallet(@Query("only") String only, @Query("login_id") String login_id);
 /**
 *报名接口
 *@author invinjun
 *created at 2016/7/26 16:43
 */
        @GET("T_enroll_Insert_Servlet")
        Observable<HttpResult<String>> postSign(@Query("only") String only, @Query("login_id") String login_id,@Query("job_id") String job_id);
/**
*查询推送记录接口】、
*@author invinjun
*created at 2016/7/26 16:44
*/
        @GET("T_push_List_Servlet")
        Observable<HttpResult<PushMessage>> getPush(@Query("only") String only, @Query("login_id") String login_id);

/**
*实名认证
*/
        @GET("T_user_realname_Insert_Servlet")
        Observable<HttpResult<String>> postRealName(@Query("only") String only, @Query("login_id") String login_id,
                                                    @Query("front_image") String front_image, @Query("behind_image") String behind_image,
                                                    @Query("realname") String realname, @Query("id_number") String id_number,
                                                    @Query("sex") String sex);
/**
*个人资料上传
*/
        @POST("T_user_resume_Update_Servlet")
        Observable<HttpResult<String>> postResume(@Query("only") String only, @Query("login_id") String login_id,
                                                  @Query("name") String name, @Query("nickname") String nickname,
                                                  @Query("school") String school, @Query("height") String height,
                                                  @Query("student") String student, @Query("name_image") String name_image,
                                                  @Query("intoschool_date") String intoschool_date, @Query("birth_date") String birth_date,
                                                  @Query("shoe_size") String shoe_size, @Query("clothing_size") String clothing_size,
                                                  @Query("sex") String sex);

/**
*查询果聊用户信息
*/
        @GET("T_UserGroup_Servlet")//T_UserGroup_Servlet
        Observable<HttpResult<List<LCChatKitUser>>> getTalkUser(@Query("only") String only, @Query("login_id") String login_id);

/**
*收藏某兼职
*/
        @POST("T_attent_Insert_Servlet")
        Observable<HttpResult<String>> postAttention(@Query("only") String only, @Query("login_id") String login_id, @Query("follow") String follow, @Query("collection") String collection);
/**
*提现验证码接口
*/
        @GET("IsmsCkeck")
        Observable<HttpResult<String>> checkSms(@Query("only") String only, @Query("tel") String tel);

/**
*提现接口新增验证码参数
*/
        @POST("T_newMoneyout_Servlet")
        Observable<HttpResult<String>> postMoney(@Query("only") String only, @Query("login_id") String login_id,@Query("smscode") String smscode,@Query("type") String type,@Query("money") String money);



}
