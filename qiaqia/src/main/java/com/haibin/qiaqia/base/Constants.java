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
package com.haibin.qiaqia.base;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final String JIANGUO_TEST="http://192.168.1.233/Qiaqia_Server/";
    public static final String JIANGUO_FACTORY="http://101.200.197.237:8080/";
    public static final String JIANGUO_USING=JIANGUO_FACTORY;
    public static final String LOGIN_WQ = JIANGUO_USING+"T_user_login_Insert_QQWX_Servlet";//QQ微信登录
    public static final String CHECK_PHONE = JIANGUO_USING+"T_user_login_Check_Tel_Servlet";//检查有没有该手机号
    public static final String GET_SMS= JIANGUO_USING+"I_SMS_Servlet";//获取短信
    public static final String REGISTER_PHONE =JIANGUO_USING+ "I_user_login_Insert_Servlet";// 手机号注册

    public static final String GET_QINIU_TOKEN= JIANGUO_USING+"T_QiNiu_Servlet";//七牛token 接口

    public static final String ONLY_PART1 = "j39dkjhk12934dsn73b";
    public static final String ONLY_PART2 = "kh49032njkd38892nfw";

    public static final String GET_SCHOOL= JIANGUO_USING+"T_school_LikeName_Servlet";//学校模糊查询 接口

    public static final String POST_REAL_NAME= JIANGUO_USING+"T_user_realname_Insert_Servlet";//实名认证
    public static final String GET_REAL_NAME= JIANGUO_USING+"T_user_realname_SelectId_Servlet";//查看实名
    public static final String CHANGE_REAL_NAME= JIANGUO_USING+"T_user_realname_Update_Servlet";//修改认证信息

//    public static final String POST_RESUME= JIANGUO_USING+"T_user_resume_Insert_Servlet";//录入简历
    public static final String GET_RESUME= JIANGUO_USING+"T_user_resume_SelectId_Servlet";//查看简历
    public static final String CHANGE_RESUME= JIANGUO_USING+"T_user_resume_Update_Servlet";//修改简历
    public static final String GET_JOB= JIANGUO_USING+"T_job_List_Servlet";//兼职列表获取
    public static final String GET_JOB_DAY= JIANGUO_USING+"T_job_List_Day_Servlet";//日周月兼职列表获取
    public static final String GET_JOB_CATEGORY= JIANGUO_USING+"T_job_List_Filter_Servlet";//日周月兼职列表获取

    public static final String POST_ATTENT= JIANGUO_USING+"T_attent_Insert_Servlet";//关注收藏接口
    public static final String GET_ATTENT= JIANGUO_USING+"T_attent_Select_Servlet";//关注收藏获
    public static final String GET_JOB_DETAIL= JIANGUO_USING+"T_Job_info_Select_JobId_Servlet";//兼职详情获取
    public static final String DELETE_COLLATTEN= JIANGUO_USING+"T_attent_Delete_Id_Servlet";//删除收藏关注

    public static final String GET_CITY= JIANGUO_USING+"T_city_Select_Servlet";//城市和轮播图
    public static final String POST_SIGN= JIANGUO_USING+"T_enroll_Insert_Servlet";//兼职报名
    public static final String POST_LOOK_JOB= JIANGUO_USING+"T_job_Look_Servlet";//浏览兼职记录接口
    public static final String GET_SIGN_JOB= JIANGUO_USING+"T_enroll_User_Servlet";//浏览兼职记录接口
    public static final String POST_AGREE_JOB= JIANGUO_USING+"T_enroll_Agree_Servlet";//确认参加兼职
    public static final String POST_STATUS= JIANGUO_USING+"T_enroll_Offer_Servlet";//改变状态接口
    public static final String GET_WAGES_INFO= JIANGUO_USING+"T_wages_User_Get_Servlet";//用户收益明细
    public static final String GET_BALANCE_INFO= JIANGUO_USING+"T_user_money_LoginId_Servlet";//用户余额查询
    public static final String POST_PAY_PASSWORD= JIANGUO_USING+"T_user_money_Password_Servlet";//用户修改支付密码
    public static final String POST_ALIPAY_INFO= JIANGUO_USING+"T_user_money_Pay_Servlet";//用户修改支付密码
    public static final String POST_DRAW_MONEY= JIANGUO_USING+"T_user_money_Out_Servlet";//用户提现金额
    public static final String GET_DRAW_INFO= JIANGUO_USING+"T_user_money_OutLook_Servlet";//用户提现信息
    public static final String GET_CITY_CATEGORY= JIANGUO_USING+"T_Job_Area_City_List_Servlet";//地区类型信息 兼职种类
    public static final String GET_USER_CITY_CATEGORY= JIANGUO_USING+"T_Job_Area_City_List_User_Servlet";//地区类型信息 兼职种类
    public static final String POST_OPINION= JIANGUO_USING+"T_opinion_Insert_Servlet";//用户反馈接口

    public static final String USER_LOGIN = "userLogin";
    public static final String LOGIN_PASSWORD="password";
    public static final String LOGIN_PHONE="phone";
    public static final String LOGIN_STATUS="status";
    public static final String LOGIN_TYPE="type";//0登录

    public static final String SP_FIRST="first";//0未点击 1 点击首页 2 点击了编辑界面引导
    public static final String SP_TEL="tel";
    public static final String SP_PASSWORD="password";
    public static final String SP_USERID="id";
    public static final String SP_STATUS="status";
    public static final String SP_WQTOKEN="qqwx_token";
    public static final String SP_RESUMM="resume";
    public static final String SP_QNTOKEN="qn_token";

    public static final String USER_INFO = "userInfo";
    public static final String INFO_ID="loginid";
    public static final String INFO_NAME="name";
    public static final String INFO_IMG="nameImage";
    public static final String SP_SCHOOL="school";
    public static final String SP_INTEGRAL="integral";
    public static final String SP_CREDIT="credit";

    private static final String LEANMESSAGE_CONSTANTS_PREFIX = "com.leancloud.im.guide";
    public static final String IMG_PATH = Environment.getExternalStorageDirectory() + File.separator + "jianguo"+ File.separator;
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
