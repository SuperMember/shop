package com.example.lenovo.taoshop.mvp.view;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  26  0026.
 */

public interface IMessageView<T> extends BaseView {
    void fail(String msg);

    void load(List<T> list);

    void loadMore(List<T> list);

    void noMore();

    void empty();
}
