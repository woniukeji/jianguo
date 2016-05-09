package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;

/**
 * Created by invinjun on 2016/3/16.
 */
public class JobDetails implements Serializable{

    /**
     * t_job_info : {"id":6,"job_id":5,"address":"宴会服务员","lon":116.454861,"lat":39.940387,"start_date":1458008358,"stop_date":1458098520,"start_time":1458008358,"stop_time":1458098520,"set_place":"步行街正门","set_time":1458008358,"limit_sex":2,"term":3,"other":"其他","work_content":"有接有送包餐 统一要求：【穿黑鞋携带身份证复印件】女生头发要盘起来！","work_require":"口齿伶俐;健康;","is_collection":"1"}
     * t_merchant : {"id":43,"login_id":0,"name":"凯宾斯基酒店","name_image":"http://7xlell.com2.z0.glb.qiniucdn.com/161c7192100b77a26601751e156aac98","about":"五星级酒店式的服务","label":"干净;整洁;卫生","score":0,"job_count":0,"user_count":0,"fans_count":0,"post":0,"regedit_time":"2016-03-14 15:27:35","login_time":"2016-03-14 15:27:35","is_follow":"1"}
     */
        /**
         * id : 6
         * job_id : 5
         * address : 宴会服务员
         * lon : 116.454861
         * lat : 39.940387
         * start_date : 1458008358
         * stop_date : 1458098520
         * start_time : 1458008358
         * stop_time : 1458098520
         * set_place : 步行街正门
         * set_time : 1458008358
         * limit_sex : 2
         * term : 3
         * other : 其他
         * work_content : 有接有送包餐 统一要求：【穿黑鞋携带身份证复印件】女生头发要盘起来！
         * work_require : 口齿伶俐;健康;
         * is_collection : 1
         */

        private TJobInfoEntity t_job_info;
        /**
         * id : 43
         * login_id : 0
         * name : 凯宾斯基酒店
         * name_image : http://7xlell.com2.z0.glb.qiniucdn.com/161c7192100b77a26601751e156aac98
         * about : 五星级酒店式的服务
         * label : 干净;整洁;卫生
         * score : 0.0
         * job_count : 0
         * user_count : 0
         * fans_count : 0
         * post : 0
         * regedit_time : 2016-03-14 15:27:35
         * login_time : 2016-03-14 15:27:35
         * is_follow : 1
         */

        private TMerchantEntity t_merchant;

        public void setT_job_info(TJobInfoEntity t_job_info) {
            this.t_job_info = t_job_info;
        }

        public void setT_merchant(TMerchantEntity t_merchant) {
            this.t_merchant = t_merchant;
        }

        public TJobInfoEntity getT_job_info() {
            return t_job_info;
        }

        public TMerchantEntity getT_merchant() {
            return t_merchant;
        }

        public static class TJobInfoEntity implements Serializable{
            private int id;
            private String job_id;
            private String address;
            private double lon;
            private double lat;
            private long start_date;
            private long stop_date;
            private String start_time;
            private String stop_time;
            private String set_place;
            private String set_time;
            private int limit_sex;
            private int term;
            private String other;
            private String work_content;
            private String work_require;
            private String is_collection;
            private String nv_job_id;
            private String nv_sum;

            public String getNv_count() {
                return nv_count;
            }

            public void setNv_count(String nv_count) {
                this.nv_count = nv_count;
            }

            public String getNv_sum() {
                return nv_sum;
            }

            public void setNv_sum(String nv_sum) {
                this.nv_sum = nv_sum;
            }

            public String getNv_job_id() {
                return nv_job_id;
            }

            public void setNv_job_id(String nv_job_id) {
                this.nv_job_id = nv_job_id;
            }

            private String nv_count;




            private String wages;//本地保留字段 用于预览界面参数
            private String sum;
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getWages() {
                return wages;
            }

            public void setWages(String wages) {
                this.wages = wages;
            }

            public String getSum() {
                return sum;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setJob_id(String job_id) {
                this.job_id = job_id;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public void setStart_date(long start_date) {
                this.start_date = start_date;
            }

            public void setStop_date(long stop_date) {
                this.stop_date = stop_date;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public void setStop_time(String stop_time) {
                this.stop_time = stop_time;
            }

            public void setSet_place(String set_place) {
                this.set_place = set_place;
            }

            public void setSet_time(String set_time) {
                this.set_time = set_time;
            }

            public void setLimit_sex(int limit_sex) {
                this.limit_sex = limit_sex;
            }

            public void setTerm(int term) {
                this.term = term;
            }

            public void setOther(String other) {
                this.other = other;
            }

            public void setWork_content(String work_content) {
                this.work_content = work_content;
            }

            public void setWork_require(String work_require) {
                this.work_require = work_require;
            }

            public void setIs_collection(String is_collection) {
                this.is_collection = is_collection;
            }

            public int getId() {
                return id;
            }

            public String getJob_id() {
                return job_id;
            }

            public String getAddress() {
                return address;
            }

            public double getLon() {
                return lon;
            }

            public double getLat() {
                return lat;
            }

            public long getStart_date() {
                return start_date;
            }

            public long getStop_date() {
                return stop_date;
            }

            public String getStart_time() {
                return start_time;
            }

            public String getStop_time() {
                return stop_time;
            }

            public String getSet_place() {
                return set_place;
            }

            public String getSet_time() {
                return set_time;
            }

            public int getLimit_sex() {
                return limit_sex;
            }

            public int getTerm() {
                return term;
            }

            public String getOther() {
                return other;
            }

            public String getWork_content() {
                return work_content;
            }

            public String getWork_require() {
                return work_require;
            }

            public String getIs_collection() {
                return is_collection;
            }
        }

        public static class TMerchantEntity implements Serializable {
            private int id;
            private int login_id;
            private String name;
            private String name_image;
            private String about;
            private String label;
            private double score;
            private int job_count;
            private int user_count;
            private int fans_count;
            private int post;
            private String regedit_time;
            private String login_time;
            private String is_follow;

            public void setId(int id) {
                this.id = id;
            }

            public void setLogin_id(int login_id) {
                this.login_id = login_id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setName_image(String name_image) {
                this.name_image = name_image;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public void setJob_count(int job_count) {
                this.job_count = job_count;
            }

            public void setUser_count(int user_count) {
                this.user_count = user_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public void setPost(int post) {
                this.post = post;
            }

            public void setRegedit_time(String regedit_time) {
                this.regedit_time = regedit_time;
            }

            public void setLogin_time(String login_time) {
                this.login_time = login_time;
            }

            public void setIs_follow(String is_follow) {
                this.is_follow = is_follow;
            }

            public int getId() {
                return id;
            }

            public int getLogin_id() {
                return login_id;
            }

            public String getName() {
                return name;
            }

            public String getName_image() {
                return name_image;
            }

            public String getAbout() {
                return about;
            }

            public String getLabel() {
                return label;
            }

            public double getScore() {
                return score;
            }

            public int getJob_count() {
                return job_count;
            }

            public int getUser_count() {
                return user_count;
            }

            public int getFans_count() {
                return fans_count;
            }

            public int getPost() {
                return post;
            }

            public String getRegedit_time() {
                return regedit_time;
            }

            public String getLogin_time() {
                return login_time;
            }

            public String getIs_follow() {
                return is_follow;
            }
        }
    }
