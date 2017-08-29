package com.example.lenovo.taoshop.mvp.view;

import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.Search;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  07  0007.
 */

public interface ISearchView extends BaseView {
    void getData(List<ItemList> list);

    void getMore(List<ItemList> list);

    void noMoredata();

    void fail();

    void empty();
}
