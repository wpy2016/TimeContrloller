package com.wpy.faxianbei.sk.activity.login.model;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.activity.base.BaseAsycTask;
import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;
import com.wpy.faxianbei.sk.activity.login.view.IViewLogin;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.entity.Lessions;
import com.wpy.faxianbei.sk.entity.Lesson;
import com.wpy.faxianbei.sk.entity.LessonTable;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.Teacher;
import com.wpy.faxianbei.sk.utils.cqu.Crawler;
import com.wpy.faxianbei.sk.utils.general.StringToBaseDataType;
import com.wpy.faxianbei.sk.utils.lock.ErrorUtilTencent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by peiyuwang on 17-1-3.
 */

public class ModelImplLogin implements IModelLogin {

    private OnSuccessOrFail listener;

    public ModelImplLogin(OnSuccessOrFail listener) {
        this.listener = listener;
    }

    @Override
    public void Login(final String id, final String pass) {
        new BaseAsycTask<String, String, String, OnSuccessOrFail>(listener, new ErrorUtilTencent()) {
            @Override
            protected String doInBackground(String... strings) {
                    try {
                        AVUser.logIn(id, pass, SkUser.class);
                        return "登录成功";
                    } catch (AVException e) {
                        return errorUtil.getErrorString(e.getCode(),e.getMessage());
                    }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.contains("成功"))
                {
                    listener.onSuccess(s);

                }else{
                    listener.onFail(s);
                }
            }
        }.execute();
    }
}
