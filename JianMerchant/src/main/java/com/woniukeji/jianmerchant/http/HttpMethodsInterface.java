package com.woniukeji.jianmerchant.http;

import com.woniukeji.jianmerchant.entity.BaseBean;
import com.woniukeji.jianmerchant.entity.CityAndCategoryBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/16.
 */
public interface HttpMethodsInterface {
    //    @GET("top250")
//    rx.Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
    @GET("T_Job_Area_City_List_Servlet")
    Observable<BaseBean<CityAndCategoryBean>> getCityCategory(@Query("only") String only, @Query("login_id") String loginId);

}
