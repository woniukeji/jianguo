package com.woniukeji.jianmerchant.entity;

/**
 * Created by invinjun on 2016/3/5.
 */
public class BaseBean <T>{
    private String message;
    private String code;
    private String sum;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

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
