package com.example.lenovo.taoshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.example.lenovo.taoshop.adapter.RecyclerViewHomeItemAdapter;
import com.example.lenovo.taoshop.bean.common.AdItem;
import com.example.lenovo.taoshop.bean.common.IndexMulEntity;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.injector.components.DaggerHomeComponent;
import com.example.lenovo.taoshop.injector.modules.HomeModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.HomePresent;
import com.example.lenovo.taoshop.mvp.view.IHomeView;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import java.util.List;

import butterknife.Bind;

public class HomeItemDetailActivity extends BaseActivity<HomePresent> implements IHomeView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_content)
    RecyclerView rvContent;

    private String title;
    private int page = 1;
    private RecyclerViewHomeItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                page++;
                myPresent.getIndexMore(page);
            }
        });

        adapter.setOnGoodClickListener(new RecyclerViewHomeItemAdapter.OnGoodClickListener() {
            @Override
            public void onClick(String imgs, String title, Long price, Long itemId, Long muserId) {
                Intent intent = new Intent(HomeItemDetailActivity.this, GoodDetailActivity.class);
                ItemList itemList = new ItemList();
                itemList.setImage(imgs);
                itemList.setTitle(title);
                itemList.setPrice(price.intValue());
                itemList.setId(itemId + "");
                itemList.setMuser_id(muserId);
                intent.putExtra("item", itemList);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void inject() {
        DaggerHomeComponent.builder().homeModule(new HomeModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        getIntentData();
        initToolbar(title, true, true, toolbar);
        adapter = new RecyclerViewHomeItemAdapter(this);
        RecyclerViewHelper.initRecyclerViewV(this, rvContent, false, adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home_item_detail;
    }

    @Override
    protected void updateView() {
        myPresent.getIndexMore(page);
    }

    public void getIntentData() {
        title = getIntent().getStringExtra("title");
    }

    @Override
    public void loadAdData(List<AdItem> list) {

    }

    @Override
    public void load(List<IndexMulEntity> list) {
        end();
        adapter.loadComplete();
        adapter.updateItems(list);
    }

    @Override
    public void fail(String msg) {
        end();
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                myPresent.getIndexMore(page);
            }
        });
    }

    @Override
    public void loadMore(List<IndexMulEntity> list) {
        end();
        adapter.loadComplete();
        adapter.addItems(list);
    }

    @Override
    public void noMore() {
        adapter.noMoreData();
    }
}
