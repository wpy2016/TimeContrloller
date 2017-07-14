package com.wpy.faxianbei.sk.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by peiyuwang on 16-12-18.
 */

public class RemoteService extends Service {

    private ServiceBuilderConnection connection;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBuilderImpl("remote");
    }

    @Override
    public void onCreate() {
        connection=new ServiceBuilderConnection(null,this,false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Intent intentToSituationService = new Intent(this, SituationService.class);
        this.startService(intentToSituationService);
        this.bindService(intentToSituationService,connection, Context.BIND_IMPORTANT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
