package com.wpy.faxianbei.sk.activity.setting.view;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.blackandwhite.view.AcBlackAndWhite;
import com.wpy.faxianbei.sk.activity.situation.view.AcSituation;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.ac_setting)
public class AcSetting extends Activity {

    Context mContext;
    @ViewInject(R.id.id_ac_setting_iv_blackandwhite)
    ImageView mIvBlackandwhite;
    @ViewInject(R.id.id_ac_setting_iv_situation)
    ImageView mIvSituation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = AcSetting.this;

    }

    @Event(value={R.id.id_ac_setting_iv_blackandwhite, R.id.id_ac_setting_iv_situation})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_setting_iv_blackandwhite:
                toNext(AcBlackAndWhite.class);
                break;
            case R.id.id_ac_setting_iv_situation:
                toNext(AcSituation.class);
                break;
        }
    }

    private void toNext(Class<?> next){
        Intent intent=new Intent(mContext,next);
        startActivity(intent);
        finish();
    }
}
