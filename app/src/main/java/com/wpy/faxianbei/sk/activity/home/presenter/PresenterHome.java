package com.wpy.faxianbei.sk.activity.home.presenter;
import android.content.Context;


import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.home.model.IModelHome;
import com.wpy.faxianbei.sk.activity.home.model.ModelImplHome;
import com.wpy.faxianbei.sk.activity.home.view.IViewHome;


/**
 * Created by peiyuwang on 17-1-5.
 */

public class PresenterHome extends BasePresenter<IViewHome> {

    private IModelHome home;

    public PresenterHome() {
        this.home = new ModelImplHome();
    }

    /**
     * 开启服务
     * @param context 当前的额上下文
     */
    public void startService(Context context){
        home.StartService(context);
    }

    /**
     * 关闭服务
     * @param context 当前的上下文
     */
    public void stopService(Context context)
    {
        home.StopService(context);
    }

    /**
     * 跳转到另外的一个界面
     * @param context 当前的上下文
     * @param next 下一个界面
     * @param isKill 决定是否结束当前的activity
     */
    public void toNext(Context context, Class<?> next,boolean isKill){
        home.toNext(context,next,isKill);
    }

    public void setDateTime(){
        if(getViewInterface()!=null)
        {
            getViewInterface().updateDate(home.getDate(),home.getDay());
        }
    }
}
