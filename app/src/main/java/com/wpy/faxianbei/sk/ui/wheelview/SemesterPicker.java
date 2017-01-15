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
    private String mYears;
    private String mSemester;
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
        LayoutInflater.from(mContext).inflate(layout_id, this);
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
        list.add("2017-2018");
        list.add("2018-2019");
        list.add("2019-2020");
        return list;
    }

    private ArrayList<String> getSemesters(){
        ArrayList<String> list=new ArrayList<String>();
        list.add("第一学期");
        list.add("第二学期");
        return list;
    }
}