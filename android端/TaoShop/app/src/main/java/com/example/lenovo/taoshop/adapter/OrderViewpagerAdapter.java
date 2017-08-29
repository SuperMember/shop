package com.example.lenovo.taoshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

public class OrderViewpagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> mTitle = new ArrayList<>();

    public OrderViewpagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
        mTitle.add("全部");
        mTitle.add("已付款");
        mTitle.add("未发货");
        mTitle.add("已发货");
        mTitle.add("交易完成");
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
