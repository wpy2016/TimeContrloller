package com.wpy.faxianbei.sk.activity.statistics.view;

import com.jn.chart.data.Entry;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;

import java.util.ArrayList;

/**
 * Created by peiyuwang on 17-1-15.
 */

public interface IViewStatistics extends OnSuccessOrFail{
     void loadSuccess(ArrayList<String> x, ArrayList<Entry> y1, ArrayList<Entry> y2);
     void screenShot(String path);
     void setTime(String time);
    void setNeedToLock(float neddtolock);
    void setLock(float lock);
    void setEffiency(float effiency);
}
