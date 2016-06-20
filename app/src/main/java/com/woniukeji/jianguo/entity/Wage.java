package com.woniukeji.jianguo.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/4/20.
 */
public class Wage {


    /**
     * message : 返回收入明细成功
     * data : {"list_t_wages":[{"id":1,"login_id":4,"job_id":3,"hould_money":50,"real_money":50,"remarks":"null","reg_time":"2016-04-20 17:35:09","job_image":"http://7xlell.com2.z0.glb.qiniucdn.com/android_9448273C4333EDBEA1449099C3C8F0D7","job_name":"钟点工北京滴","job_start":"1460563200","job_stop":"1463241600"}]}
     * code : 200
     */


        /**
         * id : 1
         * login_id : 4
         * job_id : 3
         * hould_money : 50.0
         * real_money : 50.0
         * remarks : null
         * reg_time : 2016-04-20 17:35:09
         * job_image : http://7xlell.com2.z0.glb.qiniucdn.com/android_9448273C4333EDBEA1449099C3C8F0D7
         * job_name : 钟点工北京滴
         * job_start : 1460563200
         * job_stop : 1463241600
         */

        private List<ListTWagesEntity> list_t_wages;

        public List<ListTWagesEntity> getList_t_wages() {
            return list_t_wages;
        }

        public void setList_t_wages(List<ListTWagesEntity> list_t_wages) {
            this.list_t_wages = list_t_wages;
        }

        public static class ListTWagesEntity {
            private int id;
            private int login_id;
            private int job_id;
            private double hould_money;
            private double real_money;
            private String remarks;
            private String reg_time;
            private String job_image;
            private String job_name;
            private String job_start;
            private String job_stop;

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

            public int getJob_id() {
                return job_id;
            }

            public void setJob_id(int job_id) {
                this.job_id = job_id;
            }

            public double getHould_money() {
                return hould_money;
            }

            public void setHould_money(double hould_money) {
                this.hould_money = hould_money;
            }

            public double getReal_money() {
                return real_money;
            }

            public void setReal_money(double real_money) {
                this.real_money = real_money;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getReg_time() {
                return reg_time;
            }

            public void setReg_time(String reg_time) {
                this.reg_time = reg_time;
            }

            public String getJob_image() {
                return job_image;
            }

            public void setJob_image(String job_image) {
                this.job_image = job_image;
            }

            public String getJob_name() {
                return job_name;
            }

            public void setJob_name(String job_name) {
                this.job_name = job_name;
            }

            public String getJob_start() {
                return job_start;
            }

            public void setJob_start(String job_start) {
                this.job_start = job_start;
            }

            public String getJob_stop() {
                return job_stop;
            }

            public void setJob_stop(String job_stop) {
                this.job_stop = job_stop;
            }
        }
}
