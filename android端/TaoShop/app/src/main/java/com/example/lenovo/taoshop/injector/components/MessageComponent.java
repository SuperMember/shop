package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.fragments.message.CommentMessageFragment;
import com.example.lenovo.taoshop.fragments.message.OrderMessageFragment;
import com.example.lenovo.taoshop.injector.modules.MessageModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  26  0026.
 */
@Component(modules = MessageModule.class)
public interface MessageComponent {
    void inject(OrderMessageFragment fragment);

    void inject(CommentMessageFragment fragment);
}
