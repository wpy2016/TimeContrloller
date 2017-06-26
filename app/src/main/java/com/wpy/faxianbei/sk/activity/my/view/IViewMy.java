package com.wpy.faxianbei.sk.activity.my.view;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by peiyuwang on 17-1-5.
 */

public interface IViewMy {
    public void setName(String name);
    public void setSchool(String school);
    public void setAcademic(String academic);
    public void setHeadimg(Bitmap bitmap);
    public void setList(List<String> list);
    public void setSex(String grade);
}
