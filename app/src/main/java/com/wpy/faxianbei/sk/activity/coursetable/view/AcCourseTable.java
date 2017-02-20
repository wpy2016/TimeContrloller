package com.wpy.faxianbei.sk.activity.coursetable.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.view.AcAddCourse;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.coursetable.presenter.PresenterCourseTable;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.ac_course_table)
public class AcCourseTable extends MvpBaseActivity<IViewCourseTable,PresenterCourseTable> implements IViewCourseTable {
    @ViewInject(R.id.id_ac_coursetable_tv_semester)
    TextView mtvSemester;
    @ViewInject(R.id.id_ac_coursetable_iv_add_semester)
    ImageView mivAddSemester;
    @ViewInject(R.id.id_ac_coursetable_tv_week)
    TextView mtvWeek;
    @ViewInject(R.id.id_ac_coursetable_tv_11)
    TextView mtvCourseTable11;
    @ViewInject(R.id.id_ac_coursetable_tv_12)
    TextView mtvCourseTable12;
    @ViewInject(R.id.id_ac_coursetable_ll_11)
    LinearLayout mllCourseTable11;
    @ViewInject(R.id.id_ac_coursetable_tv_13)
    TextView mtvCourseTable13;
    @ViewInject(R.id.id_ac_coursetable_tv_14)
    TextView mtvCourseTable14;
    @ViewInject(R.id.id_ac_coursetable_ll_12)
    LinearLayout mllCourseTable12;
    @ViewInject(R.id.id_ac_coursetable_tv_15)
    TextView mtvCourseTable15;
    @ViewInject(R.id.id_ac_coursetable_tv_16)
    TextView mtvCourseTable16;
    @ViewInject(R.id.id_ac_coursetable_ll_13)
    LinearLayout mllCourseTable13;
    @ViewInject(R.id.id_ac_coursetable_tv_21)
    TextView mtvCourseTable21;
    @ViewInject(R.id.id_ac_coursetable_tv_22)
    TextView mtvCourseTable22;
    @ViewInject(R.id.id_ac_coursetable_ll_21)
    LinearLayout mllCourseTable21;
    @ViewInject(R.id.id_ac_coursetable_tv_23)
    TextView mtvCourseTable23;
    @ViewInject(R.id.id_ac_coursetable_tv_24)
    TextView mtvCourseTable24;
    @ViewInject(R.id.id_ac_coursetable_ll_22)
    LinearLayout mllCourseTable22;
    @ViewInject(R.id.id_ac_coursetable_tv_25)
    TextView mtvCourseTable25;
    @ViewInject(R.id.id_ac_coursetable_tv_26)
    TextView mtvCourseTable26;
    @ViewInject(R.id.id_ac_coursetable_ll_23)
    LinearLayout mllCourseTable23;
    @ViewInject(R.id.id_ac_coursetable_tv_31)
    TextView mtvCourseTable31;
    @ViewInject(R.id.id_ac_coursetable_tv_32)
    TextView mtvCourseTable32;
    @ViewInject(R.id.id_ac_coursetable_ll_31)
    LinearLayout mllCourseTable31;
    @ViewInject(R.id.id_ac_coursetable_tv_33)
    TextView mtvCourseTable33;
    @ViewInject(R.id.id_ac_coursetable_tv_34)
    TextView mtvCourseTable34;
    @ViewInject(R.id.id_ac_coursetable_ll_32)
    LinearLayout mllCourseTable32;
    @ViewInject(R.id.id_ac_coursetable_tv_35)
    TextView mtvCourseTable35;
    @ViewInject(R.id.id_ac_coursetable_tv_36)
    TextView mtvCourseTable36;
    @ViewInject(R.id.id_ac_coursetable_ll_33)
    LinearLayout mllCourseTable33;
    @ViewInject(R.id.id_ac_coursetable_tv_41)
    TextView mtvCourseTable41;
    @ViewInject(R.id.id_ac_coursetable_tv_42)
    TextView mtvCourseTable42;
    @ViewInject(R.id.id_ac_coursetable_ll_41)
    LinearLayout mllCourseTable41;
    @ViewInject(R.id.id_ac_coursetable_tv_43)
    TextView mtvCourseTable43;
    @ViewInject(R.id.id_ac_coursetable_tv_44)
    TextView mtvCourseTable44;
    @ViewInject(R.id.id_ac_coursetable_ll_42)
    LinearLayout mllCourseTable42;
    @ViewInject(R.id.id_ac_coursetable_tv_45)
    TextView mtvCourseTable45;
    @ViewInject(R.id.id_ac_coursetable_tv_46)
    TextView mtvCourseTable46;
    @ViewInject(R.id.id_ac_coursetable_ll_43)
    LinearLayout mllCourseTable43;
    @ViewInject(R.id.id_ac_coursetable_tv_51)
    TextView mtvCourseTable51;
    @ViewInject(R.id.id_ac_coursetable_tv_52)
    TextView mtvCourseTable52;
    @ViewInject(R.id.id_ac_coursetable_ll_51)
    LinearLayout mllCourseTable51;
    @ViewInject(R.id.id_ac_coursetable_tv_53)
    TextView mtvCourseTable53;
    @ViewInject(R.id.id_ac_coursetable_tv_54)
    TextView mtvCourseTable54;
    @ViewInject(R.id.id_ac_coursetable_ll_52)
    LinearLayout mllCourseTable52;
    @ViewInject(R.id.id_ac_coursetable_tv_55)
    TextView mtvCourseTable55;
    @ViewInject(R.id.id_ac_coursetable_tv_56)
    TextView mtvCourseTable56;
    @ViewInject(R.id.id_ac_coursetable_ll_53)
    LinearLayout mllCourseTable53;
    @ViewInject(R.id.id_ac_coursetable_tv_61)
    TextView mtvCourseTable61;
    @ViewInject(R.id.id_ac_coursetable_tv_62)
    TextView mtvCourseTable62;
    @ViewInject(R.id.id_ac_coursetable_ll_61)
    LinearLayout mllCourseTable61;
    @ViewInject(R.id.id_ac_coursetable_tv_63)
    TextView mtvCourseTable63;
    @ViewInject(R.id.id_ac_coursetable_tv_64)
    TextView mtvCourseTable64;
    @ViewInject(R.id.id_ac_coursetable_ll_62)
    LinearLayout mllCourseTable62;
    @ViewInject(R.id.id_ac_coursetable_tv_65)
    TextView mtvCourseTable65;
    @ViewInject(R.id.id_ac_coursetable_tv_66)
    TextView mtvCourseTable66;
    @ViewInject(R.id.id_ac_coursetable_ll_63)
    LinearLayout mllCourseTable63;
    @ViewInject(R.id.id_ac_coursetable_tv_71)
    TextView mtvCourseTable71;
    @ViewInject(R.id.id_ac_coursetable_tv_72)
    TextView mtvCourseTable72;
    @ViewInject(R.id.id_ac_coursetable_ll_71)
    LinearLayout mllCourseTable71;
    @ViewInject(R.id.id_ac_coursetable_tv_73)
    TextView mtvCourseTable73;
    @ViewInject(R.id.id_ac_coursetable_tv_74)
    TextView mtvCourseTable74;
    @ViewInject(R.id.id_ac_coursetable_ll_72)
    LinearLayout mllCourseTable72;
    @ViewInject(R.id.id_ac_coursetable_tv_75)
    TextView mtvCourseTable75;
    @ViewInject(R.id.id_ac_coursetable_tv_76)
    TextView mtvCourseTable76;
    @ViewInject(R.id.id_ac_coursetable_ll_73)
    LinearLayout mllCourseTable73;
    private Object dataFromInternet;
    private Context mContext;
    private int year=2016;
    private int semester=0;

    int[] color={Color.parseColor("#B2DFEE"),Color.parseColor("#54FF9F"),Color.parseColor("#DDA0DD"),
            Color.parseColor("#EEA9B8"),Color.parseColor("#C2C2C2"),Color.parseColor("#9F79EE"),
            Color.parseColor("#1E90FF")};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext=AcCourseTable.this;
        mPresenter.getDateFormInternet(year,semester);
        setData(1);
    }

    private void setData(int week) {
        mtvWeek.setText("第 "+week+" 周");
       for(int i=1;i<8;i++)
        {
         for(int j=1;j<7;j++)
         {
             String lesson = mPresenter.getLesson(i,j,week,year,semester);
             TextView textView = mPresenter.getTextView(i,j,this);
             textView.setBackgroundColor(Color.WHITE);
             if(!lesson.equals(""))
             {
                 textView.setBackgroundColor(color[i-1]);
                 textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
             }
             textView.setText(lesson);
         }
        }
    }

    @Event(value = {R.id.id_ac_coursetable_tv_semester, R.id.id_ac_coursetable_iv_add_semester, R.id.id_ac_coursetable_tv_week})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_coursetable_tv_semester:
                mPresenter.showSemester(this,mtvWeek);
                break;
            case R.id.id_ac_coursetable_iv_add_semester:
                toNext(AcAddCourse.class);
                break;
            case R.id.id_ac_coursetable_tv_week:
                mPresenter.showPup(this,mtvWeek);
                break;
        }
    }

    public void toNext(Class<?> next) {
        Intent intent = new Intent(mContext, next);
        startActivity(intent);
    }


    @Override
    public PresenterCourseTable createPresenter() {
        return new PresenterCourseTable();
    }


    @Override
    public void setWeek(int week) {
        setData(week);
    }

    @Override
    public void setYeayAndSemester(int year, int semester,String message) {
        mtvSemester.setText(message);
        this.year=year;
        this.semester = semester;
        mPresenter.getDateFormInternet(year,semester);
        setData(1);
    }

    @Override
    public void showProgress() {
        showProgress();
    }

    @Override
    public void dimissProgress() {
        dimissProgress();
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFail(String message) {

    }
}
