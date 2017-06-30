package com.wpy.faxianbei.sk.activity.coursetable2.model;

/**
 * Created by wangpeiyu on 2017/6/27.
 */

public interface IModelCourseTable2 {

    int[] getRow(int row);
    String[] getSubStringByParts(int parts,String lesson);
    int getRowIntByString(String string);
}
