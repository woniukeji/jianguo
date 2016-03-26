package com.woniukeji.jianmerchant.entity;

import java.util.List;

/**
 * Created by invinjun on 2016/3/18.
 */
public class CityBannerEntity {

        /**
         * id : 1
         * image : http://101.200.205.243/banner01.jpg
         * url :
         */

        private List<ListTBannerEntity> list_t_banner;
        /**
         * id : 1
         * city : 三亚
         */

        private List<ListTCityEntity> list_t_city;

        public void setList_t_banner(List<ListTBannerEntity> list_t_banner) {
            this.list_t_banner = list_t_banner;
        }

        public void setList_t_city(List<ListTCityEntity> list_t_city) {
            this.list_t_city = list_t_city;
        }

        public List<ListTBannerEntity> getList_t_banner() {
            return list_t_banner;
        }

        public List<ListTCityEntity> getList_t_city() {
            return list_t_city;
        }

        public static class ListTBannerEntity {
            private int id;
            private String image;
            private String url;

            public void setId(int id) {
                this.id = id;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getId() {
                return id;
            }

            public String getImage() {
                return image;
            }

            public String getUrl() {
                return url;
            }
        }

        public static class ListTCityEntity {
            private int id;
            private String city;

            public void setId(int id) {
                this.id = id;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getId() {
                return id;
            }

            public String getCity() {
                return city;
            }
        }
}
