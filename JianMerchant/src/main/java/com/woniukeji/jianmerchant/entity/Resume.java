package com.woniukeji.jianmerchant.entity;

/**
 * Created by invinjun on 2016/3/8.

 用户简历表
 t_user_resume
 id       ID
 login_id 用户登录表关联ID
 nickname 昵称
 name	     姓名
 name_image   头像
 school       学校
 intoschool_date   入学时间
 sex          性别（int型：0=女，1=男）
 height       身高（int型）
 student      学生（int型：0=不是学生，1=是学生）
 birth_date   出生日期
 shoe_size    鞋码
 clothing_size 服装尺码
 sign         个性签名
 label        标签
 */
public class Resume {
    public UserResum getT_user_resume() {
        return t_user_resume;
    }

    public void setT_user_resume(UserResum t_user_resume) {
        this.t_user_resume = t_user_resume;
    }

    public UserResum t_user_resume;
    public class UserResum{

        private String id;
        private String nickname;
        private String name;
        private String intoschool_date;
        private String sex;
        private String height;
        private String student;
        private String school;
        private String name_image;
        private String birth_date;
        private String shoe_size;
        private String clothing_size;
        private String sign;
        private String label;


        public String getName_image() {
            return name_image;
        }

        public void setName_image(String name_image) {
            this.name_image = name_image;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntoschool_date() {
            return intoschool_date;
        }

        public void setIntoschool_date(String intoschool_date) {
            this.intoschool_date = intoschool_date;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getStudent() {
            return student;
        }

        public void setStudent(String student) {
            this.student = student;
        }

        public String getBirth_date() {
            return birth_date;
        }

        public void setBirth_date(String birth_date) {
            this.birth_date = birth_date;
        }

        public String getShoe_size() {
            return shoe_size;
        }

        public void setShoe_size(String shoe_size) {
            this.shoe_size = shoe_size;
        }

        public String getClothing_size() {
            return clothing_size;
        }

        public void setClothing_size(String clothing_size) {
            this.clothing_size = clothing_size;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

}
