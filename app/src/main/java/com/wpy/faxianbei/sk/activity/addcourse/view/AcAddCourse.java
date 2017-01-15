package com.wpy.faxianbei.sk.activity.addcourse.view;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.addcourse.presenter.PresenterAddCourse;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.entity.SkUser;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
@ContentView(R.layout.ac_import_course)
public class AcAddCourse extends MvpBaseActivity<IViewCourse,PresenterAddCourse> implements IViewCourse{
    @ViewInject(R.id.id_ac_addcourse_et_school)
    EditText metSchool;
    @ViewInject(R.id.id_ac_addcourse_et_academic)
    EditText metAcademic;
    @ViewInject(R.id.id_ac_addcourse_et_schoolid)
    EditText metSchoolid;
    @ViewInject(R.id.id_ac_addcourse_et_semester)
    TextView mtvSemester;
    @ViewInject(R.id.id_ac_addcourse_btn_import)
    Button mBtnImport;
    private Context mContext;
    private PopupWindow mPopSelectSemester;
    private String mstrSemester="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext= AcAddCourse.this;
        initView();
        initEvent();
    }

    private void initEvent() {
        mBtnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.importCourse(mstrSemester);
            }
        });
    }

    @Override
    public PresenterAddCourse createPresenter() {
        return new PresenterAddCourse();
    }

    private void initView() {
        SkUser currentUser = AVUser.getCurrentUser(SkUser.class);
        metSchool.setText(currentUser.getSchool());
        metAcademic.setText(currentUser.getAcademy());
        metSchoolid.setText(currentUser.getSchoolId());
    }

    @Event(value = {R.id.id_ac_addcourse_et_semester})
    private void onClick(View view) {
        mPresenter.showPupWindowForSemester(mContext,mtvSemester);
    }

    @Override
    public void setSemester(String semester) {
        mstrSemester=semester;
        mtvSemester.setText(semester);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void dimissProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}
