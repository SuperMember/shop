package com.example.lenovo.taoshop.injector.modules;

import com.example.lenovo.taoshop.mvp.present.LoginPresent;
import com.example.lenovo.taoshop.mvp.present.RegisterPresent;
import com.example.lenovo.taoshop.mvp.present.UserPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  02  0002.
 */

@Module
public class UserModule {
    @Provides
    public RegisterPresent provideRegisterPresent() {
        return new RegisterPresent();
    }

    @Provides
    public LoginPresent provideLoginPresent() {
        return new LoginPresent();
    }

    @Provides
    public UserPresent provideUserPresent() {
        return new UserPresent();
    }
}
