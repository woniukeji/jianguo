package com.woniukeji.jianguo.entity;

import java.io.Serializable;

/**
 * Created by invinjun on 2016/3/16.
 */
public class JobDetails implements Serializable{


    /**
     * t_job_info : {"id":33,"job_id":32,"tel":"13382882525","address":"石景山万达影城3号厅","lon":0,"lat":0,"start_date":"1463328000000","stop_date":"1466179200000","start_time":"7时30分","stop_time":"21时30分","set_place":"万达1号厅","set_time":"8点半","limit_sex":1,"term":0,"other":"","work_content":"卖包子 万达牌子","work_require":"吃饱了再来，不能偷吃","is_collection":"0"}
     * t_merchant : {"id":2,"login_id":43,"name":"张三","name_image":"http://7xlell.com2.z0.glb.qiniucdn.com/01678d545b4de3f2ba858ae90a1cce21","about":"在女生和男生宿舍楼发传单，发完立结工资。","label":"口齿伶俐;健康;","score":0,"job_count":0,"user_count":0,"fans_count":0,"post":0,"regedit_time":"2016-03-15 15:00:57","login_time":"2016-03-15 15:00:57","is_follow":"0"}
     */

        /**
         * id : 33
         * job_id : 32
         * tel : 13382882525
         * address : 石景山万达影城3号厅
         * lon : 0.0
         * lat : 0.0
         * start_date : 1463328000000
         * stop_date : 1466179200000
         * start_time : 7时30分
         * stop_time : 21时30分
         * set_place : 万达1号厅
         * set_time : 8点半
         * limit_sex : 1
         * term : 0
         * other :
         * work_content : 卖包子 万达牌子
         * work_require : 吃饱了再来，不能偷吃
         * is_collection : 0
         */

        private TJobInfoEntity t_job_info;
        /**
         * id : 2
         * login_id : 43
         * name : 张三
         * name_image : http://7xlell.com2.z0.glb.qiniucdn.com/01678d545b4de3f2ba858ae90a1cce21
         * about : 在女生和男生宿舍楼发传单，发完立结工资。
         * label : 口齿伶俐;健康;
         * score : 0.0
         * job_count : 0
         * user_count : 0
         * fans_count : 0
         * post : 0
         * regedit_time : 2016-03-15 15:00:57
         * login_time : 2016-03-15 15:00:57
         * is_follow : 0
         */

        private TMerchantEntity t_merchant;

        public TJobInfoEntity getT_job_info() {
            return t_job_info;
        }

        public void setT_job_info(TJobInfoEntity t_job_info) {
            this.t_job_info = t_job_info;
        }

        public TMerchantEntity getT_merchant() {
            return t_merchant;
        }

        public void setT_merchant(TMerchantEntity t_merchant) {
            this.t_merchant = t_merchant;
        }

        public static class TJobInfoEntity implements Serializable{
            private int id;
            private int job_id;
            private String tel;
            private String address;
            private double lon;
            private double lat;
            private String start_date;
            private String stop_date;
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
            private String is_enroll;

            public String getIs_enroll() {
                return is_enroll;
            }

            public void setIs_enroll(String is_enroll) {
                this.is_enroll = is_enroll;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getJob_id() {
                return job_id;
            }

            public void setJob_id(int job_id) {
                this.job_id = job_id;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
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

            public int getLimit_sex() {
                return limit_sex;
            }

            public void setLimit_sex(int limit_sex) {
                this.limit_sex = limit_sex;
            }

            public int getTerm() {
                return term;
            }

            public void setTerm(int term) {
                this.term = term;
            }

            public String getOther() {
                return other;
            }

            public void setOther(String other) {
                this.other = other;
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

            public String getIs_collection() {
                return is_collection;
            }

            public void setIs_collection(String is_collection) {
                this.is_collection = is_collection;
            }
        }

        public static class TMerchantEntity implements  Serializable{
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

            public String getName_image() {
                return name_image;
            }

            public void setName_image(String name_image) {
                this.name_image = name_image;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public int getJob_count() {
                return job_count;
            }

            public void setJob_count(int job_count) {
                this.job_count = job_count;
            }

            public int getUser_count() {
                return user_count;
            }

            public void setUser_count(int user_count) {
                this.user_count = user_count;
            }

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public int getPost() {
                return post;
            }

            public void setPost(int post) {
                this.post = post;
            }

            public String getRegedit_time() {
                return regedit_time;
            }

            public void setRegedit_time(String regedit_time) {
                this.regedit_time = regedit_time;
            }

            public String getLogin_time() {
                return login_time;
            }

            public void setLogin_time(String login_time) {
                this.login_time = login_time;
            }

            public String getIs_follow() {
                return is_follow;
            }

            public void setIs_follow(String is_follow) {
                this.is_follow = is_follow;
            }
        }
}
