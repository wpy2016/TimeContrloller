package com.wpy.faxianbei.sk.entity;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by peiyuwang on 17-1-2.
 */
@AVClassName("teacher")
public class Teacher extends AVObject {
    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    public Teacher() {
    super();
    }


    public Teacher(Parcel in) {
        super(in);
    }

    public void setName(String name){
        put("name",name);
    }
    public void setEmail(String email)
    {
        put("email",email);
    }
    public String getName(){
        return getString("name");
    }
    public String getEmail(){
        return getString("email");
    }
}
