package com.woniukeji.jianguo.entity;

/**
 * Created by Administrator on 2016/7/19.
 */

public class JobInfo {

    /**
     * message : 兼职详情信息录入成功
     * data : {"t_job_info":{"id":1169,"job_id":1169,"tel":"15510973602","address":"北京-就近分配","lon":0,"lat":0,"start_date":"1468944000","stop_date":"1471622400","start_time":"1468999800","stop_time":"1471694400","set_place":"电话预约","set_time":"电话预约","limit_sex":2,"term":2,"mode":1,"other":"null","work_content":"小区定点地推，推销家政会员卡之类的，无责底薪90+提成。","work_require":"年龄30内，有地推经验者优先","is_collection":"0","is_enroll":"0"},"t_merchant":{"id":24,"login_id":8000,"name":"兼果-生","name_image":"http://7xlell.com2.z0.glb.qiniucdn.com/161c7192100b77a26601751e156aac98","about":"五星级酒店式的服务","label":"干净;整洁;卫生","score":0,"job_count":0,"user_count":0,"fans_count":0,"post":0,"regedit_time":"2016-03-14 15:27:35","login_time":"2016-03-14 15:27:35","is_follow":"0","pay_password":"111111","tel":"15510973602"}}
     * code : 200
     */

    private String message;
    /**
     * t_job_info : {"id":1169,"job_id":1169,"tel":"15510973602","address":"北京-就近分配","lon":0,"lat":0,"start_date":"1468944000","stop_date":"1471622400","start_time":"1468999800","stop_time":"1471694400","set_place":"电话预约","set_time":"电话预约","limit_sex":2,"term":2,"mode":1,"other":"null","work_content":"小区定点地推，推销家政会员卡之类的，无责底薪90+提成。","work_require":"年龄30内，有地推经验者优先","is_collection":"0","is_enroll":"0"}
     * t_merchant : {"id":24,"login_id":8000,"name":"兼果-生","name_image":"http://7xlell.com2.z0.glb.qiniucdn.com/161c7192100b77a26601751e156aac98","about":"五星级酒店式的服务","label":"干净;整洁;卫生","score":0,"job_count":0,"user_count":0,"fans_count":0,"post":0,"regedit_time":"2016-03-14 15:27:35","login_time":"2016-03-14 15:27:35","is_follow":"0","pay_password":"111111","tel":"15510973602"}
     */

    private DataBean data;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * id : 1169
         * job_id : 1169
         * tel : 15510973602
         * address : 北京-就近分配
         * lon : 0
         * lat : 0
         * start_date : 1468944000
         * stop_date : 1471622400
         * start_time : 1468999800
         * stop_time : 1471694400
         * set_place : 电话预约
         * set_time : 电话预约
         * limit_sex : 2
         * term : 2
         * mode : 1
         * other : null
         * work_content : 小区定点地推，推销家政会员卡之类的，无责底薪90+提成。
         * work_require : 年龄30内，有地推经验者优先
         * is_collection : 0
         * is_enroll : 0
         */

        private TJobInfoBean t_job_info;
        /**
         * id : 24
         * login_id : 8000
         * name : 兼果-生
         * name_image : http://7xlell.com2.z0.glb.qiniucdn.com/161c7192100b77a26601751e156aac98
         * about : 五星级酒店式的服务
         * label : 干净;整洁;卫生
         * score : 0
         * job_count : 0
         * user_count : 0
         * fans_count : 0
         * post : 0
         * regedit_time : 2016-03-14 15:27:35
         * login_time : 2016-03-14 15:27:35
         * is_follow : 0
         * pay_password : 111111
         * tel : 15510973602
         */

        private TMerchantBean t_merchant;

        public TJobInfoBean getT_job_info() {
            return t_job_info;
        }

        public void setT_job_info(TJobInfoBean t_job_info) {
            this.t_job_info = t_job_info;
        }

        public TMerchantBean getT_merchant() {
            return t_merchant;
        }

        public void setT_merchant(TMerchantBean t_merchant) {
            this.t_merchant = t_merchant;
        }

        public static class TJobInfoBean {
            private int id;
            private int job_id;
            private String tel;
            private String address;
            private int lon;
            private int lat;
            private String start_date;
            private String stop_date;
            private String start_time;
            private String stop_time;
            private String set_place;
            private String set_time;
            private int limit_sex;
            private int term;
            private int mode;
            private String other;
            private String work_content;
            private String work_require;
            private String is_collection;
            private String is_enroll;

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

            public int getLon() {
                return lon;
            }

            public void setLon(int lon) {
                this.lon = lon;
            }

            public int getLat() {
                return lat;
            }

            public void setLat(int lat) {
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

            public int getMode() {
                return mode;
            }

            public void setMode(int mode) {
                this.mode = mode;
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

            public String getIs_enroll() {
                return is_enroll;
            }

            public void setIs_enroll(String is_enroll) {
                this.is_enroll = is_enroll;
            }
        }

        public static class TMerchantBean {
            private int id;
            private int login_id;
            private String name;
            private String name_image;
            private String about;
            private String label;
            private int score;
            private int job_count;
            private int user_count;
            private int fans_count;
            private int post;
            private String regedit_time;
            private String login_time;
            private String is_follow;
            private String pay_password;
            private String tel;

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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
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

            public String getPay_password() {
                return pay_password;
            }

            public void setPay_password(String pay_password) {
                this.pay_password = pay_password;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }
        }
    }
}
