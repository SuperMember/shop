package com.example.lenovo.taoshop.injector.modules;


import com.example.lenovo.taoshop.mvp.present.BasePresent;
import com.example.lenovo.taoshop.mvp.present.SplashPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

@Module
public class ImageModule {
    @Provides
    public SplashPresent provideSplashPresent() {
        return new SplashPresent();
    }
}
