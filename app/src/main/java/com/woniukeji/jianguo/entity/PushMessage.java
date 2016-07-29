package com.woniukeji.jianguo.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/5/31.
 */

public class PushMessage {

        /**
         * id : 71
         * login_id : 8257
         * job_name : 家乐福海口四家门店招收银员（长期兼职）
         * title : 报名
         * content : 报名信息已提交，请等待商家确认
         * type : 0
         * time : 2016-05-31 07:01:12
         */

        private List<ListTPushEntity> list_t_push;

        public List<ListTPushEntity> getList_t_push() {
            return list_t_push;
        }

        public void setList_t_push(List<ListTPushEntity> list_t_push) {
            this.list_t_push = list_t_push;
        }

        public static class ListTPushEntity {
            private int id;
            private int login_id;
            private String job_name;
            private String title;
            private String content;
            private int type;//（0=报名，1=钱包，2=实名）
            private String time;
            private String html_url;
            private int job_id;

            public int getJob_id() {
                return job_id;
            }

            public void setJob_id(int job_id) {
                this.job_id = job_id;
            }

            public String getHtml_url() {
                return html_url;
            }

            public void setHtml_url(String html_url) {
                this.html_url = html_url;
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

            public String getJob_name() {
                return job_name;
            }

            public void setJob_name(String job_name) {
                this.job_name = job_name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
