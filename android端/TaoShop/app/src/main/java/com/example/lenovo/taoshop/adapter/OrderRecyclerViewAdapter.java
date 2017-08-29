package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.opengl.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;

import com.example.lenovo.taoshop.bean.common.OrderItem;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.R;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

public class OrderRecyclerViewAdapter extends BaseQuickAdapter<OrderItem> {
    private Context context;
    private OnBtnClickListener listener;
    private OnImageViewClickListener onImageViewClickListener;

    public OrderRecyclerViewAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_order_item;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final OrderItem orderItem) {
        String status = null;
        //设置订单状态
        switch (orderItem.getStatus()) {
            case 2:
                status = "已付款";
                baseViewHolder.setVisible(R.id.btn_check_logistics, true);//查看物流
                break;
            case 3:
                status = "未发货";
                break;
            case 4:
                status = "已发货";
                baseViewHolder.setVisible(R.id.btn_check_logistics, true);
                baseViewHolder.setVisible(R.id.btn_check_get, true);//确定收货
                break;
            case 5:
                status = "交易完成";
                baseViewHolder.setVisible(R.id.btn_delete_order, true);//删除订单
                baseViewHolder.setVisible(R.id.btn_check_logistics, true);//查看物流
                break;
        }
        //监听按钮的点击事件
        ((Button) baseViewHolder.getView(R.id.btn_delete_order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onClick(0, orderItem);
            }
        });
        ((Button) baseViewHolder.getView(R.id.btn_check_logistics)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onClick(1, orderItem);
            }
        });
        ((Button) baseViewHolder.getView(R.id.btn_check_get)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onClick(2, orderItem);
            }
        });

        baseViewHolder.setText(R.id.text_goods_created, String.valueOf(orderItem.getCreate_time().toString()));//订单创建时间
        baseViewHolder.setText(R.id.text_goods_status, status);//订单状态
        baseViewHolder.setText(R.id.text_goods_name, orderItem.getTitle());//商品名称
        baseViewHolder.setText(R.id.text_goods_price, String.valueOf(orderItem.getPrice()));//商品价格
        baseViewHolder.setText(R.id.text_goods_num, "X" + String.valueOf(orderItem.getNum()));//商品数量
        baseViewHolder.setText(R.id.text_goods_info, "共1个商品，合计:" + String.valueOf(orderItem.getTotal_fee()));//商品总量
        ImageView imageView = baseViewHolder.getView(R.id.img_goods_photo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageViewClickListener != null) {
                    onImageViewClickListener.onClick(orderItem);
                }
            }
        });
        ImageLoader.getInstance().displayImage(context, orderItem.getPic_path(), imageView);//图片
    }

    public interface OnBtnClickListener {
        void onClick(int index, OrderItem orderItem);//0为删除,1为查看物流,2为确定收货
    }

    public void setOnBtnClickListener(OnBtnClickListener listener) {
        this.listener = listener;
    }

    public interface OnImageViewClickListener {
        void onClick(OrderItem orderItem);
    }

    public void setOnImageViewClickListener(OnImageViewClickListener onImageViewClickListener) {
        this.onImageViewClickListener = onImageViewClickListener;
    }
}
