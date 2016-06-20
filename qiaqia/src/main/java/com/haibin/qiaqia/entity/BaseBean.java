package com.haibin.qiaqia.entity;

/**
 * Created by invinjun on 2016/3/5.
 */
public class BaseBean<T>{
    private String message;
    private String code;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }




}
