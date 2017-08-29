package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.bean.common.TbOrderMsg;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.hankkin.library.CircleImageView;

import java.text.SimpleDateFormat;

/**
 * Created by lenovo on 2017  五月  27  0027.
 */

public class RecyclerviewMessageAdapter extends BaseQuickAdapter<TbOrderMsg> {
    private OnCardViewClickListener listener;

    public RecyclerviewMessageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_message_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final TbOrderMsg tbOrderMsg) {
        //设置时间
        holder.setText(R.id.text_time, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(tbOrderMsg.getTime()));
        //设置订单详情
        holder.setText(R.id.text_order_statue, tbOrderMsg.getMsg());
        //快递公司
        holder.setText(R.id.text_order_company, tbOrderMsg.getCompany() == null ? "" : tbOrderMsg.getCompany());
        //设置图片
        ImageView imageView = holder.getView(R.id.img_goods);
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), tbOrderMsg.getPic(), imageView, R.mipmap.user);
        //设置标题
        holder.setText(R.id.text_title, tbOrderMsg.getTitle());
        //设置物流单号
        holder.setText(R.id.text_order_id, tbOrderMsg.getLogisticsid() == null ? "" : "物流单号" + tbOrderMsg.getLogisticsid());
        CardView cardView = holder.getView(R.id.cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(tbOrderMsg);
                }
            }
        });
    }

    public interface OnCardViewClickListener {
        void onClick(TbOrderMsg tbOrderMsg);
    }

    public void setOnCardViewClickListener(OnCardViewClickListener listener) {
        this.listener = listener;
    }

}
