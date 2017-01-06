package com.wpy.faxianbei.sk.activity.login.presenter;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.wpy.faxianbei.sk.activity.base.BaseAsycTask;
import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;
import com.wpy.faxianbei.sk.activity.login.model.IModelLogin;
import com.wpy.faxianbei.sk.activity.login.model.ModelImplLogin;
import com.wpy.faxianbei.sk.activity.login.view.IViewLogin;
import com.wpy.faxianbei.sk.entity.Lessions;
import com.wpy.faxianbei.sk.entity.Lesson;
import com.wpy.faxianbei.sk.entity.LessonTable;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.Teacher;
import com.wpy.faxianbei.sk.utils.cqu.Crawler;
import com.wpy.faxianbei.sk.utils.general.StringToBaseDataType;
import com.wpy.faxianbei.sk.utils.lock.ErrorUtilTencent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by peiyuwang on 17-1-3.
 */

public class PresenterImplLogin extends BasePresenter<IViewLogin> implements OnSuccessOrFail{

    private IModelLogin login;

    public PresenterImplLogin() {
        login = new ModelImplLogin(this);
    }

    public void login(){
        if(getViewInterface()!=null)
        {
            login.Login(getViewInterface().getSchoolIdLogin(),getViewInterface().getPassLogin());
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
            getViewInterface().onLoginSuccss(message);
        }
    }

    @Override
    public void onFail(String message) {
        if(getViewInterface()!=null)
        {
            getViewInterface().onLoginFail(message);
        }
    }
}
