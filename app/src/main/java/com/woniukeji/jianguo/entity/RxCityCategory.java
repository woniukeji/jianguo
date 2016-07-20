package com.woniukeji.jianguo.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */

public class RxCityCategory {


    /**
     * message : 获取地区信息成功
     * data : {"list_t_city2":[{"id":1,"city":"三亚","code":"0899","list_t_area":[{"id":0,"city_id":0,"area_name":"地区不限"},{"id":1,"city_id":1,"area_name":"市辖区"},{"id":2,"city_id":1,"area_name":"三亚湾"},{"id":3,"city_id":1,"area_name":"海棠湾"},{"id":4,"city_id":1,"area_name":"清水湾"},{"id":5,"city_id":1,"area_name":"大东海"},{"id":6,"city_id":1,"area_name":"凤凰岛"},{"id":7,"city_id":1,"area_name":"吉阳镇"},{"id":8,"city_id":1,"area_name":"田独镇"},{"id":9,"city_id":1,"area_name":"崖城"},{"id":10,"city_id":1,"area_name":"育才"},{"id":11,"city_id":1,"area_name":"天涯"},{"id":12,"city_id":1,"area_name":"其他"}]},{"id":2,"city":"海口","code":"0898","list_t_area":[{"id":0,"city_id":0,"area_name":"地区不限"},{"id":13,"city_id":2,"area_name":"龙华区"},{"id":14,"city_id":2,"area_name":"美兰区"},{"id":15,"city_id":2,"area_name":"秀英区"},{"id":16,"city_id":2,"area_name":"琼山区"},{"id":17,"city_id":2,"area_name":"市辖区"},{"id":18,"city_id":2,"area_name":"其他"}]},{"id":3,"city":"北京","code":"010","list_t_area":[{"id":0,"city_id":0,"area_name":"地区不限"},{"id":19,"city_id":3,"area_name":"东城区"},{"id":20,"city_id":3,"area_name":"西城区"},{"id":21,"city_id":3,"area_name":"崇文区"},{"id":22,"city_id":3,"area_name":"宣武区"},{"id":23,"city_id":3,"area_name":"朝阳区"},{"id":24,"city_id":3,"area_name":"丰台区"},{"id":25,"city_id":3,"area_name":"石景山区"},{"id":26,"city_id":3,"area_name":"海淀区"},{"id":27,"city_id":3,"area_name":"门头沟区"},{"id":28,"city_id":3,"area_name":"房山区"},{"id":29,"city_id":3,"area_name":"通州区"},{"id":30,"city_id":3,"area_name":"顺义区"},{"id":31,"city_id":3,"area_name":"昌平区"},{"id":32,"city_id":3,"area_name":"大兴区"},{"id":33,"city_id":3,"area_name":"怀柔区"},{"id":34,"city_id":3,"area_name":"平谷区"},{"id":35,"city_id":3,"area_name":"东燕郊区"},{"id":36,"city_id":3,"area_name":"密云县"},{"id":37,"city_id":3,"area_name":"延庆县"},{"id":38,"city_id":3,"area_name":"其他"}]},{"id":4,"city":"杭州","code":"0571","list_t_area":[{"id":0,"city_id":0,"area_name":"地区不限"},{"id":39,"city_id":4,"area_name":"西湖区"},{"id":40,"city_id":4,"area_name":"滨江区"},{"id":41,"city_id":4,"area_name":"下沙"},{"id":42,"city_id":4,"area_name":"江干区"},{"id":43,"city_id":4,"area_name":"拱墅区"},{"id":44,"city_id":4,"area_name":"上城区"},{"id":45,"city_id":4,"area_name":"下城区"},{"id":46,"city_id":4,"area_name":"余杭区"},{"id":47,"city_id":4,"area_name":"临安"},{"id":48,"city_id":4,"area_name":"萧山区"}]},{"id":5,"city":"西安","code":"029","list_t_area":[{"id":0,"city_id":0,"area_name":"地区不限"},{"id":49,"city_id":5,"area_name":"莲湖区"},{"id":50,"city_id":5,"area_name":"新城区"},{"id":51,"city_id":5,"area_name":"碑林区"},{"id":52,"city_id":5,"area_name":"灞桥区"},{"id":53,"city_id":5,"area_name":"未央区"},{"id":54,"city_id":5,"area_name":"雁塔区"},{"id":55,"city_id":5,"area_name":"阎良区"},{"id":56,"city_id":5,"area_name":"临潼区"},{"id":57,"city_id":5,"area_name":"长安区"},{"id":58,"city_id":5,"area_name":"蓝田县"},{"id":59,"city_id":5,"area_name":"周至县"},{"id":60,"city_id":5,"area_name":"户 县"},{"id":61,"city_id":5,"area_name":"高陵县"}]}],"list_t_type":[{"id":0,"type_name":"职业不限"},{"id":1,"type_name":"礼仪模特"},{"id":2,"type_name":"摄影助理"},{"id":3,"type_name":"传单派发"},{"id":4,"type_name":"酒吧KTV"},{"id":5,"type_name":"服务生"},{"id":6,"type_name":"钟点工"},{"id":7,"type_name":"家教/助教"},{"id":8,"type_name":"销售/促销"},{"id":9,"type_name":"促销员"},{"id":10,"type_name":"实习生"},{"id":11,"type_name":"送餐员"},{"id":12,"type_name":"其他"},{"id":13,"type_name":"会展协助"},{"id":14,"type_name":"活动充场"},{"id":15,"type_name":"校园代理"},{"id":16,"type_name":"地推拉新"},{"id":17,"type_name":"话务客服"},{"id":18,"type_name":"外卖/跑腿"},{"id":19,"type_name":"安保/场控"},{"id":20,"type_name":"快递分拣"},{"id":21,"type_name":"调研员"},{"id":22,"type_name":"文案编辑"}]}
     * code : 200
     */

    private String message;
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
         * id : 1
         * city : 三亚
         * code : 0899
         * list_t_area : [{"id":0,"city_id":0,"area_name":"地区不限"},{"id":1,"city_id":1,"area_name":"市辖区"},{"id":2,"city_id":1,"area_name":"三亚湾"},{"id":3,"city_id":1,"area_name":"海棠湾"},{"id":4,"city_id":1,"area_name":"清水湾"},{"id":5,"city_id":1,"area_name":"大东海"},{"id":6,"city_id":1,"area_name":"凤凰岛"},{"id":7,"city_id":1,"area_name":"吉阳镇"},{"id":8,"city_id":1,"area_name":"田独镇"},{"id":9,"city_id":1,"area_name":"崖城"},{"id":10,"city_id":1,"area_name":"育才"},{"id":11,"city_id":1,"area_name":"天涯"},{"id":12,"city_id":1,"area_name":"其他"}]
         */

        private List<ListTCity2Bean> list_t_city2;
        /**
         * id : 0
         * type_name : 职业不限
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
            private int id;
            private String city;
            private String code;
            /**
             * id : 0
             * city_id : 0
             * area_name : 地区不限
             */

            private List<ListTAreaBean> list_t_area;

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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public List<ListTAreaBean> getList_t_area() {
                return list_t_area;
            }

            public void setList_t_area(List<ListTAreaBean> list_t_area) {
                this.list_t_area = list_t_area;
            }

            public static class ListTAreaBean {
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

        public static class ListTTypeBean {
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
}
