package com.woniukeji.jianguo.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/4/22.
 */
public class DrawMoney {


    /**
     * message : 用户支出明细查询成功
     * data : {"list_t_user_moneyout":[{"id":1,"login_id":4,"money":10,"type":0,"status":0,"time":"2016-04-22 10:26:56"}]}
     * code : 200
     */

    private String message;
    private DataEntity data;
    private String code;

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

    public static class DataEntity {
        /**
         * id : 1
         * login_id : 4
         * money : 10.0
         * type : 0
         * status : 0
         * time : 2016-04-22 10:26:56
         */

        private List<ListTUserMoneyoutEntity> list_t_user_moneyout;

        public List<ListTUserMoneyoutEntity> getList_t_user_moneyout() {
            return list_t_user_moneyout;
        }

        public void setList_t_user_moneyout(List<ListTUserMoneyoutEntity> list_t_user_moneyout) {
            this.list_t_user_moneyout = list_t_user_moneyout;
        }

        public static class ListTUserMoneyoutEntity {
            private int id;
            private int login_id;
            private double money;
            private int type;
            private int status;
            private String time;
            private String remarks;

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
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

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
