package com.haibin.qiaqia.service;

import com.haibin.qiaqia.entity.CategoryGoods;
import com.haibin.qiaqia.entity.Goods;
import com.haibin.qiaqia.entity.HttpResult;
import com.haibin.qiaqia.entity.Market;
import com.haibin.qiaqia.entity.User;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by invinjun on 2016/6/1.
 */

public interface MethodInterface {
        @POST("I_user_login_Insert_Servlet")
        Observable<User> toRegister(@Query("only") String only, @Query("phone") String phone, @Query("password") String password, @Query("sms_code") String sms_code);
/**
*获取短信验证码
*/
        @GET("I_SMS_Servlet")
        Observable<HttpResult<String>> getSMS(@Query("only") String only, @Query("phone") String phone);
/**
*手机号码密码登陆
*/
        @GET("I_user_login_Login_Servlet")
        Observable<User> toLogin(@Query("only") String only, @Query("phone") String phone,@Query("password") String password);
/**
*超市查看分类
*/
        @GET("I_chao_class_Select_Servlet")
        Observable<HttpResult<User>> getCategory(@Query("only") String only);
/**
*查看商品分类
*/

        @GET("I_chao_commodity_List_Id_Servlet")
        Observable<HttpResult<Goods>> getGoods(@Query("only") String only, @Query("login_id") String loginId, @Query("chao_class_id") String categoryId);
/**
*选购商品
*/
        @POST("I_chao_cart_Insert_Servlet")
        Observable<HttpResult<Goods>> postBuy(@Query("only") String only, @Query("login_id") String loginId, @Query("json") String GoodsJson);
/**
*购物车
*/

        @GET("I_chao_cart_List_Id_Servlet")
        Observable<HttpResult<Goods>> getCarInfo(@Query("only") String only, @Query("login_id") String loginId);

        /**
         *首页数据拉取
         */
        @GET("I_chao_commodity_List_Hot_Servlet")
        Observable<HttpResult<Goods>> getHomeInfo(@Query("only") String only);
/**
<<<<<<< Updated upstream
 * 加减删除购物车商品
 */
        @POST("I_chao_cart_Insert_JJ_Servlet")
        Observable<HttpResult<Goods>> getChangeCarGoods(@Query("only") String only,@Query("login_id") String login_id,@Query("commodity_id") String commodity_id,@Query("count") String count);

/**
 * 超市分类 chao_class_list
 */
        @GET("I_chao_class_List_Id_Servlet")
        Observable<HttpResult<Market>> getMarketClass(@Query("only") String only,@Query("type") String type);
/**
 * 超市修改分类
 */
        @POST(" I_chao_class_Update_Servlet")
        Observable<HttpResult<Goods>> updateCategory(@Query("only") String only , @Query("id") String id , @Query("name") String name);
/**
 * 超市分类商品
 */
        @GET("I_chao_commodity_List_Id_Servlet")
        Observable<HttpResult<CategoryGoods>> getCategoryGoods(@Query("only") String only , @Query("login_id") String login_id , @Query("chao_class_id") String chao_class_id);





}
