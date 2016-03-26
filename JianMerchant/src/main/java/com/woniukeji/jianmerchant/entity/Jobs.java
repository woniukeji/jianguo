package com.woniukeji.jianmerchant.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/3/7.
 */
public class Jobs {
        /**
         * id : 1
         * city_id : 1
         * merchant_id : 2
         * name : 校内派单
         * name_image : http://7xlell.com2.z0.glb.qiniucdn.com/01678d545b4de3f2ba858ae90a1cce21
         * start_date : 1458008358
         * stop_date : 1458098520
         * address : 学校里
         * money : 100.0
         * term : 3
         * limit_sex : 2
         * count : 0
         * sum : 200
         * day : 0
         * regedit_time : 2016-03-15 15:36:02:546
         * status : 1
         * hot : 0
         */

        private List<ListTJob> list_t_job;

        public void setList_t_job(List<ListTJob> list_t_job) {
            this.list_t_job = list_t_job;
        }

        public List<ListTJob> getList_t_job() {
            return list_t_job;
        }

        public static class ListTJob {
            private int id;
            private int city_id;
            private int merchant_id;
            private String name;
            private String name_image;
            private int start_date;
            private int stop_date;
            private String address;
            private double money;
            private int term;
            private int limit_sex;
            private int count;
            private int sum;
            private int day;
            private String regedit_time;
            private int status;
            private int hot;

            public void setId(int id) {
                this.id = id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public void setMerchant_id(int merchant_id) {
                this.merchant_id = merchant_id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setName_image(String name_image) {
                this.name_image = name_image;
            }

            public void setStart_date(int start_date) {
                this.start_date = start_date;
            }

            public void setStop_date(int stop_date) {
                this.stop_date = stop_date;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public void setTerm(int term) {
                this.term = term;
            }

            public void setLimit_sex(int limit_sex) {
                this.limit_sex = limit_sex;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public void setSum(int sum) {
                this.sum = sum;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public void setRegedit_time(String regedit_time) {
                this.regedit_time = regedit_time;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public int getId() {
                return id;
            }

            public int getCity_id() {
                return city_id;
            }

            public int getMerchant_id() {
                return merchant_id;
            }

            public String getName() {
                return name;
            }

            public String getName_image() {
                return name_image;
            }

            public int getStart_date() {
                return start_date;
            }

            public int getStop_date() {
                return stop_date;
            }

            public String getAddress() {
                return address;
            }

            public double getMoney() {
                return money;
            }

            public int getTerm() {
                return term;
            }

            public int getLimit_sex() {
                return limit_sex;
            }

            public int getCount() {
                return count;
            }

            public int getSum() {
                return sum;
            }

            public int getDay() {
                return day;
            }

            public String getRegedit_time() {
                return regedit_time;
            }

            public int getStatus() {
                return status;
            }

            public int getHot() {
                return hot;
            }
        }
}
