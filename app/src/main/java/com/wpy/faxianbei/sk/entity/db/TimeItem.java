package com.wpy.faxianbei.sk.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by wangpeiyu on 2017/2/22.
 */

@Table(name = "timeitem")
public class TimeItem {

    @Column(name = "id", autoGen = true, isId = true)
    private long id;

    @Column(name = "start")
    private String start;

    @Column(name = "end")
    private String end;

    @Column(name = "model")
    private int model;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private int type;

    @Column(name = "monday")
    private int Monday;

    @Column(name = "tuesday")
    private int Tuesday;
    @Column(name = "wednesday")
    private int Wednesday;

    @Column(name = "thursday")
    private int ThursDay;

    @Column(name = "firday")
    private int Friday;

    @Column(name = "saturday")
    private int Saturday;
    @Column(name = "sunday")
    private int Sunday;

    @Column(name = "startweek")
    private int startWeek;

    @Column(name = "endweek")
    private int endWeek;
    @Column(name = "recycle")
    private int isRecycle;

    public TimeItem() {

    }

    public TimeItem(String start, String end, int model, String content, int type, int monday,
                    int tuesday, int wednesday, int thursDay, int friday, int saturday, int sunday, int startWeek, int endWeek, int isRecycle) {
        this.start = start;
        this.end = end;
        this.model = model;
        this.content = content;
        this.type = type;
        Monday = monday;
        Tuesday = tuesday;
        Wednesday = wednesday;
        ThursDay = thursDay;
        Friday = friday;
        Saturday = saturday;
        Sunday = sunday;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.isRecycle = isRecycle;
    }

    public long getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getModel() {
        return model;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getMonday() {
        return Monday;
    }

    public int getTuesday() {
        return Tuesday;
    }

    public int getWednesday() {
        return Wednesday;
    }

    public int getThursDay() {
        return ThursDay;
    }

    public int getFriday() {
        return Friday;
    }

    public int getSaturday() {
        return Saturday;
    }

    public int getSunday() {
        return Sunday;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public int getIsRecycle() {
        return isRecycle;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMonday(int monday) {
        Monday = monday;
    }

    public void setTuesday(int tuesday) {
        Tuesday = tuesday;
    }

    public void setWednesday(int wednesday) {
        Wednesday = wednesday;
    }

    public void setThursDay(int thursDay) {
        ThursDay = thursDay;
    }

    public void setFriday(int friday) {
        Friday = friday;
    }

    public void setSaturday(int saturday) {
        Saturday = saturday;
    }

    public void setSunday(int sunday) {
        Sunday = sunday;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public void setIsRecycle(int isRecycle) {
        this.isRecycle = isRecycle;
    }
}
