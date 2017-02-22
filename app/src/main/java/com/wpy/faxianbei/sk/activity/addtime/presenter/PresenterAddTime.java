package com.wpy.faxianbei.sk.activity.addtime.presenter;

import android.content.Context;
import android.view.View;

import com.wpy.faxianbei.sk.activity.addcourse.view.IViewCourse;
import com.wpy.faxianbei.sk.activity.addtime.model.ModelSelectTimeImpl;
import com.wpy.faxianbei.sk.activity.addtime.view.IViewAddTime;
import com.wpy.faxianbei.sk.activity.base.BasePresenter;

/**
 * Created by peiyuwang on 17-1-10.
 */

public class PresenterAddTime extends BasePresenter<IViewAddTime> implements ModelSelectTimeImpl.SelectTimeSuccess {

    private ModelSelectTimeImpl modelSelectTime;

    public PresenterAddTime() {
        modelSelectTime = new ModelSelectTimeImpl(this);
    }


    public void showPopTime(Context context, View location,int type) {
        modelSelectTime.showPopupWindow(context,location,type);
    }

    @Override
    public void selectTimeSuccess(String time, int type) {
        if (getViewInterface() != null) {
            getViewInterface().selectTimeSuccess(time, type);
        }
    }
}
