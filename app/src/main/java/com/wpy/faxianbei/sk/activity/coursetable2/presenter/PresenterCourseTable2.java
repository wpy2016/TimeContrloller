package com.wpy.faxianbei.sk.activity.coursetable2.presenter;

import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.coursetable2.model.ModelCourseTableImpl;
import com.wpy.faxianbei.sk.activity.coursetable2.view.IViewCourstTable2;

/**
 * Created by wangpeiyu on 2017/6/27.
 */

public class PresenterCourseTable2 extends BasePresenter<IViewCourstTable2> {


    private ModelCourseTableImpl modelCourseTable;

    public PresenterCourseTable2(){
        modelCourseTable=new ModelCourseTableImpl();
    }
    public int[] getPos(int row){
        return modelCourseTable.getRow(row);
    }


    public String[] getSubStringByParts(int parts,String lesson){
        return modelCourseTable.getSubStringByParts(parts,lesson);
    }

}
