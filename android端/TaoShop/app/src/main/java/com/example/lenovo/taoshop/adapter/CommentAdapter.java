package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.TbCommentsReply;
import com.example.lenovo.taoshop.bean.common.TbCommentsResult;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.widget.CommentListView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2017  五月  09  0009.
 */

public class CommentAdapter extends BaseQuickAdapter<TbCommentsResult> {
    private Context context;
    private OnTextClickListener listener;

    public CommentAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_comment_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final TbCommentsResult item) {
        //用户头像
        ImageView imageView = holder.getView(R.id.user_img);
        ImageLoader.getInstance().displayImage(context, item.getPic(), imageView, R.mipmap.user);
        //用户名
        holder.setText(R.id.tv_nick, item.getBuyername());
        TextView user = holder.getView(R.id.tv_nick);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    TbCommentsReply tbCommentsReply = new TbCommentsReply();
                    tbCommentsReply.setName(item.getBuyername());
                    tbCommentsReply.setReplyid(item.getSoldid());
                    tbCommentsReply.setItemId(item.getItemid());
                    tbCommentsReply.setMuserid(item.getMuserId());
                    tbCommentsReply.setParentid(item.getId());
                    listener.onClick(tbCommentsReply, 0);
                }
            }
        });
        //回复时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        holder.setText(R.id.tv_time, simpleDateFormat.format(new Date(item.getTime())));
        //评论内容
        holder.setText(R.id.tv_comment, item.getComments());
        if (item.getReplylist() != null) {
            //设置回复表
            CommentListView commentListView = holder.getView(R.id.lv_comment);
            commentListView.setOnItemClick(new CommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(TbCommentsReply tbCommentsReply, int index) {
                    if (listener != null) {
                        listener.onClick(tbCommentsReply, index);
                    }
                }
            });
            ReplyAdapter adapter = new ReplyAdapter(context, item.getReplylist());
            commentListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    public interface OnTextClickListener {
        void onClick(TbCommentsReply tbCommentsReply, int index);
    }

    public void setOnTextClickListener(OnTextClickListener listener) {
        this.listener = listener;
    }
}
