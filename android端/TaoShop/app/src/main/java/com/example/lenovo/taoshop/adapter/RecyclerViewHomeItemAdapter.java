package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.bean.common.IndexMulEntity;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.utils.ImageLoader;

/**
 * Created by lenovo on 2017  六月  06  0006.
 */

public class RecyclerViewHomeItemAdapter extends BaseQuickAdapter<IndexMulEntity> {
    private OnGoodClickListener listener;

    public RecyclerViewHomeItemAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_item_good;
    }

    @Override
    protected void convert(BaseViewHolder holder, final IndexMulEntity item) {
        //图片
        ImageView imageView = holder.getView(R.id.img_good);
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getItemResults().get(0).getImage().split(",")[0], imageView, R.mipmap.user);
        //标题
        holder.setText(R.id.text_good_title, item.getItemResults().get(0).getTitle());
        //价格
        holder.setText(R.id.text_price, "¥ " + item.getItemResults().get(0).getPrice());
        //设置头
        if (item.getTitle() != null) {
            View view = holder.getView(R.id.view);
            view.setVisibility(View.VISIBLE);
            TextView textView = (TextView) view.findViewById(R.id.text_channel);
            textView.setText("更多商品");
        } else {
            View view = holder.getView(R.id.view);
            view.setVisibility(View.GONE);
        }
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getItemResults().get(0).getImage(), item.getItemResults().get(0).getTitle(),
                            item.getItemResults().get(0).getPrice(), item.getItemResults().get(0).getId(), item.getItemResults().get(0).getMuserId());
                }
            }
        });
    }

    public interface OnGoodClickListener {
        void onClick(String imgs, String title, Long price, Long itemId, Long muserId);
    }

    public void setOnGoodClickListener(OnGoodClickListener listener) {
        this.listener = listener;
    }
}
