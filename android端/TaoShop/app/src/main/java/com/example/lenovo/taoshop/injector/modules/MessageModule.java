package com.example.lenovo.taoshop.injector.modules;

import com.example.lenovo.taoshop.mvp.present.MessagePresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  26  0026.
 */
@Module
public class MessageModule {
    @Provides
    public MessagePresent provideMessagePresent() {
        return new MessagePresent();
    }
}
