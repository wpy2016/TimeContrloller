package com.wpy.faxianbei.sk.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wpy.faxianbei.sk.activity.others.LockMain;

/**
 * Created by peiyuwang on 16-12-18.
 */

public class LockInBackGroundService extends Service {

    Intent intentMain;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)
            ||intent.getAction().equals(Intent.ACTION_USER_PRESENT))
            {
                toLockMain();
            }

        }
    };

    private void toLockMain() {
        startActivity(intentMain);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        intentMain = new Intent(LockInBackGroundService.this, LockMain.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiver,intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Intent intent = new Intent(LockInBackGroundService.this,LockInBackGroundService.class);
        startService(intent);
    }

}
