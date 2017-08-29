package com.example.lenovo.taoshop;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.example.lenovo.taoshop.adapter.LogisticsRecyclerViewAdapter;
import com.example.lenovo.taoshop.bean.common.AreaEntity;
import com.example.lenovo.taoshop.bean.common.LogisticsMultiEntity;
import com.example.lenovo.taoshop.bean.common.OrderItem;
import com.example.lenovo.taoshop.injector.components.DaggerLogisticsComponent;
import com.example.lenovo.taoshop.injector.modules.LogisticsModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.LogisticsPresent;
import com.example.lenovo.taoshop.mvp.view.ILogisticsView;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class LogisticsActivity extends BaseActivity<LogisticsPresent> implements ILogisticsView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_logistics)
    RecyclerView rvLogistics;
    @Bind(R.id.swiperefresh_layout)
    SwipeRefreshLayout swiperefreshLayout;

    private OrderItem orderItem;
    private LogisticsRecyclerViewAdapter adapter;
    private List<LogisticsMultiEntity> header;
    private LogisticsMultiEntity entityHeader;


    private String logisticsId;//物流单号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swiperefreshLayout.setOnRefreshListener(listener);
    }

    public SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            myPresent.getDetail(logisticsId);
        }
    };

    @Override
    protected void inject() {
        DaggerLogisticsComponent.builder().logisticsModule(new LogisticsModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        start();//加载数据

        header = new ArrayList<>();
        entityHeader = new LogisticsMultiEntity(LogisticsMultiEntity.TYPE_HEAD);
        orderItem = (OrderItem) getIntent().getSerializableExtra("order");
        entityHeader.setOrderItem(orderItem);
        header.add(entityHeader);

        //设置物流单号
        logisticsId = orderItem.getShipping_code();

        //初始化标题
        initToolbar("物流详情", true, true, toolbar);
        //初始化rv
        adapter = new LogisticsRecyclerViewAdapter(this);
        adapter.updateItems(header);
        SlideInRightAnimationAdapter animAdapter = new SlideInRightAnimationAdapter(adapter);
        RecyclerViewHelper.initRecyclerViewV(this, rvLogistics, false, animAdapter);
        //获取物流信息
        myPresent.getDetail(logisticsId);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void get(List<LogisticsMultiEntity> list) {
        if (swiperefreshLayout != null) {
            swiperefreshLayout.setRefreshing(false);
        }
        if (list.size() == 1) {
            list.get(0).setStatus(LogisticsMultiEntity.Status.BOTTOM);
        } else {
            list.get(0).setStatus(LogisticsMultiEntity.Status.HEADER);
            list.get(list.size() - 1).setStatus(LogisticsMultiEntity.Status.BOTTOM);
        }
        list.add(0, entityHeader);
        adapter.updateItems(list);
    }

    @Override
    public void empty() {
        if (swiperefreshLayout != null) {
            swiperefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void fail(String msg) {
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                myPresent.getDetail(logisticsId);
            }
        });
    }

    @Override
    public void getArea(AreaEntity list) {

    }
}
