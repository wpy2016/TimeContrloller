package com.wpy.faxianbei.sk.activity.my.presenter;

import android.graphics.Bitmap;

import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.my.model.IModelMy;
import com.wpy.faxianbei.sk.activity.my.model.ModelImPlMy;
import com.wpy.faxianbei.sk.activity.my.view.IViewMy;

/**
 * Created by peiyuwang on 17-1-5.
 */

public class PresenterMy extends BasePresenter<IViewMy> implements ModelImPlMy.LoadHeadimg{

    private IModelMy modelMy;

    public PresenterMy() {
        modelMy = new ModelImPlMy(this);
    }

    public void setName(){
        if(getViewInterface()!=null)
        {
            getViewInterface().setName(modelMy.getName());
        }
    }

    public void setSchool(){
        if(getViewInterface()!=null)
        {
            getViewInterface().setSchool(modelMy.getSchool());
        }
    }

    public void setAcademic(){
    if(getViewInterface()!=null)
    {
        getViewInterface().setAcademic(modelMy.getAcademic());
    }
    }

    public void setGrade(){
        if(getViewInterface()!=null)
        {
            getViewInterface().setGrade(modelMy.getGrade());
        }
    }

    public void setHeadimg(){
        modelMy.getHeadimg();
    }

    @Override
    public void loadSuccess(Bitmap bitmap) {
        if(getViewInterface()!=null)
        {
            getViewInterface().setHeadimg(bitmap);
        }
    }

    public void logout(){
        modelMy.logout();
    }
}
