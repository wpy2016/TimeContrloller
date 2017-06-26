package com.wpy.faxianbei.sk.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wpy.faxianbei.sk.service.LockInBackGroundService1;


/**
 * Created by peiyuwang on 16-12-18.
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            // 开屏已经无法开启广播
            Log.i("srceen","receive on");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            //关闭屏幕已经无法开启广播
            Log.i("srceen","receive off");
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            //解锁后进行的操作
            Intent intentService =new Intent(context, LockInBackGroundService1.class);
            context.startService(intentService);
        }
    }
}
