package com.example.lenovo.taoshop.injector.modules;

import com.example.lenovo.taoshop.mvp.present.CommentPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lenovo on 2017  五月  09  0009.
 */

@Module
public class CommentModule {
    @Provides
    public CommentPresent provideCommentPresent() {
        return new CommentPresent();
    }
}
