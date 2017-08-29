package com.example.lenovo.taoshop.mvp.view;

import com.example.lenovo.taoshop.bean.common.ItemDetail;

/**
 * Created by lenovo on 2017  五月  08  0008.
 */

public interface IGoodView extends BaseView {
    void show(ItemDetail itemDetail);

    void fail(String msg);
}
