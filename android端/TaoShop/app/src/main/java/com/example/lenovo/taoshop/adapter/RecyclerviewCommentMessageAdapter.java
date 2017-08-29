package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.bean.common.TbCommentsMsg;
import com.example.lenovo.taoshop.bean.common.TbOrderMsg;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.hankkin.library.CircleImageView;

import java.text.SimpleDateFormat;

/**
 * Created by lenovo on 2017  五月  27  0027.
 */

public class RecyclerviewCommentMessageAdapter extends BaseQuickAdapter<TbCommentsMsg> {
    private OnCardViewClickListener listener;

    public RecyclerviewCommentMessageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_message_comment;
    }

    @Override
    protected void convert(BaseViewHolder holder, final TbCommentsMsg tbCommentsMsg) {
        //设置时间
        holder.setText(R.id.text_time, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(tbCommentsMsg.getTime()));
        //设置名称
        holder.setText(R.id.text_username, tbCommentsMsg.getUsername());
        holder.setText(R.id.text_comment, tbCommentsMsg.getComment());
        holder.setText(R.id.text_self_comment, tbCommentsMsg.getSelfcomment());
        //设置图片
        CircleImageView imageView = holder.getView(R.id.img_goods);
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), tbCommentsMsg.getPic(), imageView, R.mipmap.user);

        CardView cardView = holder.getView(R.id.cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(tbCommentsMsg.getItemid(), tbCommentsMsg.getMuserid());
                }
            }
        });
    }

    public interface OnCardViewClickListener {
        void onClick(Long itemId, Long muserId);
    }

    public void setOnCardViewClickListener(OnCardViewClickListener listener) {
        this.listener = listener;
    }
}
