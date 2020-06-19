package com.fire.support.helper;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.fire.support.App;
import com.fire.support.R;
import com.socks.library.KLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 */
@SuppressLint("SimpleDateFormat")
public class DateHelper {

    private static DateHelper util;

    synchronized public static DateHelper getInstance() {

        if (util == null) {
            util = new DateHelper();
        }
        return util;

    }

    private DateHelper() {
        super();
    }

    private SimpleDateFormat date_Format_Year_Month = new SimpleDateFormat("yyyy.MM");

    private SimpleDateFormat date_Format_Day = new SimpleDateFormat("dd");

    private SimpleDateFormat date_Format_1 = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    private SimpleDateFormat date_Format_2 = new SimpleDateFormat("yyyy.MM.dd");

    private SimpleDateFormat date_Format_3 = new SimpleDateFormat("EE HH:mm");

    public SimpleDateFormat date_Format_4 = new SimpleDateFormat("yyyy.MM.dd EE");

    private SimpleDateFormat date_Format_5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat date_Format_hours = new SimpleDateFormat("HH:mm");

    private SimpleDateFormat date_Format_y = new SimpleDateFormat("yyyy");

    private SimpleDateFormat date_Format_6 = new SimpleDateFormat("yyyy年M月d日");

    private SimpleDateFormat date_Format_7 = new SimpleDateFormat("MM-dd HH:mm");

    private SimpleDateFormat date_Format_8 = new SimpleDateFormat("HH:mm");

    private SimpleDateFormat date_Format_9 = new SimpleDateFormat("MM月dd日");

    private SimpleDateFormat date_Format_10 = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    private SimpleDateFormat date_Format_11 = new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat date_Format_12 = new SimpleDateFormat("MM-dd");

    private SimpleDateFormat date_Format_13 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private SimpleDateFormat date_Format_14 = new SimpleDateFormat("yyyy.MM");

    private SimpleDateFormat date_Format_15 = new SimpleDateFormat("dd");

    private SimpleDateFormat date_Format_16 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private SimpleDateFormat date_Format_17 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");


    public String getMinLong() {

        return System.currentTimeMillis() / (60 * 1000) + "";

    }


    public Date getDate(String dateStr) {
        Date date = new Date();
        if (TextUtils.isEmpty(dateStr)) {
            return date;
        }
        try {
            date = date_Format_1.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return date;

    }

    public String getDataString(Date date, SimpleDateFormat format) {

        if (date == null) {
            date = new Date();
        }
        return format.format(date);


    }

    public String getDataString_1(Date date) {
        if (date == null) {
            date = new Date();
        }
        return date_Format_1.format(date);


    }

    public String getDataString_2(Date date) {
        if (date == null) {
            date = new Date();
        }
        return date_Format_2.format(date);

    }

    public String getDataString_2(long timeMillis) {
        Date date = new Date(timeMillis);

        return date_Format_2.format(date);

    }

    public String getDataString_Y(long timeMillis) {
        Date date = new Date(timeMillis);

        return date_Format_y.format(date);

    }

    public String getDataString_3(Date date) {
        if (date == null) {
            date = new Date();
        }
        return date_Format_3.format(date);


    }

    public String getDataString_5(Date date) {
        if (date == null) {
            date = new Date();
        }
        String str = date_Format_5.format(date);
        return str;

    }

    public String getDataString_6(Date date) {
        if (date == null) {
            date = new Date();
        }
        String str = date_Format_6.format(date);
        return str;

    }

    public String getDataString_7(Date date) {
        if (date == null) {
            date = new Date();
        }
        String str = date_Format_7.format(date);
        return str;

    }

    public String getDataString_8(Date date) {
        if (date == null) {
            date = new Date();
        }
        String str = date_Format_8.format(date);
        return str;

    }

    public String getTimeString10(String time) {
        try {
            Long l = Long.parseLong(time);
            String times = date_Format_10.format(new Date(l));
            return times;
        } catch (Exception e) {
            return time;
        }
    }

    public String getTimeString9(String time) {
        try {
            Long l = Long.parseLong(time);
            String times = date_Format_9.format(new Date(l));
            return times;
        } catch (Exception e) {
            return time;
        }

    }

    public String getTimeString14(long l) {
        try {
            return date_Format_14.format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }

    public String getTimeString15(long l) {
        try {
            return date_Format_15.format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }

    public String getTimeString16(long l) {
        try {
            return date_Format_16.format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }

    public String getTimeString5(long l) {
        try {
            return date_Format_5.format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }

    public String getTimeString6(long l) {
        try {
            return date_Format_6.format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }

    public String getTimeString17(long l) {
        try {
            return date_Format_17.format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }

    public String getTimeString11(long l) {
        try {
            return date_Format_11.format(new Date(l));
        } catch (Exception e) {
            return "";
        }
    }

    public String getDataString_11(Date date) {
        if (date == null) {
            date = new Date();
        }
        return date_Format_11.format(date);
    }

    public String getDataString_12(Date date) {
        if (date == null) {
            date = new Date();
        }
        return date_Format_12.format(date);
    }

    /**
     * 时间规则
     *
     * @param date 日期
     * @return 日期字符串
     */
    public String getClockTimeFormat(long date) {

        String ftime = "";

        Date date1 = new Date(date);
        Date date2 = new Date(System.currentTimeMillis());

        int days = differentDays(date1, date2);

        if (days == 0) {
            ftime = "今天";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days < 10) {
            ftime = days + "天前";
        } else {
            ftime = String.valueOf(date);
        }
        return ftime;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {
            return day2 - day1;
        }
    }

    public String[] getMhtCircleRecentTime(long date) {
        Date time = new Date(date);

        String[] ftime = {"", ""};
        Calendar cal = Calendar.getInstance();

        if (cal.getTimeInMillis() - date < 60000) {
            ftime[0] = App.getInstance().getString(R.string.msg_date_just_now);
            return ftime;
        }

        int days = differentDays(time, cal.getTime());
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime[0] = String.valueOf(Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1));
                ftime[1] = App.getInstance().getString(R.string.msg_date_min_ago_unit);

            } else {
                ftime[0] = String.valueOf(hour);
                ftime[1] = App.getInstance().getString(R.string.msg_date_hours_ago_unit);
            }

        } else if (days == 1) {
            ftime[0] = App.getInstance().getString(R.string.msg_date_yesterday);
        } else if (days == 2) {
            ftime[0] = App.getInstance().getString(R.string.msg_date_before_yesterday);
        } else if (days > 2 && days < 10) {
            ftime[0] = String.valueOf(days);
            ftime[1] = App.getInstance().getString(R.string.msg_date_day_ago_unit);
        } else {
            ftime[0] = date_Format_Day.format(time);
            ftime[1] = date_Format_Year_Month.format(time);
        }
        return ftime;
    }


    public String getRecentTime(long date) {
        Date time = new Date(date);

        String ftime = "";
        Calendar cal = Calendar.getInstance();

        if (cal.getTimeInMillis() - date < 60000) {

            return App.getInstance().getString(R.string.msg_date_just_now);
        }

        int days = differentDays(time, cal.getTime());
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {

                ftime = App.getInstance().getString(R.string.msg_date_min_ago, Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1));
            } else {
                ftime = App.getInstance().getString(R.string.msg_date_hours_ago, hour);

            }

        } else if (days == 1) {
            ftime = App.getInstance().getString(R.string.msg_date_yesterday);
        } else if (days == 2) {
            ftime = App.getInstance().getString(R.string.msg_date_before_yesterday);
        } else if (days > 2 && days < 10) {
            ftime = App.getInstance().getString(R.string.msg_date_day_ago, days);
        } else {
            ftime = date_Format_2.format(time);
        }
        return ftime;
    }


    public String getRecentTimeIM(long date) {
        Date time = new Date(date);
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        if (cal.getTimeInMillis() - date < 60000) {
            return App.getInstance().getString(R.string.msg_date_just_now);
        }
        int days = differentDays(time, cal.getTime());
        if (days == 0) {
            ftime = date_Format_hours.format(time);
        } else if (days == 1) {
            ftime = App.getInstance().getString(R.string.msg_date_yesterday);
            ftime = ftime + " " + date_Format_hours.format(time);
        } else if (days == 2) {
            ftime = App.getInstance().getString(R.string.msg_date_before_yesterday);
            ftime = ftime + " " + date_Format_hours.format(time);
        } else {
            ftime = date_Format_13.format(time);
        }
        return ftime;
    }

    /**
     * 日期时间格式转换
     *
     * @param typeFrom 原格式
     * @param typeTo   转为格式
     * @param value    传入的要转换的参数
     * @return String
     */
    public String stringDateToStringData(String typeFrom, String typeTo,
                                         String value) {
        String re = value;
        SimpleDateFormat sdfFrom = new SimpleDateFormat(typeFrom);
        SimpleDateFormat sdfTo = new SimpleDateFormat(typeTo);

        try {
            re = sdfTo.format(sdfFrom.parse(re));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re;
    }

    /**
     * 得到这个月有多少天
     *
     * @param year  int
     * @param month int
     * @return int
     */
    public int getMonthLastDay(int year, int month) {
        if (month == 0) {
            return 0;
        }
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);

    }

    /**
     * 得到年份
     *
     * @return String
     */
    public String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "";
    }

    /**
     * 得到月份
     *
     * @return String
     */
    public String getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return (c.get(Calendar.MONTH) + 1) + "";
    }

    /**
     * 获得当天的日期
     *
     * @return String
     */
    public String getCurrDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH) + "";
    }


    /**
     * 获得当天的周几
     *
     * @return String
     */
    public int getCurrWeek() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取现在的小时
     *
     * @return String
     */
    public int getCurrHour() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获得当天的周几
     *
     * @return String
     */
    public String getCurrWeek(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        String timeString = null;
        switch (c.get(Calendar.DAY_OF_WEEK) - 1) {
            case 1:
                timeString = "星期一";
                break;
            case 2:
                timeString = "星期二";
                break;
            case 3:
                timeString = "星期三";
                break;
            case 4:
                timeString = "星期四";
                break;
            case 5:
                timeString = "星期五";
                break;
            case 6:
                timeString = "星期六";
                break;
            case 0:
                timeString = "星期日";
                break;
        }
        return timeString;
    }

    /**
     * 得到几天/周/月/年后的日期，整数往后推,负数往移动
     *
     * @param calendar     Calendar
     * @param calendarType Calendar.DATE,Calendar.WEEK_OF_YEAR,Calendar.MONTH,Calendar.
     *                     YEAR
     * @param next         int
     * @return String
     */
    public String getDayByDate(Calendar calendar, int calendarType, int next) {

        calendar.add(calendarType, next);
        Date date = calendar.getTime();
        return date_Format_1.format(date);


    }

    /*
     * 秒转化时分秒
     */
    public String formatHourMinuteSecondTime(Long ms) {
        Integer ss = 1;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();

        if (day < 0) {
            day = 0l;
        }

        if (day > 0) {
            sb.append(day + "天");
        }

        //hour = day * 24 + hour;//过去的算总小时
        hour = hour;

        String hourStr = hour + ":";
        if (hour <= 0) {
            hourStr = "";
        }

        if (hourStr.length() == 2) {
            hourStr = "0" + hourStr;
        }
        sb.append(hourStr);

        String minuteStr = minute + ":";
        if (minute <= 0) {
            minuteStr = "00:";
        }
        if (minuteStr.length() == 2) {
            minuteStr = "0" + minuteStr;
        }
        sb.append(minuteStr);


        String secondStr = second + "";
        if (second <= 0) {
            secondStr = "00";
        }

        if (secondStr.length() == 1) {
            secondStr = "0" + secondStr;
        }
        sb.append(secondStr);


//	    if(milliSecond > 0) {
//	        sb.append(milliSecond+"毫秒");
//	    }
        return sb.toString();
    }

    /*
     * 秒转化时分秒
     */
    public String formatHourMinuteSecondTime2(long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;

        Long hour = ms / hh;
        Long minute = (ms - hour * hh) / mi;
        Long second = (ms - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();

        String hourStr = hour + ":";
        if (hour <= 0) {
            hourStr = "";
        }

        if (hourStr.length() == 2) {
            hourStr = "0" + hourStr;
        }
        sb.append(hourStr);

        String minuteStr = minute + ":";
        if (minute <= 0) {
            minuteStr = "00:";
        }
        if (minuteStr.length() == 2) {
            minuteStr = "0" + minuteStr;
        }
        sb.append(minuteStr);


        String secondStr = second + "";
        if (second <= 0) {
            secondStr = "00";
        }

        if (secondStr.length() == 1) {
            secondStr = "0" + secondStr;
        }
        sb.append(secondStr);


        return sb.toString();
    }

    /*
     * 秒转化天
     */
    public String formatDayTime(Long ms) {
        Integer ss = 1;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;

        StringBuffer sb = new StringBuffer();

        if (day < 0) {
            day = 0l;
        }

        if (day > 0) {
            sb.append(day + "天");
        }
        return sb.toString();
    }

    /*
     * 秒转化天、小时（大于1天显示天，大于1小时小于1天显示小时）
     */
    public String formatDayHoursTime(Long ms) {
        Integer ss = 1;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hours = ms / hh;

        StringBuffer sb = new StringBuffer();

        if (day < 0) {
            day = 0l;
        } else if (day == 0) {
            if (hours > 0) {
                sb.append(hours + "小时");
            }
        } else {
            sb.append(day + "天");
        }
        return sb.toString();
    }

    /**
     * 返回是上午还是下午
     *
     * @return 1上午 2下午
     */
    public int getDayClockType(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        int apm = mCalendar.get(Calendar.AM_PM);
        return apm + 1;
    }

    public int getHour(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        return mCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getDay(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayOfWeek(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        return mCalendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getMonth(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        return mCalendar.get(Calendar.MONTH);
    }

    public int getYear(long time) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);

        return mCalendar.get(Calendar.YEAR);
    }

    public boolean isSameDay(long oldTime, long newTime) {
        return (newTime - oldTime < 24 * 60 * 60 * 1000) && (getDay(oldTime) == getDay(newTime));
    }

    public boolean sameDay(long day1, long day2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(day1);
        int d1 = instance.get(Calendar.DAY_OF_YEAR);
        instance.setTimeInMillis(day2);
        int d2 = instance.get(Calendar.DAY_OF_YEAR);
        return d1 == d2;
    }

    public boolean isYesterday(long day1, long day2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(day1);
        int d1 = instance.get(Calendar.DAY_OF_YEAR);
        instance.setTimeInMillis(day2);
        int d2 = instance.get(Calendar.DAY_OF_YEAR);
        return d1 - d2 == 1 || d2 - d1 == 1;
    }

    public boolean isNoClockAble(long time) {
        long now = System.currentTimeMillis();
        return (now - time < 24 * 60 * 60 * 1000) && (getDay(now) == getDay(time)) && (getDayClockType(now) == getDayClockType(time));
    }

    /**
     * 返回现在的打卡状态
     *
     * @return 1早安打卡 2晚安打卡 3连续打卡
     */
    public int getClockStatus(long time) {
        int status = 0;
        long now = System.currentTimeMillis();

        if ((now - time < 24 * 60 * 60 * 1000) && (getDay(now) == getDay(time)) && (getDayClockType(now) == getDayClockType(time))) {

            status = 3;

        } else if (getDayClockType(now) == 2) {
            status = 2;
        } else {
            status = 1;
        }

        return status;
    }


    public static Integer getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int mouth = calendar.get(Calendar.MONTH);
        // JDK think 2015-12-31 as 2016 1th week
        //如果月份是12月，且求出来的周数是第一周，说明该日期实质上是这一年的第53周，也是下一年的第一周
        if (mouth >= 11 && week <= 1) {
            week += 52;
        }
        return week;
    }

    /**
     * 计算年份
     *
     * @param date Date
     * @return Integer
     */
    public static Integer getYearOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 两个日期之间有多少周
     *
     * @param fromDate Date
     * @param toDate   Date
     * @return Integer
     */
    public static Integer weeksBetween(Date fromDate, Date toDate) {
        if (fromDate.before(toDate)) {
            Date temp = fromDate;
            fromDate = toDate;
            toDate = temp;
        }
        Integer weekNum = (getWeekOfYear(fromDate) - getWeekOfYear(toDate))
                + (getYearOfDate(fromDate) - getYearOfDate(toDate)) * 52;
        return ++weekNum;
    }

    /**
     * 获取当前是第几周  用1月1日之后的第一个星期日，作为第一个周的周末
     *
     * @param date Date
     * @return int
     */
    public static int getWeeksCurrent(Date date) {


        Calendar c = new GregorianCalendar(getYearOfDate(date), 0, 1);

        c.setFirstDayOfWeek(Calendar.MONDAY);


        int week = getWeekOfYear(c.getTime());

        int day = c.get(Calendar.DAY_OF_WEEK);

        int realWeek = 0;
        if (day == Calendar.SUNDAY) {
            realWeek = 0;
        } else {
            realWeek = 1;
        }

        int weekCurrent = getWeekOfYear(date);


        int weekNum = weekCurrent - week + realWeek;

        KLog.e(date.toString() + "  " + weekNum);

        return weekNum;


    }

    /**
     * 获取时间是第几月
     *
     * @param date
     * @return
     */
    public static int getMonthCurrent(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取时间是几号
     *
     * @param date
     * @return
     */
    public static int getDayCurrent(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断不是同一天
     *
     * @param time
     * @return
     */
    public static boolean notToday(long time) {
        int today = getDayCurrent(new Date(System.currentTimeMillis()));
        int day = getDayCurrent(new Date(time));
        // 判断是否同一天
        boolean sameDay = (today == day) && (Math.abs(System.currentTimeMillis() - time) < 24 * 60 * 60 * 1000);
        return !sameDay;
    }


    /**
     * 单位进位，中文默认为4位即（万、亿）
     */
    public int UNIT_STEP = 4;

    /**
     * 单位
     */
    public String[] CN_UNITS = new String[]{"个", "十", "百", "千", "万", "十",
            "百", "千", "亿", "十", "百", "千", "万"};

    public String[] CN_CHARS = new String[]{"零", "一", "二", "三", "四",
            "五", "六", "七", "八", "九"};


    /**
     * 数值转换为中文字符串(口语化)
     *
     * @param num          需要转换的数值
     * @param isColloquial 是否口语化。例如12转换为'十二'而不是'一十二'。
     * @return
     */
    public String cvt(long num, boolean isColloquial) {
        String[] result = this.convert(num, isColloquial);
        StringBuffer strs = new StringBuffer(32);
        for (String str : result) {
            strs.append(str);
        }
        return strs.toString();
    }

    /**
     * 将数值转换为中文
     *
     * @param num          需要转换的数值
     * @param isColloquial 是否口语化。例如12转换为'十二'而不是'一十二'。
     * @return
     */
    public String[] convert(long num, boolean isColloquial) {
        if (num < 10) {// 10以下直接返回对应汉字
            return new String[]{CN_CHARS[(int) num]};// ASCII2int
        }

        char[] chars = String.valueOf(num).toCharArray();
        if (chars.length > CN_UNITS.length) {// 超过单位表示范围的返回空
            return new String[]{};
        }

        boolean isLastUnitStep = false;// 记录上次单位进位
        ArrayList<String> cnchars = new ArrayList<String>(chars.length * 2);// 创建数组，将数字填入单位对应的位置
        for (int pos = chars.length - 1; pos >= 0; pos--) {// 从低位向高位循环
            char ch = chars[pos];
            String cnChar = CN_CHARS[ch - '0'];// ascii2int 汉字
            int unitPos = chars.length - pos - 1;// 对应的单位坐标
            String cnUnit = CN_UNITS[unitPos];// 单位
            boolean isZero = (ch == '0');// 是否为0
            boolean isZeroLow = (pos + 1 < chars.length && chars[pos + 1] == '0');// 是否低位为0

            boolean isUnitStep = (unitPos >= UNIT_STEP && (unitPos % UNIT_STEP == 0));// 当前位是否需要单位进位

            if (isUnitStep && isLastUnitStep) {// 去除相邻的上一个单位进位
                int size = cnchars.size();
                cnchars.remove(size - 1);
                if (!CN_CHARS[0].equals(cnchars.get(size - 2))) {// 补0
                    cnchars.add(CN_CHARS[0]);
                }
            }

            if (isUnitStep || !isZero) {// 单位进位(万、亿)，或者非0时加上单位
                cnchars.add(cnUnit);
                isLastUnitStep = isUnitStep;
            }
            if (isZero && (isZeroLow || isUnitStep)) {// 当前位为0低位为0，或者当前位为0并且为单位进位时进行省略
                continue;
            }
            cnchars.add(cnChar);
            isLastUnitStep = false;
        }

        Collections.reverse(cnchars);
        // 清除最后一位的0
        int chSize = cnchars.size();
        String chEnd = cnchars.get(chSize - 1);
        if (CN_CHARS[0].equals(chEnd) || CN_UNITS[0].equals(chEnd)) {
            cnchars.remove(chSize - 1);
        }

        // 口语化处理
        if (isColloquial) {
            String chFirst = cnchars.get(0);
            String chSecond = cnchars.get(1);
            if (chFirst.equals(CN_CHARS[1]) && chSecond.startsWith(CN_UNITS[1])) {// 是否以'一'开头，紧跟'十'
                cnchars.remove(0);
            }
        }
        return cnchars.toArray(new String[]{});
    }

    public String getVoteEndTimeString(long time) {
        try {
            String year = date_Format_y.format(new Date(System.currentTimeMillis())) + ".";

            String times = date_Format_10.format(new Date(time));

            if (times.startsWith(year)) {
                return times.replace(year, "");
            }
            return times;
        } catch (Exception e) {
            return "";
        }
    }

    // 根据年月日计算年龄,birthTimeString:"1994-11-14"
    public static String getAgeFromBirthTime(long birthTime) {

        String birthTimeString = getInstance().getTimeString11(birthTime);

        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        if (age < 1) {
            age = 1;
        }
        return (age - 1) + "岁";
    }
}
