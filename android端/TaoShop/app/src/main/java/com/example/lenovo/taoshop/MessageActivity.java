package com.example.lenovo.taoshop;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lenovo.taoshop.adapter.ViewPagerMessageAdapter;
import com.example.lenovo.taoshop.fragments.message.CommentMessageFragment;
import com.example.lenovo.taoshop.fragments.message.OrderMessageFragment;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/*
* 信息
* */
public class MessageActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.vp_message)
    ViewPager vpMessage;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    private ViewPagerMessageAdapter adapter;
    private OrderMessageFragment orderMessageFragment;
    private CommentMessageFragment commentMessageFragment;

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
        initToolbar("我的消息", true, true, toolbar);

        List<Fragment> list = new ArrayList<>();
        orderMessageFragment = new OrderMessageFragment();
        commentMessageFragment = new CommentMessageFragment();
        list.add(orderMessageFragment);
        list.add(commentMessageFragment);
        adapter = new ViewPagerMessageAdapter(getSupportFragmentManager(), this, list);
        vpMessage.setOffscreenPageLimit(1);
        vpMessage.setCurrentItem(0);
        vpMessage.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpMessage);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_message;
    }

    @Override
    protected void updateView() {

    }
}
