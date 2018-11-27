package com.smt.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {

    public final static String FORMAT_DATE2_TIME = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat sdf = TimeUtil.getSimpleDateFormat(FORMAT_DATE2_TIME);
    //获取某个日期与今天 相差多少天 dateFormat
    public synchronized static int getDate2Date(String strDate1, String strDate2, String dateFormat) {
        try {
            sdf.applyPattern(dateFormat);

            Date date1 = sdf.parse(strDate1);
            Date date2 = sdf.parse(strDate2);

            long diff = date2.getTime() - date1.getTime();

//            EcgLog.d(strDate1 + "与" + strDate2 + " 相差" + (diff / (24 * 60 * 60 * 1000)));

            return (int) (diff / (24 * 60 * 60 * 1000));

        } catch (Exception e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    //获取某个日期与今天 相差多少天 "yyyy-MM-dd"  大于0 表示日期大于今天，小于0 表示日期小于今天，等于0 表示等于今天
    public synchronized static int getDate2Today(String strDate, String format) {
        sdf.applyPattern(format);
        return getDate2Date(sdf.format(new Date()), strDate, format);
    }


    public static SimpleDateFormat getSimpleDateFormat(String format) {
        return new SimpleDateFormat(format, Locale.CHINA);
    }

}