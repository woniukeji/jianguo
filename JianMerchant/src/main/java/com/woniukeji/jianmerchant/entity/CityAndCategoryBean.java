package com.woniukeji.jianmerchant.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class CityAndCategoryBean {

    /**
     * city : 三亚
     * code : 0899
     * id : 1
     * list_t_area : [{"area_name":"市辖区","city_id":1,"id":1},{"area_name":"三亚湾","city_id":1,"id":2},{"area_name":"海棠湾","city_id":1,"id":3},{"area_name":"清水湾","city_id":1,"id":4},{"area_name":"大东海","city_id":1,"id":5},{"area_name":"凤凰岛","city_id":1,"id":6},{"area_name":"吉阳镇","city_id":1,"id":7},{"area_name":"田独镇","city_id":1,"id":8},{"area_name":"崖城","city_id":1,"id":9},{"area_name":"育才","city_id":1,"id":10},{"area_name":"天涯","city_id":1,"id":11},{"area_name":"其他","city_id":1,"id":12}]
     */

    private List<ListTCity2Bean> list_t_city2;
    /**
     * id : 1
     * type_name : 礼仪模特
     */

    private List<ListTTypeBean> list_t_type;

    public List<ListTCity2Bean> getList_t_city2() {
        return list_t_city2;
    }

    public void setList_t_city2(List<ListTCity2Bean> list_t_city2) {
        this.list_t_city2 = list_t_city2;
    }

    public List<ListTTypeBean> getList_t_type() {
        return list_t_type;
    }

    public void setList_t_type(List<ListTTypeBean> list_t_type) {
        this.list_t_type = list_t_type;
    }

    public static class ListTCity2Bean {
        private String city;
        private String code;
        private int id;
        /**
         * area_name : 市辖区
         * city_id : 1
         * id : 1
         */

        private List<ListTAreaBean> list_t_area;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<ListTAreaBean> getList_t_area() {
            return list_t_area;
        }

        public void setList_t_area(List<ListTAreaBean> list_t_area) {
            this.list_t_area = list_t_area;
        }

        public static class ListTAreaBean {
            private String area_name;
            private int city_id;
            private int id;

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }

    public static class ListTTypeBean {
        private int id;
        private String type_name;

        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

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
