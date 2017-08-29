package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.OrderDefActivity;
import com.example.lenovo.taoshop.fragments.OrderFragment;
import com.example.lenovo.taoshop.injector.modules.OrderModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

@Component(modules = OrderModule.class)
public interface OrderComponent {
    void inject(OrderFragment fragment);
    void inject(OrderDefActivity activity);
}
