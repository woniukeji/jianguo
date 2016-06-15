package com.haibin.qiaqia.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/6/2.
 */

public class Goods {
//    i_chao_commodity
//    id int ID
//    chao_class_id int 超市分类ID
//    name varear 商品名称
//    image varear 商品图片
//    price double 价格
//    p_date varear 生产日期
//    shelf_life varear 保质期
//    alias varear 别名
//    describe varear 描述
//    sales_sum int 销售数量
//    status int 状态（0=下架，1=正常，2=补货中）
    private int id;
    private int chao_class_id;//超时分类id
    private String name;
    private String image;
    private double price;
    private String p_date;
    private String shelf_life;
    private String alias;
    private String describe;
    private int sales_sum;
    private int  status;//（0=下架，1=正常，2=补货中）
    /**
     * message : 成功
     * data : {"list_chao_commodity":[{"id":2,"status":0,"count":0,"sales_sum":0,"chao_class_id":1,"image":"http://7xlulo.com1.z0.glb.clouddn.com/lanmei.jpg","price":29.8,"shelf_life":"六个月","alias":"蓝莓","p_date":"20160302","name":"精选蓝莓2盒(约125g/盒)","sum":100},{"id":5,"status":1,"count":0,"sales_sum":0,"chao_class_id":1,"image":"http://7xlulo.com1.z0.glb.clouddn.com/xigua.jpg","price":29,"shelf_life":"六个月","alias":"西瓜","p_date":"20160302","name":"大兴庞各庄袖珍西瓜2个","sum":100},{"id":6,"status":1,"count":0,"sales_sum":0,"chao_class_id":1,"image":"http://7xlulo.com1.z0.glb.clouddn.com/tizi.jpg","price":29.9,"shelf_life":"六个月","alias":"提子","p_date":"20160302","name":"智利红提1kg装 进口提子","sum":100},{"id":3,"status":3,"count":0,"sales_sum":0,"chao_class_id":1,"image":"http://7xlulo.com1.z0.glb.clouddn.com/lizhi.jpg","price":15.8,"shelf_life":"六个月","alias":"离职","p_date":"20160302","name":"海南妃子笑荔枝500g","sum":100},{"id":7,"status":1,"count":0,"sales_sum":0,"chao_class_id":1,"image":"http://7xlulo.com1.z0.glb.clouddn.com/hamigua.jpg","price":19.8,"shelf_life":"六个月","alias":"甜瓜","p_date":"20160302","name":"海南西州蜜甜瓜1个装（约1.5kg /个）","sum":100},{"id":9,"status":1,"count":0,"sales_sum":0,"chao_class_id":1,"image":"http://7xlulo.com1.z0.glb.clouddn.com/pingguo.jpg","price":24.8,"shelf_life":"六个月","alias":"苹果","p_date":"20160302","name":"山东精品红富士2.5kg（果径80mm）","sum":100},{"id":22,"status":1,"count":0,"sales_sum":0,"chao_class_id":2,"image":"http://7xlulo.com1.z0.glb.clouddn.com/hongshu.jpg","price":4.9,"shelf_life":"六个月","alias":"红薯","p_date":"20160302","name":"延庆生态红薯500g","sum":100},{"id":4,"status":1,"count":0,"sales_sum":0,"chao_class_id":1,"image":"http://7xlulo.com1.z0.glb.clouddn.com/yingtao.jpg","price":29.9,"shelf_life":"六个月","alias":"樱桃","p_date":"20160302","name":"大连樱桃500g约9g/个","sum":100},{"sum":0,"status":0,"id":0,"price":0,"count":0,"chao_class_id":0,"sales_sum":0}]}
     * code : 200
     */

    private String message;
    private DataEntity data;
    private String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChao_class_id() {
        return chao_class_id;
    }

    public void setChao_class_id(int chao_class_id) {
        this.chao_class_id = chao_class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getP_date() {
        return p_date;
    }

    public void setP_date(String p_date) {
        this.p_date = p_date;
    }

    public String getShelf_life() {
        return shelf_life;
    }

    public void setShelf_life(String shelf_life) {
        this.shelf_life = shelf_life;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getSales_sum() {
        return sales_sum;
    }

    public void setSales_sum(int sales_sum) {
        this.sales_sum = sales_sum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
         * id : 2
         * status : 0
         * count : 0
         * sales_sum : 0
         * chao_class_id : 1
         * image : http://7xlulo.com1.z0.glb.clouddn.com/lanmei.jpg
         * price : 29.8
         * shelf_life : 六个月
         * alias : 蓝莓
         * p_date : 20160302
         * name : 精选蓝莓2盒(约125g/盒)
         * sum : 100
         */

        private List<ListChaoCommodityEntity> list_chao_commodity;

        public List<ListChaoCommodityEntity> getList_chao_commodity() {
            return list_chao_commodity;
        }

        public void setList_chao_commodity(List<ListChaoCommodityEntity> list_chao_commodity) {
            this.list_chao_commodity = list_chao_commodity;
        }

        public static class ListChaoCommodityEntity {
            private int id;
            private int status;
            private int count;
            private int sales_sum;
            private int chao_class_id;
            private String image;
            private double price;
            private String shelf_life;
            private String alias;
            private String p_date;
            private String name;
            private int sum;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getSales_sum() {
                return sales_sum;
            }

            public void setSales_sum(int sales_sum) {
                this.sales_sum = sales_sum;
            }

            public int getChao_class_id() {
                return chao_class_id;
            }

            public void setChao_class_id(int chao_class_id) {
                this.chao_class_id = chao_class_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getShelf_life() {
                return shelf_life;
            }

            public void setShelf_life(String shelf_life) {
                this.shelf_life = shelf_life;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getP_date() {
                return p_date;
            }

            public void setP_date(String p_date) {
                this.p_date = p_date;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSum() {
                return sum;
            }

            public void setSum(int sum) {
                this.sum = sum;
            }
        }
    }
}
