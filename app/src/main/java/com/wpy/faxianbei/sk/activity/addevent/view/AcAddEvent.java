package com.wpy.faxianbei.sk.activity.addevent.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.faxianbei.sk.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.addevent)
public class AcAddEvent extends Activity {


    @ViewInject(R.id.id_ac_addevent_et_editevent)
    EditText mttEditevent;
    @ViewInject(R.id.id_ac_addevent_iv_add_start_time)
    ImageView mivAddStartTime;
    @ViewInject(R.id.id_ac_addevent_iv_add_end_time)
    ImageView mivAddEndTime;
    @ViewInject(R.id.id_ac_addevent_iv_edit_place)
    ImageView mivEditPlace;
    @ViewInject(R.id.id_ac_addevent_iv_edit_type_right)
    ImageView mivEditTypeRight;
    @ViewInject(R.id.id_ac_addevent_tv_type)
    TextView mtvType;
    @ViewInject(R.id.id_ac_addevent_iv_edit_type_left)
    ImageView mivEditTypeLeft;
    @ViewInject(R.id.id_ac_addevent_iv_edit_recycle)
    ImageView mivEditRecycle;
    @ViewInject(R.id.id_ac_addevent_iv_slient)
    ImageView mivSlient;
    @ViewInject(R.id.id_ac_addevent_iv_shake)
    ImageView mivShake;

    @ViewInject(R.id.id_ac_addevent_et_place)
    EditText metPlace;

    @ViewInject(R.id.id_ac_addevent_iv_confirm)
    ImageView mivConfrim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value = {R.id.id_ac_addevent_iv_add_start_time, R.id.id_ac_addevent_iv_add_end_time, R.id.id_ac_addevent_iv_edit_place, R.id.id_ac_addevent_iv_edit_type_right, R.id.id_ac_addevent_iv_edit_type_left, R.id.id_ac_addevent_iv_edit_recycle, R.id.id_ac_addevent_iv_slient, R.id.id_ac_addevent_iv_shake})
    private void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_ac_addevent_iv_add_start_time:
                break;
            case R.id.id_ac_addevent_iv_add_end_time:
                break;
            case R.id.id_ac_addevent_iv_edit_place:
                break;
            case R.id.id_ac_addevent_iv_edit_type_right:
                break;
            case R.id.id_ac_addevent_iv_edit_type_left:
                break;
            case R.id.id_ac_addevent_iv_edit_recycle:
                break;
            case R.id.id_ac_addevent_iv_slient:
                break;
            case R.id.id_ac_addevent_iv_shake:
                break;
        }
    }
}
