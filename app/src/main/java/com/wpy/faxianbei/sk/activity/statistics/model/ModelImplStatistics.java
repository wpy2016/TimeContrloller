package com.wpy.faxianbei.sk.activity.statistics.model;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.jn.chart.data.Entry;
import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplPupCourse;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.db.CourseTable;
import com.wpy.faxianbei.sk.entity.db.TimeItem;
import com.wpy.faxianbei.sk.entity.db.openRecord;
import com.wpy.faxianbei.sk.service.SituationService;
import com.wpy.faxianbei.sk.utils.dateUtil.DateUtil;
import com.wpy.faxianbei.sk.utils.general.ImageTools;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by peiyuwang on 17-1-15.
 */

public class ModelImplStatistics implements IModelStatistics {

    private loadResult mReslutListener;

    private long timeSum = -1l;

    public ModelImplStatistics(loadResult mReslutListener) {
        this.mReslutListener = mReslutListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void loadDate(Context context) {
        String[] x = {"1", "2", "3", "4", "5", "6", "7"};
        //应锁屏
        String[] y1 = {"0", "0", "0", "0", "0", "0", "0"};
        //实际锁屏
        String[] y2 = {"0", "0", "0", "0", "0", "0", "40.78"};
        for(int i=0;i<7;i++){
            long time=System.currentTimeMillis()-((6-i)*24*60*60*1000l);
            float minute = calcuMinutes(context,time);
            y1[i] = minute + "";
            y2[i] = (minute- getOpenTime(time) * 60) + "";
        }
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            xValues.add((DateUtil.getCurrentDay()-(x.length-1-i))+"");
        }

        //设置y轴的数据
        ArrayList<Entry> yValue = new ArrayList<>();
        for (int i = 0; i < y1.length; i++) {
            yValue.add(new Entry(Float.parseFloat(y1[i]), i));
        }
        //设置第二条折线y轴的数据
        ArrayList<Entry> yValue1 = new ArrayList<>();
        for (int i = 0; i < y2.length; i++) {
            yValue1.add(new Entry(Float.parseFloat(y2[i]), i));
        }
        mReslutListener.loadSuccess(xValues, yValue, yValue1);
    }

    /**
     * 截屏
     *
     * @param context
     * @return
     */
    @Override
    public String screenShot(Context context) {
        String path = "";
        // 获取屏幕
        View dView = ((Activity) context).getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            File file = new File(SKApplication.mSavePath, "screenshot");
            if (!file.exists()) {
                file.mkdir();
            }
            path = SKApplication.mSavePath + "/screenshot/" + System.currentTimeMillis() + ".png";
            ImageTools.saveBitmapToSDCard(bmp, path);
        }
        return path;
    }

    @Override
    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }

    public interface loadResult {
        public void loadSuccess(ArrayList<String> x, ArrayList<Entry> y1, ArrayList<Entry> y2);

        public void loadFail(String message);
    }

    /**
     * 获取当天需要锁频的时间
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public long getNeedLockTime(Context context,long currentTimeMillis) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int request = 0;
        long sum = 0l;
        try {

            /**
             *   计算当前手动添加的时间的总时间
             */
            List<TimeItem> list = SKApplication
                    .getDbManager()
                    .selector(TimeItem.class).findAll();
            if (list == null || list.isEmpty()) {

            } else {
                Date date = new Date(currentTimeMillis);
                android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy.MM.dd");
                String time = simpleDateFormat.format(date) + " 00:00";
                android.icu.text.SimpleDateFormat simpleDateFormat2 = new android.icu.text.SimpleDateFormat("yyyy.MM.dd HH:mm");
                long today = simpleDateFormat2.parse(time).getTime();
                for (TimeItem timeitem : list) {
                    if (timeitem.getEnd() > today&&timeitem.getEnd()<today+24*60*60*1000l) {
                        sum += timeitem.getEnd() - timeitem.getStart();
//                        Intent intent = new Intent(context, SituationService.class);
//                        intent.putExtra("situation", timeitem.getModel());
//                        PendingIntent Pendingintent = PendingIntent.getService(context, request, intent, PendingIntent.FLAG_ONE_SHOT);
//                        manager.set(AlarmManager.RTC_WAKEUP, timeitem.getStart(), Pendingintent);
//                        request++;
//                        Intent intentEnd = new Intent(context, SituationService.class);
//                        intentEnd.putExtra("situation", timeitem.getModel());
//                        PendingIntent pendingintentEnd = PendingIntent.getService(context, request, intentEnd, PendingIntent.FLAG_ONE_SHOT);
//                        manager.set(AlarmManager.RTC_WAKEUP, timeitem.getEnd(), pendingintentEnd);
//                        request++;
                    }
                }
            }
        } catch (DbException e) {

        } catch (ParseException e) {

        }

        /**
         * 计算当前课程的总时间
         */
        try {
            int semester = ModelImplPupCourse.getSemester(SharePreferenceUtil.instantiation.getSemester(context));
            int year = ModelImplPupCourse.getYear(SharePreferenceUtil.instantiation.getSemester(context));
            Date date = new Date(currentTimeMillis);
            SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("EEEE");
            String day = simpleDateFormatDay.format(date);
            List<CourseTable> list = SKApplication.getDbManager().selector(CourseTable.class).where(
                    WhereBuilder.b().and("stuid", "=", SkUser.getCurrentUser(SkUser.class).getSchoolId()).and("semester", "=", "" + semester)
                            .and("year", "=", "" + year).and("day", "=", day.substring(2))).findAll();
            if (list == null || list.isEmpty()) {
            } else {
                int week = getCurrentWeek(context,currentTimeMillis);
                for (int i = 0; i < list.size() / 2; i++) {
                    String[] split = list.get(i).getWeeks()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "")
                            .split(",");
                    for (String s : split) {
                        if (s.equals(week + "")) {
                            sum += 90 * 60 * 1000;
//                            Intent intent3 = new Intent(context, SituationService.class);
//                            intent3.putExtra("situation", SituationService.SHAKE);
//                            PendingIntent Pendingintent = PendingIntent.getService(context, request, intent3, PendingIntent.FLAG_ONE_SHOT);
//                            manager.set(AlarmManager.RTC_WAKEUP, getStartTime(list.get(i).getTime()), Pendingintent);
//                            request++;
//                            Intent intent4 = new Intent(context, SituationService.class);
//                            intent4.putExtra("situation", SituationService.NORMAL);
//                            PendingIntent pendingintentEnd = PendingIntent.getService(context, request, intent4, PendingIntent.FLAG_ONE_SHOT);
//                            manager.set(AlarmManager.RTC_WAKEUP, getEndime(list.get(i).getTime()), pendingintentEnd);
//                            request++;
                        }
                    }

                }
            }
        } catch (Exception e) {
        }
        return sum;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public float calcuNeedToLock(Context context,long currentTimeMillis) {
            timeSum = getNeedLockTime(context,currentTimeMillis);
        return timeSum / (1000.0f * 60 * 60);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public float calcuMinutes(Context context,long currentTimeMillis) {
            timeSum = getNeedLockTime(context,currentTimeMillis);
        return timeSum / (1000.0f * 60);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public float getEffiency(Context context,long currentTimeMillis) {
        float needToLock = calcuNeedToLock(context,currentTimeMillis);
        if ((needToLock - getOpenTime(currentTimeMillis)) != 0) {
            return ((needToLock - getOpenTime(currentTimeMillis)) / needToLock) * 100;
        } else {
            return 100;
        }
    }

    @Override
    public float getOpenTime(long currentTimeMillis) {
        long open = 0l;
        try {
            DateUtil.setTimeInMillis(currentTimeMillis);
            int year = DateUtil.getCurrentYear();
            int month = DateUtil.getCurrentMonth();
            int day =DateUtil.getCurrentDay();
            Selector<openRecord> selector = SKApplication.getDbManager().selector(openRecord.class).where(WhereBuilder.b()
                    .and("year", "=", (year + ""))
                    .and("month", "=", (month + ""))
                    .and("day", "=", (day + "")));
            List<openRecord> list = selector.findAll();
            if (list != null && !list.isEmpty()) {
                for (openRecord record : list) {
                    open += record.getOpentime();
                }
            }
        } catch (Exception e) {
        }
        return open / (1000.0f * 60 * 60);
    }

    public int getCurrentWeek(Context context,long currentTimeMillis) {
        return (int) Math.ceil((double) ((currentTimeMillis - Long.parseLong(SharePreferenceUtil.instantiation.getWeek(context))) / (1000 * 60 * 60 * 24 * 7.0)));
    }

/*
    @RequiresApi(api = Build.VERSION_CODES.N)
    public long getStartTime(String strTime) {
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
    public long getEndime(String strTime) {
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
*/
}
