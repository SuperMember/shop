package com.example.lenovo.taoshop.injector.modules;

import com.example.lenovo.taoshop.mvp.present.HomePresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

@Module
public class HomeModule {
    @Provides
    public HomePresent provideHomePresent() {
        return new HomePresent();
    }
}
