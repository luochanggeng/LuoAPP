package com.luo.app.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/19 13:42
 */
public class CommonUtils {

    public static String getWeekInfo(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (i) {
            case Calendar.SUNDAY:
                week = "星期日";
                break;
            case Calendar.MONDAY:
                week = "星期一";
                break;
            case Calendar.TUESDAY:
                week = "星期二";
                break;
            case Calendar.WEDNESDAY:
                week = "星期三";
                break;
            case Calendar.THURSDAY:
                week = "星期四";
                break;
            case Calendar.FRIDAY:
                week = "星期五";
                break;
            case Calendar.SATURDAY:
                week = "星期六";
                break;
            default:
                break;
        }
        return week ;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate(){
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return simpleDateFormat.format(System.currentTimeMillis()) ;
    }

}
