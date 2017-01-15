package com.wpy.faxianbei.sk.activity.blackandwhite.view;

import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;
import com.wpy.faxianbei.sk.utils.appinfo.bean.AppInfo;

import java.util.List;

/**
 * Created by peiyuwang on 17-1-15.
 */

public interface IViewBlackAndWhite extends OnSuccessOrFail{
    public void showBlack(List<AppInfo> list);
    public void showWhite(List<AppInfo> list);
}
