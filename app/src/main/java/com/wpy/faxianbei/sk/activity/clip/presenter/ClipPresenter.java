package com.wpy.faxianbei.sk.activity.clip.presenter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.wpy.faxianbei.sk.activity.base.BasePresenter;
import com.wpy.faxianbei.sk.activity.clip.model.IClipComplete;
import com.wpy.faxianbei.sk.activity.clip.model.IModelClip;
import com.wpy.faxianbei.sk.activity.clip.model.ModelImplClip;
import com.wpy.faxianbei.sk.activity.clip.view.IviewClip;


/**
 * Created by peiyuwang on 17-1-6.
 */
public class ClipPresenter extends BasePresenter<IviewClip> implements IClipComplete{
    private IModelClip clipModel;

    public ClipPresenter() {
        clipModel = new ModelImplClip(this);
    }

    public void showPopWindow(Context context,View view){
        clipModel.showPopWindow(context,view);
    }

    public void onActivityResult(Context mContext,int requestCode,int resultCode,Intent data){
        clipModel.onActivityResult(mContext,requestCode,resultCode,data);
    }


    @Override
    public void clipComplete(Bitmap bitmap, String path) {
        if(getViewInterface()!=null)
        {
            getViewInterface().clipComplete(bitmap,path);
        }
    }
}
