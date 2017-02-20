package com.wpy.faxianbei.sk.activity.coursetable.model;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.model.IModelPup;
import com.wpy.faxianbei.sk.ui.wheelview.WheelView;
import com.wpy.faxianbei.sk.utils.Util;

import java.util.ArrayList;

/**
 * Created by peiyuwang on 17-1-11.
 */

public class ModelImplPupWeeks implements IModelPup {

    private PopupWindow mPopupWindow;

    private PupListener mListener;

    int week=1;

    public ModelImplPupWeeks(PupListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void showPupWindow(Context context,View loaction) {
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.pop_select_week, null);
            mPopupWindow = new PopupWindow(view, Util.dpToPx(context.getResources(), 320),
                    LinearLayout.LayoutParams.WRAP_CONTENT, true);
            initView(view);
        }
        mPopupWindow.showAtLocation(loaction.getRootView().getRootView().getRootView(), Gravity.CENTER, 0, 0);
    }

    public void initView(View view) {
        final WheelView wheelView = (WheelView) view.findViewById(R.id.pop_ac_coursetable_wheel_weeks);
        wheelView.setData(getWeeks());
        wheelView.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                week = id+1;
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.id_pop_select_week_cancel);
        Button btnConfirm = (Button) view.findViewById(R.id.id_pop_select_week_confirm);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selectSuccess(week);
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
    }

    public ArrayList<String> getWeeks() {
        ArrayList<String> weeks = new ArrayList<String>();
        for(int i=1;i<23;i++)
        {
            weeks.add("第 "+i+" 周");
        }
        return weeks;
    }

    public interface PupListener{
        public void selectSuccess(int week);
    }
}
