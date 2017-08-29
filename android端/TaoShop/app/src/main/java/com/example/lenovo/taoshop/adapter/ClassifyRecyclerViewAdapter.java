package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.TbItem;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2017  五月  11  0011.
 */

public class ClassifyRecyclerViewAdapter extends BaseQuickAdapter<TbItemResult> {
    private Context context;
    private OnNumChangeListener listener;
    private Map<Long, Integer> nummap = new HashMap<>();//防止item复用错乱

    public ClassifyRecyclerViewAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_classify_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final TbItemResult item) {
        //商品图片
        final ImageView imageView = holder.getView(R.id.img_goods);
        ImageLoader.getInstance().displayImage(context, item.getImage().split(",")[0], imageView);
        holder.setText(R.id.text_title, item.getTitle().length() > 20 ? item.getTitle().substring(0, 20) : item.getTitle());//商品标题
        holder.setText(R.id.text_price, "¥" + item.getPrice() + "");//商品价格
        holder.setText(R.id.text_classify, item.getCidname());//商品类别
        //数量
        AnimShopButton btn = holder.getView(R.id.btn_num);
        if (nummap.get(item.getId()) != null) {
            btn.setCount(nummap.get(item.getId()));
        } else {
            btn.setCount(0);
        }
        //监听删除增加按钮
        btn.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                if (listener != null) {
                    listener.onChange(i, 0, imageView, item);
                }
                nummap.put(item.getId(), i);
            }

            @Override
            public void onAddFailed(int i, FailType failType) {
            }

            @Override
            public void onDelSuccess(int i) {
                if (listener != null) {
                    listener.onChange(i, 1, imageView, item);
                }
                nummap.put(item.getId(), i);
            }

            @Override
            public void onDelFaild(int i, FailType failType) {
            }
        });
    }

    public interface OnNumChangeListener {
        void onChange(int num, int type, ImageView view, TbItemResult tbItemResult);//0表示添加，1表示删除
    }

    public void setOnNumChangeListener(OnNumChangeListener listener) {
        this.listener = listener;
    }
}
