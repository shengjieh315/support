package com.fire.support.utils;

import android.net.TrafficStats;

import java.text.DecimalFormat;

public class NetSpeedUtil {

    private static long lastTotalRxBytes = 0;
    private static long lastTimeStamp = 0;

    /**
     * @param uid getApplicationInfo().uid
     * @description 获取当前网速
     */
    public static String getNetSpeed(int uid) {
        long speed = 0;
        try {
            long nowTotalRxBytes = getTotalRxBytes(uid);
            long nowTimeStamp = System.currentTimeMillis();
            speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换 kb/s
            lastTimeStamp = nowTimeStamp;
            lastTotalRxBytes = nowTotalRxBytes;
        } catch (Exception e) {

        }

        return getKMGUnitStr(speed) + "/S";
    }

    /**
     * @param uid getApplicationInfo().uid
     */
    private static long getTotalRxBytes(int uid) {
        return TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);
    }

    public static String getKMGUnitStr(long kb) {
        DecimalFormat showFloatFormat = new DecimalFormat("####.0");
        double mb = 1024;
        double gb = mb * 1024;
        if (kb >= gb) {
            return showFloatFormat.format(kb / gb) + "GB";
        } else {
            if (kb >= mb) {
                return showFloatFormat.format(kb / mb) + "MB";
            } else {
                return kb + "KB";
            }
        }
    }

    public static String getKMGUnitStrByb(long b) {
        DecimalFormat showFloatFormat = new DecimalFormat("####.0");
        double kb = 1024;
        double mb = kb * 1024;
        double gb = mb * 1024;
        if (b >= gb) {
            return showFloatFormat.format(b / gb) + "G";
        } else {
            if (b >= mb) {
                return showFloatFormat.format(b / mb) + "M";
            } else {
                if (b >= kb) {
                    return showFloatFormat.format(b / kb) + "K";
                } else {
                    if (b == 0) {
                        return b + "M";
                    } else {
                        return b + "BYTE";
                    }
                }
            }
        }
    }

}
