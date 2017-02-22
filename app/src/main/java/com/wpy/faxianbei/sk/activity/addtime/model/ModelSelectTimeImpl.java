package com.wpy.faxianbei.sk.activity.addtime.model;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.entity.Grade;
import com.wpy.faxianbei.sk.ui.wheelview.TimePicker;
import com.wpy.faxianbei.sk.utils.Util;

import java.util.Date;

/**
 * Created by wangpeiyu on 2017/2/22.
 */

public class ModelSelectTimeImpl {

    private PopupWindow mPopupWindow;

    SelectTimeSuccess mSelectTimeSuccess;

    int type;

    public ModelSelectTimeImpl(SelectTimeSuccess selectTimeSuccess){
        mSelectTimeSuccess=selectTimeSuccess;

    }

    public void showPopupWindow(Context context, View location, int type)
    {
        this.type=type;
        if(mPopupWindow==null)
        {
            View view=LayoutInflater.from(context).inflate(R.layout.pop_select_time,null);
            mPopupWindow = new PopupWindow(view, Util.dip2px(context,320), LinearLayout.LayoutParams.WRAP_CONTENT,true);
            initPopupWindow(view);

        }
        mPopupWindow.showAtLocation(location, Gravity.CENTER,0,0);
    }

    private void initPopupWindow(View view) {
        final TimePicker timePicker= (TimePicker) view.findViewById(R.id.id_pop_select_time_tp_time);
        timePicker.setDate(new Date().getTime());
        Button btnCancel= (Button) view.findViewById(R.id.id_pop_select_time_cancel);
        Button btnConfirm= (Button) view.findViewById(R.id.id_pop_select_time_confirm);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectTimeSuccess.selectTimeSuccess(timePicker.toString(),type);
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.setOutsideTouchable(true);

    }

    public interface SelectTimeSuccess{
         void selectTimeSuccess(String time,int type);
    }
}
