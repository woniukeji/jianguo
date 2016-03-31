package com.woniukeji.jianmerchant.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/3/31.
 */
public class CityCategory {

        /**
         * id : 1
         * city : 三亚
         * list_t_area : [{"id":1,"city_id":1,"area_name":"市辖区"},{"id":2,"city_id":1,"area_name":"海棠区"},{"id":3,"city_id":1,"area_name":"吉阳区"},{"id":4,"city_id":1,"area_name":"天涯区"},{"id":5,"city_id":1,"area_name":"崖州区"}]
         */

        private List<ListTCity2Entity> list_t_city2;
        /**
         * id : 1
         * type_name : 礼仪模特
         */

        private List<ListTTypeEntity> list_t_type;

        public List<ListTCity2Entity> getList_t_city2() {
            return list_t_city2;
        }

        public void setList_t_city2(List<ListTCity2Entity> list_t_city2) {
            this.list_t_city2 = list_t_city2;
        }

        public List<ListTTypeEntity> getList_t_type() {
            return list_t_type;
        }

        public void setList_t_type(List<ListTTypeEntity> list_t_type) {
            this.list_t_type = list_t_type;
        }

        public static class ListTCity2Entity {
            private int id;
            private String city;
            /**
             * id : 1
             * city_id : 1
             * area_name : 市辖区
             */

            private List<ListTAreaEntity> list_t_area;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public List<ListTAreaEntity> getList_t_area() {
                return list_t_area;
            }

            public void setList_t_area(List<ListTAreaEntity> list_t_area) {
                this.list_t_area = list_t_area;
            }

            public static class ListTAreaEntity {
                private int id;
                private int city_id;
                private String area_name;

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

                public String getArea_name() {
                    return area_name;
                }

                public void setArea_name(String area_name) {
                    this.area_name = area_name;
                }
            }
        }

        public static class ListTTypeEntity {
            private int id;
            private String type_name;

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
        }
}
