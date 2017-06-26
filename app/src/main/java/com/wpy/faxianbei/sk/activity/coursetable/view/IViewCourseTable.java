package com.wpy.faxianbei.sk.activity.coursetable.view;

import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;

/**
 * Created by peiyuwang on 17-2-20.
 */

public interface IViewCourseTable extends OnSuccessOrFail {
     void setWeek(int week);
     void setYeayAndSemester(int year,int semester,String message);
    void setDate(String[] date);
    void setMonth(String month);
}
