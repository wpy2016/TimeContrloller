package com.wpy.faxianbei.sk.activity.home.model;

import android.content.Context;

/**
 * Created by peiyuwang on 17-1-6.
 */

public interface IModelHome {
    public String getDate();
    public String getDay();
    public void StartService(Context context);
    public void StopService(Context context);
    public void toNext(Context context,Class<?> next,boolean isKill);
}
