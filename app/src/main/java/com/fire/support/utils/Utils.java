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
import android.widget.ProgressBar;

import com.fire.support.R;
import com.socks.library.KLog;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

public class Utils {


    /**
     * 切换activity
     *
     * @param v      View
     * @param act    Activity
     * @param intent Intent
     * @param type   0 normal 1  fabin 2 up 3 Scale
     */
    private static void start2Activity(View v, Context act, Intent intent, int type) {
        if (act == null) {
            return;
        }

        try {
            noMultiClick(v);


            act.startActivity(intent);


            if (act instanceof Activity) {
                Activity activity = (Activity) act;
                switch (type) {

                    case 0:
                        // left动画移动方向向左

                        activity.overridePendingTransition(R.anim.activity_switch_push_left_in,
                                R.anim.activity_switch_push_left_out);
                        break;

                    case 1:
                        activity.overridePendingTransition(R.anim.anima_alpha_share_in,
                                R.anim.anima_alpha_share_out);
                        break;


                    case 2:
                        activity.overridePendingTransition(R.anim.activity_switch_push_up_in,
                                R.anim.activity_switch_push_up_out);
                        break;


                    case 3:
                        activity.overridePendingTransition(R.anim.unzoom_in,
                                R.anim.unzoom_out);
                        break;
                    case 4:
                        activity.overridePendingTransition(R.anim.activity_switch_push_left_in,
                                R.anim.activity_switch_push_no_change);
                        break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }


    /**
     * activity切换动画 左右
     *
     * @param v      View
     * @param act    Activity
     * @param intent Intent
     */
    public static void startActivity(View v, Context act, Intent intent) {

        start2Activity(v, act, intent, 0);

    }

    /**
     * activity切换动画 淡入淡出
     *
     * @param v      View
     * @param act    Activity
     * @param intent Intent
     */
    public static void startActivityFabIn(View v, Context act, Intent intent) {


        start2Activity(v, act, intent, 1);

    }

    /**
     * activity切换动画 上下
     *
     * @param act    Activity
     * @param intent Intent
     */
    public static void startActivityUp(View v, Context act, Intent intent) {
        start2Activity(v, act, intent, 2);

    }

    /**
     * activity切换动画 覆盖
     *
     * @param act    Activity
     * @param intent Intent
     */
    public static void startActivityCover(View v, Context act, Intent intent) {
        start2Activity(v, act, intent, 4);

    }


    /**
     * activity切换动画 上下
     *
     * @param act    Activity
     * @param intent Intent
     */
    public static void startActivityUpForResult(View v, Context act, Intent intent, int requestCode) {


        noMultiClick(v);

        if (act instanceof Activity) {
            Activity activity = (Activity) act;
            activity.startActivityForResult(intent, requestCode);
            activity.overridePendingTransition(R.anim.activity_switch_push_up_in,
                    R.anim.activity_switch_push_up_out);
        } else {
            act.startActivity(intent);
        }

    }

    /**
     * activity切换动画 缩放
     *
     * @param act    Activity
     * @param intent Intent
     */
    public static void startActivityScale(View v, Context act, Intent intent) {

        start2Activity(v, act, intent, 3);
    }


    /**
     * activity切换动画
     *
     * @param act         Activity
     * @param intent      Intent
     * @param requestCode int
     */
    public static void startActivityForResult(View v, Context act, Intent intent, int requestCode) {


        noMultiClick(v);

        if (act instanceof Activity) {
            Activity activity = (Activity) act;
            activity.startActivityForResult(intent, requestCode);
            activity.overridePendingTransition(R.anim.activity_switch_push_left_in,
                    R.anim.activity_switch_push_left_out);
        } else {
            act.startActivity(intent);
        }

    }


    /**
     * 销毁活动页
     *
     * @param act  Activity
     * @param type int
     */
    private static void finishActivity(Activity act, int type) {
        if (act == null) {
            return;
        }
        act.finish();
        switch (type) {

            case 0:
                act.overridePendingTransition(R.anim.activity_switch_push_right_in,
                        R.anim.activity_switch_push_right_out);
                break;


            case 1:
                act.overridePendingTransition(R.anim.anima_alpha_share_in,
                        R.anim.anima_alpha_share_out);
                break;

            case 2:

                act.overridePendingTransition(R.anim.activity_switch_push_down_in,
                        R.anim.activity_switch_push_down_out);
                break;
            case 3:
                act.overridePendingTransition(R.anim.zoom_enter,
                        R.anim.zoom_exit);
                break;
            case 4:
                act.overridePendingTransition(R.anim.activity_switch_push_no_change,
                        R.anim.activity_switch_push_right_out);
                break;
        }


    }

    /**
     * activity销毁动画
     *
     * @param act Activity 左右
     */
    public static void finish(Activity act) {

        finishActivity(act, 0);


    }

    /**
     * activity销毁动画 淡出
     *
     * @param act Activity
     */
    public static void finishFabin(Activity act) {
        finishActivity(act, 1);
    }

    /**
     * activity销毁动画 向下
     *
     * @param act Activity
     */
    public static void finishUp(Activity act) {

        finishActivity(act, 2);

    }


    /**
     * activity销毁动画 缩放
     *
     * @param act Activity
     */
    public static void finishScale(Activity act) {
        finishActivity(act, 3);


    }

    /**
     * activity销毁动画 覆盖
     *
     * @param act Activity
     */
    public static void finishCover(Activity act) {
        finishActivity(act, 4);


    }

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

    /**
     * 缓存文件夹
     *
     * @param context Context
     * @return File
     */
    public static File getCacheDir(Context context) {
        File file = null;
        try {
            file = context.getExternalCacheDir();
        } catch (Throwable e) {

            e.printStackTrace();
        }


        boolean isSuccess = false;

        if (file != null && file.canRead() && file.canWrite()) {

            isSuccess = true;

        }

        if (!isSuccess) {

            file = context.getCacheDir();
        }


        return file;
    }

    /**
     *  反射设定RecyclerView最大滑动速度
     */
    public static void setMaxFlingVelocity(RecyclerView recyclerView, int velocity) {
        try{
            Field field = recyclerView.getClass().getDeclaredField("mMaxFlingVelocity");
            field.setAccessible(true);
            field.set(recyclerView, velocity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 获取随机值
     *
     * @param min int
     * @param max int
     * @return int
     */
    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }


    /**
     * 设置ProgressBar颜色
     *
     * @param pb    ProgressBar
     * @param color int
     */
    public static void setProgressColor(ProgressBar pb, int color) {

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorStateList stateList = ColorStateList.valueOf(color);
                pb.setProgressTintList(stateList);
                pb.setSecondaryProgressTintList(stateList);
                pb.setIndeterminateTintList(stateList);
            } else {
                //                PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                //                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                //                    mode = PorterDuff.Mode.MULTIPLY;
                //                }
                //                if (pb.getIndeterminateDrawable() != null)
                //                    pb.getIndeterminateDrawable().setColorFilter(color, mode);
                //                if (pb.getProgressDrawable() != null)
                //                    pb.getProgressDrawable().setColorFilter(color, mode);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }




}
