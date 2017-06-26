package com.example.situationchange;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btnSlient;

    Button btnShake;

    Button btnLing;

    TextView tvSituation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        btnSlient = findViewById(R.id.id_ac_main_btn_changto_slient);
        btnShake = findViewById(R.id.id_ac_main_btn_changto_shake);
        btnLing = findViewById(R.id.id_ac_main_btn_changto_ling);
        tvSituation = findViewById(R.id.id_ac_main_tv_situation);
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

                break;
            case R.id.id_ac_main_btn_changto_shake:

                break;
            case R.id.id_ac_main_btn_changto_ling:

                break;
        }
    }

    private void changeSituation(){

    }
}
