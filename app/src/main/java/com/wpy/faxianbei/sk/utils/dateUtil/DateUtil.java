package com.wpy.faxianbei.sk.utils.dateUtil;

/**
 * Created by wangpeiyu on 2017/6/21.
 */

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
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
}
