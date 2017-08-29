package com.example.lenovo.taoshop.mvp.view;

import com.example.lenovo.taoshop.bean.common.GoodsListItem;
import com.example.lenovo.taoshop.bean.common.OrderItem;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

public interface IOrderView extends BaseView {
    void loadData(List<OrderItem> list);

    void loadMoreData(List<OrderItem> list);

    void loadNoData();

    void loadNoMoreData();

    void failure(String msg);

    void submit(String msg);
}
