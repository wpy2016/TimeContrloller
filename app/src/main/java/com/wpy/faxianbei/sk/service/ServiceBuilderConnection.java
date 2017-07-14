package com.wpy.faxianbei.sk.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by wangpeiyu on 2017/7/13.
 */

public class ServiceBuilderConnection implements ServiceConnection {

    private Service localService;
    private Service remoteService;
    private boolean isLocal=false;
    ServiceBuilderConnection(Service localService,Service remoteService,boolean isLocal){
        this.localService=localService;
        this.remoteService = remoteService;
        this.isLocal=isLocal;
    }
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        if (isLocal) {
            Intent intent=new Intent(localService,RemoteService.class);
            localService.startService(intent);
            localService.bindService(intent,this, Context.BIND_IMPORTANT);
        }else{
            Intent intentToSituationService = new Intent(remoteService, SituationService.class);
            remoteService.startService(intentToSituationService);
            remoteService.bindService(intentToSituationService,this, Context.BIND_IMPORTANT);
        }
    }
}
