package com.wpy.faxianbei.sk.activity.register.model;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.wpy.faxianbei.sk.activity.base.BaseAsycTask;
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
 * Created by peiyuwang on 17-1-6.
 */

public class ModelImplRegister implements IModelRegister {

    private OnSuccessOrFail listener;
    public ModelImplRegister(OnSuccessOrFail onSuccessOrFail){
        listener=onSuccessOrFail;
    }


    @Override
    public void signUp(final String school, final String id, final String pass, final String email, final String path) {
            if (!path.isEmpty()) {
                if (new File(path).exists()) {
                    new BaseAsycTask<String,String,StuUser,OnSuccessOrFail>(listener,new ErrorUtilTencent())
                    {
                        StuUser user;

                        @Override
                        protected StuUser doInBackground(String... strings) {

                            try {
                                Crawler crawler = new Crawler();
                                String selType = "STU";
                                // 密码账号
                                String username = id;
                                String userPassword = pass;
                                // 登录账号
                                crawler.stuLogin(selType, username, userPassword);
                                // 获取个人信息
                                String stuInfomation = crawler.getStuInforHtml();
                                user = JSON.parseObject(stuInfomation, StuUser.class);
                                return user;
                            }catch (Exception e) {
                                MvpBaseActivity.sendMessage(listener,errorUtil.getErrorString(e.getMessage()),false);
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(StuUser stuUser) {
                            super.onPostExecute(stuUser);
                            if (user != null) {
                                SkUser skUser = new SkUser();
                                skUser.setUsername(user.getStudentId());
                                skUser.setPassword(pass);
                                skUser.setPass(pass);
                                skUser.setEmail(email);
                                skUser.setAcademy(user.getStudentAcademy());
                                skUser.setMajor(user.getStudentMajor());
                                skUser.setGender(user.getStudentGender());
                                skUser.setNickName(user.getStudentName());
                                skUser.setRealName(user.getStudentName());
                                /**
                                 * 上传头像
                                 */
                                try {
                                    skUser.setHead(user.getStudentId()+".png",path);
                                } catch (FileNotFoundException e) {
                                }
                                skUser.setSchool(school);
                                skUser.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        listener.dimissProgress();
                                        if (e == null) {
                                            MvpBaseActivity.sendMessage(listener, "邮箱验证通过才可以登录哦", true);
                                        } else {
                                            MvpBaseActivity.sendMessage(listener,errorUtil.getErrorString(e.getCode(),e.getMessage()), false);
                                        }
                                    }
                                });
                            } else {
                                listener.dimissProgress();
                            }
                        }
                    }.execute();

                } else {
                    listener.onFail("头像不存在");
                }
            } else {
                listener.onFail("请选择头像");
            }
    }
}
