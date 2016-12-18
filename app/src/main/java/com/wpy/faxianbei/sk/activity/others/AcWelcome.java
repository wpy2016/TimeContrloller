package com.wpy.faxianbei.sk.activity.others;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVOSCloud;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.service.LockInBackGroundService;
import com.wpy.faxianbei.sk.utils.lock.LockUtil;

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
        openService();
        Button button = (Button) findViewById(R.id.lock);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        LockUtil.lock(AcWelcome.this);
                    }
                }.start();
            }
        });
    }

    private void openService() {
        Intent intent = new Intent(AcWelcome.this, LockInBackGroundService.class);
        startService(intent);
    }
}
