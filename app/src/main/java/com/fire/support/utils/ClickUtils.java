package com.fire.support.utils;

import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

public class ClickUtils {

    private final static String TAG = "ClickUtils";
    private static Map<Integer, Long> clickRecord = new HashMap<>();
    private static long lastClickTimeMillis = 0;

    /**
     * 检测快速点击
     *
     * @return
     */
    public static boolean checkFastClick() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTimeMillis < 2000) {
            KLog.d(TAG, "click too fast.");
            return true;
        }
        lastClickTimeMillis = currentTimeMillis;
        return false;
    }

    /**
     * 检测列表快速点击
     *
     * @param position 位置
     * @return
     */
    public static boolean checkFastClick(int position) {
        long currentTimeMillis = System.currentTimeMillis();
        long lastTimeMillis = (null == clickRecord.get(position)) ? 0 : clickRecord.get(position);

        if (currentTimeMillis - lastTimeMillis < 2000) {
            KLog.d(TAG, "click too fast.");
            return true;
        }
        clickRecord.put(position, currentTimeMillis);
        return false;
    }

    /**
     * 检测快速点击
     *
     * @param interval 间隔
     * @return
     */
    public static boolean checkFastClick(long interval) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTimeMillis < interval) {
            KLog.d(TAG, "click too fast.");
            return true;
        }
        lastClickTimeMillis = currentTimeMillis;
        return false;
    }
}
