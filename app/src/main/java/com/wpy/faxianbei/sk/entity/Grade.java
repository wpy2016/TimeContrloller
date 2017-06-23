package com.wpy.faxianbei.sk.entity;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.util.List;

/**
 * Created by peiyuwang on 17-1-3.
 */
@AVClassName("grade")
public class Grade extends AVObject {

    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    public Grade() {
    }

    public Grade(Parcel in) {
        super(in);
    }

    public void setLesson(Lesson lesson) {
        put("lesson", lesson);
    }

    public void setTeacher(Teacher teacher) {
        put("teacher", teacher);
    }

    public void setWeek(int week) {
        put("week", week);
    }

    public void setClassroom(String classroom) {
        put("classroom", classroom);
    }

    public void setTime(String time) {
        put("time", time);
    }

    public void setDay(String day) {
        put("day", day);
    }

    public void setOpenScreenTime(long time) {
        put("opentime", time);
    }

    public void setGrade(float grade) {
        put("grade", grade);
    }

    public Lesson getLesson() {
        return (Lesson) get("lesson");
    }

    public Teacher getTeacher() {
        return (Teacher) get("teacher");
    }

    public int getWeek() {
        return getInt("week");
    }

    public String getClassroom() {
        return getString("classroom");
    }

    public String getTime() {
        return getString("time");
    }

    public String getDay() {
        return getString("day");
    }

    public long getOpenScreenTime() {
        return (long) get("opentime");
    }

    public float getGrade() {
        return (float) get("grade");
    }

    public void saveGrade() {
        saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    AVUser currentUser = AVUser.getCurrentUser();
                    AVRelation<AVObject> grade = currentUser.getRelation("grade");
                    grade.add(Grade.this);
                    currentUser.saveInBackground();
                }
            }
        });
    }

    public static void saveAllGrade(final List<Grade> list) {
        AVObject.saveAllInBackground(list, new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    AVUser currentUser = AVUser.getCurrentUser();
                    if (currentUser != null) {
                        AVRelation<Grade> gradeAVRelation = currentUser.getRelation("grade");
                        for (Grade grade : list) {
                            gradeAVRelation.add(grade);
                        }
                        currentUser.saveInBackground();
                    }
                }
            }
        });
    }
}

