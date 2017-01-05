package com.wpy.faxianbei.sk.activity.login.view;

import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;

/**
 * Created by peiyuwang on 17-1-3.
 */

public interface IViewLogin extends OnSuccessOrFail{
    public String getSchoolIdLogin();
    public String getPassLogin();
    public void onLoginSuccss(String message);
    public void onLoginFail(String message);
}
