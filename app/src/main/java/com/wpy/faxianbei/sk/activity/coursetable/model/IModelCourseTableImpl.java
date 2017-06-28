package com.wpy.faxianbei.sk.activity.coursetable.model;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.wpy.faxianbei.sk.activity.coursetable.view.AcCourseTable;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.Lesson;
import com.wpy.faxianbei.sk.entity.LessonTable;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.Teacher;
import com.wpy.faxianbei.sk.entity.db.CourseTable;
import com.wpy.faxianbei.sk.utils.dateUtil.DateUtil;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.Date;
import java.util.List;

/**
 * Created by peiyuwang on 17-2-20.
 */

public class IModelCourseTableImpl implements IModelCourseTable {


    @Override
    public String getLesson(int column, int raw, int week, int year, int semester) {

        String lesson = "";
        if(SkUser.getCurrentUser(SkUser.class)!=null){
            try {
                List<CourseTable> list = SKApplication.getDbManager().selector(CourseTable.class).where(
                        WhereBuilder.b().and("stuid", "=", SkUser.getCurrentUser(SkUser.class).getSchoolId()).and("semester", "=", "" + semester)
                                .and("year", "=", "" + year).and("time", "=", getTime(raw)).and("day", "=", getDay(column))).findAll();
                if (list != null && !list.isEmpty()) {
                    for (CourseTable courseTable : list) {
                        String[] split = courseTable.getWeeks()
                                .replace("[", "")
                                .replace("]", "")
                                .replace(" ", "")
                                .split(",");
                        for (String s : split) {
                            if (s.equals(week + "")) {
                                lesson = courseTable.getCourse();
                            }
                        }

                    }
                }

            } catch (DbException e) {

            }
        }
        return lesson;
    }

    public boolean isNeedLoad(int year, int semester) {
        boolean need = false;
        if(SkUser.getCurrentUser(SkUser.class)!=null){
            try {
                List<CourseTable> list = SKApplication.getDbManager().selector(CourseTable.class).where(
                        WhereBuilder.b().and("stuid", "=", SkUser.getCurrentUser(SkUser.class).getSchoolId()).and("semester", "=", "" + semester)
                                .and("year", "=", "" + year)).findAll();
                if (list == null || list.isEmpty()) {
                    need = true;
                }
            } catch (DbException e) {

            }
        }
        return need;
    }

    @Override
    public String getDay(int column) {
        String day = "";
        switch (column) {
            case 1:
                day = "一";
                break;
            case 2:
                day = "二";
                break;
            case 3:
                day = "三";
                break;
            case 4:
                day = "四";
                break;
            case 5:
                day = "五";
                break;
            case 6:
                day = "六";
                break;
            case 7:
                day = "七";
                break;
        }
        return day;
    }

    @Override
    public String getTime(int raw) {

        String time = "";
        switch (raw) {
            case 1:
                time = "1-2节";
                break;
            case 2:
                time = "3-4节";
                break;
            case 3:
                time = "5-6节";
                break;
            case 4:
                time = "7-8节";
                break;
            case 5:
                time = "9-10节";
                break;
            case 6:
                time = "11-12节";
                break;
        }
        return time;
    }

    @Override
    public void getDataFromInternet(int year, int semester) {
        if (isNeedLoad(year, semester)) {
            final SkUser currentUser = SkUser.getCurrentUser(SkUser.class);
            if (currentUser != null) {
                final AVRelation<LessonTable> lesson = currentUser.getRelation("lesson");
                AVQuery<LessonTable> query = lesson.getQuery();
                query.include("teacher");
                query.include("lesson");
                query.findInBackground(new FindCallback<LessonTable>() {
                    @Override
                    public void done(List<LessonTable> list, AVException e) {
                        try {
                            for (LessonTable lesson : list) {
                                CourseTable courseTable = new CourseTable();
                                courseTable.setClassroom(lesson.getClassroom());
                                courseTable.setDays(lesson.getDay());
                                courseTable.setSemester(lesson.getSemester());
                                courseTable.setTime(lesson.getTime());
                                courseTable.setYear(lesson.getYears());
                                courseTable.setWeeks(lesson.getWeeks().toString());
                                courseTable.setUserSchoolId(currentUser.getSchoolId());
                                Lesson lessonName = lesson.getAVObject("lesson", Lesson.class);
                                courseTable.setCourse(lessonName.getLesson());
                                Teacher teacher = lesson.getAVObject("teacher", Teacher.class);
                                courseTable.setTeacher(teacher.getName());
                                SKApplication.getDbManager().save(courseTable);
                            }
                        } catch (Exception e1) {

                        }
                    }
                });
            }
        }
    }

    @Override
    public TextView getTextView(int column, int raw, Object ac) {
        TextView textView = null;
        Class activity = ac.getClass();
        try {
            textView = (TextView) activity.getField("mtvCourseTable" + column + raw).get(ac);
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {
        }
        return textView;
    }
public TextView getDayTextView(int dayIndex,Object ac){
    TextView textView = null;
    Class activity = ac.getClass();
    try {
        textView = (TextView) activity.getField("mtvDay" + dayIndex).get(ac);
    } catch (NoSuchFieldException e) {

    } catch (IllegalAccessException e) {
    }
    return textView;
}

    @Override
    public String getCurrentSemester(Context context) {
        return SharePreferenceUtil.instantiation.getSemester(context);
    }

    @Override
    public int getCurrentWeek(Context context) {
        return (int) Math.ceil((double) ((System.currentTimeMillis() - Long.parseLong(SharePreferenceUtil.instantiation.getWeek(context))) / (1000 * 60 * 60 * 24 * 7.0)));
    }

    /**
     * 返回当前这一周的日期
     * @return
     */
    public String[] getDate() {
        String[] date = new String[7];
        long now=System.currentTimeMillis();
        String day = DateUtil.getDay(now);
        int dayIndex=parseIntFormString(day);
        for(int i=0;i<7;i++){
           long offset= (i-dayIndex)*24*60*60*1000l;
            date[i]= DateUtil.getDateOnlyDay(now+offset);
        }
        return date;
    }

    private int parseIntFormString(String day) {
        if (day.contains("星期一")) {
            return 0;
        } else if (day.equals("星期二")) {
            return 1;
        } else if (day.equals("星期三")) {
            return 2;
        } else if (day.equals("星期四")) {
            return 3;
        } else if (day.equals("星期五")) {
            return 4;
        } else if (day.equals("星期六")) {
            return 5;
        } else {
            return 6;
        }
    }
}
