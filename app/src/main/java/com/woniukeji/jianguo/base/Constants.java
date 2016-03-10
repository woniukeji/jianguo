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
package com.woniukeji.jianguo.base;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final String JIANGUO_FACTORY="http://192.168.1.233/JianGuo_Server/";
    public static final String JIANGUO_TEST="http://192.168.1.233/JianGuo_Server/";
    public static final String JIANGUO_TEST2="http://101.200.205.243:8080/";
    public static final String JIANGUO_USING=JIANGUO_TEST2;
    public static final String LOGIN_WQ = JIANGUO_USING+"T_user_login_Insert_QQWX_Servlet";//QQ微信登录
    public static final String CHECK_PHONE = JIANGUO_USING+"T_user_login_Check_Tel_Servlet";//检查有没有该手机号
    public static final String REC_SMS= JIANGUO_USING+"T_Text_Sms_T_user_login_Insert_ServletServlet";//获取短信
    public static final String REGISTER_PHONE =JIANGUO_USING+ "T_user_login_Insert_Servlet";// 手机号注册

    public static final String GET_QINIU_TOKEN= JIANGUO_USING+"T_QiNiu_Servlet";//七牛token 接口

    public static final String LOGIN_PHONE =JIANGUO_USING+ "T_user_login_Login_Tel_Servlet";//手机号密码登录
    public static final String LOGIN_QUICK =JIANGUO_USING+ "T_user_login_FastLogin_Servlet";//手机号快速登录
    public static final String CHANGE_PASSWORD =JIANGUO_USING+ "T_user_login_Update_Psd_Servlet";//修改密码
    public static final String CHECK_PHONE_BLACK= JIANGUO_USING+"T_user_login_Check_BackTel_Servlet";//快速登录忘记密码
    public static final String ONLY_PART1 = "xse2iowiowdg3542d49z";
    public static final String ONLY_PART2 = "jfiejdw4gdeqefw33ff23fi999";

    public static final String POST_REAL_NAME= JIANGUO_USING+"T_user_realname_Insert_Servlet";//实名认证
    public static final String GET_REAL_NAME= JIANGUO_USING+"T_user_realname_SelectId_Servlet";//查看实名
    public static final String CHANGE_REAL_NAME= JIANGUO_USING+"T_user_realname_Update_Servlet";//修改认证信息

    public static final String POST_RESUME= JIANGUO_USING+"T_user_resume_Insert_Servlet";//录入简历
    public static final String GET_RESUME= JIANGUO_USING+"T_user_resume_SelectId_Servlet";//查看简历
    public static final String CHANGE_RESUME= JIANGUO_USING+"T_user_resume_Update_Servlet";//修改简历

    public static final String SP_LOGIN = "loginInfo";
    public static final String SP_TYPE="type";//0代表手机登录 1微信 2未登录
    public static final String SP_TEL="tel";
    public static final String SP_PASSWORD="password";
    public static final String SP_USERID="id";
    public static final String SP_STATUS="status";
    public static final String SP_WQTOKEN="qqwx_token";

    public static final String SP_QNTOKEN="qn_token";

    public static final String SP_USER = "userInfo";
    public static final String SP_NICK="nickname";
    public static final String SP_NAME="name";
    public static final String SP_IMG="name_image";
    public static final String SP_SCHOOL="school";
    public static final String SP_INTEGRAL="integral";
    public static final String SP_CREDIT="credit";

    private static final String LEANMESSAGE_CONSTANTS_PREFIX = "com.leancloud.im.guide";
    public static final String IMG_PATH = Environment.getExternalStorageDirectory() + File.separator + "jianguo"+ File.separator;


}
