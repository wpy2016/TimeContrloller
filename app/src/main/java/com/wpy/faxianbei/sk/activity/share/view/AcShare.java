package com.wpy.faxianbei.sk.activity.share.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.CheckPermissionsActivity;
import com.wpy.faxianbei.sk.activity.dynamic.view.AcDynamic;
import com.wpy.faxianbei.sk.entity.Dynamic;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.utils.general.FileUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;

import cn.sharesdk.onekeyshare.OnekeyShare;

@ContentView(R.layout.ac_share_website)
public class AcShare extends CheckPermissionsActivity {
    @ViewInject(R.id.id_ac_share_iv_screenshot)
    ImageView mivScreenshot;
    @ViewInject(R.id.id_ac_share_iv_share)
    ImageView mivShare;
    @ViewInject(R.id.id_ac_share_et_share_content)
    EditText metContent;
    @ViewInject(R.id.id_ac_share_iv_share_in_owner)
    ImageView mivShareOwn;
    Context mContext;
    String mStrPath = null;

    protected String[] needPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.CAPTURE_AUDIO_OUTPUT
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putStringArray("permission", needPermissions);
        super.onCreate(bundle);
        x.view().inject(this);
        mContext = AcShare.this;
        initView();
    }

    private void initView() {
        mStrPath = getIntent().getStringExtra("imgpath");
        if (mStrPath != null && (!mStrPath.equals("")) && (new File(mStrPath).exists())) {
            Bitmap bitmap = FileUtil.getBitmapFormPath(mContext, mStrPath);
            mivScreenshot.setImageBitmap(bitmap);
        }
    }

    @Event(value = {R.id.id_ac_share_iv_screenshot, R.id.id_ac_share_iv_share,R.id.id_ac_share_iv_share_in_owner})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_share_iv_screenshot:
                break;
            case R.id.id_ac_share_iv_share:
                showShare();
                break;
            case R.id.id_ac_share_iv_share_in_owner:
                try {
                    shareOwn();
                } catch (FileNotFoundException e) {

                }
                break;
        }
    }

    private void shareOwn() throws FileNotFoundException {
        SkUser user= AVUser.getCurrentUser(SkUser.class);
        if(user!=null){
            progressDialog.show();
            Dynamic dynamic=new Dynamic();
            dynamic.setContent(metContent.getText().toString());
            dynamic.setShareImg(mStrPath);
            dynamic.setUser(user);
            dynamic.setCommentCount(0);
            dynamic.setLike(0);
            dynamic.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    AcShare.this.progressDialog.dismiss();
                    if(e==null){
                        toDynamic();
                    }else{
                        Toast.makeText(AcShare.this,"分享失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this,"只有登录了才可以分享哦",Toast.LENGTH_SHORT).show();
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("TimeController");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(metContent.getText().toString());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(mStrPath);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        //      oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    public void toDynamic(){
        Intent intent=new Intent(this, AcDynamic.class);
        startActivity(intent);
        finish();
    }
}
