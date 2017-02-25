package com.wpy.faxianbei.sk.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by wangpeiyu on 2017/2/25.
 */

@Table(name = "openrecord")
public class openRecord {

    @Column(name = "id",autoGen = true,isId = true)
    private long id;

    @Column(name = "year")
    private String year;

    @Column(name = "month")
    private String month;

    @Column(name = "day")
    private String day;

    @Column(name = "hour")
    private String hour;

    @Column(name = "minute")
    private String minute;

    @Column(name = "opentime")
    private long opentime;


    @Column(name = "type")
    private String type;

    public openRecord() {
    }

    public openRecord(String year, String month, String day, String hour, String minute, long opentime, String type) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.opentime = opentime;
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getType() {
        return type;
    }

    public long getOpentime() {
        return opentime;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setOpentime(long opentime) {
        this.opentime = opentime;
    }

    public void setType(String type) {
        this.type = type;
    }
}
