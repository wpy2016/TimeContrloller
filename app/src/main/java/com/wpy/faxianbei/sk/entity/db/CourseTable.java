package com.wpy.faxianbei.sk.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
/**
 * Created by peiyuwang on 17-2-20.
 */

/**
 * 本地数据库课程表
 */
@Table(name = "coursetable")
public class CourseTable {

    @Column(name = "id",autoGen = true,isId = true)
    private long id;

    @Column(name = "stuid")
    private String userSchoolId;

    @Column(name = "course")
    private String course;

    @Column(name = "week")
    private String weeks;

    @Column(name = "day")
    private String days;


    @Column(name = "teacher")
    private String teacher;

    @Column(name = "time")
    private String time;

    @Column(name = "year")
    private int year;

    @Column(name = "classroom")
    private String classroom;

    @Column(name = "semester")
    private int semester;


    public CourseTable() {
    }

    public CourseTable(String userSchoolId, String course, String weeks, String days,
                       String teacher, String time, int year,
                       String classroom, int semester) {
        this.userSchoolId = userSchoolId;
        this.course = course;
        this.weeks = weeks;
        this.days = days;
        this.teacher = teacher;
        this.time = time;
        this.year = year;
        this.classroom = classroom;
        this.semester = semester;
    }

    public String getUserSchoolId() {
        return userSchoolId;
    }

    public String getCourse() {
        return course;
    }

    public String getWeeks() {
        return weeks;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getDays() {
        return days;
    }

    public int getYear() {
        return year;
    }

    public String getTime() {
        return time;
    }

    public String getClassroom() {
        return classroom;
    }

    public int getSemester() {
        return semester;
    }

    public void setUserSchoolId(String userSchoolId) {
        this.userSchoolId = userSchoolId;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
