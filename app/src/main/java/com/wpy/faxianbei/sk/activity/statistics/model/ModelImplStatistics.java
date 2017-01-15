package com.wpy.faxianbei.sk.activity.statistics.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.jn.chart.data.Entry;
import com.jn.chart.manager.LineChartManager;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.utils.general.FileUtil;
import com.wpy.faxianbei.sk.utils.general.ImageTools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by peiyuwang on 17-1-15.
 */

public class ModelImplStatistics implements IModelStatistics {

    private loadResult mReslutListener;


    public ModelImplStatistics(loadResult mReslutListener) {
        this.mReslutListener = mReslutListener;
    }

    @Override
    public void loadDate() {
        String[] x = {"1","2","3","4","5","6","7"};
        String[] y1 = {"122.00","234.34","85.67","117.90","332.33","113.33","120.78"};
        String[] y2 = {"62.00","134.34","35.67","87.90","232.33","83.33","40.78"};
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            xValues.add(x[i]);
        }

        //设置y轴的数据
        ArrayList<Entry> yValue = new ArrayList<>();
        for (int i = 0; i < y1.length; i++) {
            yValue.add(new Entry(Float.parseFloat(y1[i]), i));
        }
        //设置第二条折线y轴的数据
        ArrayList<Entry> yValue1 = new ArrayList<>();
        for (int i = 0; i < y2.length; i++) {
            yValue1.add(new Entry(Float.parseFloat(y2[i]),  i));
        }
        mReslutListener.loadSuccess(xValues,yValue,yValue1);
    }

    /**
     * 截屏
     * @param context
     * @return
     */
    @Override
    public String screenShot(Context context) {
        String path="";
        // 获取屏幕
        View dView = ((Activity)context).getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if(bmp!=null)
        {
            File file = new File(SKApplication.mSavePath, "screenshot");
            if (!file.exists()) {
                file.mkdir();
            }
            path= SKApplication.mSavePath+"/screenshot/"+System.currentTimeMillis() + ".png";
            ImageTools.saveBitmapToSDCard(bmp,path);
        }
        return path;
    }

    @Override
    public String getDate()
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
        Date date=new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }

    public interface loadResult{
        public void loadSuccess(ArrayList<String> x, ArrayList<Entry> y1, ArrayList<Entry> y2);
        public void loadFail(String message);
    }
}
