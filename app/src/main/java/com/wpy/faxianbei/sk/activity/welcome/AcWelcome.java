package com.wpy.faxianbei.sk.activity.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.home.view.AcHome;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.utils.lock.ErrorUtilTencent;
/**
 * 欢迎界面
 */
public class AcWelcome extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_welcome);
        new Thread(){
            @Override
            public void run() {
                AVUser currentUser = AVUser.getCurrentUser();
                if(currentUser!=null)
                {
                    try {
                        currentUser.fetch();
                        toHome();
                    } catch ( AVException e) {
                        SKApplication.isLogin=false;
                        toHome();
                    }
                }else{
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    toHome();
                }
            }
        }.start();
    }

    public void toHome(){
        Intent intent = new Intent(AcWelcome.this, AcHome.class);
        AcWelcome.this.startActivity(intent);
        AcWelcome.this.finish();
    }
}
