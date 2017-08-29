package com.example.lenovo.taoshop.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.TbItemCatResult;

/**
 * Created by lenovo on 2017  五月  14  0014.
 */

public class RecyclerViewClassifyAdapter extends BaseQuickAdapter<String> {
    public RecyclerViewClassifyAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_classify_left_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.text_classify_name, item.charAt(0) + "");
    }
}
