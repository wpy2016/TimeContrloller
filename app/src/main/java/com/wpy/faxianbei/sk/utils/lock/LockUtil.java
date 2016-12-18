package com.wpy.faxianbei.sk.utils.lock;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wpy.faxianbei.sk.broadcast.LockBroadCast;

/**
 * Created by peiyuwang on 16-12-18.
 */

public class LockUtil {
    public static boolean lock(Context context){
        //获取设备管理服务
        DevicePolicyManager policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        //AdminReceiver 继承自 DeviceAdminReceiver
        ComponentName componentName = new ComponentName(context, LockBroadCast.class);
        if (policyManager.isAdminActive(componentName)) {//判断是否有权限(激活了设备管理器)
            policyManager.lockNow();// 直接锁屏
            //杀死当前应用
           // android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }else{
            //使用隐式意图调用系统方法来激活指定的设备管理器
            Intent intent = new Intent();
            //发送广播，让在Acitivity中注册的广播处理接受到的广播
            intent.setAction("com.wpy.faxianbei.sk.getLockauthority");
            context.sendBroadcast(intent);
            return false;
        }
    }
}
