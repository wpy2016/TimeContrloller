package com.wpy.faxianbei.sk.activity.addevent.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.db.TimeItem;
import com.wpy.faxianbei.sk.service.SituationService;
import com.wpy.faxianbei.sk.utils.general.StringToBaseDataType;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@ContentView(R.layout.addevent)
public class AcAddEvent extends Activity implements SelectDayTimePickerModel.SelectDayTimeSuccess, RecycleSelectModel.SelectRecycleSuccess, SelectRecycleWeekPickerModel.SelectRecycleWeek {
    @ViewInject(R.id.id_ac_addevent_et_editevent)
    EditText metEditevent;
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

    @ViewInject(R.id.id_ac_addevent_ll_start_time)
    RelativeLayout mrlStartTime;

    @ViewInject(R.id.id_ac_addevent_ll_end_time)
    RelativeLayout mrlEndTime;

    @ViewInject(R.id.id_ac_addevent_ll_recycle)
    RelativeLayout mrlRecycle;


    @ViewInject(R.id.id_ac_addevent_tv_edit_recycle_week)
    TextView mtvRecycleWeek;


    @ViewInject(R.id.id_ac_addevent_iv_delete)
            ImageView mivDelete;

    String[] mstrArrEventType;

    //日常事件
    public static final int EVENTNORMAL = 0;
    //课程事件
    public static final int EVENTCOURSE = 1;

    int EventTypePos = EVENTNORMAL;

    private int type = EVENTNORMAL;

    //保存模式
    private int model = SituationService.SHAKE;

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
    private String mstrWeeks = "1-1";

    //开始周
    private int startWeek = 1;
    //结束周
    private int endWeek = 1;

    private SelectRecycleWeekPickerModel selectRecycleWeekPickerModel;

    private boolean has = false;

    private String content = "";

    private String place = "";

    private TimeItem item;

    private boolean isWantToDelete=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        try {
            initVariables(getIntent());
        } catch (DbException e) {
        } catch (ParseException e) {

        }
    }

    private void initVariables(Intent intent) throws DbException, ParseException {
        mstrArrEventType = new String[2];
        mstrArrEventType[0] = getResources().getString(R.string.normal_event);
        mstrArrEventType[1] = getResources().getString(R.string.course_event);
        dayTimePickerModel = new SelectDayTimePickerModel(this);
        recycleSelectModel = new RecycleSelectModel(this);
        selectRecycleWeekPickerModel = new SelectRecycleWeekPickerModel(this);
        recycleString = getResources().getStringArray(R.array.recycle_string);
        has = intent.getBooleanExtra("has", false);
        if (has) {//进行对item的编辑，因为是从课表已经有的选项点击过来的

            try {
                handlerIntentByItem(intent);
            } catch (NullPointerException e) {
                handlerByNoItem(intent);
            }
        } else {//新建item，因为是从课表已经有的选项点击过来的
            handlerByNoItem(intent);
        }
        /**
         * 开始显示数据
         */
        mtvStarttime.setText(startTime);
        mtvEndtime.setText(endTime);
        if (type == EVENTNORMAL) {
            mrlRecycleWeek.setVisibility(View.INVISIBLE);
            mtvType.setText(mstrArrEventType[EVENTNORMAL]);
        } else {
            mtvType.setText(mstrArrEventType[EVENTCOURSE]);
            if (1 == recycle[0]) {
                mrlRecycleWeek.setVisibility(View.VISIBLE);
            } else {
                mrlRecycleWeek.setVisibility(View.INVISIBLE);
            }
        }
        metEditevent.setText(content);
        metPlace.setText(place);
        mtvRecycleWeek.setText(startWeek + "-" + endWeek);
        //设置重复
        updateRecycle(recycle);
        //设置模式图片
        if (model == SituationService.SHAKE) {
            resetModel();
            model = SituationService.SHAKE;
            mivShake.setImageResource(R.drawable.shake_red);
        } else {
            resetModel();
            model = SituationService.SLIENT;
            mivSlient.setImageResource(R.drawable.silence_red);
        }
        if(!has){
            mivDelete.setVisibility(View.GONE);
        }
    }

    private void handlerByNoItem(Intent intent) {
        date = intent.getStringExtra("date");
        startTime = intent.getStringExtra("start");
        endTime = intent.getStringExtra("end");
        day = intent.getIntExtra("day", 0);
        initRecycle();
    }

    private void handlerIntentByItem(Intent intent) throws DbException, ParseException,NullPointerException {
        long id = intent.getLongExtra("id", 0l);
        TimeItem item = SKApplication.getDbManager().selector(TimeItem.class).where(WhereBuilder.b().and("id", "=", id)).findFirst();
        this.item = item;
        if (0 == item.getIsRecycle()) {
            //表示不是重复的
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            Date dateTempStary = format.parse(item.getStart());
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
            this.date = format2.format(dateTempStary);
            //设置显示的开始时间
            SimpleDateFormat format3 = new SimpleDateFormat("HH:mm");
            this.startTime = format3.format(dateTempStary);
            //设置显示的结束时间
            Date dateTempEnd = format.parse(item.getEnd());
            this.endTime = format3.format(dateTempEnd);
        } else {//表示是重复的
            startTime = item.getStart();
            endTime = item.getEnd();
        }
        this.type = item.getType();
        this.model = item.getModel();
        recycle = new int[8];
        recycle[0] = item.getIsRecycle();
        recycle[1] = item.getMonday();
        recycle[2] = item.getTuesday();
        recycle[3] = item.getWednesday();
        recycle[4] = item.getThursDay();
        recycle[5] = item.getFriday();
        recycle[6] = item.getSaturday();
        recycle[7] = item.getSunday();
        startWeek = item.getStartWeek();
        endWeek = item.getEndWeek();
        content = item.getContent();
        place = item.getPlace();
        //禁用切换类型，也就是说不能将重复的切换为不重复的，或者将不重复的切换为重复的，只能在相同类型下修改
        mivEditTypeLeft.setEnabled(false);
        mivEditTypeRight.setEnabled(false);

    }

    private void initRecycle() {
        recycle = new int[8];
        for (int i = 0; i < 8; i++) {
            recycle[i] = 0;
        }
        recycle[0] = 1;
        recycle[day] = 1;
    }


    @Event(value = {R.id.id_ac_addevent_iv_add_start_time, R.id.id_ac_addevent_iv_add_end_time,
            R.id.id_ac_addevent_iv_edit_place, R.id.id_ac_addevent_iv_edit_type_right,
            R.id.id_ac_addevent_iv_edit_type_left, R.id.id_ac_addevent_iv_edit_recycle,
            R.id.id_ac_addevent_iv_slient, R.id.id_ac_addevent_iv_shake, R.id.id_ac_addevent_iv_edit_recycle_week,
            R.id.id_ac_addevent_iv_confirm,R.id.id_ac_addevent_iv_delete,R.id.id_ac_addevent_ll_start_time,
            R.id.id_ac_addevent_ll_end_time,R.id.id_ac_addevent_ll_recycle,R.id.id_ac_addevent_rl_edit_recycle_week})
    private void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_ac_addevent_ll_start_time:
            case R.id.id_ac_addevent_iv_add_start_time:
                dayTimePickerModel.showPopWindow(AcAddEvent.this, mtvStarttime, START);
                break;
            case R.id.id_ac_addevent_ll_end_time:
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
                        type = EVENTNORMAL;
                    } else {
                        mrlRecycleWeek.setVisibility(View.VISIBLE);
                        type = EVENTCOURSE;
                    }
                break;
            case R.id.id_ac_addevent_ll_recycle:
            case R.id.id_ac_addevent_iv_edit_recycle:
                if(has){
                    if(0==recycle[0]){
                        Toast.makeText(AcAddEvent.this," 不能切换重复类型",Toast.LENGTH_SHORT).show();
                    }else{
                        recycleSelectModel.showPopWindow(AcAddEvent.this, mtvStarttime);
                    }
                }else{
                    recycleSelectModel.showPopWindow(AcAddEvent.this, mtvStarttime);
                }
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
            case R.id.id_ac_addevent_rl_edit_recycle_week:
            case R.id.id_ac_addevent_iv_edit_recycle_week:
                //进行选择周次
                selectRecycleWeekPickerModel.showPopWindow(AcAddEvent.this, mtvStarttime);
                break;
            case R.id.id_ac_addevent_iv_confirm:
                if (!has) {//表示是新建的
                    createItem();
                }else{
                    editItem();
                }
                break;
            case R.id.id_ac_addevent_iv_delete:
                if(!isWantToDelete){
                    isWantToDelete=true;
                    Toast.makeText(AcAddEvent.this,"再点击一次便删除",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        SKApplication.getDbManager().delete(item);
                        Toast.makeText(AcAddEvent.this,"删除成功",Toast.LENGTH_SHORT).show();
                        AcAddEvent.this.finish();
                    } catch (DbException e) {
                        Toast.makeText(AcAddEvent.this,"删除失败",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isWantToDelete=false;
    }

    private void editItem() {
        if (!judgeIsEmptyContent()) {
            fillItemContent(this.item);
            try {
                SKApplication.getDbManager().saveOrUpdate(item);
                Toast.makeText(AcAddEvent.this, "更新成功", Toast.LENGTH_LONG).show();
                AcAddEvent.this.finish();
            } catch (DbException e) {
                Toast.makeText(AcAddEvent.this, "更新失败"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {//content内容为空
            Toast.makeText(AcAddEvent.this, "请填写时间名称", Toast.LENGTH_LONG).show();
        }
    }

    private void createItem() {
        if (!judgeIsEmptyContent()) {
            TimeItem timeItem = new TimeItem();
            fillItemContent(timeItem);
            try {
                SKApplication.getDbManager().save(timeItem);
                Toast.makeText(AcAddEvent.this, "保存成功", Toast.LENGTH_LONG).show();
                AcAddEvent.this.finish();
            } catch (DbException e) {
                Toast.makeText(AcAddEvent.this, "保存失败", Toast.LENGTH_LONG).show();
            }

        } else {//content内容为空
            Toast.makeText(AcAddEvent.this, "请填写时间名称", Toast.LENGTH_LONG).show();
        }
    }

    private void fillItemContent(TimeItem timeItem) {
        content = metEditevent.getText().toString();
        timeItem.setContent(content);
        place = metPlace.getText().toString();
        if (0 == recycle[0]) {
            timeItem.setStart(date +" "+ startTime);
            timeItem.setEnd(date + " " + endTime);
        } else {
            timeItem.setStart(startTime);
            timeItem.setEnd(endTime);
        }
        timeItem.setModel(model);
        timeItem.setType(type);
        timeItem.setMonday(recycle[1]);
        timeItem.setTuesday(recycle[2]);
        timeItem.setWednesday(recycle[3]);
        timeItem.setThursDay(recycle[4]);
        timeItem.setFriday(recycle[5]);
        timeItem.setSaturday(recycle[6]);
        timeItem.setSunday(recycle[7]);
        timeItem.setStartWeek(startWeek);
        timeItem.setEndWeek(endWeek);
        timeItem.setIsRecycle(recycle[0]);
        timeItem.setPlace(place);
    }

    private boolean judgeIsEmptyContent() {
        return TextUtils.isEmpty(metEditevent.getText().toString());
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
                startTime = time;
                mtvStarttime.setText(time);
                break;
            case END:
                endTime = time;
                mtvEndtime.setText(time);
                break;
        }
    }

    @Override
    public void selectRecycleSuccess(int[] recycle) {
        if(has){
            if(this.recycle[0]!=recycle[0]){
                Toast.makeText(AcAddEvent.this,"不能将重复事件更新为不重复事件",Toast.LENGTH_SHORT).show();
            }else{
                updateRecycle(recycle);
            }
        }else{
            updateRecycle(recycle);
        }
    }

    private void updateRecycle(int[] recycle) {
        this.recycle = recycle;
        if (0 == recycle[0]) {
            mtvRecycle.setText(recycleString[0]);
            mrlRecycleWeek.setVisibility(View.INVISIBLE);
        } else {
            if (type == EVENTCOURSE) {
                mrlRecycleWeek.setVisibility(View.VISIBLE);
            }
            StringBuffer buffer = new StringBuffer();
            boolean isFirst = true;
            for (int i = 1; i < 8; i++) {
                if (1 == recycle[i]) {
                    if (isFirst) {
                        buffer.append(recycleString[i]);
                        isFirst = false;
                    } else {
                        buffer.append("," + recycleString[i]);
                    }
                }
            }
            mtvRecycle.setText(buffer.toString());
        }
    }

    @Override
    public void selectRecycleSuccess(String weeks) {
        mstrWeeks = weeks;
        mtvRecycleWeek.setText(weeks + "周");
        String[] strings = mstrWeeks.split("-");
        startWeek = StringToBaseDataType.convertToInt(strings[0], 1);
        endWeek = StringToBaseDataType.convertToInt(strings[1], 1);
    }
}
