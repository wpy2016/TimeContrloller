package com.wpy.faxianbei.sk.activity.home.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wpy.faxianbei.sk.service.LockService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peiyuwang on 17-1-6.
 */

public class ModelImplHome implements IModelHome {
    @Override
    public String getDate()
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
        Date date=new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }

    @Override
    public String getDay() {
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormatDay=new SimpleDateFormat("EEEE");
        String day=simpleDateFormatDay.format(date);
        return day;
    }

    @Override
    public void StartService(Context context) {
        Intent intentService=new Intent(context, LockService.class);
        context.startService(intentService);
    }

    @Override
    public void StopService(Context context) {
        Intent intentService=new Intent(context,LockService.class);
        context.stopService(intentService);
    }

    @Override
    public void toNext(Context context, Class<?> next,boolean isKill) {
        Intent intentNext= new Intent(context,next);
        context.startActivity(intentNext);
        if(isKill)
        {
            ((Activity)context).finish();
        }
    }


}
