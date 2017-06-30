package com.wpy.faxianbei.sk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 万能的适配器
 */
public abstract class CommonAdapterArray<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected T[] mDatas;
    protected final int mItemLayoutId;
    private int selectPos=200;

    public CommonAdapterArray(Context context, T[] mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public T getItem(int position) {
        return mDatas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position),position,selectPos);
        return viewHolder.getConvertView();

    }

    public abstract void convert(ViewHolder helper, T item,int pos,int selectPos);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    public void setSelectPos(int pos){
        selectPos=pos;
    }
}