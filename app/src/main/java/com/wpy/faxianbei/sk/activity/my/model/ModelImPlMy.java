package com.wpy.faxianbei.sk.activity.my.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.wpy.faxianbei.sk.entity.SkUser;

import java.util.List;

/**
 * Created by peiyuwang on 17-1-5.
 */

public class ModelImPlMy implements IModelMy {

    private SkUser skUser;

    private LoadHeadimg mLoadHeadimg;

    public ModelImPlMy(LoadHeadimg loadHeadimg) {
        skUser = AVUser.getCurrentUser(SkUser.class);
        this.mLoadHeadimg = loadHeadimg;
    }

    @Override
    public String getName() {
        return skUser.getRealName();
    }

    @Override
    public String getSchool() {
        return skUser.getSchool();
    }

    @Override
    public String getAcademic() {
        return skUser.getAcademy();
    }

    @Override
    public String getGrade() {
        return skUser.getGender();
    }

    @Override
    public void getHeadimg() {
        AVFile file= skUser.getHeadImg();
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, AVException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                mLoadHeadimg.loadSuccess(bitmap);
            }
        });
    }

    @Override
    public List<String> getList() {
        return null;
    }

    @Override
    public void logout() {
        skUser.logOut();
    }

    public interface LoadHeadimg{
        public void loadSuccess(Bitmap bitmap);
    }
}
