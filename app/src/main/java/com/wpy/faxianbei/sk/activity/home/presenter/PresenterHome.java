package com.wpy.faxianbei.sk.activity.home.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.home.view.IViewHome;
import com.wpy.faxianbei.sk.service.LockInBackGroundService1;

/**
 * Created by peiyuwang on 17-1-5.
 */

public class PresenterHome extends BasePresenter<IViewHome> {
    /**
     * 开启服务
     * @param context 当前的额上下文
     */
    public void startService(Context context){
        Intent intentService=new Intent(context, LockInBackGroundService1.class);
        context.startService(intentService);
    }

    /**
     * 关闭服务
     * @param context 当前的上下文
     */
    public void stopService(Context context)
    {
        Intent intentService=new Intent(context,LockInBackGroundService1.class);
        context.stopService(intentService);
    }

    /**
     * 跳转到另外的一个界面
     * @param context 当前的上下文
     * @param next 下一个界面
     * @param isKill 决定是否结束当前的activity
     */
    public void toNext(Context context, Class<?> next,boolean isKill){
        Intent intentNext= new Intent(context,next);
        context.startActivity(intentNext);
        if(isKill)
        {
            ((Activity)context).finish();
        }
    }
}
