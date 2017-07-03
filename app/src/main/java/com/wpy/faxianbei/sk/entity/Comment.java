package com.wpy.faxianbei.sk.entity;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by wangpeiyu on 2017/7/3.
 */
@AVClassName("comment")
public class Comment extends AVObject {

    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    public Comment() {
        super();
    }

    public Comment(Parcel in) {
        super(in);
    }

    public void setUser(AVObject user){
        put("user",user);
    }

    public void setContent(String content){
        put("content",content);
    }

    public void setDynamic(AVObject dynamic){
        put("dynamic",dynamic);
    }

    public Dynamic getDynamic(){
       return (Dynamic) get("dynamic");
    }

    public String getContent()
    {
        return (String) get("content");
    }


    public SkUser getUser(){
        return (SkUser) get("user");
    }

}
