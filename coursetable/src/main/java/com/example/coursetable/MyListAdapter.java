package com.example.coursetable;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangpeiyu on 2017/6/26.
 */

public class MyListAdapter extends BaseAdapter {

    Context context;

    LayoutInflater layoutInflater;

    List<String> list;
    public MyListAdapter(Context context,List<String> list){
        this.context=context;
        this.list=list;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view=layoutInflater.inflate(R.layout.coursetable_item_layout,null);
            holder=new ViewHolder();
            holder.textView=view.findViewById(R.id.id_ac_coursetable_gv_item);
            view.setTag(holder);
        }
        holder= (ViewHolder) view.getTag();
        holder.textView.setText(getItem(i));
        return view;
    }


    private class  ViewHolder{
        TextView textView;
    }
}
