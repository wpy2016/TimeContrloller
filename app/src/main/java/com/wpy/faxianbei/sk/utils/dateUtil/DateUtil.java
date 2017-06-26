package com.wpy.faxianbei.sk.utils.dateUtil;

/**
 * Created by wangpeiyu on 2017/6/21.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取当前的日期
 */
public class DateUtil {

    private static SoftReference<Calendar> srCalender=getSrCalender();

    private static long timeInMillis=0l;


    public static  void setTimeInMillis(long timeInMillis){
        DateUtil.timeInMillis=timeInMillis;
    }

    public static int getCurrentYear(){
        if(srCalender.get()==null){
            srCalender.clear();
            srCalender=getSrCalender();
        }
        Calendar calendar=srCalender.get();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth(){
        if(srCalender.get()==null){
            srCalender.clear();
            srCalender=getSrCalender();
        }
        Calendar calendar=srCalender.get();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.MONTH);
    }

    public static int getCurrentDay(){
        if(srCalender.get()==null){
            srCalender.clear();
            srCalender=getSrCalender();
        }
        Calendar calendar=srCalender.get();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    public static int getCurrentHour(){
        if(srCalender.get()==null){
            srCalender.clear();
            srCalender=getSrCalender();
        }
        Calendar calendar=srCalender.get();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    public static int getCurrentMinute(){
        if(srCalender.get()==null){
            srCalender.clear();
            srCalender=getSrCalender();
        }
        Calendar calendar=srCalender.get();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.MINUTE);
    }

    private static SoftReference<Calendar> getSrCalender(){
        return new SoftReference<Calendar>(Calendar.getInstance());
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static long getStartTime(String strTime) {
        Date date = new Date(System.currentTimeMillis());
        android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy.MM.dd");
        String time = "";
        if (strTime.equals("1-2节")) {
            time = simpleDateFormat.format(date) + " 08:30";
        } else if (strTime.equals("3-4节")) {
            time = simpleDateFormat.format(date) + " 10:30";
        } else if (strTime.equals("5-6节")) {
            time = simpleDateFormat.format(date) + " 14:00";
        } else if (strTime.equals("7-8节")) {
            time = simpleDateFormat.format(date) + " 16:00";
        } else if (strTime.equals("9-10节")) {
            time = simpleDateFormat.format(date) + " 19:00";
        } else if (strTime.equals("10-11节")) {
            time = simpleDateFormat.format(date) + " 21:00";
        }
        android.icu.text.SimpleDateFormat simpleDateFormat2 = new android.icu.text.SimpleDateFormat("yyyy.MM.dd HH:mm");
        try {
            return simpleDateFormat2.parse(time).getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static long getEndime(String strTime) {
        Date date = new Date(System.currentTimeMillis());
        android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy.MM.dd");
        String time = "";
        if (strTime.equals("1-2节")) {
            time = simpleDateFormat.format(date) + " 10:10";
        } else if (strTime.equals("3-4节")) {
            time = simpleDateFormat.format(date) + " 12:10";
        } else if (strTime.equals("5-6节")) {
            time = simpleDateFormat.format(date) + " 15:40";
        } else if (strTime.equals("7-8节")) {
            time = simpleDateFormat.format(date) + " 17:40";
        } else if (strTime.equals("9-10节")) {
            time = simpleDateFormat.format(date) + " 20:40";
        } else if (strTime.equals("10-11节")) {
            time = simpleDateFormat.format(date) + " 22:40";
        }
        android.icu.text.SimpleDateFormat simpleDateFormat2 = new android.icu.text.SimpleDateFormat("yyyy.MM.dd HH:mm");
        try {
            return simpleDateFormat2.parse(time).getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    public static  int getCurrentWeek(Context context) {
        return (int) Math.ceil((double) ((System.currentTimeMillis() - Long.parseLong(SharePreferenceUtil.instantiation.getWeek(context))) / (1000 * 60 * 60 * 24 * 7.0)));
    }
}
