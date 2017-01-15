package com.wpy.faxianbei.sk.activity.lock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.other.SwipeBackActivity;
import com.wpy.faxianbei.sk.ui.SwipeBackLayout;

import java.util.Random;

/**
 * Created by peiyuwang on 16-12-18.
 */

public class LockMain extends SwipeBackActivity {

    private Context mContext;

    private ImageView imageView;

    private int[] lockImgid={R.drawable.lock1,R.drawable.lock2,R.drawable.lock3,R.drawable.lock4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = LockMain.this;
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        setContentView(R.layout.ac_lockmain);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageView = (ImageView) findViewById(R.id.id_ac_lockmain_iv_background);
        int id=new Random().nextInt(4);
        try{
            imageView.setImageResource(lockImgid[id]);
        }catch (Exception e)
        {
            imageView.setImageResource(lockImgid[id-1]);
        }
    }

    //屏蔽back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Toast.makeText(mContext,getResources().getString(R.string.leftswipe),Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //屏蔽menu
    @Override
    public void onWindowFocusChanged(boolean pHasWindowFocus) {
        super.onWindowFocusChanged(pHasWindowFocus);
        if (!pHasWindowFocus) {
            sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }
}
