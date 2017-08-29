package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.bean.common.BuyGoods;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.utils.RxHelper;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public class RecyclerViewBuyAdapter extends BaseQuickAdapter<BuyGoods> {
    private Context context;
    private OnNumChangeListener listener;//数量改变
    private OnCheckBoxChangeListener checkBoxChangeListener;//checkbox点击
    private Map<Long, Boolean> checks = new HashMap<>();
    private OnImageClickListener onImageClickListener;
    private OnTimeOutListener onTimeOutListener;//倒计时
    // private Subscription subscription;

    public RecyclerViewBuyAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recyclerview_buy_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final BuyGoods item) {
        //设置图片
        ImageView imageView = holder.getView(R.id.iv_good_pic);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null) {
                    onImageClickListener.onClick(item);
                }
            }
        });

        //开始倒计时
        //设置时间
        final TextView time = holder.getView(R.id.tv_time);
        //holder.setText(R.id.tv_time, item.getCreated());
        Long created = item.getCreated();//创建时间
        Long now = new Date().getTime();//现在时间
        //time.setText((now - created)+"");
        //设定15分过期
        int current = (int) ((now - created) / 1000);
        if (current == 50) {
            if (onTimeOutListener != null) {
                onTimeOutListener.onEnd(item);
            }
        } else {
            if (item.getSubscription() != null && item.getSubscription().isUnsubscribed()) {
                item.getSubscription().unsubscribe();
                item.setSubscription(null);
            }
            item.setSubscription(RxHelper.countdown((50 - current))
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            if (onTimeOutListener != null) {
                                item.getSubscription().unsubscribe();
                                onTimeOutListener.onEnd(item);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            item.getSubscription().unsubscribe();
                        }

                        @Override
                        public void onNext(Integer integer) {
                            int minute = integer / 60 % 60;
                            int second = integer % 60;
                            time.setText("订单倒计时 " + minute + ":" + second);
                        }
                    }));
        }


        ImageLoader.getInstance().displayImage(context, item.getImage().split(",")[0], imageView);
        //设置标题
        holder.setText(R.id.tv_intro, item.getTitle());

        //设置价格
        holder.setText(R.id.tv_price, "￥ " + item.getPrice() + "");
        //设置合计
        final TextView tv_total = holder.getView(R.id.tv_total);
        tv_total.setText("合计: " + (item.getNum() * item.getPrice()));

        //选中框
        final CheckBox checkBox = holder.getView(R.id.check_box);
        if (checks.get(item.getId())) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    checkBox.setChecked(true);
                    checks.put(item.getId(), true);
                } else {
                    checkBox.setChecked(false);
                    checks.put(item.getId(), false);
                }
                if (checkBoxChangeListener != null) {
                    checkBoxChangeListener.onChange();
                }
            }
        });


        //设置数量
        AnimShopButton button = holder.getView(R.id.btn_num);
        button.setCount(item.getNum());
        button.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                if (listener != null) {
                    item.setNum(item.getNum() + 1);
                    listener.onChange(item);
                }
                tv_total.setText("合计: " + (i * item.getPrice()));
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                if (listener != null) {
                    item.setNum(item.getNum() - 1);
                    listener.onChange(item);
                }
                tv_total.setText("合计: " + (i * item.getPrice()));
                if (i == 0) {
                    if (item.getSubscription() != null && item.getSubscription().isUnsubscribed()) {
                        item.getSubscription().unsubscribe();
                    }
                }
            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });
    }

    //获取总数
    public int getAllCount() {
        int total = 0;
        for (int i = 0; i < getData().size(); i++) {
            if (checks.get(getItem(i).getId())) {
                total = total + 1;
            }
        }
        return total;
    }

    @Override
    public void updateItems(List<BuyGoods> items) {
        super.updateItems(items);
        setChecks(true);
    }

    //获取总价格
    public long getTotal() {
        long total = 0;
        for (int i = 0; i < getData().size(); i++) {
            BuyGoods buyGoods = getItem(i);
            if (checks.get(buyGoods.getId()))
                total = total + buyGoods.getNum() * buyGoods.getPrice();
        }
        return total;
    }

    public void setChecks(boolean isChecked) {
        for (int i = 0; i < getData().size(); i++) {
            BuyGoods buyGoods = getItem(i);
            checks.put(buyGoods.getId(), isChecked);
        }
        notifyDataSetChanged();
    }


    public interface OnNumChangeListener {
        void onChange(BuyGoods buyGoods);//0表示添加，1表示删除
    }

    public void setOnNumChangeListener(OnNumChangeListener listener) {
        this.listener = listener;
    }

    public interface OnCheckBoxChangeListener {
        void onChange();
    }

    public void setOnCheckBoxChangeListener(OnCheckBoxChangeListener checkBoxChangeListener) {
        this.checkBoxChangeListener = checkBoxChangeListener;
    }

    public interface OnImageClickListener {
        void onClick(BuyGoods buyGoods);
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public interface OnTimeOutListener {
        void onEnd(BuyGoods buyGoods);
    }

    public void setOnTimeOutListener(OnTimeOutListener onTimeOutListener) {
        this.onTimeOutListener = onTimeOutListener;
    }
}
