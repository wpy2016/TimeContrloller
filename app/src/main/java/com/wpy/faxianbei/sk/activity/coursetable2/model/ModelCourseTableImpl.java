package com.wpy.faxianbei.sk.activity.coursetable2.model;

import com.wpy.faxianbei.sk.utils.general.StringToBaseDataType;

/**
 * Created by wangpeiyu on 2017/6/27.
 */

public class ModelCourseTableImpl implements IModelCourseTable2 {


    @Override
    public int[] getRow(int row) {
        switch (row){
            case 1:
                return new int[]{8,9};
            case 2:
                return new int[]{10,11};
            case 3:
                return new int[]{14,15};
            case 4:
                return new int[]{16,17};
            case 5:
                return new int[]{19,20};
            case 6:
                return new int[]{21,22};
        }
        return new int[]{};
    }


    /**
     *
     * @param string 12:00
     * @return
     */
    public int getRowIntByString(String string){
        String parts[]=string.split(":");
        return StringToBaseDataType.convertToInt(parts[0],0);
    }

    @Override
    public String[] getSubStringByParts(int parts,String lesson) {
        String[] lessonSub=new String[parts];
        int lessonSize=lesson.length();
        for(int i=0;i<parts;i++){
            lessonSub[i]=lesson.substring(i*lessonSize/parts,(i+1)*lessonSize/parts);
        }
        return lessonSub;
    }
}
