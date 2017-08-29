package com.example.lenovo.taoshop.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dl7.recycler.adapter.BaseMultiItemQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.dl7.recycler.entity.MultiItemEntity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.Data;
import com.example.lenovo.taoshop.bean.common.LogisticsMultiEntity;
import com.example.lenovo.taoshop.bean.common.OrderItem;
import com.example.lenovo.taoshop.utils.ImageLoader;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  06  0006.
 */

public class LogisticsRecyclerViewAdapter extends BaseMultiItemQuickAdapter<LogisticsMultiEntity> {

    private Context context;

    public LogisticsRecyclerViewAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void attachItemType() {
        addItemType(LogisticsMultiEntity.TYPE_HEAD, R.layout.layout_order_header);
        addItemType(LogisticsMultiEntity.TYPE_LOGISTICS_DETAIL, R.layout.layout_order_entity);
    }

    @Override
    protected void convert(BaseViewHolder holder, LogisticsMultiEntity item) {
        switch (item.getItemType()) {
            case LogisticsMultiEntity.TYPE_HEAD:
                handlerHeader(holder, item);
                break;
            case LogisticsMultiEntity.TYPE_LOGISTICS_DETAIL:
                handlerBody(holder, item);
                break;
        }
    }

    //实体部分
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handlerBody(BaseViewHolder holder, LogisticsMultiEntity item) {
        LinearLayout bottom = holder.getView(R.id.linearlayout_bottom);
        LinearLayout current = holder.getView(R.id.linearlayout_mid);
        LinearLayout top = holder.getView(R.id.linearlayout_top);
        if (item.getStatus() == LogisticsMultiEntity.Status.BOTTOM) {
            //底部
            bottom.setVisibility(View.VISIBLE);
            current.setVisibility(View.GONE);
            top.setVisibility(View.GONE);
        } else if (item.getStatus() == LogisticsMultiEntity.Status.CURRENT) {
            //中间
            current.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
            top.setVisibility(View.GONE);
        } else {
            //头
            top.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
            current.setVisibility(View.GONE);
        }
        Data data = item.getData();
        //设置时间
        holder.setText(R.id.text_time, data.getTime() == null ? new Date().toString()
                : data.getTime());
        //设置物流
        holder.setText(R.id.text_detail, data.getContext());
    }

    //头部
    private void handlerHeader(BaseViewHolder holder, LogisticsMultiEntity item) {
        OrderItem order = item.getOrderItem();
        //设置商品图片
        ImageView image = holder.getView(R.id.img_goods);
        ImageLoader.getInstance().displayImage(context, order.getPic_path(), image);
        holder.setText(R.id.text_logistics_company, "承运公司:" + order.getShipping_name());//设置快递公司名称
        holder.setText(R.id.text_logistics_id, "物流单号:" + order.getShipping_code());//设置物流单号
        holder.setText(R.id.text_logistics_status, order.getBuyer_message());
    }
}
