package com.wpy.faxianbei.sk.activity.home.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.home.presenter.PresenterHome;
import com.wpy.faxianbei.sk.activity.my.view.AcMy;
import com.wpy.faxianbei.sk.activity.register.view.AcRegister;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import butterknife.BindView;


@ContentView(R.layout.ac_home)
public class AcHome extends MvpBaseActivity<IViewHome, PresenterHome> implements IViewHome {
    @ViewInject(R.id.id_ac_home_iv_my)
    ImageView mIvMy;
    @ViewInject(R.id.id_ac_home_iv_lock)
    ImageView mIvLock;
    @ViewInject(R.id.id_ac_home_iv_statistics)
    ImageView mIvStatistics;
    @ViewInject(R.id.id_ac_home_iv_setting)
    ImageView mIvSetting;

    @ViewInject(R.id.id_ac_home_ll_my)
    LinearLayout mLlMy;
    @ViewInject(R.id.id_ac_home_ll_lock)
    LinearLayout mLlLock;
    @ViewInject(R.id.id_ac_home_ll_statistics)
    LinearLayout mLlStatistics;
    @ViewInject(R.id.id_ac_home_ll_setting)
    LinearLayout mLlSetting;
    @ViewInject(R.id.id_ac_home_iv_startorclose)
    ImageView mIvStartorclose;
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

    }

    @Event(value = {R.id.id_ac_home_ll_my, R.id.id_ac_home_ll_lock,
            R.id.id_ac_home_ll_statistics, R.id.id_ac_home_ll_setting, R.id.id_ac_home_iv_startorclose})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_home_ll_my:
                if (AVUser.getCurrentUser() != null) {
                    mPresenter.toNext(mContext, AcMy.class, false);
                } else {
                    mPresenter.toNext(mContext, AcRegister.class, false);
                }
                break;
            case R.id.id_ac_home_ll_lock:
                break;
            case R.id.id_ac_home_ll_statistics:
                break;
            case R.id.id_ac_home_ll_setting:
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
}
