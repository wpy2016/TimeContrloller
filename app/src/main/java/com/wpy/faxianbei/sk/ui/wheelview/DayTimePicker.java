package com.wpy.faxianbei.sk.ui.wheelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.wpy.faxianbei.sk.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DayTimePicker extends LinearLayout {
    private WheelView mWheelMinute;
    private WheelView mWheelHour;
    private int mHour;
    private int mMinute;
    private int layout_id;

    private WheelView.OnSelectListener mMinuteListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int minute, String text) {
            mMinute=minute;
        }

        @Override
        public void selecting(int id, String text) {
        }
    };
    private WheelView.OnSelectListener mHourListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int hour, String text) {
                mHour = hour;
        }

        @Override
        public void selecting(int day, String text) {
        }
    };
    private Context mContext;

    public DayTimePicker(Context context) {
        this(context, null);
    }

    public DayTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimePicker);
        int lenght = array.getIndexCount();
        for (int i = 0; i < lenght; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.TimePicker_layout_id) {
                layout_id = array.getResourceId(attr, R.layout.day_time_picker);
            }
        }
        array.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContext = getContext();
        LayoutInflater.from(mContext).inflate(layout_id, this);
        mWheelMinute = (WheelView) findViewById(R.id.minute);
        mWheelHour = (WheelView) findViewById(R.id.hour);
        mWheelMinute.setOnSelectListener(mMinuteListener);
        mWheelHour.setOnSelectListener(mHourListener);
        setDate();
    }


    /**
     * set WLQQTimePicker date
     *
     */
    public void setDate() {
        mWheelMinute.setData(getMinuteData());
        mWheelHour.setData(getHourData());
    }

    private ArrayList<String> getHourData() {
        ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < 24; i++) {
                list.add(i + "时");
            }
        return list;
    }

    private ArrayList<String> getMinuteData() {
        ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < 60; i++) {
                list.add(i + "分");
        }
        return list;
    }

    public String toString() {
     return mHour+":"+mMinute;
    }
}