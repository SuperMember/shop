package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.SearchActivity;
import com.example.lenovo.taoshop.injector.modules.SearchModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  07  0007.
 */
@Component(modules = SearchModule.class)
public interface SearchComponent {
    void inject(SearchActivity activity);
}
