package com.example.lenovo.taoshop.mvp.view;

import com.example.lenovo.taoshop.bean.common.TbCommentsResult;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  09  0009.
 */

public interface ICommentView extends BaseView {
    void show(List<TbCommentsResult> list);

    void fail(String msg);

    void getMore(List<TbCommentsResult> list);

    void noMore();

    void empty();

    void submit(String msg);
}
