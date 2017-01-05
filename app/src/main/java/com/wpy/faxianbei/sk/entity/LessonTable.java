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
 * Created by peiyuwang on 17-1-2.
 */
@AVClassName("lessontable")
public class LessonTable extends AVObject {
    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    private static AVUser avUser;

    public LessonTable() {
        super();
    }

    public LessonTable(Parcel in) {
        super(in);
    }

    public void setLesson(AVObject lesson){
        put("lesson",lesson);
    }

    public void setTeacher(AVObject teacher){
        put("teacher",teacher);
    }

    public void setWeeks(List<Integer> weeks)
    {
        put("weeks",weeks);
    }
    public void setClassroom(String classroom){
        put("classroom",classroom);
    }
    public void setTime(String time){
        put("time",time);
    }
    public void setDay(String day)
    {
        put("day",day);
    }
    public void setYears(int years){
        put("years",years);
    }
    public int getYears(){
       return (int)get("years");
    }

    public void setSemester(int semester)
    {
        put("semester",semester);
    }
    public int getSemester(){
        return (int) get("semester");
    }
    public void setAvUser(AVUser user){
        avUser=user;
    }

    public String getDay(){
        return getString("day");

    }
    public String getTime(){
        return getString("time");
    }

    public String getClassroom(){
        return getString("classroom");
    }
    public List<Integer> getWeeks(){
        return (List<Integer>) get("weeks");
    }
    public Teacher getTeacher(){
        return (Teacher) get("teacher");
    }
    public Lesson getLesson(){
        return (Lesson) get("lesson");
    }
    public void saveLesson(){
        saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null)
                {
                    if(avUser!=null)
                    {
                        AVRelation<LessonTable> relation=avUser.getRelation("lesson");
                        relation.add(LessonTable.this);
                        avUser.saveInBackground();
                    }
                }
            }
        });
    }

    public static void saveAllLessons(final List<LessonTable> list){
        AVObject.saveAllInBackground(list, new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    AVRelation<LessonTable> relation=avUser.getRelation("lesson");
                    for(LessonTable lessonTable:list)
                    {
                        relation.add(lessonTable);
                    }
                    avUser.saveInBackground();
                }
            }
        });
    }
}
