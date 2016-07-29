package com.woniukeji.jianguo.entity;

import java.io.Serializable;

/**
 * Created by invinjun on 2016/4/21.
 */
public class Balance implements Serializable{


    /**
     * message : 用户余额查询成功
     * data : {"t_user_money":{"id":1,"login_id":4,"name":"谢军","money":50,"zhifubao":"0","yinhang":"0","kahao":"0","pay_password":"0"}}
     * code : 200
     */

    private String message;
    /**
     * t_user_money : {"id":1,"login_id":4,"name":"谢军","money":50,"zhifubao":"0","yinhang":"0","kahao":"0","pay_password":"0"}
     */

    private DataEntity data;
    private String code;
    private String weixin;

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataEntity implements Serializable{
        /**
         * id : 1
         * login_id : 4
         * name : 谢军
         * money : 50.0
         * zhifubao : 0
         * yinhang : 0
         * kahao : 0
         * pay_password : 0
         */

        private TUserMoneyEntity t_user_money;

        public TUserMoneyEntity getT_user_money() {
            return t_user_money;
        }

        public void setT_user_money(TUserMoneyEntity t_user_money) {
            this.t_user_money = t_user_money;
        }

        public static class TUserMoneyEntity implements Serializable{
            private int id;
            private int login_id;
            private String name;
            private double money;
            private String zhifubao;
            private String yinhang;
            private String kahao;
            private String pay_password;
            private int pay_status;
            private String weixin;//0未绑定
            private String nickname;

            public String getWeixin() {
                return weixin;
            }

            public void setWeixin(String weixin) {
                this.weixin = weixin;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLogin_id() {
                return login_id;
            }

            public void setLogin_id(int login_id) {
                this.login_id = login_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public String getZhifubao() {
                return zhifubao;
            }

            public void setZhifubao(String zhifubao) {
                this.zhifubao = zhifubao;
            }

            public String getYinhang() {
                return yinhang;
            }

            public void setYinhang(String yinhang) {
                this.yinhang = yinhang;
            }

            public String getKahao() {
                return kahao;
            }

            public void setKahao(String kahao) {
                this.kahao = kahao;
            }

            public String getPay_password() {
                return pay_password;
            }

            public void setPay_password(String pay_password) {
                this.pay_password = pay_password;
            }
        }
    }
}
