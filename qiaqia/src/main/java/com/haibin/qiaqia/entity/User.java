package com.haibin.qiaqia.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by invinjun on 2016/6/1.
 */

public class User {

    /**
     * message : 登录成功
     * data : {"i_user_login":{"id":3,"phone":"18101050625","password":"E10ADC3949BA59ABBE56E057F20F883E","status":1},"i_user_info":{"id":3,"login_id":3,"phone":"18101050625","name":"小恰","name_image":"http://7xlulo.com1.z0.glb.clouddn.com/moren.png","regedit_time":"2016-06-06 12:13:53","login_time":"2016-06-06 12:13:53"}}
     * code : 200
     */

    @SerializedName("message") private String message;
    /**
     * i_user_login : {"id":3,"phone":"18101050625","password":"E10ADC3949BA59ABBE56E057F20F883E","status":1}
     * i_user_info : {"id":3,"login_id":3,"phone":"18101050625","name":"小恰","name_image":"http://7xlulo.com1.z0.glb.clouddn.com/moren.png","regedit_time":"2016-06-06 12:13:53","login_time":"2016-06-06 12:13:53"}
     */

    @SerializedName("data") private DataEntity data;
    @SerializedName("code") private String code;

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
         * id : 3
         * phone : 18101050625
         * password : E10ADC3949BA59ABBE56E057F20F883E
         * status : 1
         */

        @SerializedName("i_user_login") private IUserLoginEntity iUserLogin;
        /**
         * id : 3
         * login_id : 3
         * phone : 18101050625
         * name : 小恰
         * name_image : http://7xlulo.com1.z0.glb.clouddn.com/moren.png
         * regedit_time : 2016-06-06 12:13:53
         * login_time : 2016-06-06 12:13:53
         */

        @SerializedName("i_user_info") private IUserInfoEntity iUserInfo;

        public IUserLoginEntity getIUserLogin() {
            return iUserLogin;
        }

        public void setIUserLogin(IUserLoginEntity iUserLogin) {
            this.iUserLogin = iUserLogin;
        }

        public IUserInfoEntity getIUserInfo() {
            return iUserInfo;
        }

        public void setIUserInfo(IUserInfoEntity iUserInfo) {
            this.iUserInfo = iUserInfo;
        }

        public static class IUserLoginEntity {
            @SerializedName("id") private int id;
            @SerializedName("phone") private String phone;
            @SerializedName("password") private String password;
            @SerializedName("status") private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class IUserInfoEntity {
            @SerializedName("id") private int id;
            @SerializedName("login_id") private int loginId;
            @SerializedName("phone") private String phone;
            @SerializedName("name") private String name;
            @SerializedName("name_image") private String nameImage;
            @SerializedName("regedit_time") private String regeditTime;
            @SerializedName("login_time") private String loginTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLoginId() {
                return loginId;
            }

            public void setLoginId(int loginId) {
                this.loginId = loginId;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNameImage() {
                return nameImage;
            }

            public void setNameImage(String nameImage) {
                this.nameImage = nameImage;
            }

            public String getRegeditTime() {
                return regeditTime;
            }

            public void setRegeditTime(String regeditTime) {
                this.regeditTime = regeditTime;
            }

            public String getLoginTime() {
                return loginTime;
            }

            public void setLoginTime(String loginTime) {
                this.loginTime = loginTime;
            }
        }
    }
}
