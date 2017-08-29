package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.AddressActivity;
import com.example.lenovo.taoshop.EditAddrActivity;
import com.example.lenovo.taoshop.LoginActivity;
import com.example.lenovo.taoshop.RegisterActivity;
import com.example.lenovo.taoshop.injector.modules.UserModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  02  0002.
 */
@Component(modules = UserModule.class)
public interface UserComponent {
    void inject(RegisterActivity activity);

    void inject(LoginActivity activity);

    void inject(EditAddrActivity addressActivity);
}
