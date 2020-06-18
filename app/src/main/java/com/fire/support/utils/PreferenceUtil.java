package com.fire.support.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.fire.support.App;

import java.util.Set;

/**
 * Preference工具
 */
public class PreferenceUtil {

    private PreferenceUtil() {
    }

    public static SharedPreferences getPreferences(Context context) {

        context = getContext(context);

        return context.getSharedPreferences("PreferenceUtil", Context.MODE_MULTI_PROCESS);

    }

    public static String getString(String key, String defaultValue, Context context) {

        return getPreferences(context).getString(key, defaultValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(String key, Set<String> defaultValue, Context context) {
        return getPreferences(context).getStringSet(key, defaultValue);
    }


    public static int getInt(String key, int defaultValue, Context context) {
        return getPreferences(context).getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue, Context context) {
        return getPreferences(context).getLong(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue, Context context) {
        return getPreferences(context).getFloat(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue, Context context) {
        return getPreferences(context).getBoolean(key, defaultValue);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void putString(String key, String value, Context context) {
        getEditor(context).putString(key, value).apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void putStringSet(String key, Set<String> value, Context context) {
        remove(key, context);
        getEditor(context).putStringSet(key, value).commit();
    }


    public static void putInt(String key, int value, Context context) {
        getEditor(context).putInt(key, value).apply();
    }

    public static void putLong(String key, long value, Context context) {
        getEditor(context).putLong(key, value).apply();
    }

    public static void putFloat(String key, float value, Context context) {
        getEditor(context).putFloat(key, value).apply();
    }

    public static void putBoolean(String key, boolean value, Context context) {
        getEditor(context).putBoolean(key, value).apply();
    }

    public static void remove(String key, Context context) {
        getEditor(context).remove(key).apply();
    }

    public static void clear(Context context) {
        getEditor(context).clear().apply();
    }


    public static Context getContext(Context context) {

        if (context == null) {
            context = App.getInstance().getApplicationContext();
        }

        return context.getApplicationContext();

    }


}
