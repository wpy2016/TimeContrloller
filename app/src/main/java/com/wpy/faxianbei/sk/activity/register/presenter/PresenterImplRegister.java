package com.wpy.faxianbei.sk.activity.register.presenter;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.wpy.faxianbei.sk.activity.base.BaseAsycTask;
import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;
import com.wpy.faxianbei.sk.activity.register.model.IModelRegister;
import com.wpy.faxianbei.sk.activity.register.model.ModelImplRegister;
import com.wpy.faxianbei.sk.activity.register.view.IViewRegister;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.StuUser;
import com.wpy.faxianbei.sk.utils.cqu.Crawler;
import com.wpy.faxianbei.sk.utils.lock.ErrorUtilTencent;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by peiyuwang on 17-1-3.
 */

public class PresenterImplRegister extends BasePresenter<IViewRegister> implements OnSuccessOrFail{

    private IModelRegister register;


    public PresenterImplRegister() {
        register = new ModelImplRegister(this);
    }

    public void signUp() {
        if (getViewInterface() != null) {
            register.signUp(getViewInterface().getSchool(),getViewInterface().getSchoolId(),
                    getViewInterface().getPassword(),getViewInterface().getEmail(),getViewInterface().getImgPath());
        }
    }

    @Override
    public void showProgress() {
        if(getViewInterface()!=null)
        {
            getViewInterface().showProgress();
        }
    }

    @Override
    public void dimissProgress() {
        if(getViewInterface()!=null)
        {
            getViewInterface().dimissProgress();
        }
    }

    @Override
    public void onSuccess(String message) {
        if(getViewInterface()!=null)
        {
            getViewInterface().onSuccess(message);
        }

    }

    @Override
    public void onFail(String message) {
        if(getViewInterface()!=null)
        {
            getViewInterface().onFail(message);
        }
    }
}
