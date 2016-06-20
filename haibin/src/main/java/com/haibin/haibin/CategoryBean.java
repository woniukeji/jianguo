package com.haibin.haibin;

import java.util.List;

/**
 * Created by invinjun on 2016/5/26.
 */

public class CategoryBean {

    /**
     * message : 成功
     * data : {"list_chao_class":[{"id":1,"class_id":1,"name":"休闲零食"},{"id":2,"class_id":1,"name":"饼干糕点"},{"id":3,"class_id":1,"name":"熟肉豆干"},{"id":4,"class_id":1,"name":"冲调饮品"},{"id":5,"class_id":1,"name":"牛奶乳品"},{"id":6,"class_id":1,"name":"糖果巧克力"},{"id":7,"class_id":1,"name":"方便速食"},{"id":8,"class_id":1,"name":"饮料/水"},{"id":9,"class_id":1,"name":"酒"},{"id":10,"class_id":1,"name":"炒货干果"},{"id":11,"class_id":1,"name":"粮油干货"},{"id":12,"class_id":1,"name":"厨房调料"},{"id":13,"class_id":1,"name":"生活用纸"},{"id":14,"class_id":1,"name":"衣物清洁"},{"id":15,"class_id":1,"name":"居家用品"}]}
     * code : 200
     */

    private String message;
    private DataEntity data;
    private String code;
    private String qiniu_token;

    public String getQiniu_token() {
        return qiniu_token;
    }

    public void setQiniu_token(String qiniu_token) {
        this.qiniu_token = qiniu_token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataEntity {
        /**
         * id : 1
         * class_id : 1
         * name : 休闲零食
         */

        private List<ListChaoClassEntity> list_chao_class;

        public List<ListChaoClassEntity> getList_chao_class() {
            return list_chao_class;
        }

        public void setList_chao_class(List<ListChaoClassEntity> list_chao_class) {
            this.list_chao_class = list_chao_class;
        }

        public static class ListChaoClassEntity {
            private int id;
            private int class_id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getClass_id() {
                return class_id;
            }

            public void setClass_id(int class_id) {
                this.class_id = class_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
