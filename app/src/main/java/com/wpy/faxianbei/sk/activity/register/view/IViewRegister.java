package com.wpy.faxianbei.sk.activity.register.view;

import com.wpy.faxianbei.sk.activity.base.OnSuccessOrFail;

/**
 * Created by peiyuwang on 17-1-3.
 */

public interface IViewRegister extends OnSuccessOrFail{
    public String getSchool();
    public String getSchoolId();
    public String getPassword();
    public String getEmail();
    public String getImgPath();
    public String getSchoolIdLogin();
    public String getPassLogin();
}
