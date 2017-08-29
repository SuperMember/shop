package com.example.lenovo.taoshop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.SearchActivity;
import com.example.lenovo.taoshop.adapter.ViewPagerFragmentClassifyAdapter;
import com.example.lenovo.taoshop.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class ClassifyFragment extends BaseFragment {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.action_search)
    ImageView actionSearch;
   /* @Bind(R.id.view_title)
    View viewTitle;*/


    private List<Fragment> fragmentList;
    private ViewPagerFragmentClassifyAdapter adapter;
    private List<String> typelist;

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        //设置状态栏高度
       /* AppBarLayout.LayoutParams layoutParams = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusHeight());
        viewTitle.setLayoutParams(layoutParams);
*/
        typelist = Arrays.asList(getResources().getStringArray(R.array.classify));

        fragmentList = new ArrayList<>();
        //初始化标题和viewpager
        for (int i = 0; i < 6; i++) {
            ClassifyFragmentItem classifyFragmentItem = ClassifyFragmentItem.getInstance(Long.parseLong(typelist.get(i)));
            fragmentList.add(classifyFragmentItem);
        }
        adapter = new ViewPagerFragmentClassifyAdapter(getFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(0);
        //tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tabcolor));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置为滑动模式
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_classify;
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

    @OnClick({R.id.action_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_search:
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "transition_search_back");
                Intent intent = new Intent();
                intent.setClass(getActivity(), SearchActivity.class);
                ActivityCompat.startActivity(getActivity(), intent, activityOptionsCompat.toBundle());
                break;
        }
    }
}
