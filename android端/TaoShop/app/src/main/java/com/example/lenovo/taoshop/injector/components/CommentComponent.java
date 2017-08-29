package com.example.lenovo.taoshop.injector.components;

import com.example.lenovo.taoshop.CommentsActivity;
import com.example.lenovo.taoshop.OrderCommentActivity;
import com.example.lenovo.taoshop.injector.modules.CommentModule;

import dagger.Component;

/**
 * Created by lenovo on 2017  五月  09  0009.
 */
@Component(modules = CommentModule.class)
public interface CommentComponent {
    void inject(CommentsActivity activity);

    void inject(OrderCommentActivity activity);
}
