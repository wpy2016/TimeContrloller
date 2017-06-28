package com.wpy.faxianbei.sk.activity.coursetable2.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.view.AcAddCourse;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.coursetable.presenter.PresenterCourseTable;
import com.wpy.faxianbei.sk.activity.coursetable.view.IViewCourseTable;
import com.wpy.faxianbei.sk.activity.coursetable2.presenter.PresenterCourseTable2;
import com.wpy.faxianbei.sk.adapter.CommonAdapterArray;
import com.wpy.faxianbei.sk.adapter.ViewHolder;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashSet;


@ContentView(R.layout.ac_course_table_new)
public class AcCourseTable2 extends MvpBaseActivity<IViewCourstTable2, PresenterCourseTable2> implements IViewCourseTable, IViewCourstTable2 {
    @ViewInject(R.id.id_ac_coursetable2_tv_semester)
    TextView mtvSemester;
    @ViewInject(R.id.id_ac_coursetable2_iv_add_semester)
    ImageView mivAddSemester;
    @ViewInject(R.id.id_ac_coursetable2_tv_week)
    TextView mtvWeek;
    @ViewInject(R.id.id_ac_coursetable2_tv_1)
    TextView mtvDay1;
    @ViewInject(R.id.id_ac_coursetable2_tv_2)
    TextView mtvDay2;
    @ViewInject(R.id.id_ac_coursetable2_tv_3)
    TextView mtvDay3;
    @ViewInject(R.id.id_ac_coursetable2_tv_4)
    TextView mtvDay4;
    @ViewInject(R.id.id_ac_coursetable2_tv_5)
    TextView mtvDay5;
    @ViewInject(R.id.id_ac_coursetable2_tv_6)
    TextView mtvDay6;
    @ViewInject(R.id.id_ac_coursetable2_tv_7)
    TextView mtvDay7;
    @ViewInject(R.id.id_ac_coursetable2_month)
    TextView mtvMonth;
    @ViewInject(R.id.id_ac_coursetable2_gv_timeitem)
    GridView mGvTable;
    @ViewInject(R.id.id_ac_coursetable2_iv_semester_down)
    ImageView mivModifySemester;
    @ViewInject(R.id.id_ac_coursetable2_iv_week_down)
    ImageView mivModifyWeek;

    private Context mContext;
    private int year = 2016;
    private int semester = 1;
    int currentweek = 1;
    int[] bgid = {R.drawable.surround1, R.drawable.surround2, R.drawable.surround3,
            R.drawable.surround4, R.drawable.surround5,
            R.drawable.surround6, R.drawable.surround7,
            R.drawable.surround8,R.drawable.surround9,
            R.drawable.surround10,R.drawable.surround11,
            R.drawable.surround12};

    private PresenterCourseTable mPresenterCourseTable;

    private String[] arrayCourstTable;

    private CommonAdapterArray<String> adapter;

    private CourseTableTogether[] set;

    private int curPos = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = AcCourseTable2.this;
        //赋值presenter
        mPresenterCourseTable = new PresenterCourseTable();
        mPresenterCourseTable.attachView(this);

        //初始化listCourstTable
        arrayCourstTable = new String[200];
        initListData();
        //
        set = new CourseTableTogether[200];
        mPresenterCourseTable.getDateFormInternet(year, semester);
        mPresenterCourseTable.initDate(mContext);
        initEvent();
    }

    private void initEvent() {
        mGvTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (set[position].ishaveCourse) {
                    Toast.makeText(AcCourseTable2.this, "当前有课，不能添加自定义任务，专心上课哦", Toast.LENGTH_SHORT).show();
                } else {
                    if (position == curPos) {//说明两次点击这个,需要跳转到添加时间段界面，此时只能编辑事件，不能编辑时间了
                        curPos = 200;
                        adapter.setSelectPos(200);
                        adapter.notifyDataSetChanged();
                        /******************做其他操作***********************/
                        Toast.makeText(AcCourseTable2.this, "等待设置时间", Toast.LENGTH_SHORT).show();


                    } else {
                        TextView textView = (TextView) view.findViewById(R.id.id_ac_coursetable2_gv_item);
                        textView.setBackgroundResource(R.drawable.add_new);
                        curPos = position;
                        adapter.setSelectPos(curPos);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initListData() {
        int time = 0;
        for (int i = 0; i < 200; i++) {
            if (i % 8 == 0) {
                arrayCourstTable[i] = time + ":00";
                time++;
            }
        }
    }

    /**
     * 使用GridView来显示课程数据
     *
     * @param week
     */
    private void setData(int week) {
        mtvWeek.setText("第 " + week + " 周");
        set=new CourseTableTogether[200];
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 7; j++) {
                String lesson = mPresenterCourseTable.getLesson(i, j, week, year, semester);
                if (lesson == null || lesson.equals("")) {//说明当前没有课

                } else {
                    int row[] = mPresenter.getPos(j);
                    int size = row.length;
                    String lessonSub[] = mPresenter.getSubStringByParts(size, lesson);
                    int color=((int)(Math.random()*1000))%12;
                    for (int k = 0; k < size; k++) {
                        int pos = row[k] * 8 + i;
                        arrayCourstTable[pos] = lessonSub[k];
                        set[pos]=new CourseTableTogether();
                        set[pos].ishaveCourse=true;
                        set[pos].together=row;
                        set[pos].colorPos=bgid[color];
                    }
                }
            }
        }
        adapter = new CommonAdapterArray<String>(this, arrayCourstTable, R.layout.coursetable_item_layout) {
            @Override
            public void convert(ViewHolder helper, String item, int pos, int selectPos) {
                TextView tvItem = (TextView) helper.getView(R.id.id_ac_coursetable2_gv_item);
                tvItem.setBackgroundColor(Color.WHITE);
                if (item != null) {
                    tvItem.setText(item);
                    int offset = pos % 8;
                    if (offset != 0) {
                        if(set[pos]!=null)
                        tvItem.setBackgroundResource(set[pos].colorPos);
                    }
                } else {
                    tvItem.setText("");
                }
                if (pos == selectPos) {
                    tvItem.setBackgroundResource(R.drawable.add_new);
                }
            }
        };
        adapter.setSelectPos(curPos);
        mGvTable.setAdapter(adapter);
    }

    @Event(value = {R.id.id_ac_coursetable2_tv_semester, R.id.id_ac_coursetable2_iv_semester_down,
            R.id.id_ac_coursetable2_iv_week_down, R.id.id_ac_coursetable2_iv_add_semester, R.id.id_ac_coursetable2_tv_week})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_coursetable2_iv_semester_down:
            case R.id.id_ac_coursetable2_tv_semester:
                mPresenterCourseTable.showSemester(mContext, mtvWeek);
                break;
            case R.id.id_ac_coursetable2_iv_add_semester:
                toNext(AcAddCourse.class);
                break;
            case R.id.id_ac_coursetable2_iv_week_down:
            case R.id.id_ac_coursetable2_tv_week:
                mPresenterCourseTable.showPup(mContext, mtvWeek);
                break;
        }
    }

    public void toNext(Class<?> next) {
        Intent intent = new Intent(mContext, next);
        startActivity(intent);
    }


    @Override
    public PresenterCourseTable2 createPresenter() {
        return new PresenterCourseTable2();
    }


    @Override
    public void setWeek(int week) {
        setData(week);
    }

    @Override
    public void setYeayAndSemester(int year, int semester, String message) {
        mtvSemester.setText(message);
        SharePreferenceUtil.instantiation.saveCurrentSemester(mContext, message);
        this.year = year;
        this.semester = semester;
        mPresenterCourseTable.getDateFormInternet(year, semester);
        currentweek = mPresenterCourseTable.getCurrentWeek(mContext);
        setData(currentweek);
    }

    @Override
    public void setDate(String[] date) {
        for (int i = 1; i < 8; i++) {
            TextView textView = mPresenterCourseTable.getDayTextView(i, this);
            textView.setText(date[i - 1]);
        }
    }

    public void setMonth(String month) {
        mtvMonth.setText(month);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void dimissProgress() {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFail(String message) {

    }

    private class CourseTableTogether {
        int[] together;
        int colorPos;
        boolean ishaveCourse;
    }
}
