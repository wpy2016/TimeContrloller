package com.wpy.faxianbei.sk.utils.appinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.utils.appinfo.bean.AppInfo;

import java.util.HashMap;
import java.util.List;

public class AppInfoAdapter extends BaseAdapter {
    private List<AppInfo> appList;
    private LayoutInflater layoutInflater;
    private Context context;
    // 用来控制CheckBox的选中状况
    private  HashMap<Integer, Boolean> isSelected;
    public AppInfoAdapter(Context context, List<AppInfo> appList) {
        this.context = context;
        this.appList = appList;
        this.layoutInflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        // 初始化数据
        initDate();
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < appList.size(); i++) {
            getIsSelected().put(i, false);
        }
    }
    /**
     * 组件集合，对应list.xml中的控件
     */
    public final class AppInfoHolder {
        public TextView title;
        public Switch switch_item;
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AppInfoHolder holer = null;
        if (convertView == null) {
            holer = new AppInfoHolder();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.list, null);
            holer.title = (TextView) convertView.findViewById(R.id.tv_title);
            holer.switch_item = (Switch) convertView.findViewById(R.id.switch_item);
            convertView.setTag(holer);
        } else {
            holer = (AppInfoHolder) convertView.getTag();
        }
        if (appList.get(position).appName != null) {
            holer.title.setText((String) appList.get(position).appName);
        }
        // 监听checkBox并根据原来的状态来设置新的状态
        holer.switch_item.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (isSelected.get(position)) {
                    isSelected.put(position, false);
                } else {
                    isSelected.put(position, true);
                }
            }
        });
        // 根据isSelected来设置checkbox的选中状况
        holer.switch_item.setChecked(getIsSelected().get(position));
        return convertView;
    }
    public  HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
}
