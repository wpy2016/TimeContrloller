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

public class PresenterImplRegister extends BasePresenter<IViewRegister> {

    public PresenterImplRegister() {
    }

    public void signUp() {
        if (getViewInterface() != null) {
            if (!getViewInterface().getImgPath().isEmpty()) {
                if (!new File(getViewInterface().getImgPath()).exists()) {
                    new BaseAsycTask<String,String,StuUser,IViewRegister>(getViewInterface(),new ErrorUtilTencent())
                    {
                        StuUser user;

                        @Override
                        protected StuUser doInBackground(String... strings) {

                            try {
                                Crawler crawler = new Crawler();
                                String selType = "STU";
                                // 密码账号
                                String username = getViewInterface().getSchoolId();
                                String userPassword = getViewInterface().getPassword();
                                // 登录账号
                                crawler.stuLogin(selType, username, userPassword);
                                // 获取个人信息
                                String stuInfomation = crawler.getStuInforHtml();
                                user = JSON.parseObject(stuInfomation, StuUser.class);
                                return user;
                            }catch (Exception e) {
                                MvpBaseActivity.sendMessage(getViewInterface(),errorUtil.getErrorString(e.getMessage()),false);
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(StuUser stuUser) {
                            super.onPostExecute(stuUser);
                            if (user != null) {
                                SkUser skUser = new SkUser();
                                skUser.setUsername(user.getStudentId());
                                skUser.setPassword(getViewInterface().getPassword());
                                skUser.setEmail(getViewInterface().getEmail());
                                skUser.setAcademy(user.getStudentAcademy());
                                skUser.setMajor(user.getStudentMajor());
                                skUser.setGender(user.getStudentGender());
                                skUser.setNickName(user.getStudentName());
                                skUser.setRealName(user.getStudentName());
                                skUser.setSchool(getViewInterface().getSchool());
                                skUser.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        getViewInterface().dimissProgress();
                                        if (e == null) {
                                            MvpBaseActivity.sendMessage(getViewInterface(), "邮箱验证通过才可以登录哦", true);
                                        } else {
                                                MvpBaseActivity.sendMessage(getViewInterface(),errorUtil.getErrorString(e.getCode(),e.getMessage()), false);
                                        }
                                    }
                                });
                            } else {
                                getViewInterface().dimissProgress();
                            }
                        }
                    }.execute();

                } else {
                    getViewInterface().onFail("头像不存在");
                }
            } else {
                getViewInterface().onFail("请选择头像");
            }
        }
    }
}
