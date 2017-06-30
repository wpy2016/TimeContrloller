package com.wpy.faxianbei.sk.activity.addevent.model;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.ui.wheelview.RecycleWeekPicker;
import com.wpy.faxianbei.sk.utils.Util;

/**
 * Created by wangpeiyu on 2017/6/29.
 */

public class SelectRecycleWeekPickerModel {

    private PopupWindow mPopupWindow;

    SelectRecycleWeek selectRecycleWeek;


    public SelectRecycleWeekPickerModel(SelectRecycleWeek listen)
    {
        this.selectRecycleWeek=listen;
    }

    public void showPopWindow(Context context, View location){
        if(mPopupWindow==null){
            View view= LayoutInflater.from(context).inflate(R.layout.pop_select_recycle_week,null);
            mPopupWindow=new PopupWindow(view, Util.dip2px(context,240), LinearLayout.LayoutParams.WRAP_CONTENT,true);
            initPopupWindow(view);
        }
        mPopupWindow.showAtLocation(location, Gravity.CENTER,0,0);
    }

    private void initPopupWindow(View view) {
        final RecycleWeekPicker dayTimePicker= (RecycleWeekPicker) view.findViewById(R.id.id_pop_select_recycle_week);
        Button btnConfirm= (Button) view.findViewById(R.id.id_pop_select_recycle_week_confirm);
        Button btnCancel= (Button) view.findViewById(R.id.id_pop_select_recycle_week_cancel);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                selectRecycleWeek.selectRecycleSuccess(dayTimePicker.toString());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

    }
    public interface SelectRecycleWeek {
        void selectRecycleSuccess(String time);
    }
}
