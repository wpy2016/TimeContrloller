package com.wpy.faxianbei.sk.activity.blackandwhite.model;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.text.IDNA;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.blackandwhite.presenter.PresenterBlackAndWhite;
import com.wpy.faxianbei.sk.utils.appinfo.adapter.AppInfoAdapter;
import com.wpy.faxianbei.sk.utils.appinfo.bean.AppInfo;
import com.wpy.faxianbei.sk.utils.appinfo.util.ApplicationInfoUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * Created by peiyuwang on 17-1-15.
 */

public class ModelImplBlackAndWhite implements IModelBlackAndWhite {
    IAppInfo mAppInfo;

    private AlertDialog selfdialog;

    public ModelImplBlackAndWhite(IAppInfo mAppInfo) {
        this.mAppInfo = mAppInfo;
    }
    @Override
    public void showDialog(final Context context, final PresenterBlackAndWhite.BlackOrWhite type) {
        //将 layout布局  转为 view对象
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_my_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        selfdialog = builder.create();
        selfdialog.show();
        final List<AppInfo> nonsystemAppList = ApplicationInfoUtil.getAllNonsystemProgramInfo(context);
        final AppInfoAdapter adapter = new AppInfoAdapter(context, nonsystemAppList);
        ListView lv = (ListView) view.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        //取得 dialog中的值
        ImageView iv_bwok = (ImageView) view.findViewById(R.id.iv_bwok);
        iv_bwok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<Integer, Boolean> mapSelected = adapter.getIsSelected();
                List<AppInfo> selectedList = new ArrayList<AppInfo>();
                Iterator iter = mapSelected.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    if ((boolean) entry.getValue() == true) {
                        selectedList.add(nonsystemAppList.get((int) entry.getKey()));
                    }
                }
                selfdialog.dismiss();
                switch (type)
                {
                    case BLACK:
                        mAppInfo.loadSuccessBlack(selectedList);
                        break;
                    case WHITE:
                        mAppInfo.loadSuccessWhite(selectedList);
                        break;
                    default:
                }
            }
        });
    }
    public interface IAppInfo{
        public void loadSuccessBlack(List<AppInfo> list);
        public void loadSuccessWhite(List<AppInfo> list);
        public void loadFail(String message);
    }
}
