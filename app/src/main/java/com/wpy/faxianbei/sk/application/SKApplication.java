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

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by peiyuwang on 17-1-2.
 */

public class SKApplication extends Application {

    public static String mSavePath;

    public static boolean isLogin=true;


    public static DbManager mDbManager;

    public static DbManager.DaoConfig mDbConfig;


    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(SKApplication.this,"FFwHvC1gi4JDqPnfqkOmshDH-9Nh9j0Va","aLETvSFc2y1G2jmBWeBpSX96");
        doInit();
    }


    //do init in other thread
    private  void doInit(){
        new Thread(){
            @Override
            public void run() {
                AVObject.registerSubclass(Lesson.class);
                AVObject.registerSubclass(Teacher.class);
                AVObject.registerSubclass(LessonTable.class);
                AVUser.alwaysUseSubUserClass(SkUser.class);
                //图片保存路径
                mSavePath = getExternalFilesDir(null).getAbsolutePath();
                initDb();
            }
        }.start();
    }

    private void initDb() {
        //初始化xutils
        x.Ext.init(this);
        //设置可以debug
        x.Ext.setDebug(true);
        mDbConfig = new DbManager.DaoConfig()
                .setDbName("test")
                .setAllowTransaction(true)
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager dbManager, int i, int i1) {
                        //执行更新的操作
                    }
                });

        mDbManager = x.getDb(mDbConfig);
    }

    /**
     * 返回本地数据库管理者
     * @return xutil本地数据库管理
     */
    public static DbManager getDbManager(){
        if(mDbManager==null)
        {
            if(mDbConfig!=null)
            {
                mDbManager=x.getDb(mDbConfig);
            }
        }
        return mDbManager;
    }


}
