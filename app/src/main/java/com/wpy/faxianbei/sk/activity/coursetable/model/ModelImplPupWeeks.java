package com.wpy.faxianbei.sk.activity.coursetable.model;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.model.IModelPup;
import com.wpy.faxianbei.sk.ui.wheelview.WheelView;
import com.wpy.faxianbei.sk.utils.Util;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by peiyuwang on 17-1-11.
 */

public class ModelImplPupWeeks implements IModelPup {

    private PopupWindow mPopupWindow;

    private PupListener mListener;

    private Context context;

    private  int week=1;

    public ModelImplPupWeeks(PupListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void showPupWindow(Context context,View loaction) {
        this.context = context;
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
        final EditText editText = (EditText) view.findViewById(R.id.id_pop_select_week_et_week);
        int currentweek = (int) Math.ceil((double)((System.currentTimeMillis()-Long.parseLong(SharePreferenceUtil.instantiation.getWeek(context)))/(1000*60*60*24*7.0)));
        editText.setText(currentweek+"");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty())
                {
                    int currentweek2 = Integer.parseInt(editText.getText().toString())-1;
                    //如果单单是这样写currentweek2*7*24*60*60*1000是错误的，因为这样表示的是整数类型，整数类型不能表示这么大
                    //所以必须要这样写currentweek2*7*24*60*60*1000l，加上l表示长整形
                    long first = System.currentTimeMillis()-currentweek2*7*24*60*60*1000l;
                    SharePreferenceUtil.instantiation.saveFirstWeek(context,first+"");
                }
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
