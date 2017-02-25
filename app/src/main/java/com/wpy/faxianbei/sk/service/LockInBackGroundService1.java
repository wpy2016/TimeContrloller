package com.wpy.faxianbei.sk.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.model.ModelImplPupCourse;
import com.wpy.faxianbei.sk.activity.welcome.AcWelcome;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.db.CourseTable;
import com.wpy.faxianbei.sk.entity.db.TimeItem;
import com.wpy.faxianbei.sk.entity.db.openRecord;
import com.wpy.faxianbei.sk.utils.save.sharepreference.SharePreferenceUtil;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by peiyuwang on 16-12-18.
 */

public class LockInBackGroundService1 extends Service {

    CalcuThread calcuThread;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        calcuThread=new CalcuThread();
        new Thread(){
            boolean lock=false;
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    int semester = ModelImplPupCourse.getSemester(SharePreferenceUtil.instantiation.getSemester(LockInBackGroundService1.this));
                    int year = ModelImplPupCourse.getYear(SharePreferenceUtil.instantiation.getSemester(LockInBackGroundService1.this));
                    Date date=new Date(System.currentTimeMillis());
                    SimpleDateFormat simpleDateFormatDay=new SimpleDateFormat("EEEE");
                    String day=simpleDateFormatDay.format(date);
                    List<CourseTable> list = SKApplication.getDbManager().selector(CourseTable.class).where(
                            WhereBuilder.b().and("stuid","=", SkUser.getCurrentUser(SkUser.class).getSchoolId()).and("semester","=",""+semester)
                                    .and("year","=",""+year).and("day","=",day.substring(2))).findAll();
                    if(list==null||list.isEmpty())
                    {}else{
                        int week=getCurrentWeek(LockInBackGroundService1.this);
                        for(int i=0;i<list.size()/2;i++)
                        {
                            String[] split = list.get(i).getWeeks()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .replace(" ","")
                                    .split(",");
                            for(String s:split)
                            {
                                String sss = list.get(i).getCourse();
                                if(s.equals(week+""))
                                {
                                    lock=true;
                                    if(System.currentTimeMillis()>getStartTime(list.get(i).getTime())&&System.currentTimeMillis()<getEndime(list.get(i).getTime()))
                                    {
                                        new Handler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Notification.Builder builder=new Notification.Builder(getApplicationContext());
                                                Intent intent=new Intent(LockInBackGroundService1.this, AcWelcome.class);
                                                PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),110,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                                RemoteViews remoteViews = new RemoteViews(LockInBackGroundService1.this.getPackageName(),R.layout.notification_always);
                                                remoteViews.setTextViewText(R.id.notification_tv_lesson,"大数据");
                                                remoteViews.setTextViewText(R.id.notification_tv_classroom,"D1314");
                                                remoteViews.setTextViewText(R.id.notification_tv_time,"3-4节");
                                                remoteViews.setTextViewText(R.id.notification_tv_grade,""+95);
                                                remoteViews.setImageViewResource(R.id.notification_iv_img,R.drawable.logo);
                                                Notification notification = builder.setContent(remoteViews)
                                                        .setSmallIcon(R.mipmap.ic_launcher)
                                                        .setContentIntent(pendingIntent).build();
                                                startForeground(1,notification);
                                            }
                                        });
                                    }
                                }
                            }

                        }
                    }

                    try {
                        List<TimeItem> listtime= SKApplication
                                .getDbManager()
                                .selector(TimeItem.class).findAll();
                        if(listtime==null||listtime.isEmpty())
                        {

                        }else{
                            for(TimeItem timeitem:listtime)
                            {
                                if(timeitem.getEnd()>System.currentTimeMillis())
                                {
                                    lock=true;
                                }
                            }
                        }
                    } catch (DbException e) {

                    }

                    if(lock){
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                    Intent intentTOMain=new Intent(LockInBackGroundService1.this, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
                                    intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    LockInBackGroundService1.this.startActivity(intentTOMain);
                                }
                            });
                        calcuThread.start();
                    }
                } catch (Exception e) {
                }
            }
        }.start();
    }


    private class CalcuThread extends Thread{
        //当屏幕已经关闭的时候，就可以关闭线程了
       private boolean go=true;
      private   long openTime=0l;
        @Override
        public void run() {
            PowerManager manager = (PowerManager) LockInBackGroundService1.this.getSystemService(Context.POWER_SERVICE);
            while (go) {
                openTime+=1000;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                if (manager.isScreenOn()) {
                } else {
                    go=false;
                    //将记录的数据保存起来
                    Date date=new Date(System.currentTimeMillis());
                    openRecord openRecord=new openRecord();
                    openRecord.setDay(date.getDay()+"");
                    openRecord.setYear(date.getYear()+"");
                    openRecord.setMonth(date.getMonth()+"");
                    openRecord.setMinute(date.getMinutes()+"");
                    openRecord.setHour(date.getHours()+"");
                    openRecord.setOpentime(openTime);
                    openRecord.setType(0+"");
                    try {
                        //将记录给保存起来
                        SKApplication.getDbManager().save(openRecord);
                    } catch (DbException e) {
                    }
                }
            }
        }

        public long getOpenTime(){
            return openTime;
        }

        public boolean getStatu(){
            return go;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public long getStartTime(String strTime){
        Date date = new Date(System.currentTimeMillis());
        android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy.MM.dd");
        String time="";
        if(strTime.equals("1-2节")){
           time = simpleDateFormat.format(date) + " 08:30";
        }else if(strTime.equals("3-4节"))
        {
            time=simpleDateFormat.format(date) + " 10:30";
        }else if(strTime.equals("5-6节"))
        {
            time=simpleDateFormat.format(date) + " 14:00";
        }else if(strTime.equals("7-8节"))
        {
            time=simpleDateFormat.format(date) + " 16:00";
        }else if(strTime.equals("9-10节"))
        {
            time=simpleDateFormat.format(date) + " 19:00";
        }else if(strTime.equals("10-11节"))
        {
            time=simpleDateFormat.format(date) + " 21:00";
        }
        android.icu.text.SimpleDateFormat simpleDateFormat2 = new android.icu.text.SimpleDateFormat("yyyy.MM.dd HH:mm");
        try {
            return simpleDateFormat2.parse(time).getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public long getEndime(String strTime){
        Date date = new Date(System.currentTimeMillis());
        android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy.MM.dd");
        String time="";
        if(strTime.equals("1-2节")){
            time = simpleDateFormat.format(date) + " 10:10";
        }else if(strTime.equals("3-4节"))
        {
            time=simpleDateFormat.format(date) + " 12:10";
        }else if(strTime.equals("5-6节"))
        {
            time=simpleDateFormat.format(date) + " 15:40";
        }else if(strTime.equals("7-8节"))
        {
            time=simpleDateFormat.format(date) + " 17:40";
        }else if(strTime.equals("9-10节"))
        {
            time=simpleDateFormat.format(date) + " 20:40";
        }else if(strTime.equals("10-11节"))
        {
            time=simpleDateFormat.format(date) + " 22:40";
        }
        android.icu.text.SimpleDateFormat simpleDateFormat2 = new android.icu.text.SimpleDateFormat("yyyy.MM.dd HH:mm");
        try {
            return simpleDateFormat2.parse(time).getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    public int getCurrentWeek(Context context) {
        return (int) Math.ceil((double)((System.currentTimeMillis()-Long.parseLong(SharePreferenceUtil.instantiation.getWeek(context)))/(1000*60*60*24*7.0)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
