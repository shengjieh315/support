package com.fire.support.utils.screen;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.fire.support.App;

/**
 * Created by zhy on 15/11/18.
 */
public class AutoLayoutConfig {

    private static AutoLayoutConfig sInstance = new AutoLayoutConfig();


    private int mScreenWidth;
    private int mScreenHeight;


    private AutoLayoutConfig() {

        int[] screenSize = getScreenSize();
        if (screenSize[0] < screenSize[1]) {
            mScreenWidth = screenSize[0];
            mScreenHeight = screenSize[1];
        } else {
            mScreenWidth = screenSize[1];
            mScreenHeight = screenSize[0];
        }

    }

    public void reset() {
        int[] screenSize = getScreenSize();
        if (screenSize[0] < screenSize[1]) {
            mScreenWidth = screenSize[0];
            mScreenHeight = screenSize[1];
        } else {
            mScreenWidth = screenSize[1];
            mScreenHeight = screenSize[0];
        }

    }

    public static AutoLayoutConfig getInstance() {
        return sInstance;
    }


    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }


    public int[] getScreenSize() {

        int[] size = new int[2];
        int widthPixels = 0;
        int heightPixels = 0;
        Display d = null;
        try {
            WindowManager w = (WindowManager) App.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            d = w.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            d.getMetrics(metrics);

            widthPixels = metrics.widthPixels;
            heightPixels = metrics.heightPixels;
        } catch (Throwable e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Throwable ignored) {
            }
        }

        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Throwable ignored) {
            }
        }


        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

}
