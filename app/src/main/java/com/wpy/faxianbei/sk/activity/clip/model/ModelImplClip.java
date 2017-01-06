package com.wpy.faxianbei.sk.activity.clip.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.activity.base.ClipActivity;
import com.wpy.faxianbei.sk.application.SKApplication;
import com.wpy.faxianbei.sk.utils.general.FileUtil;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * 用于设置头像的
 * 完全封装了弹出选择是照相还是从相册选择，
 * 选择之后，跳转到裁剪界面，裁剪完成将裁剪后的bitmap和path传递给activity
 * Created by peiyuwang on 17-1-6.
 */

public class ModelImplClip implements IModelClip{

    IClipComplete clipComplete;

    public ModelImplClip(IClipComplete clipComplete) {
        this.clipComplete = clipComplete;
    }

    //图片保存的文件夹
    private static String PHOTOSAVEPATH = SKApplication.mSavePath + "/crop_photo/";

    //以当前时间的毫秒数当做文件名，设置好的图片的路径
    private String photoname = System.currentTimeMillis() + ".png";
    private String mPath;  //要找的图片路径

    private final static int PHOTOBYGALLERY = 0;//从相册获取照片

    private final static int PHOTOTACK = 1;//拍照获取

    private final static int PHOTOCOMPLETEBYTAKE = 2;//完成
    private final static int PHOTOCOMPLETEBYGALLERY = 3;//完成

    private static int PHOTOCROP = 3;//图片裁剪

    private PopupWindow mPopupWindow;

    @Override
    public void showPopWindow(Context context, View location){
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.pop_select_photo, null);
            mPopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT, true);
            initPopupWindow(context, mPopupWindow,view);
        }
        //设置位置
        mPopupWindow.showAtLocation(location, Gravity.CENTER, 0, 0);
    }
    private void initPopupWindow(final Context context,final PopupWindow popupWindow, View v) {
        //获取控件
        TextView mTxtGallery = (TextView) v.findViewById(R.id.id_pop_select_photo_tv_from_gallery);
        TextView mTxtTack = (TextView) v.findViewById(R.id.id_pop_select_photo_tv_take_photo);
        TextView mCancel = (TextView) v.findViewById(R.id.id_pop_select_photo_tv_cancel);
        mTxtGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                startToGetPhotoByGallery(context);
            }
        });
        mTxtTack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                startToGetPhotoByTack(context);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing())
                    popupWindow.dismiss();
            }
        });
        //设置动画
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        //设置可以点击外面
        popupWindow.setOutsideTouchable(true);
        //设置popupwindow为透明的，这样背景就是主界面的内容
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }


    private void startToGetPhotoByGallery(Context context) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity)context).startActivityForResult(openGalleryIntent, PHOTOBYGALLERY);
    }

    private void startToGetPhotoByTack(Context context) {
        photoname = String.valueOf(System.currentTimeMillis()) + ".png";
        Uri imageUri = null;
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = Uri.fromFile(new File(PHOTOSAVEPATH, photoname));
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        ((Activity)context).startActivityForResult(openCameraIntent, PHOTOTACK);
    }

    @Override
    public void onActivityResult(Context mContext,int requestCode,int resultCode,Intent data){
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri uri = null;
        switch (requestCode) {
            case PHOTOBYGALLERY:
                uri = data.getData();
                if (uri != null) {
                    if (Build.VERSION.SDK_INT > 18) {
                        if (DocumentsContract.isDocumentUri(mContext, uri)) {
                            String wholeID = DocumentsContract.getDocumentId(uri);
                            String id = wholeID.split(":")[1];
                            String[] column = {MediaStore.Images.Media.DATA};
                            String sel = MediaStore.Images.Media._ID + "=?";
                            Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                                    sel, new String[]{id}, null);
                            int columnIndex = cursor.getColumnIndex(column[0]);
                            if (cursor.moveToFirst()) {
                                mPath = cursor.getString(columnIndex);
                            }
                            cursor.close();
                        } else {
                            String[] projection = {MediaStore.Images.Media.DATA};
                            Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            mPath = cursor.getString(column_index);
                        }
                    } else {
                        String[] projection = {MediaStore.Images.Media.DATA};
                        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        mPath = cursor.getString(column_index);
                    }
                }
                /**
                 * 获取到照片之后调用裁剪acticity
                 */
                Intent intentGalley = new Intent(mContext, ClipActivity.class);
                intentGalley.putExtra("path", mPath);
                ((Activity)mContext).startActivityForResult(intentGalley, PHOTOCOMPLETEBYGALLERY);
                break;
            case PHOTOTACK:
                mPath = PHOTOSAVEPATH + photoname;
                /**
                 * 拿到uri后进行裁剪处理
                 */
                Intent intentTake = new Intent(mContext, ClipActivity.class);
                intentTake.putExtra("path", mPath);
                ((Activity)mContext).startActivityForResult(intentTake, PHOTOCOMPLETEBYTAKE);
                break;
            case PHOTOCOMPLETEBYTAKE:
                final String temppath = data.getStringExtra("path");
                /**
                 * 删除旧文件
                 */
                File file = new File(mPath);
                file.delete();
                mPath = temppath;
                //通过接口传递出去
                clipComplete.clipComplete(FileUtil.getBitmapFormPath(mContext, temppath),temppath);
                break;
            case PHOTOCOMPLETEBYGALLERY:
                final String temppathgallery = data.getStringExtra("path");
                mPath = temppathgallery;
                //通过接口传递出去
                clipComplete.clipComplete(FileUtil.getBitmapFormPath(mContext, temppathgallery),temppathgallery);
                break;
        }
    }
}
