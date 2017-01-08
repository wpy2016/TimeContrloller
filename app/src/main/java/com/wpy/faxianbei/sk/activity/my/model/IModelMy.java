package com.wpy.faxianbei.sk.activity.my.model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by peiyuwang on 17-1-5.
 */

public interface IModelMy {
    public String getName();
    public String getSchool();
    public String getAcademic();
    public String getGrade();
    public void getHeadimg();
    public List<String> getList();
    public void logout();

}

