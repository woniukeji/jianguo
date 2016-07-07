package com.woniukeji.jianguo.entity;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by invinjun on 2016/3/3.
 */
public abstract class CodeCallback extends Callback<SmsCode>
{
    @Override
    public SmsCode parseNetworkResponse(Response response,int id) throws IOException
    {
        String string = response.body().string();
        SmsCode smsCode = new Gson().fromJson(string, SmsCode.class);
        return smsCode;
    }
}
