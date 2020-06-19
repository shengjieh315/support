package com.fire.support.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.fire.support.R;
import com.socks.library.KLog;

import java.util.List;

import androidx.core.graphics.drawable.DrawableCompat;

public class Utils {


    /**
     * activity是否在顶层
     *
     * @param activity Activity
     * @return boolean
     */
    public static boolean isTopActivity(Activity activity) {
        boolean isTop = false;
        ActivityManager am = (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        KLog.d("isTopActivity = " + cn.getClassName());
        if (cn.getClassName().contains(activity.getComponentName().getClassName())) {
            isTop = true;
        }
        KLog.d("isTop = " + isTop);
        return isTop;
    }


    /**
     * 判断应用是否在后台
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isAppInBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return true;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }


    /**
     * list转成string
     *
     * @param tags List<String>
     * @return String
     */
    public static String tag2String(List<String> tags) {

        String tagStr = "";
        for (String tag : tags) {

            if (TextUtils.isEmpty(tagStr)) {

                tagStr += tag;
            } else {
                tagStr += "," + tag;
            }

        }

        return tagStr;
    }


    /**
     * 选择打开url的浏览器
     *
     * @param context Context
     * @param url     String
     */
    public static void openWeb(Context context, String url) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(url));
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.open)));
    }


    /**
     * 改变Drawable颜色
     *
     * @param drawable Drawable
     * @param color    int
     * @return Drawable
     */
    public static Drawable tintDrawable(Drawable drawable, int color) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
        return wrappedDrawable;
    }


    /**
     * 颜色值计算
     *
     * @param fraction   float
     * @param startValue Integer
     * @param endValue   Integer
     * @return Integer
     */
    public static Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                | (int) ((startB + (int) (fraction * (endB - startB))));
    }


    /**
     * 防止多次点击
     *
     * @param view View
     */
    public static void noMultiClick(final View view) {

        if (view == null) {
            return;
        }
        try {
            view.setEnabled(false);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (view.getContext() == null) {
                        return;
                    }
                    view.setEnabled(true);


                }
            }, 500);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    /**
     * 防止多次点击
     *
     * @param view View
     */
    public static void noMultiRequestClick(final View view) {

        if (view == null) {
            return;
        }
        try {
            view.setEnabled(false);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (view.getContext() == null) {
                        return;
                    }
                    view.setEnabled(true);


                }
            }, 2000);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    /**
     * 是否是Android5.0以上
     *
     * @return boolean
     */
    public static boolean isMaxLOLLIPOP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


}
