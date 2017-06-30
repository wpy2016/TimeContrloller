package com.wpy.faxianbei.sk.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplPupCourse;
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

public class IsNeedLockThread extends Thread{

    boolean lock = false;

    boolean isTip = false;

    private Handler mhandler;

    Context mContext;

    LockInBackGroundService1.CalcuThread calcuThread;
    public IsNeedLockThread(Handler handler, Context context, LockInBackGroundService1.CalcuThread thread){
        this.mhandler=handler;
        this.mContext=context;
        this.calcuThread=thread;
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
                /********************************************待改***********************************************************/
              /*  if (timeitem.getStart()<System.currentTimeMillis()&&timeitem.getEnd() > System.currentTimeMillis()) {
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
                }*/
            }
        }
    }

    /**
     * 获取当前年份、当前学期、当前星期几的所有课程
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
                                mhandler.post(new myRunnable(list.get(i),(Service) mContext));
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
