package com.wpy.faxianbei.sk.activity.dynamic.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.wpy.faxianbei.sk.activity.dynamic.view.DynamicWithComment;
import com.wpy.faxianbei.sk.entity.Comment;
import com.wpy.faxianbei.sk.entity.Dynamic;
import com.wpy.faxianbei.sk.entity.SkUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * JS支持类，用于在js方法中调用Java方法
 */
public class JsSupport {
    private Context mContext;
    private String json;

    private List<DynamicWithComment> listData;

    public JsSupport(Context context) {
        mContext = context;
        listData = new ArrayList<>();
    }

    /**
     * nothing
     *
     * @param json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * 无
     *
     * @return
     */
    @JavascriptInterface
    public String getJson() {
        return json;
    }

    /**
     * js测试方法，显示字符串
     *
     * @param str 显示内容
     */
    @JavascriptInterface
    public void showToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }


    @JavascriptInterface
    public void setUserInfo() {

    }

    /**
     * 拼接服务器端数据为html代码，数据内容：DynamicID，头像，用户名，动态内容，用户分享的图片，点赞数，动态发送时间，其他人的评论
     *
     * @return 要显示的html页面代码
     */
    @JavascriptInterface
    public String getComment() {
        final Semaphore semaphore = new Semaphore(0);
        new Thread() {
            @Override
            public void run() {
                try {
                    AVQuery<Dynamic> query = AVObject.getQuery(Dynamic.class);
                    query.include("user");
                    query.orderByDescending("createdAt");
                    query.limit(5);
                    List<Dynamic> dynamics = query.find();
                    for (Dynamic dynamic : dynamics) {
                        DynamicWithComment dynamicWithComment = new DynamicWithComment();
                        dynamicWithComment.setDynamic(dynamic);
                        dynamicWithComment.setComment("");
                        AVRelation<Comment> commentRelation = dynamic.getRelation("comment");
                        AVQuery<Comment> queryComment = commentRelation.getQuery();
                        queryComment.include("user");
                        List<Comment> comments = queryComment.find();
                        if (comments != null && !comments.isEmpty()) {
                            StringBuilder builder = new StringBuilder();
                            for (Comment comment : comments) {
                                SkUser user = comment.getUser();
                                builder.append(user.getRealName() + ":" + comment.getContent() + "\n");
                                //添加comment
                                dynamicWithComment.addComments(comment);
                            }
                            dynamicWithComment.setComment(builder.toString());
                        }
                        listData.add(dynamicWithComment);
                    }
                    semaphore.release();
                } catch (AVException e) {
                }
            }
        }.start();
        /**********************************处理空的************************************/
        String dynamicHTML="";
        try {
            semaphore.acquire();
            Dynamic dynamic=listData.get(0).getDynamic();
            SkUser user=dynamic.getUser();
            List<Comment> comments=listData.get(0).getListComment();
            int dynamicID = 0;
            String imgUserHead =user.getHeadImg().getUrl();
            String userID = user.getRealName();
            String dynamicComment = dynamic.getContent();
            String imgUserDynamic = dynamic.getShareImg().getUrl();
            int likeNums = dynamic.getLike();
            String sendTime = dynamic.getCreatedAt().getHours()+":"+ dynamic.getCreatedAt().getMinutes();
            StringBuffer buffer=new StringBuffer();
            for(Comment comment:comments){
                buffer.append("<p><span>");
                buffer.append(user.getRealName());
                buffer.append("</span>：");
                buffer.append(comment.getContent());
                buffer.append("</p>");
            }
            String othersComments =buffer.toString();
            // 拼接工作
            dynamicHTML = "<div class='row item item" + dynamicID + "'><div class='col-xs-2'><div class='logo01_box'>" +
                    "<img src='" + imgUserHead + "'/></div></div><div class='col-xs-10 xs8'><div class='towp'><p class='p1'>" +
                    userID + "</p><p class='p2'>" + dynamicComment + "</p></div><div class='img_box'><img src='" + imgUserDynamic +
                    "'/></div><div class='texts_box'><div class='pl'><img src='state-thumbs-up.svg' class='heart'/> " +
                    "<span class=\"num\">" + likeNums + "</span><img src='state-comment.svg' class='argument'/> <span>评论" +
                    "</span><img src=\"state-delete.svg\" class=\"dele\"/> <span>屏蔽</span></div><p class='time'>" +
                    sendTime + "</p><div class='pls'>" + othersComments + "</div></div></div></div>";
        } catch (InterruptedException e) {
        }
        return dynamicHTML;
    }

}
