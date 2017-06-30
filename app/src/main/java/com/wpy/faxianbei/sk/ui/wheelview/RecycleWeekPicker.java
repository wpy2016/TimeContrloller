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

public class RecycleWeekPicker extends LinearLayout {
    private WheelView mWheelStartWeek;
    private WheelView mWheelEndWeek;
    private int startWeek=1;
    private int endWeek=1;
    private int layout_id;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(0x110==msg.what){
                mWheelEndWeek.resetData(updateEndWeek(startWeek));
                mWheelEndWeek.setDefault(0);
            }
        }
    };

    private WheelView.OnSelectListener mStartWeekListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int start, String text) {
            startWeek=start+1;
            endWeek=startWeek;
            mHandler.sendEmptyMessage(0x110);
        }

        @Override
        public void selecting(int id, String text) {
        }
    };
    private WheelView.OnSelectListener mEndWeekListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int end, String text) {
            endWeek=end+startWeek;
        }

        @Override
        public void selecting(int day, String text) {
        }
    };
    private Context mContext;

    public RecycleWeekPicker(Context context) {
        this(context, null);
    }

    public RecycleWeekPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimePicker);
        int lenght = array.getIndexCount();
        for (int i = 0; i < lenght; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.TimePicker_layout_id) {
                layout_id = array.getResourceId(attr, R.layout.recycle_week_picker);
            }
        }
        array.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContext = getContext();
        LayoutInflater.from(mContext).inflate(layout_id, this);
        mWheelStartWeek = (WheelView) findViewById(R.id.start_week);
        mWheelEndWeek = (WheelView) findViewById(R.id.end_week);
        mWheelStartWeek.setOnSelectListener(mStartWeekListener);
        mWheelEndWeek.setOnSelectListener(mEndWeekListener);
        setDate();
        mWheelStartWeek.setDefault(0);
        mWheelEndWeek.setDefault(0);
    }


    /**
     * set WLQQTimePicker date
     *
     */
    public void setDate() {
        mWheelStartWeek.setData(getStartWeek());
        mWheelEndWeek.setData(getEndWeek());
    }

    private ArrayList<String> getEndWeek() {
        ArrayList<String> list = new ArrayList<String>();
            for (int i = 1; i < 24; i++) {
                list.add("第"+i + "周");
            }
        return list;
    }

    private ArrayList<String> getStartWeek() {
        ArrayList<String> list = new ArrayList<String>();
            for (int i = 1; i < 24; i++) {
                list.add("第"+i + "周");
        }
        return list;
    }

    private ArrayList<String> updateEndWeek(int pos){
        ArrayList<String> list = new ArrayList<String>();
        for (int i = pos; i < 24; i++) {
            list.add("第"+i + "周");
        }
        return list;
    }

    public String toString() {
     return startWeek+"-"+endWeek;
    }
}