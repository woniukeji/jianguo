package com.woniukeji.jianguo.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by invinjun on 2016/3/4.
 */
public class CommonUtils {
    //判断号码是否合法
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

}
