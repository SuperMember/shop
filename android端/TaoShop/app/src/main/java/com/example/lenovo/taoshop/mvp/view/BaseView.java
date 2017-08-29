package com.example.lenovo.taoshop.mvp.view;


import com.example.lenovo.taoshop.widget.EmptyLayout;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public interface BaseView {
    /*
    * 开始渲染视图
    * */
    void start();

    /*
    * 渲染视图结束
    * */
    void end();

    /*
    * 出错
    * */
    void error(EmptyLayout.OnRetryListener onRetryListener);

    /*
    * 没有数据
    * */
    void nodata();

    /*
    * 没有数据允许刷新
    * */
    void nodata(EmptyLayout.OnRetryListener onRetryListener);
}
