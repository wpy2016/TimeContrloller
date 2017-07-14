package com.wpy.faxianbei.sk.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ServiceCompat;

public class SituationService extends Service {

    public static final int NORMAL=0;
    public static  final int SLIENT=1;
    public static final int SHAKE=2;

    SituationAndNotificationThread thread;

    ServiceBuilderConnection connection;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public SituationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBuilderImpl("localService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        connection = new ServiceBuilderConnection(this, null, true);
        thread=new SituationAndNotificationThread(mHandler,this);
        thread.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Intent intentToRemoteService = new Intent(this, RemoteService.class);
        startService(intentToRemoteService);
        bindService(intentToRemoteService, connection, Context.BIND_IMPORTANT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
