package com.woniukeji.jianguo.entity;

import java.io.Serializable;

/**
 * Created by invinjun on 2016/3/3.
 */
public class User  implements Serializable {


    private T_user_login_Bean t_user_login;
    private T_user_info_Bean t_user_info;

    public T_user_login_Bean getT_user_login() {
        return t_user_login;
    }

    public void setT_user_login(T_user_login_Bean t_user_login) {
        this.t_user_login = t_user_login;
    }

    public T_user_info_Bean getT_user_info() {
        return t_user_info;
    }

    public void setT_user_info(T_user_info_Bean t_user_info) {
        this.t_user_info = t_user_info;
    }

    public class T_user_info_Bean implements Serializable{

        //�û����ϱ�
        private int id;//ID
        private int login_id;//�û���¼�����ID
        private String nickname;//�ǳ�
        private String name;//����
        private String name_image;//ͷ��
        private String school;//ѧУ
        private int realname;//ʵ����֤
        private int credit;//����ֵ
        private int integral;//����
        private String regedit_time;//ע��ʱ��
        private String login_time;//��¼ʱ��

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public int getLogin_id() {
            return login_id;
        }
        public void setLogin_id(int loginId) {
            login_id = loginId;
        }
        public String getNickname() {
            return nickname;
        }
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        public String getName_image() {
            return name_image;
        }
        public void setName_image(String nameImage) {
            name_image = nameImage;
        }
        public String getSchool() {
            return school;
        }
        public void setSchool(String school) {
            this.school = school;
        }
        public int getRealname() {
            return realname;
        }
        public void setRealname(int realname) {
            this.realname = realname;
        }
        public int getCredit() {
            return credit;
        }
        public void setCredit(int credit) {
            this.credit = credit;
        }
        public int getIntegral() {
            return integral;
        }
        public void setIntegral(int integral) {
            this.integral = integral;
        }
        public String getRegedit_time() {
            return regedit_time;
        }
        public void setRegedit_time(String regeditTime) {
            regedit_time = regeditTime;
        }
        public String getLogin_time() {
            return login_time;
        }
        public void setLogin_time(String loginTime) {
            login_time = loginTime;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

    }
    public class T_user_login_Bean implements Serializable{

        //�û���¼��
        private int id;//ID
        private String tel;//����
        private String password;//�绰
        private String qqwx_token;//QQ
        private int status;//״̬

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getTel() {
            return tel;
        }
        public void setTel(String tel) {
            this.tel = tel;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }
        public void setQqwx_token(String qqwx_token) {
            this.qqwx_token = qqwx_token;
        }
        public String getQqwx_token() {
            return qqwx_token;
        }

    }

}
