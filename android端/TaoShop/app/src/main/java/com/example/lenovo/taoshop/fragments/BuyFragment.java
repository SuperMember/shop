package com.example.lenovo.taoshop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.example.lenovo.taoshop.GoodDetailActivity;
import com.example.lenovo.taoshop.LoginActivity;
import com.example.lenovo.taoshop.OrderDefActivity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.adapter.RecyclerViewBuyAdapter;
import com.example.lenovo.taoshop.bean.common.BuyGoods;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.dao.mvp.present.SqlPresent;
import com.example.lenovo.taoshop.dao.mvp.view.ISqlView;
import com.example.lenovo.taoshop.fragments.base.BaseFragment;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.BuyEvent;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class BuyFragment extends BaseFragment<SqlPresent> implements ISqlView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_buy)
    RecyclerView rvBuy;

    @Bind(R.id.check_box)
    CheckBox checkBox_all;//全选
    @Bind(R.id.tv_cart_total)
    TextView tvCartTotal;
    @Bind(R.id.tv_cart_select_num)
    TextView tvCartSelectNum;
    @Bind(R.id.swiperefresh_layout)
    SwipeRefreshLayout swiperefreshLayout;
    @Bind(R.id.view_title)
    View viewTitle;
    @Bind(R.id.tv_cart_buy_or_del)
    TextView tvCartBuyOrDel;

    private RecyclerViewBuyAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView() {
        //设置状态栏高度
        AppBarLayout.LayoutParams layoutParams = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusHeight());
        viewTitle.setLayoutParams(layoutParams);

        adapter = new RecyclerViewBuyAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewV(mContext, rvBuy, true, adapter);
        rvBuy.setItemAnimator(new ScaleInRightAnimator());//设置动画
        start();
        myPresent.select();//取数据
    }


    @Override
    protected void setListener() {
        //刷新监听
        swiperefreshLayout.setOnRefreshListener(listener);

        //监听数量改变
        adapter.setOnNumChangeListener(new RecyclerViewBuyAdapter.OnNumChangeListener() {
            @Override
            public void onChange(BuyGoods buyGoods) {
                if (buyGoods.getNum() == 0) {
                    adapter.removeItem(buyGoods);
                    adapter.notifyDataSetChanged();
                    myPresent.delete(buyGoods.getId());
                    RxBus.getInstance().post(new BuyEvent(BuyEvent.DEL_NUM, null));
                } else {
                    myPresent.update(buyGoods);//修改数量
                }
                if (checkBox_all.isChecked()) {
                    tvCartSelectNum.setText("已选" + adapter.getAllCount() + "件商品");
                    tvCartTotal.setText("￥" + adapter.getTotal());
                }
                if (adapter.getAllCount() == 0) {
                    checkBox_all.setChecked(false);
                }
            }
        });

        //监听item的checkbox点击事件
        adapter.setOnCheckBoxChangeListener(new RecyclerViewBuyAdapter.OnCheckBoxChangeListener() {
            @Override
            public void onChange() {
                //重新设置
                //adapter.notifyDataSetChanged();
                tvCartSelectNum.setText("已选" + adapter.getAllCount() + "件商品");//设置选择数量
                tvCartTotal.setText("￥" + adapter.getTotal());//设置选择总数
                if (adapter.getAllCount() == 0) {
                    checkBox_all.setChecked(false);
                } else {
                    checkBox_all.setChecked(true);
                }
            }
        });

        //监听全部或反选
        checkBox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adapter.setChecks(true);
                } else {
                    adapter.setChecks(false);
                }
                tvCartSelectNum.setText("已选" + adapter.getAllCount() + "件商品");//设置选择数量
                tvCartTotal.setText("￥" + adapter.getTotal());//设置选择总数
            }
        });

        //监听图片点击
        adapter.setOnImageClickListener(new RecyclerViewBuyAdapter.OnImageClickListener() {
            @Override
            public void onClick(BuyGoods buyGoods) {
                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                ItemList itemList = new ItemList();
                itemList.setImage(buyGoods.getImage());
                itemList.setPrice((int) buyGoods.getPrice());
                itemList.setId(buyGoods.getId() + "");
                itemList.setTitle(buyGoods.getTitle());
                itemList.setMuser_id(buyGoods.getMuser_id());
                intent.putExtra("item", itemList);
                startActivity(intent);
            }
        });

        //监听倒计时结束
        adapter.setOnTimeOutListener(new RecyclerViewBuyAdapter.OnTimeOutListener() {
            @Override
            public void onEnd(BuyGoods buyGoods) {
                myPresent.delete(buyGoods.getId());
                //adapter.notifyDataSetChanged();
                adapter.removeItem(buyGoods);
                RxBus.getInstance().post(new BuyEvent(BuyEvent.DEL_NUM, null));//更改图标
            }
        });
    }

    private SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            myPresent.select();
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.fragment_buy;
    }

    @Override
    protected void updateView() {

    }

    @Override
    protected void initToolbar() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initInjector() {
        myPresent = new SqlPresent();
        myPresent.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    //从数据库中获取数据
    @Override
    public void show(List<BuyGoods> list) {
        end();
        if (swiperefreshLayout != null) {
            swiperefreshLayout.setRefreshing(false);
        }
        adapter.updateItems(list);
        if (list != null && list.size() != 0) {
            checkBox_all.setChecked(true);
        } else {
            checkBox_all.setChecked(false);
        }
        tvCartSelectNum.setText("已选" + adapter.getAllCount() + "件商品");//设置选择数量
        tvCartTotal.setText("￥" + adapter.getTotal());//设置选择总数
    }

    @Override
    public void empty() {
        nodata(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.select();
            }
        });
    }

    @Override
    public void error(String msg) {
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.select();
            }
        });
    }

    @OnClick(R.id.tv_cart_buy_or_del)
    public void onClick() {
        UserEntity userEntity = SharedPreferencesUtil.getInstance(mContext).getInfo();
        if (userEntity.getUsername() == null || userEntity.getUsername().equals("")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
        //仅适合购买一件商品
        Intent intent = new Intent(getActivity(), OrderDefActivity.class);
        ItemList itemList = new ItemList();
        itemList.setId(adapter.getItem(0).getId() + "");
        itemList.setPrice((int) adapter.getItem(0).getPrice());
        itemList.setTitle(adapter.getItem(0).getTitle());
        itemList.setImage(adapter.getItem(0).getImage());
        itemList.setMuser_id(adapter.getItem(0).getMuser_id());
        intent.putExtra("item", itemList);
        startActivity(intent);
    }
}
