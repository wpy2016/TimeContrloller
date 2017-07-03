package com.wpy.faxianbei.sk.entity;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by wangpeiyu on 2017/7/3.
 */
@AVClassName("dynamic")
public class Dynamic extends AVObject {

    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    ICommentCallBack commentCallBack;


    public Dynamic() {
        super();
    }



    public Dynamic(Parcel in) {
        super(in);
    }
    public void setUser(AVObject user) {
        put("user", user);
    }

    public void setContent(String content) {
        put("content", content);
    }

    public void setShareImg(String path) throws FileNotFoundException {
        put("shareimg", AVFile.withAbsoluteLocalPath(System.currentTimeMillis() + ".png", path));
    }

    public void setComment(final Comment comment) {
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    AVRelation<Comment> relation = Dynamic.this.getRelation("comment");
                    relation.add(comment);
                    Dynamic.this.saveInBackground();
                }
            }
        });
    }

    public void getAllComments() throws AVException {
        AVRelation<Comment> commentRelation = this.getRelation("comment");
        AVQuery<Comment> query = commentRelation.getQuery();
        query.include("user");
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> list, AVException e) {
                if(e==null&&commentCallBack!=null)
                {
                    commentCallBack.commentCallBack(list);
                }
            }
        });
    }

    public void setCommentCount(int count){
        put("count",count);
    }

    public int getCommentCount(){
      return (int) get("count");
    }

    public AVFile getShareImg() {
        return (AVFile) get("shareimg");
    }

    public String getContent() {
        return (String) get("content");
    }

    public void setLike(int num){
        put("like",num);
    }

    public int getLike(){
        return (int) get("like");
    }


    public SkUser getUser() {
        return (SkUser) get("user");
    }

    public interface ICommentCallBack {
        void commentCallBack(List<Comment> list);
    }

    public void setCommentCallBack(ICommentCallBack commentCallBack) {
        this.commentCallBack = commentCallBack;
    }

    public ICommentCallBack getCommentCallBack() {
        return commentCallBack;
    }
}
