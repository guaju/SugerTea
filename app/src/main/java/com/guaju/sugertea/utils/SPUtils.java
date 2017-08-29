package com.guaju.sugertea.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by guaju on 2017/8/29.
 */

public class SPUtils {
    private static volatile SPUtils spUtils;
    private final SharedPreferences sp;
    private String name;
    private final SharedPreferences.Editor editor;

    private SPUtils(Context context, String name) {
        this.name = name;
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SPUtils getInstance(Context context, String name) {
        if (spUtils == null || (!spUtils.name.equals(name))) {
            synchronized (SPUtils.class) {
                if (spUtils == null || (!spUtils.name.equals(name))) {
                    spUtils = new SPUtils(context, name);
                }
            }
        }
        return spUtils;
    }

    public void putSp(String key, Object obj) {
        //判断要存的数据类型是什么类型的
        if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        }
        if (obj instanceof String) {
            editor.putString(key, (String) obj);
        }
        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        }
        if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        }
        if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        }
        //事务 事务只有在commit之后，这个事务才会真正的终止，commit 就是事务的特点
        editor.commit();
    }

    public Object getSp(String key, Class clazz) {
        if (sp == null) {
            return null;
        }
        if (clazz == Integer.class) {
            int anInt = sp.getInt(key, 0);
            return anInt;
        }
        if (clazz == String.class) {
            String string = sp.getString(key, "");
            return string;
        }
        if (clazz == Boolean.class) {
            boolean aBoolean = sp.getBoolean(key, false);
            return aBoolean;
        }
        if (clazz == Float.class) {
            float aFloat = sp.getFloat(key, 0.0f);
            return aFloat;
        }
        if (clazz == Long.class) {
            long aLong = sp.getLong(key, 0);
            return aLong;
        } else {


            return null;
        }
    }
}
