package com.haibin.qiaqia.entity;

/**
 * Created by invinjun on 2016/6/1.
 */

public class User {
private String message;
    private int code;

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

    public User.i_user_info getI_user_login() {
        return i_user_login;
    }

    public void setI_user_login(User.i_user_info i_user_login) {
        this.i_user_login = i_user_login;
    }

    public User.i_user_info getI_user_info() {
        return i_user_info;
    }

    public void setI_user_info(User.i_user_info i_user_info) {
        this.i_user_info = i_user_info;
    }

    private i_user_info i_user_login;
    private i_user_info i_user_info;
    private class i_user_login{
        int id;
        String phone;
        String password;
        int status;//（0=被封号，1=可以登录）
    }
  private class i_user_info {
       int id;

       int login_id;

       String phone;

       String name;// 昵称

       String name_image;// 头像

       String regedit_time;// 注册时间

       String login_time;// 登录时间
  }
}
