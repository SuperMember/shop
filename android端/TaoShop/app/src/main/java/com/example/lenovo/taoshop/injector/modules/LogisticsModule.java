package com.example.lenovo.taoshop.injector.modules;

import com.example.lenovo.taoshop.mvp.present.LogisticsPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  05  0005.
 */

@Module
public class LogisticsModule {
    @Provides
    public LogisticsPresent provideLogisticsPresent() {
        return new LogisticsPresent();
    }
}
