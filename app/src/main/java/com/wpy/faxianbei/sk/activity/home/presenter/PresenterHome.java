package com.wpy.faxianbei.sk.activity.home.presenter;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.activity.base.BaseAsycTask;
import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;
import com.wpy.faxianbei.sk.activity.home.model.IModelHome;
import com.wpy.faxianbei.sk.activity.home.model.ModelImplHome;
import com.wpy.faxianbei.sk.activity.home.view.IViewHome;
import com.wpy.faxianbei.sk.activity.my.view.AcMy;
import com.wpy.faxianbei.sk.activity.register.view.AcRegister;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.utils.lock.ErrorUtilTencent;


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

    public void toMyOrRegister(final Context context){
        if (AVUser.getCurrentUser() != null) {
            if(SKApplication.isLogin)
            {
                toNext(context, AcMy.class, false);
            }else{
                new BaseAsycTask<String,String,String,OnSuccessOrFail>(getViewInterface(),new ErrorUtilTencent()){

                    @Override
                    protected void onPreExecute() {

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        if(s!=null)
                        {
                            getViewInterface().onFail(s);
                        }
                    }

                    @Override
                    protected String doInBackground(String... strings) {
                        String error=null;
                        try {
                            AVUser.getCurrentUser().fetch();
                            SKApplication.isLogin=true;
                            toNext(context, AcMy.class, false);
                        } catch (final AVException e) {
                            error = errorUtil.getErrorString(e.getCode(),e.getMessage());
                        }
                        return error;
                    }
                }.execute();
            }

        } else {
            toNext(context, AcRegister.class, false);
        }

    }
}
