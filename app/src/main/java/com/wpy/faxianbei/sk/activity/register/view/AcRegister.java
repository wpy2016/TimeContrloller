package com.wpy.faxianbei.sk.activity.register.view;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.avos.avoscloud.AVOSCloud;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.clip.presenter.ClipPresenter;
import com.wpy.faxianbei.sk.activity.clip.view.IviewClip;
import com.wpy.faxianbei.sk.activity.login.presenter.PresenterImplLogin;
import com.wpy.faxianbei.sk.activity.login.view.IViewLogin;
import com.wpy.faxianbei.sk.activity.my.view.AcMy;
import com.wpy.faxianbei.sk.activity.register.presenter.PresenterImplRegister;
import com.wpy.faxianbei.sk.adapter.RegisterLoginPagerAdaper;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.ui.CircleIndicator;
import com.wpy.faxianbei.sk.ui.anim.DepthPageTransformer;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.ac_register)
public class AcRegister extends MvpBaseActivity<IViewRegister, PresenterImplRegister> implements IViewRegister ,IViewLogin,IviewClip{
    @ViewInject(R.id.id_ac_register_iv_head)
    ImageView mIvHead;
    @ViewInject(R.id.id_ac_register_vp_registerlogin)
    ViewPager mVpRegisterlogin;
    @ViewInject(R.id.id_ac_register_circle_indicator)
    CircleIndicator mCircleIndicator;
    @ViewInject(R.id.id_ac_register_btn_tologin)
    TextView mtvTologin;

    private List<View> mView=new ArrayList<View>();
    private Context mContext;

    private EditText metRegisterSchool;
    private EditText metRegisterSchoolId;
    private EditText metRegisterPass;
    private EditText metRegisterEmail;
    private EditText metLoginSchoolId;
    private EditText metLoginPass;
    private Button mBtnRegister;
    private Button mBtnLogin;
    private RegisterLoginPagerAdaper mRegisterLoginPagerAdaper;
    private PresenterImplLogin mPresenterLogin;
    private ClipPresenter mClipPresenter;

    Form mFormRegister =new Form();
    Form mFormLogin = new Form();

    /**
     * 选择图片
     */
    private PopupWindow mPopWindow;

    //图片保存的文件夹
    private static String PHOTOSAVEPATH = SKApplication.mSavePath + "/crop_photo/";

    //以当前时间的毫秒数当做文件名，设置好的图片的路径
    private String photoname = System.currentTimeMillis() + ".png";
    private String mPath="";  //要找的图片路径

    private final static int PHOTOBYGALLERY = 0;//从相册获取照片

    private final static int PHOTOTACK = 1;//拍照获取

    private final static int PHOTOCOMPLETEBYTAKE = 2;//完成
    private final static int PHOTOCOMPLETEBYGALLERY = 3;//完成

    private static int PHOTOCROP = 3;//图片裁剪





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(this,"FFwHvC1gi4JDqPnfqkOmshDH-9Nh9j0Va","aLETvSFc2y1G2jmBWeBpSX96");
        //login Presenter
        mPresenterLogin = new PresenterImplLogin();
        mPresenterLogin.attachView(this);
        mClipPresenter=new ClipPresenter();
        mClipPresenter.attachView(this);
        x.view().inject(this);
        mContext=AcRegister.this;
        initView();
        initEvent();
    }
    private void initEvent() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFormRegister.validate())
                {
                    mPresenter.signUp();
                }
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFormLogin.validate())
                {
                    mPresenterLogin.login();
                }
            }
        });
        mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //展示popwindow
                mClipPresenter.showPopWindow(mContext,mIvHead);
            }
        });
    }

    private void initView() {
        View registerView = LayoutInflater.from(mContext).inflate(R.layout.register_viewpager_layout, null);
        metRegisterSchool= (EditText) registerView.findViewById(R.id.id_ac_register_ev_school);
        metRegisterSchoolId = (EditText) registerView.findViewById(R.id.id_ac_register_ev_schoolid);
        metRegisterPass = (EditText) registerView.findViewById(R.id.id_ac_register_ev_schoolpass);
        metRegisterEmail= (EditText) registerView.findViewById(R.id.id_ac_register_ev_email);
        mBtnRegister= (Button) registerView.findViewById(R.id.id_ac_register_btn_register);
        View loginView = LayoutInflater.from(mContext).inflate(R.layout.login_viewpager_layout, null);
        metLoginSchoolId = (EditText) loginView.findViewById(R.id.id_ac_login_ev_schoolid);
        metLoginPass = (EditText) loginView.findViewById(R.id.id_ac_login_ev_schoolpass);
        mBtnLogin = (Button) loginView.findViewById(R.id.id_ac_login_btn_login);
        mView.add(registerView);
        mView.add(loginView);
        mRegisterLoginPagerAdaper = new RegisterLoginPagerAdaper(mView);
        mVpRegisterlogin.setAdapter(mRegisterLoginPagerAdaper);
        mVpRegisterlogin.setPageTransformer(true, new DepthPageTransformer());
        mCircleIndicator.setViewPager(mVpRegisterlogin);
        /**
         * Form
         */
        NotEmptyValidator emptyValidator=new NotEmptyValidator(mContext);
        EmailValidator emailValidator = new EmailValidator(mContext);
        //注册学校
        Validate validateRegisterSchool=new Validate(metRegisterSchool);
        validateRegisterSchool.addValidator(emptyValidator);
        //注册学号
        Validate validateRegisterSchoolId=new Validate(metRegisterSchoolId);
        validateRegisterSchoolId.addValidator(emptyValidator);
        //注册密码
        Validate validateRegisterPass=new Validate(metRegisterPass);
        validateRegisterPass.addValidator(emptyValidator);
        //注册邮箱
        Validate validateRegisterEmail=new Validate(metRegisterEmail);
        validateRegisterEmail.addValidator(emptyValidator);
        validateRegisterEmail.addValidator(emailValidator);
        //登录学号、密码
        Validate validateLoginSchoolId=new Validate(metLoginSchoolId);
        validateLoginSchoolId.addValidator(emptyValidator);
        Validate validateLoginPass=new Validate(metLoginPass);
        validateLoginPass.addValidator(emptyValidator);
        mFormRegister.addValidates(validateRegisterSchool);
        mFormRegister.addValidates(validateRegisterSchoolId);
        mFormRegister.addValidates(validateRegisterPass);
        mFormRegister.addValidates(validateRegisterEmail);
        mFormLogin.addValidates(validateLoginSchoolId);
        mFormLogin.addValidates(validateLoginPass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterLogin.detachView();
        mClipPresenter.detachView();
    }

    @Override
    public PresenterImplRegister createPresenter() {
        return new PresenterImplRegister();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mClipPresenter.onActivityResult(mContext,requestCode,resultCode,data);
    }

    /**
     *
     *以下是接口的实现
     */


    @Override
    public String getSchool() {
        if(metRegisterSchool!=null)
        {
            return metRegisterSchool.getText().toString();
        }
        return "";
    }

    @Override
    public String getSchoolId() {
        if(metRegisterSchoolId!=null)
        {
         return metRegisterSchoolId.getText().toString();
        }
        return "";
    }

    @Override
    public String getPassword() {
        if(metRegisterPass!=null)
        {
            return metRegisterPass.getText().toString();
        }
        return "";
    }

    @Override
    public String getEmail() {
        if(metRegisterEmail!=null)
        {
         return metRegisterEmail.getText().toString();
        }
        return "";
    }



    @Override
    public String getImgPath() {
        return mPath;
    }

    @Override
    public String getSchoolIdLogin() {
        if(metLoginSchoolId!=null)
        {
            return metLoginSchoolId.getText().toString();
        }
        return "";
    }

    @Override
    public String getPassLogin() {
        if(metLoginPass!=null)
        {
            return metLoginPass.getText().toString();
        }
        return "";
    }

    @Override
    public void onLoginSuccss(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, AcMy.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFail(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
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

    @Override
    public void clipComplete(Bitmap bitmap, String path) {
        mIvHead.setImageBitmap(bitmap);
        mPath=path;
    }
}
