package com.haibin.qiaqia.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


import com.haibin.qiaqia.base.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

/**
 * Created by invinjun on 2015/5/26.
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
//    private static final String FILE_NAME = "userInfo";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param fileName
     * @param key
     * @param object
     */
//    public static void setParam(Context context, String fileName, String key, Object object) {
//
//        String type = object.getClass().getSimpleName();
//        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//
//        if ("String".equals(type)) {
//            editor.putString(key, (String) object);
//        } else if ("Integer".equals(type)) {
//            editor.putInt(key, (Integer) object);
//        } else if ("Boolean".equals(type)) {
//            editor.putBoolean(key, (Boolean) object);
//        } else if ("Float".equals(type)) {
//            editor.putFloat(key, (Float) object);
//        } else if ("Long".equals(type)) {
//            editor.putLong(key, (Long) object);
//        }
//
//        editor.commit();
//    }
    public static void setParam(Context context, String fileName, String key, Boolean object) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, (Boolean) object);
        editor.commit();
    }
    public static void setParam(Context context, String fileName, String key, String object) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, (String) object);
        editor.commit();
    }
    public static void setParam(Context context, String fileName, String key, Integer object) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, (Integer) object);
        editor.commit();
    }
    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param fileName      sp的名称
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String fileName, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    public static void deleteParams(Context context) {

        SharedPreferences sp = context.getSharedPreferences(Constants.USER_LOGIN, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
        SharedPreferences sp1 = context.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
        sp1.edit().clear().commit();
    }

    /**
     * list to sp
     *
     * @param SceneList
     * @return
     * @throws IOException
     */
    public static String SceneList2String(List SceneList)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    /**
     * string to list
     *
     * @param SceneListString
     * @return
     * @throws StreamCorruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List String2SceneList(String SceneListString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        List SceneList = (List) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }

    /**
     * 保存sp数据
     */
    public static void setListSP(Context context, String spName, String key, List SceneList) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        try {
            String liststr = SceneList2String(SceneList);
            edit.putString(key, liststr);
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取list sp数据
     */
    public static Object getListSP(Context context, String fileName, String key, List SceneList) {
        List string2SceneList = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String liststr = sharedPreferences.getString(key, "");
        try {
            string2SceneList = String2SceneList(liststr);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return string2SceneList;
    }

}

