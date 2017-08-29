package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.utils.ImageLoader;

/**
 * Created by lenovo on 2017  五月  08  0008.
 */

public class SearchRecyclerViewAdapter extends BaseQuickAdapter<ItemList> {
    private Context context;

    public SearchRecyclerViewAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_search_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, ItemList item) {
        //设置商品图片
        ImageView imageView = holder.getView(R.id.img_goods);
        ImageLoader.getInstance().displayImage(context, item.getImage().split(",")[0], imageView);
        //设置商品标题
        holder.setText(R.id.text_goods_title, item.getTitle());
        //设置商品价格
        holder.setText(R.id.text_goods_price, "元 " + item.getPrice() + "");
    }
}
