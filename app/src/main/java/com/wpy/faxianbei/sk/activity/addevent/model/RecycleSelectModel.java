package com.wpy.faxianbei.sk.activity.addevent.model;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.utils.Util;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by wangpeiyu on 2017/6/29.
 */

public class RecycleSelectModel implements View.OnClickListener {

    private PopupWindow mPopupWindow;

    private SelectRecycleSuccess recycleSuccess;

    //共8个，第0个表示是否是不重复的，0表示不重复，1表示重复
    private int recycle[];

    //拿着这个PopWindow的所有控件
    ViewHolder holder;

    public RecycleSelectModel(SelectRecycleSuccess listen) {
        this.recycleSuccess = listen;
        recycle = new int[8];
        initRecycle();
    }

    private void initRecycle() {
        for (int i = 0; i < 8; i++) {
            recycle[i] = 0;
        }
    }

    public void showPopWindow(Context context, View location) {
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycle_pop, null);
            mPopupWindow = new PopupWindow(view, Util.dip2px(context, 240), LinearLayout.LayoutParams.WRAP_CONTENT, true);
            initPopupWindow(view);
        }
        initRecycleImage();
        initRecycle();
        mPopupWindow.showAtLocation(location, Gravity.CENTER, 0, 0);
    }

    private void initPopupWindow(View view) {
        holder = new ViewHolder();
        x.view().inject(holder, view);
        holder.mllMonday.setOnClickListener(this);
        holder.mllTuesday.setOnClickListener(this);
        holder.mllWednesday.setOnClickListener(this);
        holder.mllThursday.setOnClickListener(this);
        holder.mllFirday.setOnClickListener(this);
        holder.mllSaturday.setOnClickListener(this);
        holder.mllSunday.setOnClickListener(this);
        holder.mllNoRecycle.setOnClickListener(this);
        holder.mivConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_recycle_ll_monday:
                if (0 == recycle[1]) {
                    recycle[1] = 1;
                    recycle[0] = 1;
                    holder.mivMonday.setVisibility(View.VISIBLE);
                    holder.mivNoRecycle.setVisibility(View.INVISIBLE);
                } else {
                    recycle[1] = 0;
                    holder.mivMonday.setVisibility(View.INVISIBLE);
                    updateRecycleZero();
                }
                break;
            case R.id.id_recycle_ll_tuesday:
                if (0 == recycle[2]) {
                    recycle[2] = 1;
                    recycle[0] = 1;
                    holder.mivTuesday.setVisibility(View.VISIBLE);
                    holder.mivNoRecycle.setVisibility(View.INVISIBLE);
                } else {
                    recycle[2] = 0;
                    holder.mivTuesday.setVisibility(View.INVISIBLE);
                    updateRecycleZero();
                }
                break;
            case R.id.id_recycle_ll_wednesday:
                if (0 == recycle[3]) {
                    recycle[3] = 1;
                    recycle[0] = 1;
                    holder.mivWednesday.setVisibility(View.VISIBLE);
                    holder.mivNoRecycle.setVisibility(View.INVISIBLE);
                } else {
                    recycle[3] = 0;
                    holder.mivWednesday.setVisibility(View.INVISIBLE);
                    updateRecycleZero();
                }
                break;
            case R.id.id_recycle_ll_thursday:
                if (0 == recycle[4]) {
                    recycle[4] = 1;
                    recycle[0] = 1;
                    holder.mivThursday.setVisibility(View.VISIBLE);
                    holder.mivNoRecycle.setVisibility(View.INVISIBLE);
                } else {
                    recycle[4] = 0;
                    holder.mivThursday.setVisibility(View.INVISIBLE);
                    updateRecycleZero();
                }
                break;
            case R.id.id_recycle_ll_firday:
                if (0 == recycle[5]) {
                    recycle[5] = 1;
                    recycle[0] = 1;
                    holder.mivFirday.setVisibility(View.VISIBLE);
                    holder.mivNoRecycle.setVisibility(View.INVISIBLE);
                } else {
                    recycle[5] = 0;
                    holder.mivFirday.setVisibility(View.INVISIBLE);
                    updateRecycleZero();
                }
                break;
            case R.id.id_recycle_ll_saturday:
                if (0 == recycle[6]) {
                    recycle[6] = 1;
                    recycle[0] = 1;
                    holder.mivSaturday.setVisibility(View.VISIBLE);
                    holder.mivNoRecycle.setVisibility(View.INVISIBLE);
                } else {
                    recycle[6] = 0;
                    holder.mivSaturday.setVisibility(View.INVISIBLE);
                    updateRecycleZero();
                }
                break;
            case R.id.id_recycle_ll_sunday:
                if (0 == recycle[7]) {
                    recycle[7] = 1;
                    recycle[0] = 1;
                    holder.mivSunday.setVisibility(View.VISIBLE);
                    holder.mivNoRecycle.setVisibility(View.INVISIBLE);
                } else {
                    recycle[7] = 0;
                    holder.mivSunday.setVisibility(View.INVISIBLE);
                    updateRecycleZero();
                }
                break;
            case R.id.id_recycle_ll_no_recycle:
                initRecycle();
                initRecycleImage();
                break;
            case R.id.id_recycle_iv_confirm:
                mPopupWindow.dismiss();
                recycleSuccess.selectRecycleSuccess(recycle);
                break;
        }
    }

    private void updateRecycleZero() {
        int result = 0;
        for (int i = 1; i < 8; i++) {
            result = result | recycle[i];
        }
        recycle[0] = result;
        if(0==result){
            holder.mivNoRecycle.setVisibility(View.VISIBLE);
        }else{
            holder.mivNoRecycle.setVisibility(View.INVISIBLE);
        }
    }

    private void initRecycleImage() {
        holder.mivMonday.setVisibility(View.INVISIBLE);
        holder.mivTuesday.setVisibility(View.INVISIBLE);
        holder.mivWednesday.setVisibility(View.INVISIBLE);
        holder.mivThursday.setVisibility(View.INVISIBLE);
        holder.mivFirday.setVisibility(View.INVISIBLE);
        holder.mivSaturday.setVisibility(View.INVISIBLE);
        holder.mivSunday.setVisibility(View.INVISIBLE);
        holder.mivNoRecycle.setVisibility(View.VISIBLE);
    }

    public interface SelectRecycleSuccess {
        void selectRecycleSuccess(int recycle[]);
    }

    private class ViewHolder {
        @ViewInject(R.id.id_recycle_ll_monday)
        RelativeLayout mllMonday;
        @ViewInject(R.id.id_recycle_ll_tuesday)
        RelativeLayout mllTuesday;
        @ViewInject(R.id.id_recycle_ll_wednesday)
        RelativeLayout mllWednesday;
        @ViewInject(R.id.id_recycle_ll_thursday)
        RelativeLayout mllThursday;
        @ViewInject(R.id.id_recycle_ll_firday)
        RelativeLayout mllFirday;
        @ViewInject(R.id.id_recycle_ll_saturday)
        RelativeLayout mllSaturday;
        @ViewInject(R.id.id_recycle_ll_sunday)
        RelativeLayout mllSunday;
        @ViewInject(R.id.id_recycle_ll_no_recycle)
        RelativeLayout mllNoRecycle;
        @ViewInject(R.id.id_recycle_iv_monday)
        ImageView mivMonday;
        @ViewInject(R.id.id_recycle_iv_tuesday)
        ImageView mivTuesday;
        @ViewInject(R.id.id_recycle_iv_wednesday)
        ImageView mivWednesday;
        @ViewInject(R.id.id_recycle_iv_thursday)
        ImageView mivThursday;
        @ViewInject(R.id.id_recycle_iv_firday)
        ImageView mivFirday;
        @ViewInject(R.id.id_recycle_iv_saturday)
        ImageView mivSaturday;
        @ViewInject(R.id.id_recycle_iv_sunday)
        ImageView mivSunday;
        @ViewInject(R.id.id_recycle_iv_no_recycle)
        ImageView mivNoRecycle;
        @ViewInject(R.id.id_recycle_iv_confirm)
        ImageView mivConfirm;
    }
}
