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
import com.wpy.faxianbei.sk.activity.addevent.view.AcAddEvent;
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
            float lock= (minute- getOpenTime(time) * 60);
            if(lock<0)
                lock=0;
            y2[i] =lock+ "";
        }
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            long time=System.currentTimeMillis()-((6-i)*24*60*60*1000l);
            xValues.add(DateUtil.getDateOnlyDay(time)+"");
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
                long dayEarly=DateUtil.getEarlyestTimeOfDay(currentTimeMillis);
                long dayLast=DateUtil.getLastestTimeOfDay(currentTimeMillis);
                int day=DateUtil.parseIntFormDayString(DateUtil.getDay(currentTimeMillis))+1;//此时1表示星期一，2表示星期二
                for (TimeItem timeitem : list) {
                    if (timeitem.getType() == AcAddEvent.EVENTNORMAL) {
                        if (0 == timeitem.getIsRecycle()) {
                            //表示不重复的日常事件
                            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                            long startTime = format.parse(timeitem.getStart()).getTime();
                            long endTime = format.parse(timeitem.getEnd()).getTime();
                            if (startTime>=dayEarly&&endTime<=dayLast) {
                                sum=sum+(endTime-startTime);
                            }
                        } else {
                            //表示重复的日常事件 recycle[0]=1
                            int recycle[] = getRecycle(timeitem);
                            if (recycle[day] == 1) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                                String formatdate=format.format(new Date(currentTimeMillis));
                                String strStart = formatdate + " " + timeitem.getStart();
                                String strEnd = formatdate + " " + timeitem.getEnd();
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                                long start = format2.parse(strStart).getTime();
                                long end = format2.parse(strEnd).getTime();
                                sum=sum+(end-start);
                            }
                        }
                    } else {
                        if (0 == timeitem.getIsRecycle()) {
                            //表示不重复的课程事件
                            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                            long startTime = format.parse(timeitem.getStart()).getTime();
                            long endTime = format.parse(timeitem.getEnd()).getTime();
                            if (startTime>=dayEarly&&endTime<=dayLast) {
                                sum=sum+(endTime-startTime);
                            }
                        } else {
                            //表示重复的课程事件
                            int recycle[] = getRecycle(timeitem);
                            if (recycle[day] == 1) {
                                int dayweek = (int) Math.ceil((double) ((currentTimeMillis - Long.parseLong(SharePreferenceUtil.instantiation.getWeek(context))) / (1000l * 60 * 60 * 24 * 7.0)));
                                if (dayweek >= timeitem.getStartWeek() && dayweek <= timeitem.getEndWeek()) {
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                                    String formatdate=format.format(new Date(currentTimeMillis));
                                    String strStart = formatdate + " " + timeitem.getStart();
                                    String strEnd = formatdate + " " + timeitem.getEnd();
                                    SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                                    long start = format2.parse(strStart).getTime();
                                    long end = format2.parse(strEnd).getTime();
                                    sum=sum+(end-start);
                                }
                            }
                        }
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
                        }
                    }

                }
            }
        } catch (Exception e) {
        }
        return sum;
    }


    private int[] getRecycle(TimeItem item) {
        int recycle[] = new int[8];
        recycle[0] = 1;
        recycle[1] = item.getMonday();
        recycle[2] = item.getTuesday();
        recycle[3] = item.getWednesday();
        recycle[4] = item.getThursDay();
        recycle[5] = item.getFriday();
        recycle[6] = item.getSaturday();
        recycle[7] = item.getSunday();
        return recycle;
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
        if(needToLock==0){
            return 100;
        }
        if ((needToLock - getOpenTime(currentTimeMillis)) >= 0) {
            return ((needToLock - getOpenTime(currentTimeMillis)) / needToLock) * 100;
        } else {
            return 0;
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
}
