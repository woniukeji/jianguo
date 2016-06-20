package com.woniukeji.jianguo.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/5/3.
 */
public class School {
    private tschool t_school;
    private List<tschool> list_t_school;

    public List<tschool> getList_t_school() {
        return list_t_school;
    }

    public void setList_t_school(List<tschool> list_t_school) {
        this.list_t_school = list_t_school;
    }

    public class tschool {
        private int id;
        private int city_id;
        private String name;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
