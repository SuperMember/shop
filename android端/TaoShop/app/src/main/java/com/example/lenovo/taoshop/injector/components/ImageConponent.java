package com.example.lenovo.taoshop.injector.components;



import com.example.lenovo.taoshop.injector.modules.ImageModule;
import com.example.lenovo.taoshop.SplashActivity;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

@Component(modules = ImageModule.class)
public interface ImageConponent {
    void inject(SplashActivity activity);
}
