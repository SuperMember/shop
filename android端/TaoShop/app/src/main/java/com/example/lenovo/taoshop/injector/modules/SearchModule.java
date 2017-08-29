package com.example.lenovo.taoshop.injector.modules;

import com.example.lenovo.taoshop.mvp.present.SearchPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  07  0007.
 */

@Module
public class SearchModule {
    public SearchModule() {
    }

    @Provides
    public SearchPresent provideSearchPresent() {
        return new SearchPresent();
    }
}
