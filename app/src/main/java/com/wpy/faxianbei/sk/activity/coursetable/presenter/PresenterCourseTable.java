package com.wpy.faxianbei.sk.activity.coursetable.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplPupCourse;
import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.coursetable.model.IModelCourseTable;
import com.wpy.faxianbei.sk.activity.coursetable.model.IModelCourseTableImpl;
import com.wpy.faxianbei.sk.activity.coursetable.model.ModelImplPupWeeks;
import com.wpy.faxianbei.sk.activity.coursetable.view.IViewCourseTable;

/**
 * Created by peiyuwang on 17-2-20.
 */

public class PresenterCourseTable extends BasePresenter<IViewCourseTable> implements ModelImplPupWeeks.PupListener,ModelImplPupCourse.PupListener{
    private ModelImplPupWeeks modelImplPupWeek;
    private IModelCourseTable modelCourseTable;
    private ModelImplPupCourse modelImplPupCourse;
    public PresenterCourseTable() {
        this.modelImplPupWeek =new ModelImplPupWeeks(this);
        modelImplPupCourse = new ModelImplPupCourse(this);
        modelCourseTable = new IModelCourseTableImpl();
    }

    @Override
    public void selectSuccess(int week) {
        getViewInterface().setWeek(week);
    }

    public void showPup(Context context, View view){
        modelImplPupWeek.showPupWindow(context,view);
    }

    public void getDateFormInternet(int year,int semester){
        modelCourseTable.getDataFromInternet(year,semester);
    }

    public String getLesson(int column, int raw, int week,int year,int semester){
        return modelCourseTable.getLesson(column,raw,week,year,semester);
    }

    public String Day(int column)
    {
       return modelCourseTable.getDay(column);
    }

    public String getTime(int raw)
    {
        return modelCourseTable.getTime(raw);
    }

    public TextView getTextView(int column, int raw, Object ac){
        return modelCourseTable.getTextView(column,raw,ac);
    }

    public void showSemester(Context context,View view){
        modelImplPupCourse.showPupWindow(context,view);
    }

    @Override
    public void selectSuccess(String message) {
        getViewInterface().setYeayAndSemester(ModelImplPupCourse.getYear(message),ModelImplPupCourse.getSemester(message),message);
    }


}
