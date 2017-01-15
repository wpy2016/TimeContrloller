package com.wpy.faxianbei.sk.activity.share.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.utils.general.FileUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.File;
@ContentView(R.layout.ac_share_website)
public class AcShare extends Activity {
    @ViewInject(R.id.id_ac_share_iv_screenshot)
    ImageView mivScreenshot;
    @ViewInject(R.id.id_ac_share_iv_weibo)
    ImageView mivWeibo;
    @ViewInject(R.id.id_ac_share_iv_qq)
    ImageView mivQq;
    @ViewInject(R.id.id_ac_share_iv_weixin)
    ImageView mivWeixin;
    @ViewInject(R.id.id_ac_share_iv_download)
    ImageView mivDownload;
    Context mContext;
    String mStrPath=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext=AcShare.this;
        initView();
    }

    private void initView() {
        mStrPath = getIntent().getStringExtra("imgpath");
        if(mStrPath!=null&&(!mStrPath.equals(""))&&(new File(mStrPath).exists()))
        {
            Bitmap bitmap= FileUtil.getBitmapFormPath(mContext,mStrPath);
            mivScreenshot.setImageBitmap(bitmap);
        }
    }

    @Event(value={R.id.id_ac_share_iv_screenshot, R.id.id_ac_share_iv_weibo, R.id.id_ac_share_iv_qq, R.id.id_ac_share_iv_weixin, R.id.id_ac_share_iv_download})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ac_share_iv_screenshot:
                break;
            case R.id.id_ac_share_iv_weibo:
                break;
            case R.id.id_ac_share_iv_qq:
                break;
            case R.id.id_ac_share_iv_weixin:
                break;
            case R.id.id_ac_share_iv_download:
                break;
        }
    }
}
