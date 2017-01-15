package com.wpy.faxianbei.sk.activity.blackandwhite.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.base.MvpBaseActivity;
import com.wpy.faxianbei.sk.activity.blackandwhite.presenter.PresenterBlackAndWhite;
import com.wpy.faxianbei.sk.adapter.CommonAdapter;
import com.wpy.faxianbei.sk.adapter.ViewHolder;
import com.wpy.faxianbei.sk.utils.appinfo.bean.AppInfo;
import com.wpy.faxianbei.sk.utils.appinfo.adapter.AppInfoAdapter;
import com.wpy.faxianbei.sk.utils.appinfo.util.ApplicationInfoUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.ac_black_white_list)
public class AcBlackAndWhite extends MvpBaseActivity<IViewBlackAndWhite,PresenterBlackAndWhite> implements IViewBlackAndWhite{

    private AlertDialog selfdialog;
    @ViewInject(R.id.grid_black)
    private GridView grid_black;
    @ViewInject(R.id.grid_white)
    private GridView grid_white;
    @ViewInject(R.id.tv_getNonSysb)
    private TextView tv_getNonSysb;
    @ViewInject(R.id.tv_getNonSysw)
    private TextView tv_getNonSysw;
    private List<Map<String, Object>> itemsb=null;
    private List<Map<String, Object>> itemsw=null;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext=AcBlackAndWhite.this;
        tv_getNonSysb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPresenter.showDialog(mContext, PresenterBlackAndWhite.BlackOrWhite.BLACK);
            }
        });

        tv_getNonSysw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPresenter.showDialog(mContext, PresenterBlackAndWhite.BlackOrWhite.WHITE);
            }
        });
    }

    @Override
    public PresenterBlackAndWhite createPresenter() {
        return new PresenterBlackAndWhite();
    }

    @Override
    public void showBlack(List<AppInfo> list) {
        grid_black.setAdapter(new CommonAdapter<AppInfo>(mContext,list,R.layout.layout_my_griditems) {
            @Override
            public void convert(ViewHolder helper, AppInfo item) {
                helper.setImageDrawer(R.id.img_icon,item.appIcon);
            }
        });
    }

    @Override
    public void showWhite(List<AppInfo> list) {
        grid_white.setAdapter(new CommonAdapter<AppInfo>(mContext,list,R.layout.layout_my_griditems) {
            @Override
            public void convert(ViewHolder helper, AppInfo item) {
                helper.setImageDrawer(R.id.img_icon,item.appIcon);
            }
        });
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
}
