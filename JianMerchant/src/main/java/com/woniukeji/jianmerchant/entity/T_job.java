package com.woniukeji.jianmerchant.entity;

/**
 * Created by invinjun on 2016/4/12.
 */
public class T_job {


    /**
     * t_job : {"id":23,"city_id":2,"area_id":7,"type_id":7,"merchant_id":2,"name":"男女不同","name_image":"http://7xlell.com2.z0.glb.qiniucdn.com/android_59096F6232581D4A42E919A57DAD8147","start_date":"1468598400","stop_date":"1471276800","address":"住兴趣三号","mode":2,"money":135,"term":2,"limit_sex":31,"count":1,"sum":12,"day":0,"regedit_time":"2016-04-11 14:49:51:380","status":0,"hot":1,"alike":"1460357386949","reg_date":"2016-04-11 14:49:51","look":3,"is_model":0,"model_name":"0","city_id_name":"海口","area_id_name":"秀英区","type_id_name":"家教","merchant_id_name":"张三","info_start_time":"10时30分","info_stop_time":"20时30分","info_set_place":"大街上","info_set_time":"鸡叫之时","info_limit_sex":"31","info_term":"2","info_other":"","info_work_content":"头巾能够","info_work_require":"安宁经济","info_tel":"18101050635","nv_job_id":"22","nv_sum":"12","nv_count":"0"}
     */

        /**
         * id : 23
         * city_id : 2
         * area_id : 7
         * type_id : 7
         * merchant_id : 2
         * name : 男女不同
         * name_image : http://7xlell.com2.z0.glb.qiniucdn.com/android_59096F6232581D4A42E919A57DAD8147
         * start_date : 1468598400
         * stop_date : 1471276800
         * address : 住兴趣三号
         * mode : 2
         * money : 135.0
         * term : 2
         * limit_sex : 31
         * count : 1
         * sum : 12
         * day : 0
         * regedit_time : 2016-04-11 14:49:51:380
         * status : 0
         * hot : 1
         * alike : 1460357386949
         * reg_date : 2016-04-11 14:49:51
         * look : 3
         * is_model : 0
         * model_name : 0
         * city_id_name : 海口
         * area_id_name : 秀英区
         * type_id_name : 家教
         * merchant_id_name : 张三
         * info_start_time : 10时30分
         * info_stop_time : 20时30分
         * info_set_place : 大街上
         * info_set_time : 鸡叫之时
         * info_limit_sex : 31
         * info_term : 2
         * info_other :
         * info_work_content : 头巾能够
         * info_work_require : 安宁经济
         * info_tel : 18101050635
         * nv_job_id : 22
         * nv_sum : 12
         * nv_count : 0
         */

        private TJobEntity t_job;

        public TJobEntity getT_job() {
            return t_job;
        }

        public void setT_job(TJobEntity t_job) {
            this.t_job = t_job;
        }

        public static class TJobEntity {
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
            private double money;
            private int term;
            private int limit_sex;
            private int count;
            private int sum;
            private int day;
            private String regedit_time;
            private int status;
            private int hot;
            private String alike;
            private String reg_date;
            private int look;
            private int is_model;
            private String model_name;
            private String city_id_name;
            private String area_id_name;
            private String type_id_name;
            private String merchant_id_name;
            private String info_start_time;
            private String info_stop_time;
            private String info_set_place;
            private String info_set_time;
            private String info_limit_sex;
            private String info_term;
            private String info_other;
            private String info_work_content;
            private String info_work_require;
            private String info_tel;
            private String nv_job_id;
            private String nv_sum;
            private String nv_count;

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

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
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

            public int getIs_model() {
                return is_model;
            }

            public void setIs_model(int is_model) {
                this.is_model = is_model;
            }

            public String getModel_name() {
                return model_name;
            }

            public void setModel_name(String model_name) {
                this.model_name = model_name;
            }

            public String getCity_id_name() {
                return city_id_name;
            }

            public void setCity_id_name(String city_id_name) {
                this.city_id_name = city_id_name;
            }

            public String getArea_id_name() {
                return area_id_name;
            }

            public void setArea_id_name(String area_id_name) {
                this.area_id_name = area_id_name;
            }

            public String getType_id_name() {
                return type_id_name;
            }

            public void setType_id_name(String type_id_name) {
                this.type_id_name = type_id_name;
            }

            public String getMerchant_id_name() {
                return merchant_id_name;
            }

            public void setMerchant_id_name(String merchant_id_name) {
                this.merchant_id_name = merchant_id_name;
            }

            public String getInfo_start_time() {
                return info_start_time;
            }

            public void setInfo_start_time(String info_start_time) {
                this.info_start_time = info_start_time;
            }

            public String getInfo_stop_time() {
                return info_stop_time;
            }

            public void setInfo_stop_time(String info_stop_time) {
                this.info_stop_time = info_stop_time;
            }

            public String getInfo_set_place() {
                return info_set_place;
            }

            public void setInfo_set_place(String info_set_place) {
                this.info_set_place = info_set_place;
            }

            public String getInfo_set_time() {
                return info_set_time;
            }

            public void setInfo_set_time(String info_set_time) {
                this.info_set_time = info_set_time;
            }

            public String getInfo_limit_sex() {
                return info_limit_sex;
            }

            public void setInfo_limit_sex(String info_limit_sex) {
                this.info_limit_sex = info_limit_sex;
            }

            public String getInfo_term() {
                return info_term;
            }

            public void setInfo_term(String info_term) {
                this.info_term = info_term;
            }

            public String getInfo_other() {
                return info_other;
            }

            public void setInfo_other(String info_other) {
                this.info_other = info_other;
            }

            public String getInfo_work_content() {
                return info_work_content;
            }

            public void setInfo_work_content(String info_work_content) {
                this.info_work_content = info_work_content;
            }

            public String getInfo_work_require() {
                return info_work_require;
            }

            public void setInfo_work_require(String info_work_require) {
                this.info_work_require = info_work_require;
            }

            public String getInfo_tel() {
                return info_tel;
            }

            public void setInfo_tel(String info_tel) {
                this.info_tel = info_tel;
            }

            public String getNv_job_id() {
                return nv_job_id;
            }

            public void setNv_job_id(String nv_job_id) {
                this.nv_job_id = nv_job_id;
            }

            public String getNv_sum() {
                return nv_sum;
            }

            public void setNv_sum(String nv_sum) {
                this.nv_sum = nv_sum;
            }

            public String getNv_count() {
                return nv_count;
            }

            public void setNv_count(String nv_count) {
                this.nv_count = nv_count;
            }
        }
}
