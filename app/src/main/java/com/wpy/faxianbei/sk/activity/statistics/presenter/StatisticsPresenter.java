package com.wpy.faxianbei.sk.activity.statistics.presenter;

import android.content.Context;

import com.jn.chart.data.Entry;
import com.jn.chart.manager.LineChartManager;
import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.statistics.model.IModelStatistics;
import com.wpy.faxianbei.sk.activity.statistics.model.ModelImplStatistics;
import com.wpy.faxianbei.sk.activity.statistics.view.IViewStatistics;

import java.util.ArrayList;

/**
 * Created by peiyuwang on 17-1-15.
 */

public class StatisticsPresenter extends BasePresenter<IViewStatistics> implements ModelImplStatistics.loadResult {
    private IModelStatistics mModelStatistics;

    public StatisticsPresenter() {
        this.mModelStatistics = new ModelImplStatistics(this);
    }

    public void loadDate(){
        mModelStatistics.loadDate();
    }


    public void  screenShot(Context context){
        String path=mModelStatistics.screenShot(context);
        if(getViewInterface()!=null)
        {
            getViewInterface().screenShot(path);
        }
    }


    /**
     *
     * 接口
     */


    @Override
    public void loadSuccess(ArrayList<String> x, ArrayList<Entry> y1, ArrayList<Entry> y2) {
        if(getViewInterface()!=null)
        {
            getViewInterface().loadSuccess(x,y1,y2);
        }
    }

    @Override
    public void loadFail(String message) {
        if(getViewInterface()!=null)
        {
            getViewInterface().onFail(message);
        }
    }

    public void setTime(){
        getViewInterface().setTime(mModelStatistics.getDate());
    }

}
