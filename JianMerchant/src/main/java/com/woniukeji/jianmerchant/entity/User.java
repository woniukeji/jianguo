package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;

/**
 * Created by invinjun on 2016/3/3.
 */
public class User  implements Serializable {


    /**
     * t_user_login : {"id":43,"tel":"222222","password":"E10ADC3949BA59ABBE56E057F20F883E","qqwx_token":"null","status":1,"qiniu":"l8JTtsVLzAV4yEvMvLd7Jno_4pDBwg180-_sGPbP:ND7kawNfpCzoLSPKpROgPM-0TBk=:eyJzY29wZSI6ImppYW5ndW8iLCJkZWFkbGluZSI6MTQ1OTc0MjM2Mn0="}
     * t_merchant : {"id":2,"login_id":43,"name":"校内派单","name_image":"http://7xlell.com2.z0.glb.qiniucdn.com/01678d545b4de3f2ba858ae90a1cce21","about":"在女生和男生宿舍楼发传单，发完立结工资。","label":"口齿伶俐;健康;","score":0,"job_count":0,"user_count":0,"fans_count":0,"post":0,"regedit_time":"2016-03-15 15:00:57","login_time":"2016-03-15 15:00:57"}
     */

        /**
         {"message":"登录成功","data":{"t_user_login":{"id":43,"tel":"222222","password":"E10ADC3949BA59ABBE56E057F20F883E",
         "qqwx_token":"null","status":1,"qiniu":"l8JTtsVLzAV4yEvMvLd7Jno_4pDBwg180-_sGPbP:_OFx6EsAre1PKrOrxawasOjsr_c\u003d:eyJzY29wZSI6ImppYW5ndW8iLCJkZWFkbGluZSI6MTQ2MTczMDc0OX0\u003d"},
         "t_merchant":{"id":2,"login_id":43,"name":"张三","name_image":"http://7xlell.com2.z0.glb.qiniucdn.com/01678d545b4de3f2ba858ae90a1cce21","about":"在女生和男生宿舍楼发传单，发完立结工资。
         ","label":"口齿伶俐;健康;","score":0.0,"job_count":0,"user_count":0,"fans_count":0,"post":0,"regedit_time":"2016-03-15 15:00:57","login_time":"2016-03-15 15:00:57","pay_password":"111111"}},"code":"200"}*/

        private TUserLoginEntity t_user_login;
        /**
         * id : 2
         * login_id : 43
         * name : 校内派单
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
         */

        private TMerchantEntity t_merchant;

        public TUserLoginEntity getT_user_login() {
            return t_user_login;
        }

        public void setT_user_login(TUserLoginEntity t_user_login) {
            this.t_user_login = t_user_login;
        }

        public TMerchantEntity getT_merchant() {
            return t_merchant;
        }

        public void setT_merchant(TMerchantEntity t_merchant) {
            this.t_merchant = t_merchant;
        }

        public static class TUserLoginEntity {
            private int id;
            private String tel;
            private String password;
            private String qqwx_token;
            private int status;
            private String qiniu;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getQqwx_token() {
                return qqwx_token;
            }

            public void setQqwx_token(String qqwx_token) {
                this.qqwx_token = qqwx_token;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getQiniu() {
                return qiniu;
            }

            public void setQiniu(String qiniu) {
                this.qiniu = qiniu;
            }
        }

        public static class TMerchantEntity {
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
            private String pay_password;

            public String getPay_password() {
                return pay_password;
            }

            public void setPay_password(String pay_password) {
                this.pay_password = pay_password;
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
        }
}
