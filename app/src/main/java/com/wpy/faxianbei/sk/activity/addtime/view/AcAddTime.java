package com.wpy.faxianbei.sk.activity.addtime.view;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addtime.presenter.PresenterAddTime;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.db.TimeItem;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;


@ContentView(R.layout.ac_addtime)
public class AcAddTime extends MvpBaseActivity<IViewAddTime,PresenterAddTime> implements IViewAddTime{

    @ViewInject(R.id.id_ac_addtime_tv_starttime)
    TextView mtvStarttime;
    @ViewInject(R.id.id_ac_addtime_tv_endtime)
    TextView mtvEndtime;
    @ViewInject(R.id.id_ac_addtime_iv_save)
    ImageView mivSave;
    @ViewInject(R.id.id_ac_addtime_iv_slient)
    ImageView mivSlient;
    @ViewInject(R.id.id_ac_addtime_iv_shake)
    ImageView mivShake;

    private final int START=0;
    private final int END=1;

    private long lStart=0;
    private long lEnd=0;

    private Context mContext;

    private int model=2;

    public static final int SHAKE=0;
    public static final int SLIENT=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext=AcAddTime.this;
    }

    @Override
    public PresenterAddTime createPresenter() {
        return new PresenterAddTime();
    }

    @Event(value = {R.id.id_ac_addtime_tv_starttime, R.id.id_ac_addtime_tv_endtime,
            R.id.id_ac_addtime_iv_save, R.id.id_ac_addtime_iv_slient, R.id.id_ac_addtime_iv_shake})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_addtime_tv_starttime:
                mPresenter.showPopTime(mContext,mtvStarttime,START);
                break;
            case R.id.id_ac_addtime_tv_endtime:
                mPresenter.showPopTime(mContext,mtvEndtime,END);
                break;
            case R.id.id_ac_addtime_iv_save:
                if(model==2){
                    Toast.makeText(mContext,"请选择当前时间段的情景模式",Toast.LENGTH_SHORT).show();
                }else{
                    /**
                     * 保存当前的时间段
                     */
                    TimeItem time=new TimeItem(lStart,lEnd,model);
                    try {
                        SKApplication.getDbManager().save(time);
                        Toast.makeText(mContext,"保存成功",Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {

                    }
                }
                break;
            case R.id.id_ac_addtime_iv_slient:
                model=SLIENT;
                Toast.makeText(mContext,"你选择的情景模式为静音",Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_ac_addtime_iv_shake:
                model=SHAKE;
                Toast.makeText(mContext,"你选择的情景模式为震动",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void selectTimeSuccess(String time, int type) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        switch (type)
        {
            case START:
                try {
                    mtvStarttime.setText(time);
                    lStart= simpleDateFormat.parse(time).getTime();
                } catch (ParseException e) {
                }
                break;
            case END:
                try {
                    mtvEndtime.setText(time);
                    lEnd= simpleDateFormat.parse(time).getTime();
                } catch (ParseException e) {
                }
                break;
        }
    }


}
