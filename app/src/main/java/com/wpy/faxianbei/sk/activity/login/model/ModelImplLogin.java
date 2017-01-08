package com.wpy.faxianbei.sk.activity.login.model;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.activity.base.BaseAsycTask;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;
import com.wpy.faxianbei.sk.activity.login.view.IViewLogin;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.Lessions;
import com.wpy.faxianbei.sk.entity.Lesson;
import com.wpy.faxianbei.sk.entity.LessonTable;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.Teacher;
import com.wpy.faxianbei.sk.utils.cqu.Crawler;
import com.wpy.faxianbei.sk.utils.general.StringToBaseDataType;
import com.wpy.faxianbei.sk.utils.lock.ErrorUtilTencent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by peiyuwang on 17-1-3.
 */

public class ModelImplLogin implements IModelLogin {

    private OnSuccessOrFail listener;

    public ModelImplLogin(OnSuccessOrFail listener) {
        this.listener = listener;
    }

    @Override
    public void Login(final String id, final String pass) {
        new BaseAsycTask<String, String, String, OnSuccessOrFail>(listener, new ErrorUtilTencent()) {
            @Override
            protected String doInBackground(String... strings) {
                    try {
                        AVUser.logIn(id, pass, SkUser.class);
                        return "登录成功";
                    } catch (AVException e) {
                        return errorUtil.getErrorString(e.getCode(),e.getMessage());
                    }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.contains("成功"))
                {
                    listener.onSuccess(s);
                    postInfo(id,pass,2016,0);

                }else{
                    listener.onFail(s);
                }
            }
        }.execute();
    }

    public void postInfo(final String id,final String pass,final int year, final int semester) {
        new BaseAsycTask<String, String, Integer, OnSuccessOrFail>(listener, new ErrorUtilTencent()) {

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(Integer integer) {

            }

            @Override
            protected Integer doInBackground(String... strings) {
                try {
                    List<Lessions> lessionses = null;
                    SkUser skUser = AVUser.getCurrentUser(SkUser.class);
                    if (skUser != null) {
                        Crawler crawler = new Crawler();
                        String selType = "STU";
                        // 密码账号
                        String username = id;
                        String userPassword = pass;
                        // 登录账号
                        crawler.stuLogin(selType, username, userPassword);
                        String allLesson = crawler.getLessonsHtml(year + "", semester + "");
                        String strAllLesson = JSON.parseObject(allLesson).getString("lessons");
                        lessionses = JSON.parseArray(strAllLesson, Lessions.class);
                        saveLessons(getLessons(lessionses));
                        saveTeachers(getTeachers(lessionses));
                        saveLessonTable(lessionses, year, semester);
                    }
                    return 0;
                } catch (Exception e) {
                    return 0;
                }
            }
        }.execute();
    }

    private void saveLessonTable(List<Lessions> list, int year, int semester) throws AVException {
        SkUser currentUser = AVUser.getCurrentUser(SkUser.class);
        if (currentUser != null) {
            AVRelation<LessonTable> relation = currentUser.getRelation("lesson");
            AVQuery<LessonTable> query = relation.getQuery(LessonTable.class);
            query.whereEqualTo("years", year);
            query.whereEqualTo("semester", semester);
            List<LessonTable> lessonTables = query.find();
            if (lessonTables == null || (lessonTables != null && lessonTables.isEmpty())) {
                for (Lessions lessons : list) {
                    String strteacher = lessons.getTeacher();
                    String strlesson = lessons.getLesson();
                    AVQuery<Teacher> teacherAVQuery = AVObject.getQuery(Teacher.class);
                    teacherAVQuery.whereEqualTo("name", strteacher);
                    AVQuery<Lesson> lessonAVQuery = AVObject.getQuery(Lesson.class);
                    lessonAVQuery.whereEqualTo("lesson", strlesson);
                    Teacher teacher = teacherAVQuery.getFirst();
                    Lesson lesson = lessonAVQuery.getFirst();
                    if (teacher != null && lesson != null) {
                        LessonTable lessonTable = new LessonTable();
                        lessonTable.setAvUser(AVUser.getCurrentUser());
                        lessonTable.setClassroom(lessons.getLocation());
                        lessonTable.setDay(lessons.getDay());
                        lessonTable.setTeacher(teacher);
                        lessonTable.setYears(year);
                        lessonTable.setSemester(semester);
                        lessonTable.setWeeks(StringToBaseDataType.getWeekArra(lessons.getWeek()));
                        lessonTable.setLesson(lesson);
                        lessonTable.setTime(lessons.getTime());
                        lessonTable.saveLesson();
                    }
                }
            }
        }
    }

    private Set<String> getLessons(List<Lessions> list) {
        Set<String> set = new HashSet<String>();
        for (Lessions lessions : list) {
            if (!lessions.getLesson().contains("体育")) {
                if (!set.contains(lessions.getLesson())) {
                    set.add(lessions.getLesson());
                }
            }
        }
        return set;
    }

    private void saveLessons(Set<String> set) throws AVException {
        Iterator<String> iterator = set.iterator();
        List<Lesson> listLesson = new ArrayList<>();
        while (iterator.hasNext()) {
            String strLesson = iterator.next();
            AVQuery<Lesson> query = AVObject.getQuery(Lesson.class);
            query.whereEqualTo("lesson", strLesson);
            List<Lesson> lessons = query.find();
            if ((lessons == null) || (lessons != null && lessons.isEmpty())) {
                Lesson lesson = new Lesson();
                lesson.setLesson(strLesson);
                listLesson.add(lesson);
            }
        }
        AVObject.saveAll(listLesson);
    }

    private Set<String> getTeachers(List<Lessions> list) {
        Set<String> set = new HashSet<String>();
        for (Lessions lessions : list) {
            if (!lessions.getLesson().contains("体育")) {
                if (!set.contains(lessions.getTeacher())) {
                    set.add(lessions.getTeacher());
                }
            }
        }
        return set;
    }

    private void saveTeachers(Set<String> set) throws AVException {
        Iterator<String> iterator = set.iterator();
        List<Teacher> listTeachers = new ArrayList<>();
        while (iterator.hasNext()) {
            String strName = iterator.next();
            AVQuery<Teacher> query = AVObject.getQuery(Teacher.class);
            query.whereEqualTo("name", strName);
            List<Teacher> teachers = query.find();
            if ((teachers == null) || (teachers != null && teachers.isEmpty())) {
                Teacher teacher = new Teacher();
                teacher.setName(strName);
                teacher.setEmail(null);
                listTeachers.add(teacher);
            }
        }
        AVObject.saveAll(listTeachers);
    }
}
