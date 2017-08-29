package com.example.lenovo.taoshop.injector.modules;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.example.lenovo.taoshop.OrderActivity;
import com.example.lenovo.taoshop.adapter.OrderRecyclerViewAdapter;
import com.example.lenovo.taoshop.mvp.present.OrderPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

@Module
public class OrderModule {


    @Provides
    public OrderPresent provideOrderPresent() {
        return new OrderPresent();
    }

}
