package com.wpy.faxianbei.sk.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.db.openRecord;
import com.wpy.faxianbei.sk.utils.dateUtil.DateUtil;

import org.xutils.ex.DbException;


/**
 * Created by peiyuwang on 16-12-18.
 */

public class LockInBackGroundService1 extends Service {

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    CalcuThread calcuThread;

    IsNeedLockThread needLockThread;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }


     class CalcuThread extends Thread {
        //当屏幕已经关闭的时候，就可以关闭线程了
        private boolean go = true;
        private long openTime = 0l;

        public void reset(){
            go=true;
        }

        @Override
        public void run() {
            PowerManager manager = (PowerManager) LockInBackGroundService1.this.getSystemService(Context.POWER_SERVICE);
            while (go) {
                openTime += 1000;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                if (manager.isScreenOn()) {
                    //屏幕依然在启动着，不进行任何的操作
                } else {
                    go = false;
                    //将记录的数据保存起来
                    openRecord openRecord = new openRecord();
                    DateUtil.setTimeInMillis(System.currentTimeMillis());
                    openRecord.setDay(DateUtil.getCurrentDay() + "");
                    openRecord.setYear(DateUtil.getCurrentYear()+ "");
                    openRecord.setMonth(DateUtil.getCurrentMonth() + "");
                    openRecord.setMinute(DateUtil.getCurrentMinute() + "");
                    openRecord.setHour(DateUtil.getCurrentHour() + "");
                    openRecord.setOpentime(openTime);
                    openRecord.setType(0 + "");
                    try {
                        //将记录给保存起来
                        SKApplication.getDbManager().save(openRecord);
                    } catch (DbException e) {
                    }
                }
            }
        }
    }


    private void caculate() {
        needLockThread=new IsNeedLockThread(mhandler,this,calcuThread);
        needLockThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        calcuThread = new CalcuThread();
        calcuThread.reset();
        caculate();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
