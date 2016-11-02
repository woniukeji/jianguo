package com.woniukeji.jianguo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by invinjun on 2016/3/7.
 */
public class Jobs {


        /**
         * id : 58
         * city_id : 3
         * area_id : 14
         * type_id : 1
         * merchant_id : 1
         * name : 王府井大饭店
         * name_image : http://7xlell.com2.z0.glb.qiniucdn.com/FgHuNorFtCH9QWWdgRozL_pgBinA
         * start_date : 1459652281
         * stop_date : 1462071484
         * address : 王府井大饭店一楼大厅
         * mode : 2
         * money : 300.0
         * term : 2
         * limit_sex : 0
         * count : 8
         * sum : 33
         * day : 0
         * regedit_time : 2016-04-08 09:34:47:635
         * status : 1
         * hot : 0
         * alike : 0
         * reg_date : 2016-04-08 09:34:47
         * look : 3
         * user_status : 0
         */

        private List<ListTJobEntity> list_t_job;

        public List<ListTJobEntity> getList_t_job() {
            return list_t_job;
        }

        public void setList_t_job(List<ListTJobEntity> list_t_job) {
            this.list_t_job = list_t_job;
        }

        public static class ListTJobEntity implements Serializable{
            private int id;
            private int city_id;
            private int area_id;
            private int type_id;
            private int merchant_id;
            private String name;
            private String name_image;
            private String start_date;
            private String stop_date;
            private String address;
            private int mode;
            private String money;
            private int term;
            private int limit_sex;
            private int user_count;

            public int getUser_count() {
                return user_count;
            }

            public void setUser_count(int user_count) {
                this.user_count = user_count;
            }

            private int count;
            private int sum;
            private int day;
            private String regedit_time;
            private int status;
            private int hot;
            private String alike;
            private String reg_date;
            private int look;
            private String user_status;
            private long info_start_time;
            private long info_stop_time;
            private int max;//用于区分长期短期 实习生旅行的字段

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public long getInfo_start_time() {
                return info_start_time;
            }

            public void setInfo_start_time(long info_start_time) {
                this.info_start_time = info_start_time;
            }

            public long getInfo_stop_time() {
                return info_stop_time;
            }

            public void setInfo_stop_time(long info_stop_time) {
                this.info_stop_time = info_stop_time;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public int getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(int merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName_image() {
                return name_image;
            }

            public void setName_image(String name_image) {
                this.name_image = name_image;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getStop_date() {
                return stop_date;
            }

            public void setStop_date(String stop_date) {
                this.stop_date = stop_date;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getMode() {
                return mode;
            }

            public void setMode(int mode) {
                this.mode = mode;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getTerm() {
                return term;
            }

            public void setTerm(int term) {
                this.term = term;
            }

            public int getLimit_sex() {
                return limit_sex;
            }

            public void setLimit_sex(int limit_sex) {
                this.limit_sex = limit_sex;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getSum() {
                return sum;
            }

            public void setSum(int sum) {
                this.sum = sum;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public String getRegedit_time() {
                return regedit_time;
            }

            public void setRegedit_time(String regedit_time) {
                this.regedit_time = regedit_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public String getAlike() {
                return alike;
            }

            public void setAlike(String alike) {
                this.alike = alike;
            }

            public String getReg_date() {
                return reg_date;
            }

            public void setReg_date(String reg_date) {
                this.reg_date = reg_date;
            }

            public int getLook() {
                return look;
            }

            public void setLook(int look) {
                this.look = look;
            }

            public String getUser_status() {
                return user_status;

            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
            }
        }
}
