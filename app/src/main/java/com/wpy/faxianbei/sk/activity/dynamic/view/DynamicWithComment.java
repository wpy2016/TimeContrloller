package com.wpy.faxianbei.sk.activity.dynamic.view;

import com.wpy.faxianbei.sk.entity.Comment;
import com.wpy.faxianbei.sk.entity.Dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangpeiyu on 2017/7/3.
 */

public class DynamicWithComment {
    private Dynamic dynamic;
    private String comment;
    int like=0;

    public void setLike(int like) {
        this.like = like;
    }

    public int getLike() {

        return like;
    }

    private List<Comment> listComment;



    public List<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
    }

    public DynamicWithComment() {
        listComment=new ArrayList<>();
    }

    DynamicWithComment(Dynamic dynamic, String comment) {
        this.dynamic = dynamic;
        comment = comment;
    }

    public void addComments(Comment comment){
        listComment.add(comment);
    }

    public void getComment(int pos){
        listComment.get(pos);
    }
    public Dynamic getDynamic() {
        return dynamic;
    }

    public String getComment() {
        return comment;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
        like=dynamic.getLike();
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
