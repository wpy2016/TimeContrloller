package com.example.situationchange;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends CheckPermissionsActivity implements View.OnClickListener {

    Button btnSlient;

    Button btnShake;

    Button btnLing;

    TextView tvSituation;
    AudioManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        manager = (AudioManager) MainActivity.this.getSystemService(AUDIO_SERVICE);
        tvSituation.setText(situation(manager.getRingerMode()));
    }

    private void initView() {
        btnSlient = (Button) findViewById(R.id.id_ac_main_btn_changto_slient);
        btnShake = (Button) findViewById(R.id.id_ac_main_btn_changto_shake);
        btnLing = (Button) findViewById(R.id.id_ac_main_btn_changto_ling);
        tvSituation = (TextView) findViewById(R.id.id_ac_main_tv_situation);
    }

    private void initEvent() {
        btnSlient.setOnClickListener(this);
        btnShake.setOnClickListener(this);
        btnLing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.id_ac_main_btn_changto_slient:
                manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            case R.id.id_ac_main_btn_changto_shake:
                manager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case R.id.id_ac_main_btn_changto_ling:
                manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
        }
        tvSituation.setText(situation(manager.getRingerMode()));
    }

    private String situation(int id) {
        String model=null;
        switch (id) {
            case AudioManager.RINGER_MODE_SILENT:
                model="静音";
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                model="震动";
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                model="响铃";
                break;
        }
        return model;
    }

}
