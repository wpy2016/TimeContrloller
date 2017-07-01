package com.wpy.faxianbei.sk.activity.dynamic.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.wpy.faxianbei.sk.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public ArrayList<String> datas = null;
    public MyAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dynamic_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mivUserImg;
        public TextView mtvUserName;
        public TextView mtvContent;
        public ImageView mivDynamic;
        public ImageView mivLike;
        public ImageView mivComment;
        public ViewHolder(View view){
            super(view);
            mivUserImg= (ImageView) view.findViewById(R.id.id_dynamic_item_iv_user_img);
            mtvUserName= (TextView) view.findViewById(R.id.id_dynamic_item_iv_user_name);
            mtvContent= (TextView) view.findViewById(R.id.id_dynamic_item_tv_dynamic_content);
            mivDynamic= (ImageView) view.findViewById(R.id.id_dynamic_item_tv_dynamic_img);
            mivLike= (ImageView) view.findViewById(R.id.id_dynamic_item_tv_dynamic_like);
            mivComment= (ImageView) view.findViewById(R.id.id_dynamic_item_tv_dynamic_comment);
        }
    }
}
