package com.wpy.faxianbei.sk.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



/**
 * Created by peiyuwang on 16-12-18.
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentTOMain=new Intent(context, com.wpy.faxianbei.sk.activity.lock.LockMain.class);
        intentTOMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            // 开屏
            Log.i("open","打开屏幕");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.i("close","关闭屏幕");
            //关闭屏幕后
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            //解锁后进行的操作
            context.startActivity(intentTOMain);
        }
    }
}
