package com.wpy.faxianbei.sk.entity;

/**
 * Created by peiyuwang on 16-12-18.
 */

public class StuUser {

    /**
     * 学号
     */
    private String studentId;


    /**
     * 生日
     */
    private String studentBirthDay;

    /**
     * 性别
     */
    private String studentGender;

    /**
     * 专业
     */
    private String studentMajor;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 民族
     */
    private String studentNation;

    /**
     * 学院
     */
    private String studentAcademy;

    /**
     * 班级
     */
    private String studentClass;

    public StuUser(String studentId, String studentBirthDay, String studentMajor,
                   String studentGender,
                   String studentName, String studentNation, String studentAcademy,
                   String studentClass) {
        this.studentId = studentId;
        this.studentBirthDay = studentBirthDay;
        this.studentMajor = studentMajor;
        this.studentGender = studentGender;
        this.studentName = studentName;
        this.studentNation = studentNation;
        this.studentAcademy = studentAcademy;
        this.studentClass = studentClass;
    }

    public StuUser() {
    }

    public void setStudentId(String studentId){
        this.studentId = studentId;
    }
    public String getStudentId(){
        return this.studentId;
    }
    public void setStudentBirthDay(String studentBirthDay){
        this.studentBirthDay = studentBirthDay;
    }
    public String getStudentBirthDay(){
        return this.studentBirthDay;
    }
    public void setStudentGender(String studentGender){
        this.studentGender = studentGender;
    }
    public String getStudentGender(){
        return this.studentGender;
    }
    public void setStudentMajor(String studentMajor){
        this.studentMajor = studentMajor;
    }
    public String getStudentMajor(){
        return this.studentMajor;
    }
    public void setStudentName(String studentName){
        this.studentName = studentName;
    }
    public String getStudentName(){
        return this.studentName;
    }
    public void setStudentNation(String studentNation){
        this.studentNation = studentNation;
    }
    public String getStudentNation(){
        return this.studentNation;
    }
    public void setStudentAcademy(String studentAcademy){
        this.studentAcademy = studentAcademy;
    }
    public String getStudentAcademy(){
        return this.studentAcademy;
    }
    public void setStudentClass(String studentClass){
        this.studentClass = studentClass;
    }
    public String getStudentClass(){
        return this.studentClass;
    }
}
