package com.wpy.faxianbei.sk.activity.coursetable.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpy.faxianbei.sk.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import butterknife.OnClick;

@ContentView(R.layout.ac_course_table)
public class AcCourseTable extends Activity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value = {R.id.id_ac_coursetable_tv_semester, R.id.id_ac_coursetable_iv_add_semester, R.id.id_ac_coursetable_tv_week})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_coursetable_tv_semester:
                break;
            case R.id.id_ac_coursetable_iv_add_semester:
                break;
            case R.id.id_ac_coursetable_tv_week:
                break;
        }
    }
}
