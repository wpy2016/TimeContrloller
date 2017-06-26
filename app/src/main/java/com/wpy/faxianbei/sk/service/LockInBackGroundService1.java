package com.wpy.faxianbei.sk.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplPupCourse;
import com.wpy.faxianbei.sk.activity.welcome.AcWelcome;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.db.CourseTable;
import com.wpy.faxianbei.sk.entity.db.TimeItem;
import com.wpy.faxianbei.sk.entity.db.openRecord;
import com.wpy.faxianbei.sk.utils.dateUtil.DateUtil;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by peiyuwang on 16-12-18.
 */

public class LockInBackGroundService1 extends Service {

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    CalcuThread calcuThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        calcuThread = new CalcuThread();
    }


    private class CalcuThread extends Thread {
        //当屏幕已经关闭的时候，就可以关闭线程了
        private boolean go = true;
        private long openTime = 0l;

        public void reset(){
            go=true;
        }

        @Override
        public void run() {
            PowerManager manager = (PowerManager) LockInBackGroundService1.this.getSystemService(Context.POWER_SERVICE);
            while (go) {
                openTime += 1000;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                if (manager.isScreenOn()) {
                    //屏幕依然在启动着，不进行任何的操作
                } else {
                    go = false;
                    //将记录的数据保存起来
                    openRecord openRecord = new openRecord();
                    DateUtil.setTimeInMillis(System.currentTimeMillis());
                    openRecord.setDay(DateUtil.getCurrentDay() + "");
                    openRecord.setYear(DateUtil.getCurrentYear()+ "");
                    openRecord.setMonth(DateUtil.getCurrentMonth() + "");
                    openRecord.setMinute(DateUtil.getCurrentMinute() + "");
                    openRecord.setHour(DateUtil.getCurrentHour() + "");
                    openRecord.setOpentime(openTime);
                    openRecord.setType(0 + "");
                    try {
                        //将记录给保存起来
                        SKApplication.getDbManager().save(openRecord);
                    } catch (DbException e) {
                    }
                }
            }
        }

        public long getOpenTime() {
            return openTime;
        }

        public boolean getStatu() {
            return go;
        }
    }


    public class myRunnable implements Runnable {

        CourseTable courseTable;

        public myRunnable(CourseTable courseTable) {
            this.courseTable = courseTable;
        }

        @Override
        public void run() {
            Notification.Builder builder = new Notification.Builder(getApplicationContext());
            Intent intent = new Intent(LockInBackGroundService1.this, AcWelcome.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 110, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews remoteViews = new RemoteViews(LockInBackGroundService1.this.getPackageName(), R.layout.notification_always);
            remoteViews.setTextViewText(R.id.notification_tv_lesson, courseTable.getCourse());
            remoteViews.setTextViewText(R.id.notification_tv_classroom, courseTable.getClassroom());
            remoteViews.setTextViewText(R.id.notification_tv_time, courseTable.getTime());
            remoteViews.setTextViewText(R.id.notification_tv_grade, "" + 100);
            remoteViews.setImageViewResource(R.id.notification_iv_img, R.drawable.logo);
            Notification notification = builder.setContent(remoteViews)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent).build();
            startForeground(1, notification);
        }
    }

    private void caculate() {
        new Thread() {
            boolean lock = false;

            boolean isTip = false;

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
                        if (timeitem.getEnd() > System.currentTimeMillis()) {
                            if (!lock) {
                                mhandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intentTOMain = new Intent(LockInBackGroundService1.this, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
                                        intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        LockInBackGroundService1.this.startActivity(intentTOMain);
                                    }
                                });
                                Log.i("mytime","mytime calcuthread before");
                                calcuThread.start();
                            }
                            lock = true;
                            break;
                        }
                    }
                }
            }

            /**
             * 获取当前年份、当前学期、当前星期几的所有课程
             * @return
             */
            private List<CourseTable> getCurrentDayLessons() throws Exception {
                //获取当前的学期
                int semester = ModelImplPupCourse.getSemester(SharePreferenceUtil.instantiation.getSemester(LockInBackGroundService1.this));
                // 获取当前的年份
                int year = ModelImplPupCourse.getYear(SharePreferenceUtil.instantiation.getSemester(LockInBackGroundService1.this));
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
             * @param list
             */
            @TargetApi(Build.VERSION_CODES.N)
            private void caculateCurrentDayLessonIsInTimeQuantum(List<CourseTable> list) {
                if (list == null || list.isEmpty()) {
                } else {
                    //获取当前是第几周
                    int week = getCurrentWeek(LockInBackGroundService1.this);
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
                                if (System.currentTimeMillis() > getStartTime(list.get(i).getTime()) && System.currentTimeMillis() < getEndime(list.get(i).getTime())) {
                                    mhandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intentTOMain = new Intent(LockInBackGroundService1.this, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
                                            intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            LockInBackGroundService1.this.startActivity(intentTOMain);
                                        }
                                    });
                                    calcuThread.start();
                                    lock = true;
                                }
                                if (!isTip) {
                                    if (System.currentTimeMillis() < getStartTime(list.get(i).getTime()))
                                        mhandler.post(new myRunnable(list.get(i)));
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
        }.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        calcuThread = new CalcuThread();
        calcuThread.reset();
        caculate();
        return super.onStartCommand(intent, flags, startId);
    }

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

    public int getCurrentWeek(Context context) {
        return (int) Math.ceil((double) ((System.currentTimeMillis() - Long.parseLong(SharePreferenceUtil.instantiation.getWeek(context))) / (1000 * 60 * 60 * 24 * 7.0)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
