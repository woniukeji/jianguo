package com.haibin.qiaqia.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by invinjun on 2016/3/4.
 */
public class CommonUtils {


    //判断号码是否合法
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((17[6-7])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }
    //当前时间命名文件
    public static String generateFileName() {
        // 获得当前时间
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // 转换为字符串
        String formatDate = format.format(new Date());
        // 随机生成文件编号
        int random = new Random().nextInt(10000);
        return new StringBuffer().append(formatDate).append(
                random).toString();
    }
}
