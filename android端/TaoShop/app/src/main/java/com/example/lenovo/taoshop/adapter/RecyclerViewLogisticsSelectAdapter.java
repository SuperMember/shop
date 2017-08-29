package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.view.View;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.TbAddr;

/**
 * Created by lenovo on 2017  五月  23  0023.
 */

public class RecyclerViewLogisticsSelectAdapter extends BaseQuickAdapter<TbAddr> {

    private OnViewItemClickListener onViewItemClickListener;

    public RecyclerViewLogisticsSelectAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_addr_select_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final TbAddr item) {
        //设置收货人
        holder.setText(R.id.tv_username, item.getName());
        //手机
        holder.setText(R.id.tv_phone, item.getTel());
        //收货地址
        holder.setText(R.id.tv_addr, item.getArea() + " " + item.getAddr());

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onViewItemClickListener != null) {
                    onViewItemClickListener.onClick(item);
                }
            }
        });
    }

    public interface OnViewItemClickListener {
        void onClick(TbAddr tbAddr);
    }

    public void setOnViewItemClickListener(OnViewItemClickListener onViewItemClickListener) {
        this.onViewItemClickListener = onViewItemClickListener;
    }
}
