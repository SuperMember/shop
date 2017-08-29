package com.example.lenovo.taoshop.injector.modules;

import com.example.lenovo.taoshop.mvp.present.ClassifyPresent;
import com.example.lenovo.taoshop.mvp.present.GoodPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  08  0008.
 */

@Module
public class GoodModule {
    @Provides
    public GoodPresent provideGoodPresent() {
        return new GoodPresent();
    }

    @Provides
    public ClassifyPresent provideClassifyPresent() {
        return new ClassifyPresent();
    }
}
