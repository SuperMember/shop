package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  25  0025.
 */

public class ViewPagerMessageAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<String> mTitle = new ArrayList<>();
    private List<Fragment> mFragment;

    public ViewPagerMessageAdapter(FragmentManager fm, Context context, List<Fragment> mFragment) {
        super(fm);
        this.context = context;
        mTitle.add("通知消息");
        mTitle.add("互动消息");
        this.mFragment = mFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
