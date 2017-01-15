package com.wpy.faxianbei.sk.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by peiyuwang on 17-1-2.
 */
public class SkUser extends AVUser {
    public SkUser() {
        super();
    }

    public SkUser(Parcel in) {
        super(in);
    }

    public void setSchoolId(String schoolId){
        setUsername(schoolId);
    }

    public void setSchool(String school)
    {
        put("school",school);
    }
    public void setNickName(String nickName)
    {
        put("nickname",nickName);
    }
    public void setRealName(String realName)
    {
        put("realname",realName);
    }
    public void setGender(String gender)
    {
        put("gender",gender);
    }
    public void setAcademy(String academy)
    {
        put("academy",academy);
    }
    public void setMajor(String major){
        put("major",major);
    }
    public void setPass(String pass){
        put("pass",pass);
    }

    public String getPass(){
        return getString("pass");
    }
    public String getSchoolId(){
        return getUsername();
    }
    public String getSchool(){
        return getString("school");
    }
    public String getNickName(){
        return getString("nickname");
    }
    public String getRealName(){
        return getString("realname");
    }
    public String getGender(){
        return getString("gender");
    }
    public String getAcademy(){
        return getString("academy");
    }
    public String getMajor(){
        return getString("major");
    }
    public void setHead(String fileName,String path) throws FileNotFoundException {
        put("headimg",AVFile.withAbsoluteLocalPath(fileName,path));
    }
    public AVFile getHeadImg(){
        AVFile headimg = (AVFile) get("headimg");
        return headimg;
    }
}
