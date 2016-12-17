package com.wpy.faxianbei.sk.activity.others;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.wpy.faxianbei.sk.R;

/**
 * 欢迎界面
 */
public class AcWelcome extends CheckPermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_welcome);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"FFwHvC1gi4JDqPnfqkOmshDH-9Nh9j0Va","aLETvSFc2y1G2jmBWeBpSX96");
        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });
    }
}
