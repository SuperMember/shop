package com.example.lenovo.taoshop.mvp.view;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

public interface ILoginView extends BaseView {
    void login(String token);

    void unsuccess(String msg);
}
