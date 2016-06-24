package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by invinjun on 2016/4/1.
 */
public class Model implements Serializable{


        /**
         * id : 1
         * city_id : 1
         * area_id : 1
         * type_id : 1
         * merchant_id : 2
         * name : 校内派单
         * name_image : http://7xlell.com2.z0.glb.qiniucdn.com/01678d545b4de3f2ba858ae90a1cce21
         * start_date : 1458008358
         * stop_date : 1458098520
         * address : 学校里
         * mode : 2
         * money : 100.0
         * term : 3
         * limit_sex : 2
         * count : 154
         * sum : 200
         * day : 0
         * regedit_time : 2016-03-15 15:36:02:546
         * status : 1
         * hot : 0
         * alike : 0
         * reg_date : null
         * model_name : 0
         * city_id_name : 三亚
         * area_id_name : 市辖区
         * type_id_name : 礼仪模特
         * merchant_id_name : 张三
         * info_start_time : 1458008358
         * info_stop_time : 1458098520
         * info_set_place : 女生男生宿舍门口
         * info_set_time : 1458008358
         * info_limit_sex : 2
         * info_term : 3
         * info_other : 其他
         * info_work_content : 在女生和男生宿舍楼发传单，发完立结工资
         * info_work_require : 口齿伶俐;健康;
         */

        private List<ListTJobEntity> list_t_job;

        public List<ListTJobEntity> getList_t_job() {
            return list_t_job;
        }
    private ListTJobEntity t_job;

    public ListTJobEntity getT_job() {
        return t_job;
    }

    public void setT_job(ListTJobEntity t_job) {
        this.t_job = t_job;
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
            private String look;
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
            private String remarks;
            private String user_count;
            private int max;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public String getUser_count() {
                return user_count;
            }

            public void setUser_count(String user_count) {
                this.user_count = user_count;
            }


            public int getIs_model() {
                return is_model;
            }

            public void setIs_model(int is_model) {
                this.is_model = is_model;
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

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getLook() {
                return look;
            }

            public void setLook(String look) {
                this.look = look;
            }






            public String getInfo_tel() {
                return info_tel;
            }

            public void setInfo_tel(String info_tel) {
                this.info_tel = info_tel;
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
        }
    }
