package com.wpy.faxianbei.sk.activity.blackandwhite.model;

import android.content.Context;

import com.wpy.faxianbei.sk.activity.blackandwhite.presenter.PresenterBlackAndWhite;

/**
 * Created by peiyuwang on 17-1-15.
 */

public interface IModelBlackAndWhite {
    public void showDialog(Context context, PresenterBlackAndWhite.BlackOrWhite type);
}
