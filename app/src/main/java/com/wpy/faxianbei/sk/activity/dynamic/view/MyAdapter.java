package com.wpy.faxianbei.sk.activity.dynamic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.wpy.faxianbei.sk.R;
import com.wpy.faxianbei.sk.entity.Comment;
import com.wpy.faxianbei.sk.entity.Dynamic;
import com.wpy.faxianbei.sk.entity.SkUser;

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public List<DynamicWithComment> datas = null;
    private View.OnClickListener listener;


    public MyAdapter(Context context, List<DynamicWithComment> datas, View.OnClickListener clickListener) {
        this.datas = datas;
        listener = clickListener;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dynamic_item, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        DynamicWithComment dynamicWithComment = datas.get(position);
        Dynamic dynamic = dynamicWithComment.getDynamic();
        SkUser user = dynamic.getUser();
        //加载头像
        AVFile fileUserImg = user.getHeadImg();
        String url = fileUserImg.getUrl();
        viewHolder.mivUserImg.setTag(url);
        fileUserImg.getDataInBackground(new Callback(viewHolder.mivUserImg, url));
        //加载名字
        viewHolder.mtvUserName.setText(user.getRealName());
        //加载内容
        viewHolder.mtvContent.setText(dynamic.getContent());
        //加载分享的图片
        AVFile fileShareImg = dynamic.getShareImg();
        String urlshare = fileShareImg.getUrl();
        viewHolder.mivDynamic.setTag(urlshare);
        fileShareImg.getDataInBackground(new Callback(viewHolder.mivDynamic, urlshare));
        //处理点击和加载的事件
        viewHolder.mivLike.setTag(position);
        viewHolder.mivComment.setTag(position);
        viewHolder.mivLike.setOnClickListener(listener);
        viewHolder.mivComment.setOnClickListener(listener);
        //加载评论
        String comment = dynamicWithComment.getComment();
        if (comment == null || TextUtils.isEmpty(comment)) {
            viewHolder.divider.setVisibility(View.GONE);
            viewHolder.mtvComment.setText("");
        } else {
            viewHolder.divider.setVisibility(View.VISIBLE);
            viewHolder.mtvComment.setText(dynamicWithComment.getComment());
        }
        //加载点赞数量
        viewHolder.mtvLikeCount.setText("");
        if(0!=dynamicWithComment.getLike()){
            viewHolder.mtvLikeCount.setText(dynamicWithComment.getLike()+"");
        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mivUserImg;
        public TextView mtvUserName;
        public TextView mtvContent;
        public ImageView mivDynamic;
        public ImageView mivLike;
        public ImageView mivComment;
        public TextView mtvComment;
        public View divider;
        public TextView mtvLikeCount;

        public ViewHolder(View view) {
            super(view);
            mivUserImg = (ImageView) view.findViewById(R.id.id_dynamic_item_iv_user_img);
            mtvUserName = (TextView) view.findViewById(R.id.id_dynamic_item_iv_user_name);
            mtvContent = (TextView) view.findViewById(R.id.id_dynamic_item_tv_dynamic_content);
            mivDynamic = (ImageView) view.findViewById(R.id.id_dynamic_item_tv_dynamic_img);
            mivLike = (ImageView) view.findViewById(R.id.id_dynamic_item_iv_dynamic_like);
            mivComment = (ImageView) view.findViewById(R.id.id_dynamic_item_tv_dynamic_comment);
            mtvComment = (TextView) view.findViewById(R.id.id_ac_dynamic_tv_comment);
            divider = view.findViewById(R.id.id_dynamic_item_comment_divider);
            mtvLikeCount= (TextView) view.findViewById(R.id.id_dynamic_item_tv_dynamic_like_count);
        }
    }

    public class Callback extends GetDataCallback {

        ImageView imageView;
        String url;

        Callback(ImageView iv, String url) {
            imageView = iv;
            this.url = url;
        }

        @Override
        public void done(byte[] bytes, AVException e) {
            if (imageView.getTag().equals(url)) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private class CommentCallBack implements Dynamic.ICommentCallBack {

        private TextView mtvComment;

        private int pos;

        CommentCallBack(TextView textView) {
            mtvComment = textView;
        }

        @Override
        public void commentCallBack(List<Comment> list) {
            StringBuilder builder = new StringBuilder();
            for (Comment comment : list) {
                SkUser user = comment.getUser();
                builder.append(user.getRealName() + ":" + comment.getContent() + "\n");
            }
            if (pos == (int) mtvComment.getTag())
                mtvComment.setText(builder.toString());
        }
    }
}
