package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.AreaActivity;
import com.example.lenovo.taoshop.LogisticsActivity;
import com.example.lenovo.taoshop.injector.modules.LogisticsModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  05  0005.
 */

@Component(modules = LogisticsModule.class)
public interface LogisticsComponent {
    void inject(LogisticsActivity activity);

    void inject(AreaActivity areaActivity);
}
