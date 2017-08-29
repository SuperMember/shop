package com.example.lenovo.taoshop.injector.modules;

import com.example.lenovo.taoshop.mvp.present.InfoPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  六月  01  0001.
 */

@Module
public class InfoModule {
    @Provides
    public InfoPresent provideInfoPresent() {
        return new InfoPresent();
    }
}
