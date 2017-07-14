package com.wpy.faxianbei.sk.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplPupCourse;
import com.wpy.faxianbei.sk.activity.addevent.view.AcAddEvent;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.db.CourseTable;
import com.wpy.faxianbei.sk.entity.db.TimeItem;
import com.wpy.faxianbei.sk.utils.dateUtil.DateUtil;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import org.xutils.db.sqlite.WhereBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wangpeiyu on 2017/6/26.
 */

public class IsNeedLockThread extends Thread {

    boolean lock = false;

    boolean isTip = false;

    private Handler mhandler;

    Context mContext;

    LockService.CalcuThread calcuThread;

    public IsNeedLockThread(Handler handler, Context context, LockService.CalcuThread thread) {
        this.mhandler = handler;
        this.mContext = context;
        this.calcuThread = thread;
    }


    /**
     * 判断手动添加的时间段中是否包含现在
     */
    private void judgeCurrentTimeIsInMyAddTimeQuantum() throws Exception {
        List<TimeItem> listtime = SKApplication
                .getDbManager()
                .selector(TimeItem.class).findAll();
        if (listtime == null || listtime.isEmpty()) {

        } else {
            for (TimeItem timeitem : listtime) {
                if (timeitem.getType() == AcAddEvent.EVENTNORMAL) {
                    if (0 == timeitem.getIsRecycle()) {
                        //表示不重复的日常事件
                        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                        long startTime = format.parse(timeitem.getStart()).getTime();
                        long endTime = format.parse(timeitem.getEnd()).getTime();
                        long now = System.currentTimeMillis();
                        if (now >= startTime && now <= endTime) {
                            if (!lock) {
                                mhandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intentTOMain = new Intent(mContext, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
                                        intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(intentTOMain);
                                    }
                                });
                                calcuThread.start();
                            }
                            lock = true;
                            break;
                        }
                    } else {
                        //表示重复的日常事件 recycle[0]=1
                        int day = DateUtil.parseIntFormDayString(DateUtil.getDay(System.currentTimeMillis())) + 1;
                        int recycle[] = getRecycle(timeitem);
                        if (recycle[day] == 1) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                            String strStart = format.format(new Date(System.currentTimeMillis())) + " " + timeitem.getStart();
                            String strEnd = format.format(new Date(System.currentTimeMillis())) + " " + timeitem.getEnd();
                            SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                            long start = format2.parse(strStart).getTime();
                            long end = format2.parse(strEnd).getTime();
                            long now = System.currentTimeMillis();
                            if (now >= start && now <= end) {
                                if (!lock) {
                                    mhandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intentTOMain = new Intent(mContext, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
                                            intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            mContext.startActivity(intentTOMain);
                                        }
                                    });
                                    calcuThread.start();
                                }
                                lock = true;
                                break;
                            }
                        }
                    }
                } else {
                    if (0 == timeitem.getIsRecycle()) {
                        //表示不重复的课程事件
                        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                        long startTime = format.parse(timeitem.getStart()).getTime();
                        long endTime = format.parse(timeitem.getEnd()).getTime();
                        long now = System.currentTimeMillis();
                        if (now >= startTime && now <= endTime) {
                            if (!lock) {
                                mhandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intentTOMain = new Intent(mContext, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
                                        intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(intentTOMain);
                                    }
                                });
                                calcuThread.start();
                            }
                            lock = true;
                            break;
                        }
                    } else {
                        //表示重复的课程事件
                        int day = DateUtil.parseIntFormDayString(DateUtil.getDay(System.currentTimeMillis())) + 1;
                        int recycle[] = getRecycle(timeitem);
                        if (recycle[day] == 1) {
                            int currentWeek = (int) Math.ceil((double) ((System.currentTimeMillis() - Long.parseLong(SharePreferenceUtil.instantiation.getWeek(mContext))) / (1000l * 60 * 60 * 24 * 7.0)));
                            if (currentWeek >= timeitem.getStartWeek() && currentWeek <= timeitem.getEndWeek()) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                                String strStart = format.format(new Date(System.currentTimeMillis())) + " " + timeitem.getStart();
                                String strEnd = format.format(new Date(System.currentTimeMillis())) + " " + timeitem.getEnd();
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                                long start = format2.parse(strStart).getTime();
                                long end = format2.parse(strEnd).getTime();
                                long now = System.currentTimeMillis();
                                if (now >= start && now <= end) {
                                    if (!lock) {
                                        mhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intentTOMain = new Intent(mContext, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
                                                intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                mContext.startActivity(intentTOMain);
                                            }
                                        });
                                        calcuThread.start();
                                    }
                                    lock = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
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

    /**
     * 获取当前年份、当前学期、当前星期几的所有课程
     *
     * @return
     */
    private List<CourseTable> getCurrentDayLessons() throws Exception {
        //获取当前的学期
        int semester = ModelImplPupCourse.getSemester(SharePreferenceUtil.instantiation.getSemester(mContext));
        // 获取当前的年份
        int year = ModelImplPupCourse.getYear(SharePreferenceUtil.instantiation.getSemester(mContext));
        //获取当前的系统时间
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("EEEE");
        //获取当前是星期几
        String day = simpleDateFormatDay.format(date);
        //从数据库中获取是当前学期、当前年份、当前星期几的所有课程
        final List<CourseTable> list = SKApplication.getDbManager().selector(CourseTable.class).where(
                WhereBuilder.b().and("stuid", "=", SkUser.getCurrentUser(SkUser.class).getSchoolId()).and("semester", "=", "" + semester)
                        .and("year", "=", "" + year).and("day", "=", day.substring(2))).findAll();
        return list;
    }

    /**
     * 计算当前所有课程是否是本周的，并开始判断当前时间是否处于上课时间，进而转到锁屏界面，并进行提示
     *
     * @param list
     */
    @TargetApi(Build.VERSION_CODES.N)
    private void caculateCurrentDayLessonIsInTimeQuantum(List<CourseTable> list) {
        if (list == null || list.isEmpty()) {
        } else {
            //获取当前是第几周
            int week = DateUtil.getCurrentWeek(mContext);
            //判断上面从数据库中获取的所有数据项中的课程的周次是否包含当前周
            for (int i = 0; i < list.size() / 2; i++) {
                //解析list中每一项的周次
                String[] split = list.get(i).getWeeks()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(" ", "")
                        .split(",");
                for (String s : split) {
                    //判断所有的周次中是否包含当前周
                    if (s.equals(week + "")) { //如果匹配当前周
                        if (System.currentTimeMillis() > DateUtil.getStartTime(list.get(i).getTime()) && System.currentTimeMillis() < DateUtil.getEndime(list.get(i).getTime())) {
                            mhandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intentTOMain = new Intent(mContext, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
                                    intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intentTOMain);
                                }
                            });
                            calcuThread.start();
                            lock = true;
                        }
                        if (!isTip) {
                            if (System.currentTimeMillis() < DateUtil.getStartTime(list.get(i).getTime()))
                                mhandler.post(new myRunnable(list.get(i), (Service) mContext));
                            isTip = true;
                        }
                    }
                }

            }

        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void run() {
        try {
            judgeCurrentTimeIsInMyAddTimeQuantum();
            if (!lock) {
                caculateCurrentDayLessonIsInTimeQuantum(getCurrentDayLessons());
            }
        } catch (Exception e) {
        }
    }
}
