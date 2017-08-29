package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.GoodDetailActivity;
import com.example.lenovo.taoshop.fragments.ClassifyFragmentItem;
import com.example.lenovo.taoshop.fragments.ClassifyFragmentItemTab;
import com.example.lenovo.taoshop.injector.modules.GoodModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  08  0008.
 */

@Component(modules = GoodModule.class)
public interface GoodComponent {
    void inject(GoodDetailActivity activity);
    void inject(ClassifyFragmentItem fragmentItem);
    void inject(ClassifyFragmentItemTab fragmentItemTab);
}
