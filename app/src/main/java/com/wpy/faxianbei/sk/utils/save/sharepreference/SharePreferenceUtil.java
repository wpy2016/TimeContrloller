package com.wpy.faxianbei.sk.utils.save.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by peiyuwang on 17-2-20.
 */

/**
 * 枚举实现单例
 */
public enum  SharePreferenceUtil {

    instantiation;

    public void saveCurrentSemester(Context context, String message){
        SharedPreferences semester = context.getSharedPreferences("semester", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = semester.edit();
        edit.putString("semester",message);
        edit.apply();
    }

    public void saveFirstWeek(Context context, String date){
        SharedPreferences semester = context.getSharedPreferences("firstweek", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = semester.edit();
        edit.putString("firstweek",date);
        edit.apply();
    }

    public String getWeek(Context context){
        SharedPreferences semester = context.getSharedPreferences("firstweek", Context.MODE_PRIVATE);
     return semester.getString("firstweek",(System.currentTimeMillis()-100000)+"");
    }

    public String getSemester(Context context){
        SharedPreferences semester = context.getSharedPreferences("semester", Context.MODE_PRIVATE);
     return semester.getString("semester","2016-2017 第二学期");
    }

}
