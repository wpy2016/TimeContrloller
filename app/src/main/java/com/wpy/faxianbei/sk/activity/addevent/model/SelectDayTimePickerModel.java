package com.wpy.faxianbei.sk.activity.addevent.model;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.ui.wheelview.DayTimePicker;
import com.wpy.faxianbei.sk.utils.Util;

/**
 * Created by wangpeiyu on 2017/6/29.
 */

public class SelectDayTimePickerModel {

    private PopupWindow mPopupWindow;

    SelectDayTimeSuccess selectDayTimeSuccess;

    int type;


    public SelectDayTimePickerModel(SelectDayTimeSuccess listen)
    {
        this.selectDayTimeSuccess=listen;
    }

    public void showPopWindow(Context context, View location, int type){
        this.type=type;
        if(mPopupWindow==null){
            View view= LayoutInflater.from(context).inflate(R.layout.pop_select_day_time,null);
            mPopupWindow=new PopupWindow(view, Util.dip2px(context,240), LinearLayout.LayoutParams.WRAP_CONTENT,true);
            initPopupWindow(view);
        }
        mPopupWindow.showAtLocation(location, Gravity.CENTER,0,0);
    }

    private void initPopupWindow(View view) {
        final DayTimePicker dayTimePicker= (DayTimePicker) view.findViewById(R.id.id_pop_select_daytime_tp_time);
        Button btnConfirm= (Button) view.findViewById(R.id.id_pop_select_daytime_confirm);
        Button btnCancel= (Button) view.findViewById(R.id.id_pop_select_daytime_cancel);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                selectDayTimeSuccess.selectTimeSuccess(dayTimePicker.toString(),type);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

    }
    public interface SelectDayTimeSuccess{
        void selectTimeSuccess(String time,int type);
    }
}
