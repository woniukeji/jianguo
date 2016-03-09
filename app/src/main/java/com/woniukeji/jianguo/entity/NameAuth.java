package com.woniukeji.jianguo.entity;

/**
 * Created by invinjun on 2016/3/8.
 * 用户实名表
 t_user_realname
 id           ID
 login_id     用户登录表关联ID
 front_image  正面
 behind_image 反面
 realname     真实姓名
 id_number    身份证号
 sex	     性别
 */
public class NameAuth {
    private String id;
    private String front_image;
    private String behind_image;
    private String realname;
    private String id_number;
    private String sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFront_image() {
        return front_image;
    }

    public void setFront_image(String front_image) {
        this.front_image = front_image;
    }

    public String getBehind_image() {
        return behind_image;
    }

    public void setBehind_image(String behind_image) {
        this.behind_image = behind_image;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
