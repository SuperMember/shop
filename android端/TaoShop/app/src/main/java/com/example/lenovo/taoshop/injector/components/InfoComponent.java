package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.fragments.SelfFragment;
import com.example.lenovo.taoshop.injector.modules.InfoModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  六月  01  0001.
 */
@Component(modules = InfoModule.class)
public interface InfoComponent {
    void inject(SelfFragment fragment);
}
