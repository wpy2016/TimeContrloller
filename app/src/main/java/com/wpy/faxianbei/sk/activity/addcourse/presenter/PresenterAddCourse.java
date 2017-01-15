package com.wpy.faxianbei.sk.activity.addcourse.presenter;
import android.content.Context;
import android.view.View;
import com.wpy.faxianbei.sk.activity.addcourse.model.IModelAddCourse;
import com.wpy.faxianbei.sk.activity.addcourse.model.IModelPup;
import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplAddCourse;
import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplPupCourse;
import com.wpy.faxianbei.sk.activity.addcourse.view.IViewCourse;
import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;

/**
 * Created by peiyuwang on 17-1-10.
 */

public class PresenterAddCourse extends BasePresenter<IViewCourse> implements ModelImplPupCourse.PupListener,OnSuccessOrFail{

    private IModelAddCourse mModelCourse;

    private IModelPup mModelPup;

    public PresenterAddCourse() {
        mModelCourse = new ModelImplAddCourse(this);
        mModelPup = new ModelImplPupCourse(this);
    }

    @Override
    public void selectSuccess(String message) {
        if(getViewInterface()!=null)
        {
            getViewInterface().setSemester(message);
        }
    }

    public void showPupWindowForSemester(Context context, View location){
        mModelPup.showPupWindow(context,location);
    }

    public void importCourse(String semester){
        mModelCourse.importCourse(ModelImplPupCourse.getYear(semester),ModelImplPupCourse.getSemester(semester));
    }

    @Override
    public void showProgress() {
        if(getViewInterface()!=null)
        {
            getViewInterface().showProgress();
        }
    }

    @Override
    public void dimissProgress() {
        if(getViewInterface()!=null)
        {
         getViewInterface().dimissProgress();
        }
    }

    @Override
    public void onSuccess(String message) {
        if(getViewInterface()!=null)
        {
            getViewInterface().onSuccess(message);
        }
    }

    @Override
    public void onFail(String message) {
        if(getViewInterface()!=null)
        {
            getViewInterface().onFail(message);
        }
    }
}
