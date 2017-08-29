package com.example.lenovo.taoshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.example.lenovo.taoshop.adapter.SearchRecyclerViewAdapter;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.Search;
import com.example.lenovo.taoshop.dao.SQLDataBaseHelper;
import com.example.lenovo.taoshop.injector.components.DaggerSearchComponent;
import com.example.lenovo.taoshop.injector.modules.SearchModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.SearchPresent;
import com.example.lenovo.taoshop.mvp.view.ISearchView;
import com.example.lenovo.taoshop.widget.EmptyLayout;
import com.example.lenovo.taoshop.widget.FlowLayout;
import com.example.lenovo.taoshop.widget.HistoryListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchPresent> implements ISearchView {
    @Bind(R.id.edit_search)
    AutoCompleteTextView editSearch;

    @Bind(R.id.flowlayout)
    FlowLayout flowlayout;

    @Bind(R.id.listView)
    HistoryListView listView;//历史记录
    @Bind(R.id.img_search)
    ImageView img_search;
    @Bind(R.id.tv_clear)
    TextView tvClear;
    @Bind(R.id.relativelayout_search)

    RelativeLayout relativelayoutSearch;
    @Bind(R.id.scrollview_search)
    ScrollView scrollviewSearch;
    @Bind(R.id.rv_search)
    RecyclerView rvSearch;


    private int page = 1;//保存字段
    private List<String> list_search_results = new ArrayList<>();//搜索下拉框数据
    private List<String> list_history;//历史记录数据
    private ArrayAdapter<String> adapter_search_results;//搜索下拉框适配器
    private ArrayAdapter adapter_history;//历史记录适配器
    //数据库操作类
    private SQLDataBaseHelper sqlDataBaseHelper;

    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;

    private String query;//搜索内容

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        //异步请求返回结果
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !s.equals("")) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听flowlayout中textview的点击事件
        flowlayout.setOnItemClick(new FlowLayout.OnFlowLayoutItemClickListener() {
            @Override
            public void onItemClick(String str) {
                editSearch.setText(str);
            }
        });
        //监听历史记录的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editSearch.setText(((TextView) view).getText());
            }
        });

        searchRecyclerViewAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                page++;
                myPresent.getSearchData(query, page, 30);
            }
        });

        //监听点击事件
        searchRecyclerViewAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转到商品详情页
                ItemList itemList = searchRecyclerViewAdapter.getItem(position);
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this, GoodDetailActivity.class);
                //传递商品id
                intent.putExtra("item", itemList);
                invoke(false, intent);
            }
        });
    }

    @Override
    protected void inject() {
        DaggerSearchComponent.builder().searchModule(new SearchModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        sqlDataBaseHelper = new SQLDataBaseHelper(this);
        list_history = sqlDataBaseHelper.get();
        adapter_history = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_history);
        listView.setAdapter(adapter_history);
        adapter_history.notifyDataSetChanged();

        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this);
        RecyclerViewHelper.initRecyclerViewSV(this, rvSearch, false, searchRecyclerViewAdapter, 2);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void updateView() {

    }

    @OnClick({R.id.tv_clear, R.id.img_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:
                sqlDataBaseHelper.deleteAll();
                list_history = sqlDataBaseHelper.get();
                adapter_search_results = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_spinner_dropdown_item, list_search_results);
                listView.setAdapter(adapter_search_results);
                adapter_history.notifyDataSetChanged();
                break;
            case R.id.img_search:
                //搜索请求
                //获取搜索框的搜索字段
                //加入到历史记录数据库中
                relativelayoutSearch.setVisibility(View.VISIBLE);
                scrollviewSearch.setVisibility(View.GONE);
                start();
                query = editSearch.getText().toString().trim();
                //sqlDataBaseHelper.insert(query);
                myPresent.getSearchData(query, page, 30);
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sqlDataBaseHelper.close();
    }

    //更新搜索页面
    @Override
    public void getData(List<ItemList> list) {
        end();
        //返回结果
        searchRecyclerViewAdapter.updateItems(list);
    }

    //没有更多数据
    @Override
    public void noMoredata() {
        searchRecyclerViewAdapter.loadComplete();
        searchRecyclerViewAdapter.noMoreData();
    }

    //获取更多数据
    @Override
    public void getMore(List<ItemList> list) {
        searchRecyclerViewAdapter.addItems(list);
    }

    @Override
    public void fail() {
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getSearchData(editSearch.getText().toString().trim(), page, 30);
            }
        });
    }

    @Override
    public void empty() {
        nodata();
    }
}
