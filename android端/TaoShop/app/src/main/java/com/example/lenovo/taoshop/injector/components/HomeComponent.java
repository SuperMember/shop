package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.HomeItemDetailActivity;
import com.example.lenovo.taoshop.fragments.HomeFragment;
import com.example.lenovo.taoshop.injector.modules.HomeModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */
@Component(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);

    void inject(HomeItemDetailActivity activity);
}
