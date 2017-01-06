package com.wpy.faxianbei.sk.activity.clip.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by peiyuwang on 17-1-6.
 */

public interface IModelClip {
    public void showPopWindow(Context context, View location);
    public void onActivityResult(Context mContext,int requestCode,int resultCode,Intent data);
}
