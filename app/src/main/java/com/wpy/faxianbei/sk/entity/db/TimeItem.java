package com.wpy.faxianbei.sk.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by wangpeiyu on 2017/2/22.
 */

@Table(name = "timeitem")
public class TimeItem {

    @Column(name = "id",autoGen = true,isId = true)
    private long id;

    @Column(name = "start")
    private long start;

    @Column(name = "end")
    private long end;

    @Column(name = "model")
    private int model;

    public TimeItem(long start, long end, int model) {
        this.start = start;
        this.end = end;
        this.model = model;
    }

    public TimeItem() {


    }


    public long getEnd() {
        return end;
    }

    public long getStart() {
        return start;
    }

    public int getModel() {
        return model;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
