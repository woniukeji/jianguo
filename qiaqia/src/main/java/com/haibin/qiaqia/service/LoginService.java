package com.haibin.qiaqia.service;

import com.haibin.qiaqia.entity.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by invinjun on 2016/6/1.
 */

public interface LoginService {
    @GET("top250")
    Call<User> getTopMovie(@Query("start") int start, @Query("count") int count);
}
