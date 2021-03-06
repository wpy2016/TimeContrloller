package com.wpy.faxianbei.sk.ui.wheelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.wpy.faxianbei.sk.R;

import java.util.ArrayList;
public class SemesterPicker extends LinearLayout {
    private int layout_id;
    private WheelView mWheelSemester;
    private WheelView mWheelYears;
    private Context mContext;
    private ArrayList<String> mListYear;
    private ArrayList<String> mListSemester;
    private String mYears="2016-2017";
    private String mSemester="第一学期";
    private WheelView.OnSelectListener mSemesterListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int semester, String text) {
            mSemester=mListSemester.get(semester);
        }

        @Override
        public void selecting(int id, String text) {
        }
    };
    private WheelView.OnSelectListener mYearsListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int year, String text) {
            mYears =mListYear.get(year);
        }

        @Override
        public void selecting(int year, String text) {
        }
    };


    public SemesterPicker(Context context) {
        this(context, null);
    }

    public SemesterPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimePicker);
        int lenght = array.getIndexCount();
        for (int i = 0; i < lenght; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.TimePicker_layout_id) {
                layout_id = array.getResourceId(attr, R.layout.semester_picker);
            }
        }
        array.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContext = getContext();
        //第二个参数就是父布局，所以返回的view就直接在该LinearLayout中了，所以layout_id中的布局就在LinearLayout中了
        LayoutInflater.from(mContext).inflate(layout_id, this);
        //findViewById就是在该布局中查找的
        mWheelSemester = (WheelView) findViewById(R.id.wheel_semester);
        mWheelYears = (WheelView) findViewById(R.id.wheel_years);
        mWheelSemester.setOnSelectListener(mSemesterListener);
        mWheelYears.setOnSelectListener(mYearsListener);
        mListSemester =getSemesters();
        mListYear= getmYears();
        mWheelSemester.setData(mListSemester);
        mWheelYears.setData(mListYear);
    }

    public String toString() {
        return mYears+" "+mSemester;
    }

    private ArrayList<String> getmYears(){
        ArrayList<String> list=new ArrayList<String>();
        list.add("2015-2016");
        list.add("2016-2017");
        return list;
    }

    private ArrayList<String> getSemesters(){
        ArrayList<String> list=new ArrayList<String>();
        list.add("第一学期");
        list.add("第二学期");
        return list;
    }
}