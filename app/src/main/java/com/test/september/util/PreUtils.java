package com.test.september.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KUNLAN
 * on 2019-03-26
 */
public class PreUtils {

    private static final String SHARE_PREFS_NAME = "itheima";

    public static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getBoolean(key, defaultValue);
    }

    public static void putString(Context ctx, String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getString(key, defaultValue);
    }

    public static void putInt(Context ctx, String key, int value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getInt(key, defaultValue);
    }

//    public static void putBoolean(String key, boolean value, Context ctx) {
//        SharedPreferences sp = ctx.getSharedPreferences("config",
//                Context.MODE_PRIVATE);
//        sp.edit().putBoolean(key, value).apply();
//    }
//
//
//    public static boolean getBoolean(String key, boolean defValue, Context ctx) {
//        SharedPreferences sp = ctx.getSharedPreferences("config",
//                Context.MODE_PRIVATE);
//        return sp.getBoolean(key, defValue);
//    }
//
//
//    public static void putString(String key, String value, Context ctx) {
//        SharedPreferences sp = ctx.getSharedPreferences("config",
//                Context.MODE_PRIVATE);
//        sp.edit().putString(key, value).apply();
//    }
//
//
//    public static String getString(String key, String defValue, Context ctx) {
//        SharedPreferences sp = ctx.getSharedPreferences("config",
//                Context.MODE_PRIVATE);
//        return sp.getString(key, defValue);
//    }
//
//
//    public static void putInt(String key, int value, Context ctx) {
//        SharedPreferences sp = ctx.getSharedPreferences("config",
//                Context.MODE_PRIVATE);
//        sp.edit().putInt(key, value).apply();
//    }
//
//
//    public static int getInt(String key, int defValue, Context ctx) {
//        SharedPreferences sp = ctx.getSharedPreferences("config",
//                Context.MODE_PRIVATE);
//        return sp.getInt(key, defValue);
//    }

//
//    public static void remove(String key, Context ctx) {
//        SharedPreferences sp = ctx.getSharedPreferences("config",
//                Context.MODE_PRIVATE);
//        sp.edit().remove(key).apply();
//    }
}
