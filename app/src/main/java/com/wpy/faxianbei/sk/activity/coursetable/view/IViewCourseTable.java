package com.wpy.faxianbei.sk.activity.coursetable.view;

import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;

/**
 * Created by peiyuwang on 17-2-20.
 */

public interface IViewCourseTable extends OnSuccessOrFail {
    public void setWeek(int week);
    public void setYeayAndSemester(int year,int semester,String message);
}
