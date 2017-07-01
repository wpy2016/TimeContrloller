package com.wpy.faxianbei.sk.activity.dynamic.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.ui.xrecyclerview.ProgressStyle;
import com.wpy.faxianbei.sk.ui.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class AcDynamic extends Activity implements XRecyclerView.LoadingListener {

    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;

    private View Head;

    private ImageView mUserImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dynamic);
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        listData = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            listData.add("item" + i);
        }
        mAdapter = new MyAdapter(listData);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.refresh();
        AVFile file= AVUser.getCurrentUser(SkUser.class).getHeadImg();
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, AVException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                mUserImg.setImageBitmap(bitmap);
            }
        });
    }


    private void initEvent() {
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setPullRefreshEnabled(true);
    }

    private void initView() {
        mRecyclerView = (XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        Head = LayoutInflater.from(this).inflate(R.layout.view_header, (ViewGroup) findViewById(android.R.id.content), false);
        mUserImg = (ImageView) Head.findViewById(R.id.iv_user_head);
        mRecyclerView.addHeaderView(Head);
    }

    @Override
    public void onRefresh() {
        refreshTime++;
        times = 0;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                listData.clear();
                for (int i = 0; i < 15; i++) {
                    listData.add("item" + i + "after " + refreshTime + " times of refresh");
                }
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }

        }, 1000);            //refresh data here
    }

    @Override
    public void onLoadMore() {
        if (times < 2) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    for (int i = 0; i < 15; i++) {
                        listData.add("item" + (1 + listData.size()));
                    }
                    mRecyclerView.loadMoreComplete();
                    mAdapter.notifyDataSetChanged();
                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    for (int i = 0; i < 9; i++) {
                        listData.add("item" + (1 + listData.size()));
                    }
                    mRecyclerView.setNoMore(true);
                    mAdapter.notifyDataSetChanged();
                }
            }, 1000);
        }
        times++;
    }
}
