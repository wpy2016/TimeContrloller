package com.wpy.faxianbei.sk.activity.statistics.model;

import android.content.Context;

/**
 * Created by peiyuwang on 17-1-15.
 */

public interface IModelStatistics {

    void loadDate(Context context);

    String getDate();

    String screenShot(Context context);

    float calcuNeedToLock(Context context,long currentTimeMillis);

    float calcuMinutes(Context context,long currentTimeMillis);

    float getEffiency(Context context,long currentTimeMillis);

    float getOpenTime(long currentTimeMillis);

}
