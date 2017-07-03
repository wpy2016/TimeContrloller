package com.wpy.faxianbei.sk.activity.dynamic.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.CheckPermissionsActivity;
import com.wpy.faxianbei.sk.activity.dynamic.model.FillCommentModel;
import com.wpy.faxianbei.sk.entity.Comment;
import com.wpy.faxianbei.sk.entity.Dynamic;
import com.wpy.faxianbei.sk.entity.SkUser;
import com.wpy.faxianbei.sk.ui.goodview.GoodView;
import com.wpy.faxianbei.sk.ui.xrecyclerview.ProgressStyle;
import com.wpy.faxianbei.sk.ui.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AcDynamic extends CheckPermissionsActivity implements XRecyclerView.LoadingListener,
        View.OnClickListener, FillCommentModel.FillCommentListener {

    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<DynamicWithComment> listData;

    private View Head;

    private ImageView mUserImg;

    FillCommentModel model;

    private int clickPos = 0;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    mAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    break;
                case 0x110:
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete();
                    break;
                case 0x111:
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.loadMoreComplete();
                    break;
                case 0x112:
                    mRecyclerView.setNoMore(true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dynamic);
        initView();
        initEvent();
        try {
            initData();
        } catch (AVException e) {

        }
    }

    private void initData() throws AVException {
        model = new FillCommentModel(this);
        listData = new ArrayList<>();
        AVQuery<Dynamic> query = AVObject.getQuery(Dynamic.class);
        query.include("user");
        query.limit(5);
        query.orderByDescending("createdAt");
        progressDialog.show();
        loadData();
        mAdapter = new MyAdapter(this, listData, this);
        mRecyclerView.setAdapter(mAdapter);
        AVFile file = AVUser.getCurrentUser(SkUser.class).getHeadImg();
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, AVException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                mUserImg.setImageBitmap(bitmap);
            }
        });
    }

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    AVQuery<Dynamic> query = AVObject.getQuery(Dynamic.class);
                    query.include("user");
                    query.orderByDescending("createdAt");
                    query.limit(5);
                    List<Dynamic> dynamics = query.find();
                    for (Dynamic dynamic : dynamics) {
                        DynamicWithComment dynamicWithComment = new DynamicWithComment();
                        dynamicWithComment.setDynamic(dynamic);
                        dynamicWithComment.setComment("");
                        AVRelation<Comment> commentRelation = dynamic.getRelation("comment");
                        AVQuery<Comment> queryComment = commentRelation.getQuery();
                        queryComment.include("user");
                        List<Comment> comments = queryComment.find();
                        if (comments != null && !comments.isEmpty()) {
                            StringBuilder builder = new StringBuilder();
                            for (Comment comment : comments) {
                                SkUser user = comment.getUser();
                                builder.append(user.getRealName() + ":" + comment.getContent() + "\n");
                            }
                            dynamicWithComment.setComment(builder.toString());
                        }
                        listData.add(dynamicWithComment);
                    }
                    mHandler.sendEmptyMessage(0x11);
                } catch (AVException e) {
                }
            }
        }.start();
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
        new Thread() {
            @Override
            public void run() {
                try {
                    listData.clear();
                    AVQuery<Dynamic> query = AVObject.getQuery(Dynamic.class);
                    query.include("user");
                    query.orderByDescending("createdAt");
                    query.limit(5);
                    List<Dynamic> dynamics = query.find();
                    for (Dynamic dynamic : dynamics) {
                        DynamicWithComment dynamicWithComment = new DynamicWithComment();
                        dynamicWithComment.setDynamic(dynamic);
                        dynamicWithComment.setComment("");
                        AVRelation<Comment> commentRelation = dynamic.getRelation("comment");
                        AVQuery<Comment> queryComment = commentRelation.getQuery();
                        queryComment.include("user");
                        List<Comment> comments = queryComment.find();
                        if (comments != null && !comments.isEmpty()) {
                            StringBuilder builder = new StringBuilder();
                            for (Comment comment : comments) {
                                SkUser user = comment.getUser();
                                builder.append(user.getRealName() + ":" + comment.getContent() + "\n");
                            }
                            dynamicWithComment.setComment(builder.toString());
                        }
                        listData.add(dynamicWithComment);
                    }
                    mHandler.sendEmptyMessage(0x110);
                } catch (AVException e) {
                }
            }
        }.start();
    }

    @Override
    public void onLoadMore() {

        new Thread() {
            @Override
            public void run() {
                try {
                    AVQuery<Dynamic> query = AVObject.getQuery(Dynamic.class);
                    query.include("user");
                    query.orderByDescending("createdAt");
                    query.skip(listData.size());
                    query.limit(5);
                    List<Dynamic> dynamics = query.find();
                    if (dynamics != null && !dynamics.isEmpty()) {
                        for (Dynamic dynamic : dynamics) {
                            DynamicWithComment dynamicWithComment = new DynamicWithComment();
                            dynamicWithComment.setDynamic(dynamic);
                            dynamicWithComment.setComment("");
                            AVRelation<Comment> commentRelation = dynamic.getRelation("comment");
                            AVQuery<Comment> queryComment = commentRelation.getQuery();
                            queryComment.include("user");
                            List<Comment> comments = queryComment.find();
                            if (comments != null && !comments.isEmpty()) {
                                StringBuilder builder = new StringBuilder();
                                for (Comment comment : comments) {
                                    SkUser user = comment.getUser();
                                    builder.append(user.getRealName() + ":" + comment.getContent() + "\n");
                                }
                                dynamicWithComment.setComment(builder.toString());
                            }
                            listData.add(dynamicWithComment);
                        }
                    } else {
                        mHandler.sendEmptyMessage(0x112);
                    }
                    mHandler.sendEmptyMessage(0x111);
                } catch (AVException e) {
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_dynamic_item_iv_dynamic_like:
                //处理点赞
                GoodView goodView=new GoodView(this);
                goodView.setTextInfo("+1", Color.parseColor("#f24453"), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,10,getResources().getDisplayMetrics()));
                goodView.show(v);
                DynamicWithComment dynamicWithCommentLike=listData.get((Integer) v.getTag());
                dynamicWithCommentLike.like++;
                mAdapter.notifyDataSetChanged();
                Dynamic dynamic = dynamicWithCommentLike.getDynamic();
                dynamic.increment("like");
                dynamic.saveInBackground();
                break;
            case R.id.id_dynamic_item_tv_dynamic_comment:
                clickPos = (int) v.getTag();
                model.showPopWindow(AcDynamic.this, v);
                break;
        }
    }

    @Override
    public void fillComplete(String content) {
        DynamicWithComment dynamicWithComment = listData.get(clickPos);
        Dynamic dynamic = dynamicWithComment.getDynamic();
        dynamicWithComment.setComment(dynamicWithComment.getComment()+dynamic.getUser().getRealName()+":"+content+"\n");
        Comment comment = new Comment();
        comment.setUser(SkUser.getCurrentUser(SkUser.class));
        comment.setContent(content);
        dynamic.increment("count");
        comment.setDynamic(dynamic);
        dynamic.setComment(comment);
        //更新listview
        mAdapter.notifyDataSetChanged();
    }
}
