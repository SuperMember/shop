package com.example.lenovo.taoshop.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.example.lenovo.taoshop.GoodDetailActivity;
import com.example.lenovo.taoshop.LogisticsActivity;
import com.example.lenovo.taoshop.OrderCommentActivity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.adapter.OrderRecyclerViewAdapter;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.OrderItem;
import com.example.lenovo.taoshop.fragments.base.BaseFragment;
import com.example.lenovo.taoshop.injector.components.DaggerOrderComponent;
import com.example.lenovo.taoshop.injector.modules.OrderModule;
import com.example.lenovo.taoshop.mvp.present.OrderPresent;
import com.example.lenovo.taoshop.mvp.view.IOrderView;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import java.util.List;

import butterknife.Bind;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

public class OrderFragment extends BaseFragment<OrderPresent> implements IOrderView {
    @Bind(R.id.rv_content)
    RecyclerView rvContent;
    @Bind(R.id.swiperefresh_layout)
    SwipeRefreshLayout swiperefreshLayout;

    private OrderRecyclerViewAdapter mAdapter;
    private View mNodataView;

    private Integer type;
    private Long userId;
    private int page = 1;
    private int deleteId = 0;//删除的index
    private ProgressDialog progressDialog;

    public static OrderFragment getInstance(Integer type, Long userId) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putLong("userId", userId);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    protected void initInjector() {
        DaggerOrderComponent.builder().orderModule(new OrderModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        //获取参数
        Bundle bundle = getArguments();
        type = bundle.getInt("type");
        userId = bundle.getLong("userId");
        mAdapter = new OrderRecyclerViewAdapter(mContext);
        SlideInRightAnimationAdapter animAdapter = new SlideInRightAnimationAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewV(mContext, rvContent, true, new AlphaInAnimationAdapter(animAdapter));

        mNodataView = LayoutInflater.from(mContext).inflate(R.layout.layout_order_nodata, null);

        progressDialog = new ProgressDialog(getActivity());
    }

    private SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            myPresent.getOrderAll(userId, page, type);
        }
    };

    @Override
    protected void setListener() {
        swiperefreshLayout.setOnRefreshListener(listener);

        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                ++page;
                myPresent.getOrderAll(userId, page, type);
            }
        });
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mAdapter.setOnBtnClickListener(new OrderRecyclerViewAdapter.OnBtnClickListener() {
            @Override
            public void onClick(int index, final OrderItem orderItem) {
                switch (index) {
                    case 0:
                        //删除
                        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setTitle("删除")
                                .setMessage("确定要删除该订单吗")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog.show();
                                        myPresent.deleteOrder(Long.parseLong(orderItem.getOrder_id()));
                                    }
                                }).create();
                        dialog.show();
                        break;
                    case 1:
                        //查看物流
                        Intent intent = new Intent();
                        String status = null;
                        switch (type) {
                            case 2:
                                status = "已付款";
                                break;
                            case 3:
                                status = "未发货";
                                break;
                            case 4:
                                status = "已发货";
                                break;
                            case 5:
                                status = "交易完成";
                                break;
                        }
                        orderItem.setBuyer_message(status);
                        intent.putExtra("order", orderItem);
                        intent.setClass(mContext, LogisticsActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        //确定收货
                        //评论页面
                        Intent comment = new Intent(getActivity(), OrderCommentActivity.class);
                        ItemList itemList = new ItemList();
                        itemList.setMuser_id(orderItem.getMuser_id());
                        itemList.setId(orderItem.getItem_id());
                        itemList.setTitle(orderItem.getTitle());
                        itemList.setPrice((int) orderItem.getPrice());
                        itemList.setImage(orderItem.getPic_path());
                        //itemList.setCidname(orderItem.get);
                        comment.putExtra("item", itemList);
                        comment.putExtra("order", Long.parseLong(orderItem.getOrder_id()));
                        startActivity(comment);
                        break;
                }
            }
        });

        mAdapter.setOnImageViewClickListener(new OrderRecyclerViewAdapter.OnImageViewClickListener() {
            @Override
            public void onClick(OrderItem orderItem) {
                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                ItemList itemList = new ItemList();
                itemList.setPrice((int) orderItem.getPrice());
                itemList.setTitle(orderItem.getTitle());
                itemList.setImage(orderItem.getPic_path());
                itemList.setId(orderItem.getItem_id());
                intent.putExtra("item", itemList);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.vp_fragment_order;
    }

    @Override
    protected void updateView() {
        //获取相关订单
        start();//页面开始加载
        myPresent.getOrderAll(userId, page, type);
    }

    @Override
    protected void initToolbar() {

    }


    @Override
    public void loadData(List<OrderItem> list) {
        if (swiperefreshLayout != null) {
            swiperefreshLayout.setRefreshing(false);
        }
        end();
        mAdapter.updateItems(list);
    }

    @Override
    public void loadMoreData(List<OrderItem> list) {
        mAdapter.loadComplete();
        mAdapter.addItems(list);
    }

    @Override
    public void loadNoData() {
        nodata(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getOrderAll(userId, page, type);
            }
        });
        mAdapter.loadAbnormal();
    }

    @Override
    public void loadNoMoreData() {
        mAdapter.noMoreData();
    }

    @Override
    public void failure(final String msg) {
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getOrderAll(userId, page, type);
            }
        });
    }

    @Override
    public void submit(String msg) {
        progressDialog.dismiss();
        if (swiperefreshLayout != null) {
            swiperefreshLayout.setRefreshing(true);
            listener.onRefresh();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
