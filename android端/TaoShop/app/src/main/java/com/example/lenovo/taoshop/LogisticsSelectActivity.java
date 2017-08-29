package com.example.lenovo.taoshop;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.example.lenovo.taoshop.adapter.RecyclerViewLogisticsSelectAdapter;
import com.example.lenovo.taoshop.bean.common.TbAddr;
import com.example.lenovo.taoshop.bean.common.TbArea;
import com.example.lenovo.taoshop.dao.mvp.present.ISqlPresent;
import com.example.lenovo.taoshop.dao.mvp.present.SqlPresent;
import com.example.lenovo.taoshop.dao.mvp.view.ISqlView;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.AddrEvent;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;

import java.util.List;

import butterknife.Bind;

public class LogisticsSelectActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_select)
    RecyclerView rvSelect;

    private RecyclerViewLogisticsSelectAdapter adapter;

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

        //监听点击事件
        adapter.setOnViewItemClickListener(new RecyclerViewLogisticsSelectAdapter.OnViewItemClickListener() {
            @Override
            public void onClick(TbAddr tbAddr) {
                RxBus.getInstance().post(new AddrEvent(tbAddr.getTel(), tbAddr.getName(), tbAddr.getAddr() + " " + tbAddr.getArea()));
                finish();
            }
        });
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        adapter = new RecyclerViewLogisticsSelectAdapter(this);
        RecyclerViewHelper.initRecyclerViewV(this, rvSelect, false, adapter);

        initToolbar("选择地址", true, true, toolbar);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_logistics_select;
    }

    @Override
    protected void updateView() {
        //获取数据
        List<TbAddr> list = SharedPreferencesUtil.getInstance(this).getInfo().getTbAddrs();
        adapter.updateItems(list);
    }
}
