package com.woniukeji.jianmerchant.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by invinjun on 2016/4/7.
 */
public class PublishUser implements Serializable{

        /**
         * id : 48
         * login_id : 67
         * nickname : 兼果客服~果果
         * name :
         * name_image : http://q.qlogo.cn/qqapp/1104770733/2E9A3A4C5B6FDE3724DEB60A02FBF154/40
         * school :
         * realname : 0
         * credit : 0
         * integral : 0
         * regedit_time : 2016-04-01 19:04:14
         * login_time : 2016-04-01 19:04:14
         * complete_job : 0
         * cancel_job : 0
         * time_job : 2016-04-07 17:03:59
         * status_job : 0
         * remarks_job :
         * sex_resume : 0
         * intoschool_date_resume :
         */

        private List<ListTUserInfoEntity> list_t_user_info;

        public List<ListTUserInfoEntity> getList_t_user_info() {
            return list_t_user_info;
        }

        public void setList_t_user_info(List<ListTUserInfoEntity> list_t_user_info) {
            this.list_t_user_info = list_t_user_info;
        }

        public static class ListTUserInfoEntity implements Serializable{
            private int id;
            private int login_id;
            private String nickname;
            private String name;
            private String name_image;
            private String school;
            private String realname;
            private int credit;
            private int integral;
            private String regedit_time;
            private String login_time;
            private int complete_job;
            private int cancel_job;
            private String time_job;
            private String tel;

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getUser_status() {
                return user_status;
            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
            }

            private String user_status;
            private String remarks_job;
            private int sex_resume;
            private String intoschool_date_resume;

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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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

            public String getSchool() {
                return school;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public int getCredit() {
                return credit;
            }

            public void setCredit(int credit) {
                this.credit = credit;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
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

            public int getComplete_job() {
                return complete_job;
            }

            public void setComplete_job(int complete_job) {
                this.complete_job = complete_job;
            }

            public int getCancel_job() {
                return cancel_job;
            }

            public void setCancel_job(int cancel_job) {
                this.cancel_job = cancel_job;
            }

            public String getTime_job() {
                return time_job;
            }

            public void setTime_job(String time_job) {
                this.time_job = time_job;
            }


            public String getRemarks_job() {
                return remarks_job;
            }

            public void setRemarks_job(String remarks_job) {
                this.remarks_job = remarks_job;
            }

            public int getSex_resume() {
                return sex_resume;
            }

            public void setSex_resume(int sex_resume) {
                this.sex_resume = sex_resume;
            }

            public String getIntoschool_date_resume() {
                return intoschool_date_resume;
            }

            public void setIntoschool_date_resume(String intoschool_date_resume) {
                this.intoschool_date_resume = intoschool_date_resume;
            }
        }
}
