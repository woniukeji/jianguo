package com.haibin.qiaqia.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/6/25 0025.
 */

public class CategoryGoods {

    /**
     * message : 成功
     * data : {"list_chao_commodity":[{"id":1,"chao_class_id":1,"name":"新奇士美国脐橙4个约180g/个","image":"http://7xlulo.com1.z0.glb.clouddn.com/chengzi.jpg","price":14.8,"p_date":"20160202","shelf_life":"八个月","alias":"橙子","sales_sum":0,"sum":20,"status":2,"count":0},{"id":4,"chao_class_id":1,"name":"大连樱桃500g约9g/个","image":"http://7xlulo.com1.z0.glb.clouddn.com/yingtao.jpg","price":29.9,"p_date":"20160302","shelf_life":"六个月","alias":"樱桃","sales_sum":0,"sum":100,"status":1,"count":0},{"id":5,"chao_class_id":1,"name":"大兴庞各庄袖珍西瓜2个","image":"http://7xlulo.com1.z0.glb.clouddn.com/xigua.jpg","price":29,"p_date":"20160302","shelf_life":"六个月","alias":"西瓜","sales_sum":0,"sum":100,"status":1,"count":0},{"id":6,"chao_class_id":1,"name":"智利红提1kg装 进口提子","image":"http://7xlulo.com1.z0.glb.clouddn.com/tizi.jpg","price":29.9,"p_date":"20160302","shelf_life":"六个月","alias":"提子","sales_sum":0,"sum":100,"status":1,"count":0},{"id":7,"chao_class_id":1,"name":"海南西州蜜甜瓜1个装（约1.5kg /个）","image":"http://7xlulo.com1.z0.glb.clouddn.com/hamigua.jpg","price":19.8,"p_date":"20160302","shelf_life":"六个月","alias":"甜瓜","sales_sum":0,"sum":100,"status":1,"count":0},{"id":8,"chao_class_id":1,"name":"泰国金枕头榴莲6-7斤 (约1-2个)","image":"http://7xlulo.com1.z0.glb.clouddn.com/liulian.jpg","price":138,"p_date":"20160302","shelf_life":"六个月","alias":"榴莲","sales_sum":0,"sum":100,"status":1,"count":0},{"id":9,"chao_class_id":1,"name":"山东精品红富士2.5kg（果径80mm）","image":"http://7xlulo.com1.z0.glb.clouddn.com/pingguo.jpg","price":24.8,"p_date":"20160302","shelf_life":"六个月","alias":"苹果","sales_sum":0,"sum":100,"status":1,"count":0},{"id":10,"chao_class_id":1,"name":"Zespri佳沛新西兰阳光金奇异果12个92-114g/个","image":"http://7xlulo.com1.z0.glb.clouddn.com/qiyiguo.jpg","price":69,"p_date":"20160302","shelf_life":"六个月","alias":"奇异果","sales_sum":0,"sum":100,"status":1,"count":0},{"id":11,"chao_class_id":1,"name":"福建漳州杨梅（早梅）2盒 约450g/盒","image":"http://7xlulo.com1.z0.glb.clouddn.com/yangmei.jpg","price":26.8,"p_date":"20160302","shelf_life":"六个月","alias":"杨梅","sales_sum":0,"sum":100,"status":1,"count":0},{"id":12,"chao_class_id":1,"name":"河北春雪水蜜桃4个约150g/个","image":"http://7xlulo.com1.z0.glb.clouddn.com/shuimitao.jpg","price":16.8,"p_date":"20160302","shelf_life":"六个月","alias":"水蜜桃","sales_sum":0,"sum":100,"status":1,"count":0},{"id":13,"chao_class_id":1,"name":"海南三亚小台农芒果1kg","image":"http://7xlulo.com1.z0.glb.clouddn.com/mangguo.jpg","price":19.9,"p_date":"20160302","shelf_life":"六个月","alias":"芒果","sales_sum":0,"sum":100,"status":1,"count":0},{"id":14,"chao_class_id":1,"name":"菲律宾凤梨2个","image":"http://7xlulo.com1.z0.glb.clouddn.com/boluo.jpg","price":29.9,"p_date":"20160302","shelf_life":"六个月","alias":"凤梨","sales_sum":0,"sum":100,"status":1,"count":0},{"id":15,"chao_class_id":1,"name":"海南夏威夷木瓜0.5kg装","image":"http://7xlulo.com1.z0.glb.clouddn.com/mugua.jpg","price":8,"p_date":"20160302","shelf_life":"六个月","alias":"木瓜","sales_sum":0,"sum":100,"status":1,"count":0},{"id":16,"chao_class_id":1,"name":"大连黄油桃1kg约60g/个","image":"http://7xlulo.com1.z0.glb.clouddn.com/youtao.jpg","price":24.8,"p_date":"20160302","shelf_life":"六个月","alias":"油桃","sales_sum":0,"sum":100,"status":1,"count":0},{"id":17,"chao_class_id":1,"name":"山东莘县花蕾甜瓜1kg约230g/个","image":"http://7xlulo.com1.z0.glb.clouddn.com/tiangua.jpg","price":12.8,"p_date":"20160302","shelf_life":"六个月","alias":"甜瓜","sales_sum":0,"sum":100,"status":1,"count":0},{"id":18,"chao_class_id":1,"name":"泰国椰青4个 精品热带水果 送开椰器+吸管","image":"http://7xlulo.com1.z0.glb.clouddn.com/yezi.jpg","price":76,"p_date":"20160302","shelf_life":"六个月","alias":"椰子","sales_sum":0,"sum":100,"status":1,"count":0}]}
     * code : 200
     */

        /**
         * id : 1
         * chao_class_id : 1
         * name : 新奇士美国脐橙4个约180g/个
         * image : http://7xlulo.com1.z0.glb.clouddn.com/chengzi.jpg
         * price : 14.8
         * p_date : 20160202
         * shelf_life : 八个月
         * alias : 橙子
         * sales_sum : 0
         * sum : 20
         * status : 2
         * count : 0
         */

        private List<ListChaoCommodityBean> list_chao_commodity;

        public List<ListChaoCommodityBean> getList_chao_commodity() {
            return list_chao_commodity;
        }

        public void setList_chao_commodity(List<ListChaoCommodityBean> list_chao_commodity) {
            this.list_chao_commodity = list_chao_commodity;
        }

        public static class ListChaoCommodityBean {
            private int id;
            private int chao_class_id;
            private String name;
            private String image;
            private double price;
            private String p_date;
            private String shelf_life;
            private String alias;
            private int sales_sum;
            private int sum;
            private int status;
            private int count;

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

            public int getSales_sum() {
                return sales_sum;
            }

            public void setSales_sum(int sales_sum) {
                this.sales_sum = sales_sum;
            }

            public int getSum() {
                return sum;
            }

            public void setSum(int sum) {
                this.sum = sum;
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
        }
    }

