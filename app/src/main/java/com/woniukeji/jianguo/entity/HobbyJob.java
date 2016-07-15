package com.woniukeji.jianguo.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */

public class HobbyJob {

        /**
         * id : 1
         * type_name : 礼仪模特
         * is_type : false
         */

        private List<ListTHobbyTypeBean> list_t_hobby_type;
        private List<String> list_t_hobby_time;

        public List<ListTHobbyTypeBean> getList_t_hobby_type() {
            return list_t_hobby_type;
        }

        public void setList_t_hobby_type(List<ListTHobbyTypeBean> list_t_hobby_type) {
            this.list_t_hobby_type = list_t_hobby_type;
        }

        public List<String> getList_t_hobby_time() {
            return list_t_hobby_time;
        }

        public void setList_t_hobby_time(List<String> list_t_hobby_time) {
            this.list_t_hobby_time = list_t_hobby_time;
        }

        public static class ListTHobbyTypeBean {
            private int id;
            private String type_name;
            private boolean is_type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public boolean getIs_type() {
                return is_type;
            }

            public void setIs_type(boolean is_type) {
                this.is_type = is_type;
            }
        }
}
