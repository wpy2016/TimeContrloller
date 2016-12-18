package com.wpy.faxianbei.sk.entity;

/**
 * Created by peiyuwang on 16-12-18.
 */

public class Lessions {

    private String teacher;

    private String week;

    private String lesson;

    private String location;

    private String time;

    private String day;

    public Lessions() {
    }

    public Lessions(String teacher, String week, String location, String lesson,
                    String time, String day) {
        this.teacher = teacher;
        this.week = week;
        this.location = location;
        this.lesson = lesson;
        this.time = time;
        this.day = day;
    }

    public void setTeacher(String teacher){
        this.teacher = teacher;
    }
    public String getTeacher(){
        return this.teacher;
    }
    public void setWeek(String week){
        this.week = week;
    }
    public String getWeek(){
        return this.week;
    }
    public void setLesson(String lesson){
        this.lesson = lesson;
    }
    public String getLesson(){
        return this.lesson;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setDay(String day){
        this.day = day;
    }
    public String getDay(){
        return this.day;
    }
}
