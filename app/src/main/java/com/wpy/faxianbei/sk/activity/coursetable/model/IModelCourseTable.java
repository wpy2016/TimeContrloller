package com.wpy.faxianbei.sk.activity.coursetable.model;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by peiyuwang on 17-2-20.
 */

public interface IModelCourseTable {
     String getLesson(int column, int raw, int week,int year,int semester);
    String getDay(int column);
    String getTime(int raw);
    void getDataFromInternet(int year,int semester);
    TextView getTextView(int column, int raw, Object ac);
    String getCurrentSemester(Context context);
    int getCurrentWeek(Context context);
}
