/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.woniukeji.jianmerchant.base;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final String JIANGUO_FACTORY="http://192.168.1.132/JianGuo_Server/";
    public static final String JIANGUO_TEST="http://192.168.1.233/JianGuo_Server/";
    public static final String JIANGUO_TEST2="http://v3.jianguojob.com:8080/";
    public static final String JIANGUO_USING=JIANGUO_FACTORY;
    public static final String LOGIN= JIANGUO_USING+"T_user_login_Login_Merchant_Servlet";//
    public static final String REC_SMS= JIANGUO_USING+"T_user_login_Check_BackTel_Servlet";//忘记密码 快速登录
    public static final String GET_CITY_CATEGORY= JIANGUO_USING+"T_Job_Area_City_List_Servlet";//地区类型信息 兼 职种类
    public static final String POST_PART_INFO= JIANGUO_USING+"T_Job_Insert_Servlet";//提交兼职信息
    public static final String GET_PART_HISTORY= JIANGUO_USING+"T_job_Model_List_Servlet";//获取兼职历史记录
    public static final String DELETE_JOB_MODEL= JIANGUO_USING+"T_job_Model_Delete_Servlet";//删除兼职模板
    public static final String GET_PART_JOB_PUBLISH= JIANGUO_USING+"T_job_Merchant_Id_Zhong_Servlet";//获取发布的兼职列表

    public static final String POST_PART_JOB_WAGES= JIANGUO_USING+"T_wages_Insert_ChangServlet";//jeisuan
    public static final String POST_PART_JOB_DOWN= JIANGUO_USING+"T_enroll_Agree_Chang_Servlet";//下架




    public static final String CHECK_PHONE = JIANGUO_USING+"T_user_login_Check_Tel_Servlet";//检查有没有该手机号

    public static final String REGISTER_PHONE =JIANGUO_USING+ "T_user_login_Insert_Servlet";// 手机号注册

    public static final String GET_QINIU_TOKEN= JIANGUO_USING+"T_QiNiu_Servlet";//七牛token 接口

    public static final String LOGIN_PHONE =JIANGUO_USING+ "T_user_login_Login_Tel_Servlet";//手机号密码登录
    public static final String LOGIN_QUICK =JIANGUO_USING+ "T_user_login_FastLogin_Servlet";//手机号快速登录
    public static final String CHANGE_PASSWORD =JIANGUO_USING+ "T_user_login_Update_Psd_Servlet";//修改密码
    public static final String CHECK_PHONE_BLACK= JIANGUO_USING+"T_user_login_Check_BackTel_Servlet";//快速登录忘记密码
    public static final String POST_BIND_PHONE= JIANGUO_USING+"T_user_login_BindingTel_Servlet";//绑定手机号
    public static final String ONLY_PART1 = "xse2iowiowdg3542d49z";
    public static final String ONLY_PART2 = "jfiejdw4gdeqefw33ff23fi999";

    public static final String GET_SCHOOL= JIANGUO_USING+"T_school_LikeName_Servlet";//学校模糊查询 接口

    public static final String POST_REAL_NAME= JIANGUO_USING+"T_user_realname_Insert_Servlet";//实名认证
    public static final String GET_REAL_NAME= JIANGUO_USING+"T_user_realname_SelectId_Servlet";//查看实名
    public static final String CHANGE_REAL_NAME= JIANGUO_USING+"T_user_realname_Update_Servlet";//修改认证信息

//    public static final String POST_RESUME= JIANGUO_USING+"T_user_resume_Insert_Servlet";//录入简历
    public static final String GET_RESUME= JIANGUO_USING+"T_user_resume_SelectId_Servlet";//查看简历
    public static final String CHANGE_RESUME= JIANGUO_USING+"T_user_resume_Update_Servlet";//修改简历
    public static final String GET_JOB= JIANGUO_USING+"T_job_List_Servlet";//兼职列表获取
    public static final String GET_JOB_DETAIL= JIANGUO_USING+"T_Job_info_Select_JobId_Servlet";//兼职详情获取
    public static final String GET_ADMIT_LIST= JIANGUO_USING+"T_enroll_Job_Servlet";//报名列表
    public static final String POST_ADMIT= JIANGUO_USING+"T_enroll_Offer_Servlet";//商家录取
    public static final String POST_DOWN= JIANGUO_USING+"T_enroll_Agree_Servlet";//下架或者结束兼职
    public static final String GET_SINGLE_JOB_INFO= JIANGUO_USING+"T_job_Id_Servlet";//查询单条目兼职信息
    public static final String POST_JOB_INFO= JIANGUO_USING+"T_job_Update_Servlet";//修改兼职信息
    public static final String GET_PAY_LIST= JIANGUO_USING+"T_enroll_Job_User_Servlet";//获取发放工资用用户列表
    public static final String POST_WAGES= JIANGUO_USING+"T_wages_Insert_Servlet";//工资结算接口
    public static final String POST_PAY_PASSWORD= JIANGUO_USING+"T_mechant_pay_Update_Servlet";//结算密码

    public static final String GET_CITY= JIANGUO_USING+"T_city_Select_Servlet";//城市和轮播图





    public static final String LOGIN_INFO = "loginInfo";
    public static final String SP_TYPE="type";//0代表手机登录 1微信 2未登录
    public static final String SP_TEL="tel";
    public static final String SP_PASSWORD="password";
    public static final String SP_USERID="id";
    public static final String SP_STATUS="status";
    public static final String SP_WQTOKEN="qqwx_token";

    public static final String SP_QNTOKEN="qn_token";

    public static final String USER_INFO = "userInfo";
    public static final String USER_NICK ="nickname";
    public static final String USER_NAME ="name";
    public static final String USER_IMG ="name_image";
    public static final String SP_SCHOOL="school";
    public static final String SP_INTEGRAL="integral";
    public static final String SP_CREDIT="credit";

    public static final String USER_MERCHANT_ID ="merchantid";
    public static final String USER_PAY_PASS ="pay_password";
    private static final String LEANMESSAGE_CONSTANTS_PREFIX = "com.leancloud.im.guide";
    public static final String IMG_PATH = Environment.getExternalStorageDirectory() + File.separator + "jianguoMer"+ File.separator;
    public static final String UPDATED_AT = "updatedAt";

    /*

        leancloud消息协议
        creatimg    string   创建者头像
        otherimg    string   被邀请者头像
        creatname   string   创建者名字
        othername   string   被邀请者名字
        ctype        int     对话类型：私人对话0，提醒1，通知2，群聊3

     */
    public static final String CREAT_IMG = "creatimg";
    public static final String CREAT_NAME = "creatname";
    public static final String OTHER_IMG = "otherimg";
    public static final String OTHER_NAME = "othername";
    public static final String C_TYPE = "ctype";


}
