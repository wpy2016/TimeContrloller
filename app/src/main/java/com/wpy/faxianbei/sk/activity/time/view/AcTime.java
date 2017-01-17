package com.wpy.faxianbei.sk.activity.time.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.view.AcAddCourse;
import com.wpy.faxianbei.sk.activity.addtime.view.AcAddTime;
import com.wpy.faxianbei.sk.activity.coursetable.view.AcCourseTable;
import com.wpy.faxianbei.sk.activity.register.view.AcRegister;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import butterknife.OnClick;

@ContentView(R.layout.ac_time)
public class AcTime extends Activity {

    @ViewInject(R.id.id_ac_time_iv_add_course)
    ImageView mivAddCourse;
    @ViewInject(R.id.id_ac_time_iv_add_time)
    ImageView mivAddTime;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = AcTime.this;
    }

    @Event(value = {R.id.id_ac_time_iv_add_course, R.id.id_ac_time_iv_add_time})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_time_iv_add_course:
                /*****************************2**********************************/
                if (AVUser.getCurrentUser() == null) {
                    toNext(AcCourseTable.class);
                } else {
                    toNext(AcCourseTable.class);
                }
                break;
            case R.id.id_ac_time_iv_add_time:
                toNext(AcAddTime.class);
                break;
        }
    }

    public void toNext(Class<?> next) {
        Intent intent = new Intent(mContext, next);
        startActivity(intent);
        finish();
    }
}
