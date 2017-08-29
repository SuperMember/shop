package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  10  0010.
 */

public class ViewPagerFragmentClassifyAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> list;
    private List<String> mTitle;

    public ViewPagerFragmentClassifyAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
        initTitle();
    }

    private void initTitle() {
        mTitle = new ArrayList<>();
        mTitle.add("家用电器");
        mTitle.add("电脑/办公");
        mTitle.add("手机");
        mTitle.add("数码");
        mTitle.add("服饰");
        mTitle.add("运动健康");
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
