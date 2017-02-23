package com.wpy.faxianbei.sk.activity.statistics.model;

import android.content.Context;

/**
 * Created by peiyuwang on 17-1-15.
 */

public interface IModelStatistics {
     void loadDate();
     String getDate();
     String screenShot(Context context);
     float calcuNeedToLock(Context context);
     float calcuMinutes(Context context);
    float getEffiency(Context context);
     float getOpenTime();
}
