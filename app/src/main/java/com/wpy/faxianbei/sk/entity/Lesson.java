package com.wpy.faxianbei.sk.entity;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by peiyuwang on 17-1-2.
 */
@AVClassName("lesson")
public class Lesson extends AVObject {

    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    public Lesson() {
        super();
    }

    public Lesson(Parcel in) {
        super(in);
    }


    public void setLesson(String lesson)
    {
        put("lesson",lesson);
    }
    public String getLesson(){
        return getString("lesson");
    }
}
