package com.wpy.faxianbei.sk.activity.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.coursetable2.view.AcCourseTable2;
import com.wpy.faxianbei.sk.activity.dynamic.view.AcDynamic;
import com.wpy.faxianbei.sk.activity.dynamic.webview.AcDynamicWebView;
import com.wpy.faxianbei.sk.activity.home.presenter.PresenterHome;
import com.wpy.faxianbei.sk.activity.statistics.view.AcStatistics;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.service.RemoteService;
import com.wpy.faxianbei.sk.service.SituationService;
import com.wpy.faxianbei.sk.ui.clock.ClockBitmap;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.ac_home)
public class AcHome extends MvpBaseActivity<IViewHome, PresenterHome> implements IViewHome {
    @ViewInject(R.id.id_ac_home_iv_my)
    ImageView mIvMy;
    @ViewInject(R.id.id_ac_home_iv_lock)
    ImageView mIvLock;
    @ViewInject(R.id.id_ac_home_iv_statistics)
    ImageView mIvStatistics;
    @ViewInject(R.id.id_ac_home_iv_dynamic)
    ImageView mIvSetting;

    @ViewInject(R.id.id_ac_home_ll_my)
    LinearLayout mLlMy;
    @ViewInject(R.id.id_ac_home_ll_lock)
    LinearLayout mLlLock;
    @ViewInject(R.id.id_ac_home_ll_statistics)
    LinearLayout mLlStatistics;
    @ViewInject(R.id.id_ac_home_ll_dynamic)
    LinearLayout mLlDynamic;
    @ViewInject(R.id.id_ac_home_iv_startorclose)
    ClockBitmap clock;

    @ViewInject(R.id.id_ac_home_tv_time)
    TextView mtvTime;
    @ViewInject(R.id.id_ac_home_tv_day)
    TextView mtvDay;


    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = AcHome.this;
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "FFwHvC1gi4JDqPnfqkOmshDH-9Nh9j0Va", "aLETvSFc2y1G2jmBWeBpSX96");
        startSituationService();
        startRemoteService();
    }

    @Override
    public PresenterHome createPresenter() {
        return new PresenterHome();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setDateTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dimissProgress() {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFail(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();

    }

    @Event(value = {R.id.id_ac_home_ll_my, R.id.id_ac_home_ll_lock,
            R.id.id_ac_home_ll_statistics, R.id.id_ac_home_ll_dynamic, R.id.id_ac_home_iv_startorclose})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_home_ll_my:
                mPresenter.toMyOrRegister(mContext);
                break;
            case R.id.id_ac_home_ll_lock:
                mPresenter.toNext(mContext, AcCourseTable2.class,false);
                break;
            case R.id.id_ac_home_ll_statistics:
                mPresenter.toNext(mContext, AcStatistics.class,false);
                break;
            case R.id.id_ac_home_ll_dynamic:
                SkUser user=SkUser.getCurrentUser(SkUser.class);
                if(user!=null){
                    // mPresenter.toNext(mContext, AcDynamicWebView.class,false);//改为动态相应的Activity即可  yangxin
                    mPresenter.toNext(mContext, AcDynamic.class,false);//改为动态相应的Activity即可
                }else{
                    Toast.makeText(AcHome.this,"只有登录了才可以进入动态o",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.id_ac_home_iv_startorclose:
                break;
        }
    }


    @Override
    public void updateDate(String date, String day) {
        mtvDay.setText(day);
        mtvTime.setText(date);
    }

    private void startSituationService() {
        Intent intent=new Intent(getApplicationContext(), SituationService.class);
        startService(intent);
    }

    private void startRemoteService() {
        Intent intent=new Intent(getApplicationContext(), RemoteService.class);
        startService(intent);
    }
}
