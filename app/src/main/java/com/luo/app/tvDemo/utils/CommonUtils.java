package com.luo.app.tvDemo.utils;

import android.annotation.SuppressLint;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    @SuppressLint("DefaultLocale")
    public static String formatPlayerTime(long time){
        time = time/ 1000;
        long hour = time/3600%24;
        long min = time%3600/60;
        long sec = time%3600%60;
        return String.format("%02d:%02d:%02d", hour,min,sec);
    }

    private static ScheduledExecutorService countDownScheduled;

    public static int countDownSecond = 5;

    public static void startCountDownTimer(final Handler mUiHandler) {
        countDownSecond = 5;
        if (countDownScheduled != null) {
            return;
        }
        countDownScheduled = Executors.newSingleThreadScheduledExecutor();
        countDownScheduled.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                mUiHandler.sendMessage(mUiHandler.obtainMessage(0));
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public static void cancelCountDownTimer() {
        if (countDownScheduled != null && !countDownScheduled.isShutdown()) {
            countDownScheduled.shutdown();
            countDownScheduled = null;
        }
    }
}
