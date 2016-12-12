package com.wpy.faxianbei.sk.activity.others;

import android.app.Activity;
import android.os.Bundle;

import com.wpy.faxianbei.sk.R;

/**
 * 欢迎界面
 */
public class AcWelcome extends CheckPermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_welcome);
    }
}
