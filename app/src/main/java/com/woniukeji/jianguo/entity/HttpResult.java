package com.woniukeji.jianguo.entity;

/**
 * Created by invinjun on 2016/6/1.
 */

public class HttpResult<T> {
    private String message;
    private int code;
    private T data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
