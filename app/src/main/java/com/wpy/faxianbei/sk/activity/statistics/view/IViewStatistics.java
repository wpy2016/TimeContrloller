package com.wpy.faxianbei.sk.activity.statistics.view;

import com.jn.chart.data.Entry;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;

import java.util.ArrayList;

/**
 * Created by peiyuwang on 17-1-15.
 */

public interface IViewStatistics extends OnSuccessOrFail{
    public void loadSuccess(ArrayList<String> x, ArrayList<Entry> y1, ArrayList<Entry> y2);
    public void screenShot(String path);
}
