package com.woniukeji.jianguo.entity;

/**
 * Created by invinjun on 2016/3/4.
 */
public class SmsCode {
    private String message;
    private String code;
    private String is_tel;//（1=已经有该手机号，不再发验证码，0=没有手机号，发验证码）
    private String text;//		验证码

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIs_tel() {
        return is_tel;
    }

    public void setIs_tel(String is_tel) {
        this.is_tel = is_tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
