package com.example.lenovo.taoshop;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lenovo.taoshop.adapter.OrderViewpagerAdapter;
import com.example.lenovo.taoshop.bean.common.GoodsListItem;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.fragments.OrderFragment;
import com.example.lenovo.taoshop.injector.components.DaggerOrderComponent;
import com.example.lenovo.taoshop.injector.modules.OrderModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.OrderPresent;
import com.example.lenovo.taoshop.mvp.view.IOrderView;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class OrderActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private OrderViewpagerAdapter orderViewpagerAdapter;
    private int index = 0;
    private UserEntity userEntity;

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
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        //获取index值，展示哪一页数据
        index = getIntent().getIntExtra("index", 0);
        //获取用户信息
        userEntity = SharedPreferencesUtil.getInstance(this).getInfo();

        initToolbar("我的订单", true, true, toolbar);
        //初始化viewpager和适配器
        viewPager.setOffscreenPageLimit(0);
        List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(OrderFragment.getInstance(i + 1, userEntity.getId()));
        }
        orderViewpagerAdapter = new OrderViewpagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(orderViewpagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(index);//设置指定页


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void updateView() {

    }

}
