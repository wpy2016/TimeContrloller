package com.wpy.faxianbei.sk.application;

import android.app.Application;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.entity.Lesson;
import com.wpy.faxianbei.sk.entity.LessonTable;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.entity.Teacher;

/**
 * Created by peiyuwang on 17-1-2.
 */

public class SKApplication extends Application {

    public static String mSavePath;

    public static boolean isLogin=true;

    @Override
    public void onCreate() {
        super.onCreate();
        AVObject.registerSubclass(Lesson.class);
        AVObject.registerSubclass(Teacher.class);
        AVObject.registerSubclass(LessonTable.class);
        AVUser.alwaysUseSubUserClass(SkUser.class);
        AVOSCloud.initialize(this,"FFwHvC1gi4JDqPnfqkOmshDH-9Nh9j0Va","aLETvSFc2y1G2jmBWeBpSX96");
        //图片保存路径
        mSavePath = getExternalFilesDir(null).getAbsolutePath();
    }


}
