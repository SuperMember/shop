package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseMultiItemQuickAdapter;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.dl7.recycler.entity.MultiItemEntity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.bean.common.IndexMulEntity;
import com.example.lenovo.taoshop.bean.common.TbItem;
import com.example.lenovo.taoshop.utils.ImageLoader;

import org.w3c.dom.Text;

import java.util.List;


/**
 * Created by lenovo on 2017  五月  17  0017.
 */

public class RecyclerViewHomeAdapter extends BaseMultiItemQuickAdapter<IndexMulEntity> {
    private OnGoodClickListener listener;

    public RecyclerViewHomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected void attachItemType() {
        addItemType(IndexMulEntity.RECOMMEND_ITEM, R.layout.recyclerview_index_item_recommand);
        addItemType(IndexMulEntity.SPECIAL_ITEM, R.layout.recyclerview_index_item);
        addItemType(IndexMulEntity.GOOD_OTHER_ITEM, R.layout.recyclerview_item_good);
    }

    @Override
    protected void convert(BaseViewHolder holder, IndexMulEntity item) {
        switch (item.getItemType()) {
            case IndexMulEntity.RECOMMEND_ITEM:
                _handleRecommend(holder, item);
                break;
            case IndexMulEntity.SPECIAL_ITEM:
                _handleSpecial(holder, item);
                break;
            case IndexMulEntity.GOOD_OTHER_ITEM:
                _handleGood(holder, item);
                break;
        }
    }

    //其他商品
    private void _handleGood(BaseViewHolder holder, final IndexMulEntity item) {
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

    //专区
    private void _handleSpecial(BaseViewHolder holder, final IndexMulEntity item) {
        //设置图片
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getTbItems().get(0).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_good1));
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getTbItems().get(1).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_good2));
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getTbItems().get(2).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_good3));
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getTbItems().get(3).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_good4));
        ((ImageView) holder.getView(R.id.img_good1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getTbItems().get(0).getImage(), item.getTbItems().get(0).getTitle(),
                            item.getTbItems().get(0).getPrice(), item.getTbItems().get(0).getId(), item.getTbItems().get(0).getMuserId());
                }
            }
        });
        ((ImageView) holder.getView(R.id.img_good2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getTbItems().get(1).getImage(), item.getTbItems().get(1).getTitle(),
                            item.getTbItems().get(1).getPrice(), item.getTbItems().get(1).getId(), item.getTbItems().get(1).getMuserId());
                }
            }
        });
        ((ImageView) holder.getView(R.id.img_good3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getTbItems().get(2).getImage(), item.getTbItems().get(2).getTitle(),
                            item.getTbItems().get(2).getPrice(), item.getTbItems().get(2).getId(), item.getTbItems().get(2).getMuserId());
                }
            }
        });
        ((ImageView) holder.getView(R.id.img_good4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getTbItems().get(3).getImage(), item.getTbItems().get(3).getTitle(),
                            item.getTbItems().get(3).getPrice(), item.getTbItems().get(3).getId(), item.getTbItems().get(3).getMuserId());
                }
            }
        });
        //设置价格
        holder.setText(R.id.text_price1, "¥" + item.getTbItems().get(0).getPrice());
        holder.setText(R.id.text_price2, "¥" + item.getTbItems().get(1).getPrice());
        holder.setText(R.id.text_price3, "¥" + item.getTbItems().get(2).getPrice());
        holder.setText(R.id.text_price4, "¥" + item.getTbItems().get(3).getPrice());
        //设置标题
        holder.setText(R.id.text_good_title1, item.getTbItems().get(0).getTitle());
        holder.setText(R.id.text_good_title2, item.getTbItems().get(1).getTitle());
        holder.setText(R.id.text_good_title3, item.getTbItems().get(2).getTitle());
        holder.setText(R.id.text_good_title4, item.getTbItems().get(3).getTitle());
        //设置标题
        holder.setText(R.id.text_channel, item.getTitle());
    }

    //推荐
    private void _handleRecommend(BaseViewHolder holder, final IndexMulEntity item) {
        //设置图片
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getItemResults().get(0).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_recommand0));
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getItemResults().get(1).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_recommand1));
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getItemResults().get(2).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_recommand2));
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getItemResults().get(3).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_recommand3));
        ImageLoader.getInstance().displayImage(MyApplication.getContext(), item.getItemResults().get(4).getImage().split(",")[0], (ImageView) holder.getView(R.id.img_recommand4));
        ((ImageView) holder.getView(R.id.img_recommand0)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getItemResults().get(0).getImage(), item.getItemResults().get(0).getTitle(),
                            item.getItemResults().get(0).getPrice(), item.getItemResults().get(0).getId(), item.getItemResults().get(0).getMuserId());
                }
            }
        });
        ((ImageView) holder.getView(R.id.img_recommand1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getItemResults().get(1).getImage(), item.getItemResults().get(1).getTitle(),
                            item.getItemResults().get(1).getPrice(), item.getItemResults().get(1).getId(), item.getItemResults().get(1).getMuserId());
                }
            }
        });
        ((ImageView) holder.getView(R.id.img_recommand2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getItemResults().get(2).getImage(), item.getItemResults().get(2).getTitle(),
                            item.getItemResults().get(2).getPrice(), item.getItemResults().get(2).getId(), item.getItemResults().get(2).getMuserId());
                }
            }
        });
        ((ImageView) holder.getView(R.id.img_recommand3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getItemResults().get(3).getImage(), item.getItemResults().get(3).getTitle(),
                            item.getItemResults().get(3).getPrice(), item.getItemResults().get(3).getId(), item.getItemResults().get(3).getMuserId());
                }
            }
        });
        ((ImageView) holder.getView(R.id.img_recommand4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item.getItemResults().get(4).getImage(), item.getItemResults().get(4).getTitle(),
                            item.getItemResults().get(4).getPrice(), item.getItemResults().get(4).getId(), item.getItemResults().get(4).getMuserId());
                }
            }
        });
        //设置价格
        holder.setText(R.id.text_price0, "¥" + item.getItemResults().get(0).getPrice());
        holder.setText(R.id.text_price1, "¥" + item.getItemResults().get(1).getPrice());
        holder.setText(R.id.text_price2, "¥" + item.getItemResults().get(2).getPrice());
        holder.setText(R.id.text_price3, "¥" + item.getItemResults().get(3).getPrice());
        holder.setText(R.id.text_price4, "¥" + item.getItemResults().get(4).getPrice());
        //设置标题
        holder.setText(R.id.text_good_title0, item.getItemResults().get(0).getTitle());
        holder.setText(R.id.text_good_title1, item.getItemResults().get(1).getTitle());
        holder.setText(R.id.text_good_title2, item.getItemResults().get(2).getTitle());
        holder.setText(R.id.text_good_title3, item.getItemResults().get(3).getTitle());
        holder.setText(R.id.text_good_title4, item.getItemResults().get(4).getTitle());
        //设置标题
        holder.setText(R.id.text_channel, item.getTitle());
    }

    public interface OnGoodClickListener {
        void onClick(String imgs, String title, Long price, Long itemId, Long muserId);
    }

    public void setOnGoodClickListener(OnGoodClickListener listener) {
        this.listener = listener;
    }
}
