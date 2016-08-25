package com.woniukeji.jianguo.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */

public class JobInfo {
    /**
     * id : 182
     * set_place : 公交站
     * set_time : 六点半
     * start_date : 1471622400
     * stop_date : 1476806400
     * start_time : 1471658400
     * stop_time : 1476864000
     * work_content : 工作内容哈哈哈哈
     * work_require : 工作要求嘻嘻嘻嘻
     * address : 大堂
     * reg_date : 2016-08-19 09:42:36
     * job_name : 测试男女人数
     * job_image : http://v3.jianguojob.com/logo.png
     * job_money : 120元/天
     * limit_sex : 3
     * finallySum : 33
     * nowCount : 0
     * merchant_name : 王五
     * merchant_image : http://7xlell.com2.z0.glb.qiniucdn.com/01678d545b4de3f2ba858ae90a1cce21
     * merchant_id : 3
     * merchant_about : 五星级酒店式的服务
     * other : null
     * status : 0
     * mode : 周结
     * isEnroll : 0
     * isFavorite : 0
     * limit : ["需面试","身份证复印件"]
     * welfare : ["包吃包住","包一餐","报销路费","工资高","有礼物","自由工作"]
     */
        private int id;
        private String set_place;
        private String set_time;
        private String start_date;
        private String stop_date;
        private String start_time;
        private String stop_time;
        private String work_content;
        private String work_require;
        private String address;
        private String reg_date;
        private String job_name;
        private String job_image;
        private String job_money;
        private String limit_sex;
        private int finallySum;
        private int nowCount;
        private String merchant_name;
        private String merchant_image;
        private int merchant_id;
        private String merchant_about;
        private String other;
        private int status;
        private String mode;
        private String isEnroll;
        private String isFavorite;
        private String merchant_LogId;

    public String getMerchant_LogId() {
        return merchant_LogId;
    }

    public void setMerchant_LogId(String merchant_LogId) {
        this.merchant_LogId = merchant_LogId;
    }

    private List<String> limit;
        private List<String> welfare;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSet_place() {
            return set_place;
        }

        public void setSet_place(String set_place) {
            this.set_place = set_place;
        }

        public String getSet_time() {
            return set_time;
        }

        public void setSet_time(String set_time) {
            this.set_time = set_time;
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

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStop_time() {
            return stop_time;
        }

        public void setStop_time(String stop_time) {
            this.stop_time = stop_time;
        }

        public String getWork_content() {
            return work_content;
        }

        public void setWork_content(String work_content) {
            this.work_content = work_content;
        }

        public String getWork_require() {
            return work_require;
        }

        public void setWork_require(String work_require) {
            this.work_require = work_require;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReg_date() {
            return reg_date;
        }

        public void setReg_date(String reg_date) {
            this.reg_date = reg_date;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public String getJob_image() {
            return job_image;
        }

        public void setJob_image(String job_image) {
            this.job_image = job_image;
        }

        public String getJob_money() {
            return job_money;
        }

        public void setJob_money(String job_money) {
            this.job_money = job_money;
        }

        public String getLimit_sex() {
            return limit_sex;
        }

        public void setLimit_sex(String limit_sex) {
            this.limit_sex = limit_sex;
        }

        public int getFinallySum() {
            return finallySum;
        }

        public void setFinallySum(int finallySum) {
            this.finallySum = finallySum;
        }

        public int getNowCount() {
            return nowCount;
        }

        public void setNowCount(int nowCount) {
            this.nowCount = nowCount;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getMerchant_image() {
            return merchant_image;
        }

        public void setMerchant_image(String merchant_image) {
            this.merchant_image = merchant_image;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getMerchant_about() {
            return merchant_about;
        }

        public void setMerchant_about(String merchant_about) {
            this.merchant_about = merchant_about;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getIsEnroll() {
            return isEnroll;
        }

        public void setIsEnroll(String isEnroll) {
            this.isEnroll = isEnroll;
        }

        public String getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(String isFavorite) {
            this.isFavorite = isFavorite;
        }

        public List<String> getLimit() {
            return limit;
        }

        public void setLimit(List<String> limit) {
            this.limit = limit;
        }

        public List<String> getWelfare() {
            return welfare;
        }

        public void setWelfare(List<String> welfare) {
            this.welfare = welfare;
        }
    }
