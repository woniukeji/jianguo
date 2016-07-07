package com.woniukeji.jianguo.entity;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by invinjun on 2016/3/3.
 */
public abstract class BaseCallback extends Callback<BaseBean>
{
    @Override
    public BaseBean parseNetworkResponse(Response response,int id) throws IOException
    {
        String string = response.body().string();
        BaseBean user = new Gson().fromJson(string, BaseBean.class);
        return user;
    }
}
