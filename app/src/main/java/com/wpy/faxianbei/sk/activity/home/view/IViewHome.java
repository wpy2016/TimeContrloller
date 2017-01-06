package com.wpy.faxianbei.sk.activity.home.view;

import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;

/**
 * Created by peiyuwang on 17-1-5.
 */

public interface IViewHome  extends OnSuccessOrFail{
    public void updateDate(String date,String day);
}
