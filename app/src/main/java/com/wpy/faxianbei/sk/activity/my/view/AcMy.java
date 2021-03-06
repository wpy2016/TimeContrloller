package com.wpy.faxianbei.sk.activity.my.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.my.presenter.PresenterMy;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.ac_my)
public class AcMy extends MvpBaseActivity<IViewMy, PresenterMy> implements IViewMy {

    @ViewInject(R.id.id_ac_my_iv_headimg)
    ImageView mivHeadimg;
    @ViewInject(R.id.id_ac_my_tv_username)
    TextView mtvName;
    @ViewInject(R.id.id_ac_my_tv_school)
    TextView mtvSchool;
    @ViewInject(R.id.id_ac_my_tv_academic)
    TextView mtvAcademic;
    @ViewInject(R.id.id_ac_my_tv_sex)
    TextView mtvSex;
    @ViewInject(R.id.id_ac_my_btn_exit)
    Button mtvLogout;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_my);
        AVOSCloud.initialize(this,"FFwHvC1gi4JDqPnfqkOmshDH-9Nh9j0Va","aLETvSFc2y1G2jmBWeBpSX96");
        x.view().inject(this);
        mContext = AcMy.this;
        initData();
        initEvent();

    }

    private void initEvent() {
        mtvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.logout();
                AcMy.this.finish();
            }
        });
    }

    @Override
    public PresenterMy createPresenter() {
        return new PresenterMy();
    }

    private void initData() {
        mPresenter.setName();
        mPresenter.setSchool();
        mPresenter.setAcademic();
        mPresenter.setSex();
        mPresenter.setHeadimg();
    }


    /**
     * 实现的View的接口
     */


    @Override
    public void setName(String name) {
        mtvName.setText(name);
    }

    @Override
    public void setSchool(String school) {
        mtvSchool.setText(school);
    }

    @Override
    public void setAcademic(String academic) {
        mtvAcademic.setText(academic);
    }

    @Override
    public void setHeadimg(Bitmap bitmap) {
        mivHeadimg.setImageBitmap(bitmap);
    }

    @Override
    public void setList(List<String> list) {
/*****************************更新排行榜*******************************/
    }

    @Override
    public void setSex(String sex) {
        mtvSex.setText(sex);
    }
}
