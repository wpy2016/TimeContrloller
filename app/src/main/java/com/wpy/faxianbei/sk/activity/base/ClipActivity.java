package com.wpy.faxianbei.sk.activity.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.ui.clip.ClipImageLayout;
import com.wpy.faxianbei.sk.utils.general.ImageTools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
/**
 */
@ContentView(R.layout.ac_clipimage)
public class ClipActivity extends CheckPermissionsActivity {
    @ViewInject(R.id.id_ac_clipimage_clipimagelayout)
    private ClipImageLayout mClipImageLayout;
    @ViewInject(R.id.id_ac_clip_btn_confirm)
    private Button mbtnSavePhoto;
    private String path = "";
    private ProgressDialog mProgressDialog;
    private String[] permissionarray = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };
    private Context mContext;

    @Event(value = {R.id.id_ac_clip_btn_confirm}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_clip_btn_confirm:
                mProgressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Bitmap clipBitmap = mClipImageLayout.clip();
                        File file = new File(SKApplication.mSavePath, "crop_photo");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        String savePath = SKApplication.mSavePath
                                + "/crop_photo/" + System.currentTimeMillis() + ".png";
                        ImageTools.saveBitmapToSDCard(clipBitmap, savePath);
                        mProgressDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("path", savePath);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }.start();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle_permission = new Bundle();
        bundle_permission.putStringArray("permission", permissionarray);
        super.onCreate(bundle_permission);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        initView();
        //这步必须要加
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("请稍后...");
        path = getIntent().getStringExtra("path");
        if (path != null)
        if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            /*****************************可以用默认加载图片**************************************/
            return;
        }
        Bitmap bitmap = ImageTools.convertToBitmap(path, 800, 1000);
        if (bitmap == null) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            /*****************************可以用默认加载图片**************************************/
            return;
        }
        mClipImageLayout.setBitmap(bitmap);
    }

    private void initView() {
        mContext = ClipActivity.this;
    }
}
