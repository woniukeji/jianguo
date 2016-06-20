package com.haibin.qiaqia.http;

/**
 * Created by invinjun on 2016/6/4.
 * 处理接口调用时main线程progress
 */

public interface SubscriberOnNextListener  <T>{
    void onNext(T t);
}
