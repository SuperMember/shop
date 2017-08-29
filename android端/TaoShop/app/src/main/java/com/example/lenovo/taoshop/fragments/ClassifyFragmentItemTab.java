package com.example.lenovo.taoshop.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.example.lenovo.taoshop.GoodDetailActivity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.adapter.ClassifyRecyclerViewAdapter;
import com.example.lenovo.taoshop.bean.common.BuyGoods;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.TbItem;
import com.example.lenovo.taoshop.bean.common.TbItemCat;
import com.example.lenovo.taoshop.bean.common.TbItemCatResult;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.dao.mvp.present.SqlPresent;
import com.example.lenovo.taoshop.fragments.base.BaseFragment;
import com.example.lenovo.taoshop.injector.components.DaggerGoodComponent;
import com.example.lenovo.taoshop.injector.modules.GoodModule;
import com.example.lenovo.taoshop.mvp.present.ClassifyPresent;
import com.example.lenovo.taoshop.mvp.view.IClassifyView;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.BuyEvent;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017  五月  11  0011.
 */

public class ClassifyFragmentItemTab extends BaseFragment<ClassifyPresent> implements IClassifyView {

    @Bind(R.id.rv_content)
    RecyclerView rvContent;
    @Bind(R.id.swiperefresh_layout)
    SwipeRefreshLayout swiperefreshLayout;


    private long page = 1;
    private long parentId;
    private Long cid;
    private ClassifyRecyclerViewAdapter adapter;


    private SqlPresent sqlPresent = new SqlPresent();//数据库操作

    public void setDatas(long parentId, Long cid) {
        this.parentId = parentId;
        this.cid = cid;
        myPresent.getGoods(parentId, page, cid);
        start();
    }

    @Override
    protected void initInjector() {
        DaggerGoodComponent.builder().goodModule(new GoodModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        adapter = new ClassifyRecyclerViewAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewV(mContext, rvContent, false, adapter);
    }

    @Override
    protected void setListener() {
        //item内容点击
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ItemList itemList = new ItemList();
                TbItemResult tbItemResult = adapter.getItem(position);
                //设置属性
                itemList.setMuser_id(tbItemResult.getMuserId());//卖家id
                itemList.setId(tbItemResult.getId() + "");
                itemList.setImage(tbItemResult.getImage());
                itemList.setPrice(tbItemResult.getPrice().intValue());
                itemList.setTitle(tbItemResult.getTitle());
                itemList.setCidname(tbItemResult.getCidname());
                Intent intent = new Intent();
                intent.putExtra("item", itemList);
                intent.setClass(getActivity(), GoodDetailActivity.class);
                startActivity(intent);
            }
        });

        //增加删除商品数量
        adapter.setOnNumChangeListener(new ClassifyRecyclerViewAdapter.OnNumChangeListener() {
            @Override
            public void onChange(int num, int type, ImageView view, TbItemResult tbItemResult) {
                //转化数据
                //封装数据
                BuyGoods buyGoods = new BuyGoods();
                buyGoods.setImage(tbItemResult.getImage());
                buyGoods.setMuser_id(tbItemResult.getMuserId());
                buyGoods.setCid(tbItemResult.getCid());
                buyGoods.setId(tbItemResult.getId());
                buyGoods.setNum(num);
                buyGoods.setTitle(tbItemResult.getTitle());
                buyGoods.setPrice(tbItemResult.getPrice());
                buyGoods.setCreated(new Date().getTime());
                //对数据库进行增加和删除
                BuyEvent buyEvent;
                if (type == 0) {
                    //添加
                    buyEvent = new BuyEvent(BuyEvent.ADD_NUM, view);
                    if (num == 1) {
                        //插入
                        sqlPresent.insert(buyGoods);
                    } else {
                        //更新
                        sqlPresent.update(buyGoods);
                    }
                } else {
                    //删除
                    buyEvent = new BuyEvent(BuyEvent.DEL_NUM, view);
                    if (num == 0) {
                        //删除
                        sqlPresent.delete(tbItemResult.getId());
                    } else {
                        //更新
                        sqlPresent.update(buyGoods);
                    }
                }
                RxBus.getInstance().post(buyEvent);//rxbus事件发送
            }
        });

        //底部加载
        adapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                page++;
                myPresent.getGoods(parentId, page, cid);
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_classify_item_tab;
    }

    @Override
    protected void updateView() {

    }

    @Override
    protected void initToolbar() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void get(List<TbItemResult> tbItems) {
        end();
        adapter.updateItems(tbItems);
    }

    @Override
    public void fail(String msg) {
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getGoods(parentId, page, null);
            }
        });
    }

    @Override
    public void getMore(List<TbItemResult> tbItems) {
        end();
        adapter.addItems(tbItems);
    }

    @Override
    public void noMore() {
        adapter.loadComplete();
        adapter.noMoreData();
    }

    @Override
    public void empty() {
        nodata();
    }

    @Override
    public void getClassify(List<TbItemCatResult> list) {

    }

}
