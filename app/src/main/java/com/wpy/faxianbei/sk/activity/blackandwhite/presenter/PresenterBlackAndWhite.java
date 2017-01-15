package com.wpy.faxianbei.sk.activity.blackandwhite.presenter;

import android.content.Context;

import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.blackandwhite.model.IModelBlackAndWhite;
import com.wpy.faxianbei.sk.activity.blackandwhite.model.ModelImplBlackAndWhite;
import com.wpy.faxianbei.sk.activity.blackandwhite.view.IViewBlackAndWhite;
import com.wpy.faxianbei.sk.utils.appinfo.bean.AppInfo;

import org.xutils.view.annotation.ContentView;

import java.util.List;

/**
 * Created by peiyuwang on 17-1-15.
 */

public class PresenterBlackAndWhite extends BasePresenter<IViewBlackAndWhite> implements ModelImplBlackAndWhite.IAppInfo{
    private IModelBlackAndWhite mModelBlackAndWhite;
    public PresenterBlackAndWhite() {
        mModelBlackAndWhite=new ModelImplBlackAndWhite(this);
    }
    public enum BlackOrWhite {
        BLACK, WHITE
    }
    @Override
    public void loadSuccessBlack(List<AppInfo> list) {
        if(getViewInterface()!=null)
        {
            getViewInterface().showBlack(list);
        }
    }

    @Override
    public void loadSuccessWhite(List<AppInfo> list) {
        if(getViewInterface()!=null)
        {
            getViewInterface().showWhite(list);
        }
    }

    @Override
    public void loadFail(String message) {
    }

    public void showDialog(Context context,BlackOrWhite type){
        mModelBlackAndWhite.showDialog(context,type);
    }
}
