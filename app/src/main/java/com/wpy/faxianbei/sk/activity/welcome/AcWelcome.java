package com.wpy.faxianbei.sk.activity.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.home.view.AcHome;


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
                try {
                    sleep(2000);
                    Intent intent = new Intent(AcWelcome.this, AcHome.class);
                    AcWelcome.this.startActivity(intent);
                    AcWelcome.this.finish();
                } catch (InterruptedException e) {
                }
            }
        }.start();
    }
}
