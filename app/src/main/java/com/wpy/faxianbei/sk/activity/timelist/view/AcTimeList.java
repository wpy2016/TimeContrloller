package com.wpy.faxianbei.sk.activity.timelist.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addtime.view.AcAddTime;
import com.wpy.faxianbei.sk.adapter.CommonAdapter;
import com.wpy.faxianbei.sk.adapter.ViewHolder;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.db.TimeItem;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ContentView(R.layout.ac_timelist)
public class AcTimeList extends Activity {

    @ViewInject(R.id.id_ac_timelist_tv_showtime)
    TextView mtvShowtime;
    @ViewInject(R.id.id_ac_timelist_lv_showtimelist)
    ListView mlvShowtimelist;
    @ViewInject(R.id.id_ac_timelist_iv_addtime)
    ImageView mivAddtime;

    List<TimeItem> timeArrayList=new ArrayList<TimeItem>();

    CommonAdapter<TimeItem> adapter;

    private Context mContext;

    @Override
    protected void onResume() {
        super.onResume();
        try {
           List<TimeItem> list= SKApplication
                   .getDbManager()
                   .selector(TimeItem.class).findAll();
            if(list==null||list.isEmpty())
            {
                Toast.makeText(mContext,"今天没有时间段控制哦",Toast.LENGTH_SHORT).show();

            }else{
                timeArrayList.clear();
                for(TimeItem timeitem:list)
                {
                    if(timeitem.getEnd()>System.currentTimeMillis())
                    {
                        timeArrayList.add(timeitem);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        } catch (DbException e) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = AcTimeList.this;
        adapter=new CommonAdapter<TimeItem>(mContext,timeArrayList,R.layout.list_time) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void convert(ViewHolder helper, TimeItem item) {
                Date date=new Date(item.getStart());
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                helper.setText(R.id.id_timelist_start,simpleDateFormat.format(date).substring(5));
                date.setTime(item.getEnd());
                helper.setText(R.id.id_timelist_end,simpleDateFormat.format(date).substring(5));
            }
        };
        mlvShowtimelist.setAdapter(adapter);
        mtvShowtime.setText(getDateNow());
    }

    @Event(value = {R.id.id_ac_timelist_iv_addtime})
    private void onClick(View view) {
        Intent intent=new Intent(AcTimeList.this, AcAddTime.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDateNow(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
       return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }
}
