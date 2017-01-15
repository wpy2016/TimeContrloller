package com.wpy.faxianbei.sk.activity.addcourse.model;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.ui.wheelview.SemesterPicker;
import com.wpy.faxianbei.sk.utils.Util;

/**
 * Created by peiyuwang on 17-1-11.
 */

public class ModelImplPupCourse implements IModelPup {

    private PopupWindow mPopupWindow;

    private PupListener mListener;

    public ModelImplPupCourse(PupListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void showPupWindow(Context context,View loaction) {
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.pop_select_semester, null);
            mPopupWindow = new PopupWindow(view, Util.dpToPx(context.getResources(), 320),
                    LinearLayout.LayoutParams.WRAP_CONTENT, true);
            initView(view);
        }
        mPopupWindow.showAtLocation(loaction.getRootView().getRootView().getRootView(), Gravity.CENTER, 0, 0);
    }

    public void initView(View view){
        final SemesterPicker semesterPicker = (SemesterPicker) view.findViewById(R.id.id_pop_select_semester);
        Button btnCancel = (Button) view.findViewById(R.id.id_pop_select_semester_cancel);
        Button btnConfirm = (Button) view.findViewById(R.id.id_pop_select_semester_confirm);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selectSuccess(semesterPicker.toString());
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
    }

    public interface PupListener{
        public void selectSuccess(String message);
    }

    public static int getYear(String semester){
        if(semester.length()>4)
         return  Integer.parseInt(semester.substring(0,4));
        else
            return 2016;//默认返回
    }

    public static int getSemester(String semester){
        String[] split = semester.split(" ");
        if(split.length==2)
        {
            if(split[1].equals("第一学期"))
            {
                return 0;
            }else{
                return 1;
            }
        }else{
            return  0;//默认是0
        }
    }

}
