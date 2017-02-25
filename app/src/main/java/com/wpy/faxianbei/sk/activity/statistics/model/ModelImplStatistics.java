package com.wpy.faxianbei.sk.activity.statistics.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.jn.chart.data.Entry;
import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplPupCourse;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.db.CourseTable;
import com.wpy.faxianbei.sk.entity.db.TimeItem;
import com.wpy.faxianbei.sk.entity.db.openRecord;
import com.wpy.faxianbei.sk.utils.general.ImageTools;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

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

    @Override
    public void loadDate() {
        String[] x = {"1", "2", "3", "4", "5", "6", "7"};
        String[] y1 = {"122.00", "234.34", "85.67", "117.90", "332.33", "113.33", "120.78"};
        String[] y2 = {"62.00", "134.34", "35.67", "87.90", "232.33", "83.33", "40.78"};
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            xValues.add(x[i]);
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
    public long getNeedLockTime(Context context) {
        long sum = 0l;
        try {
            List<TimeItem> list = SKApplication
                    .getDbManager()
                    .selector(TimeItem.class).findAll();
            if (list == null || list.isEmpty()) {

            } else {
                Date date = new Date(System.currentTimeMillis());
                android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy.MM.dd");
                String time = simpleDateFormat.format(date) + " 00:00";
                android.icu.text.SimpleDateFormat simpleDateFormat2 = new android.icu.text.SimpleDateFormat("yyyy.MM.dd HH:mm");
                long today = simpleDateFormat2.parse(time).getTime();
                for (TimeItem timeitem : list) {
                    if (timeitem.getEnd() > today) {
                        sum += timeitem.getEnd() - timeitem.getStart();
                    }
                }
            }
        } catch (DbException e) {

        } catch (ParseException e) {

        }

        try {
            int semester = ModelImplPupCourse.getSemester(SharePreferenceUtil.instantiation.getSemester(context));
            int year = ModelImplPupCourse.getYear(SharePreferenceUtil.instantiation.getSemester(context));
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("EEEE");
            String day = simpleDateFormatDay.format(date);
            List<CourseTable> list = SKApplication.getDbManager().selector(CourseTable.class).where(
                    WhereBuilder.b().and("stuid", "=", SkUser.getCurrentUser(SkUser.class).getSchoolId()).and("semester", "=", "" + semester)
                            .and("year", "=", "" + year).and("day", "=", day.substring(2))).findAll();
            if (list == null || list.isEmpty()) {
            } else {
                int week = getCurrentWeek(context);
                for (int i = 0; i < list.size() / 2; i++) {
                    String[] split = list.get(i).getWeeks()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "")
                            .split(",");
                    for (String s : split) {
                        String sss = list.get(i).getCourse();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    public float calcuNeedToLock(Context context) {
        if (timeSum == -1) {
            timeSum = getNeedLockTime(context);
        }
        return timeSum / (1000.0f * 60 * 60);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public float calcuMinutes(Context context) {
        if (timeSum == -1) {
            timeSum = getNeedLockTime(context);
        }
        return timeSum / (1000.0f * 60);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public float getEffiency(Context context) {
        float needToLock = calcuNeedToLock(context);
        if ((needToLock - getOpenTime()) != 0) {
            return ((needToLock - getOpenTime()) /needToLock)* 100;
        } else {
            return 100;
        }
    }

    @Override
    public float getOpenTime() {
        long open = 0l;
        Date date = new Date(System.currentTimeMillis());
        try {
            List<openRecord> list = SKApplication.getDbManager().selector(openRecord.class)
                    .and("year", "=", (date.getYear() + ""))
                    .and("month", "=", (date.getMonth() + ""))
                    .and("day", "=", (date.getDay() + "")).findAll();
            if (list != null && list.isEmpty()) {
                for (openRecord record : list) {
                    open += record.getOpentime();
                }
            }
        } catch (DbException e) {
        }catch(NullPointerException e){

        }
        return open / (1000.0f * 60 * 60);
    }

    public int getCurrentWeek(Context context) {
        return (int) Math.ceil((double) ((System.currentTimeMillis() - Long.parseLong(SharePreferenceUtil.instantiation.getWeek(context))) / (1000 * 60 * 60 * 24 * 7.0)));
    }
}
