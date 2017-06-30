package com.wpy.faxianbei.sk.activity.addevent.view;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addevent.model.RecycleSelectModel;
import com.wpy.faxianbei.sk.activity.addevent.model.SelectDayTimePickerModel;
import com.wpy.faxianbei.sk.activity.addevent.model.SelectRecycleWeekPickerModel;
import com.wpy.faxianbei.sk.activity.addtime.presenter.PresenterAddTime;
import com.wpy.faxianbei.sk.activity.addtime.view.IViewAddTime;
import com.wpy.faxianbei.sk.entity.db.TimeItem;
import com.wpy.faxianbei.sk.service.SituationService;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.util.Arrays;


@ContentView(R.layout.addevent)
public class AcAddEvent extends Activity implements SelectDayTimePickerModel.SelectDayTimeSuccess, RecycleSelectModel.SelectRecycleSuccess ,SelectRecycleWeekPickerModel.SelectRecycleWeek{
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

    @ViewInject(R.id.id_ac_addevent_tv_end_time)
    TextView mtvEndtime;

    @ViewInject(R.id.id_ac_addevent_tv_start_time)
    TextView mtvStarttime;


    @ViewInject(R.id.id_ac_addevent_tv_edit_recycle)
    TextView mtvRecycle;

    @ViewInject(R.id.id_ac_addevent_rl_edit_recycle_week)
    RelativeLayout mrlRecycleWeek;

    @ViewInject(R.id.id_ac_addevent_tv_edit_recycle_week)
    TextView mtvRecycleWeek;

    @ViewInject(R.id.id_ac_addevent_iv_edit_recycle_week)
    ImageView mivRecycleWeek;


    String[] mstrArrEventType;

    //日常事件
    public static final int EVENTNORMAL = 0;
    //课程事件
    public static final int EVENTCOURSE = 1;

    int EventTypePos = EVENTNORMAL;

    //保存模式
    private int model = 3;

    private final int START = 0;
    private final int END = 1;

    private SelectDayTimePickerModel dayTimePickerModel;


    //保存跳转进来时的年月日
    private String date;

    private String startTime;

    private String endTime;

    private RecycleSelectModel recycleSelectModel;

    //保存当前时间是周几
    private int day;

    //保存重复信息
    private int recycle[];

    //保存周一、周二、周三之类的字符串
    private String[] recycleString;

    //选择的周次
    private String mstrWeeks;

    private SelectRecycleWeekPickerModel selectRecycleWeekPickerModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initVariables(getIntent());
    }

    private void initVariables(Intent intent) {
        mstrArrEventType = new String[2];
        mstrArrEventType[0] = getResources().getString(R.string.normal_event);
        mstrArrEventType[1] = getResources().getString(R.string.course_event);
        dayTimePickerModel = new SelectDayTimePickerModel(this);
        recycleSelectModel = new RecycleSelectModel(this);
        selectRecycleWeekPickerModel=new SelectRecycleWeekPickerModel(this);
        date = intent.getStringExtra("date");
        startTime = intent.getStringExtra("start");
        endTime = intent.getStringExtra("end");
        day=intent.getIntExtra("day",0);
        mtvStarttime.setText(startTime);
        mtvEndtime.setText(endTime);
        initRecycle();
        recycleString=getResources().getStringArray(R.array.recycle_string);
        mtvRecycle.setText(recycleString[day+1]);
        Toast.makeText(this, date, Toast.LENGTH_LONG).show();
    }

    private void initRecycle() {
        recycle=new int[8];
        for(int i=0;i<8;i++){
            recycle[i]=0;
        }
        recycle[0]=1;
        recycle[day]=1;
    }


    @Event(value = {R.id.id_ac_addevent_iv_add_start_time, R.id.id_ac_addevent_iv_add_end_time,
            R.id.id_ac_addevent_iv_edit_place, R.id.id_ac_addevent_iv_edit_type_right,
            R.id.id_ac_addevent_iv_edit_type_left, R.id.id_ac_addevent_iv_edit_recycle,
            R.id.id_ac_addevent_iv_slient, R.id.id_ac_addevent_iv_shake,R.id.id_ac_addevent_iv_edit_recycle_week,
            R.id.id_ac_addevent_iv_confirm})
    private void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_ac_addevent_iv_add_start_time:
                dayTimePickerModel.showPopWindow(AcAddEvent.this, mtvStarttime, START);
                break;
            case R.id.id_ac_addevent_iv_add_end_time:
                dayTimePickerModel.showPopWindow(AcAddEvent.this, mtvEndtime, END);
                break;
            case R.id.id_ac_addevent_iv_edit_place:
                metPlace.setEnabled(true);
                break;
            case R.id.id_ac_addevent_iv_edit_type_right:
            case R.id.id_ac_addevent_iv_edit_type_left:
                EventTypePos++;
                mtvType.setText(mstrArrEventType[EventTypePos % 2]);
                if (EventTypePos % 2 == 0) {
                    mrlRecycleWeek.setVisibility(View.INVISIBLE);
                } else {
                  mrlRecycleWeek.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.id_ac_addevent_iv_edit_recycle:
                    recycleSelectModel.showPopWindow(AcAddEvent.this, mtvStarttime);
                break;
            case R.id.id_ac_addevent_iv_slient:
                resetModel();
                model = SituationService.SLIENT;
                mivSlient.setImageResource(R.drawable.silence_red);
                break;
            case R.id.id_ac_addevent_iv_shake:
                resetModel();
                model = SituationService.SHAKE;
                mivShake.setImageResource(R.drawable.shake_red);
                break;
            case R.id.id_ac_addevent_iv_edit_recycle_week:
                //进行选择周次
                selectRecycleWeekPickerModel.showPopWindow(AcAddEvent.this,mtvStarttime);
                break;
            case R.id.id_ac_addevent_iv_confirm:

                if(!judgeIsEmptyContent()){
                    TimeItem item=new TimeItem();
                    item.setContent(mttEditevent.getText().toString());
                    item.setStart(startTime);

                }else{//content内容为空
                    Toast.makeText(AcAddEvent.this,"请填写时间名称",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean judgeIsEmptyContent() {
        return TextUtils.isEmpty(mttEditevent.getText().toString());
    }

    private void resetModel() {
        model = 3;
        mivShake.setImageResource(R.drawable.shake_green);
        mivSlient.setImageResource(R.drawable.silence_green);
    }

    @Override
    public void selectTimeSuccess(String time, int type) {

        switch (type) {
            case START:
                startTime=time;
                mtvStarttime.setText(time);
                break;
            case END:
                endTime=time;
                mtvEndtime.setText(time);
                break;
        }
    }

    @Override
    public void selectRecycleSuccess(int[] recycle) {
        this.recycle=recycle;
        if(0==recycle[0]){
            mtvRecycle.setText(recycleString[0]);
        }else{
            StringBuffer buffer=new StringBuffer();
            for(int i=1;i<8;i++)
            {
                if(1==recycle[i]){
                    if(1==i){
                        buffer.append(recycleString[i]);
                    }
                    else{
                        buffer.append(","+recycleString[i]);
                    }
                }
            }
            mtvRecycle.setText(buffer.toString());
        }
    }

    @Override
    public void selectRecycleSuccess(String weeks) {
        mstrWeeks=weeks;
        mtvRecycleWeek.setText(weeks+"周");
    }
}
