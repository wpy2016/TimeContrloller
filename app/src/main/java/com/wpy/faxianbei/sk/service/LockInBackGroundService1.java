package com.wpy.faxianbei.sk.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.welcome.AcWelcome;


/**
 * Created by peiyuwang on 16-12-18.
 */

public class LockInBackGroundService1 extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Notification.Builder builder=new Notification.Builder(getApplicationContext());
        Intent intent=new Intent(LockInBackGroundService1.this, AcWelcome.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),110,intent,PendingIntent.FLAG_UPDATE_CURRENT);
         RemoteViews remoteViews = new RemoteViews(this.getPackageName(),R.layout.notification_always);
        remoteViews.setTextViewText(R.id.notification_tv_lesson,"大数据");
        remoteViews.setTextViewText(R.id.notification_tv_classroom,"D1314");
        remoteViews.setTextViewText(R.id.notification_tv_time,"3-4节");
        remoteViews.setTextViewText(R.id.notification_tv_grade,""+95);
        remoteViews.setImageViewResource(R.id.notification_iv_img,R.mipmap.ic_launcher);
        Notification notification = builder.setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
        .setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Notification.Builder builder=new Notification.Builder(getApplicationContext());
                Intent intent=new Intent(LockInBackGroundService1.this, AcWelcome.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),110,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                RemoteViews remoteViews = new RemoteViews(LockInBackGroundService1.this.getPackageName(),R.layout.notification_always);
                remoteViews.setTextViewText(R.id.notification_tv_lesson,"信息安全导论");
                remoteViews.setTextViewText(R.id.notification_tv_classroom,"Ds1501");
                remoteViews.setTextViewText(R.id.notification_tv_time,"8-9节");
                remoteViews.setTextViewText(R.id.notification_tv_grade,""+95);
                remoteViews.setImageViewResource(R.id.notification_iv_img,R.mipmap.ic_launcher);
                Notification notification = builder.setContent(remoteViews)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent).build();
                startForeground(1,notification);
            }
        },1000*60);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    PowerManager manager = (PowerManager) LockInBackGroundService1.this.getSystemService(Context.POWER_SERVICE);
                    if (manager.isScreenOn()) {
                        Log.i("srceen", "on");
                    } else {
                        Log.i("screen", "off");
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
