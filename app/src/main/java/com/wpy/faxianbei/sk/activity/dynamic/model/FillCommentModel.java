package com.wpy.faxianbei.sk.activity.dynamic.model;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.ui.wheelview.RecycleWeekPicker;
import com.wpy.faxianbei.sk.utils.Util;

/**
 * Created by wangpeiyu on 2017/6/29.
 */

public class FillCommentModel {

    private PopupWindow mPopupWindow;

    FillCommentListener fillCommentListener;

    Button btnConfirm;

    EditText text;

    Button btnCancel;

    public FillCommentModel(FillCommentListener listen)
    {
        this.fillCommentListener=listen;
    }

    public void showPopWindow(Context context, View location){
        if(mPopupWindow==null){
            View view= LayoutInflater.from(context).inflate(R.layout.pop_fill_comment,null);
            mPopupWindow=new PopupWindow(view, Util.dip2px(context,240), LinearLayout.LayoutParams.WRAP_CONTENT,true);
            initPopupWindow(view);
        }
        text.setText("");
        mPopupWindow.showAtLocation(location, Gravity.CENTER,0,0);
    }

    private void initPopupWindow(View view) {
         text= (EditText) view.findViewById(R.id.id_pop_fill_comment);
         btnConfirm= (Button) view.findViewById(R.id.id_pop_fill_comment_confirm);
         btnCancel= (Button) view.findViewById(R.id.id_pop_fill_comment_cancel);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                fillCommentListener.fillComplete(text.getText().toString());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

    }
    public interface FillCommentListener {
        void fillComplete(String time);
    }
}
