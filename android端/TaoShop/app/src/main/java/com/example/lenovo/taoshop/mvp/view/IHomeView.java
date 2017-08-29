package com.example.lenovo.taoshop.mvp.view;


import com.example.lenovo.taoshop.bean.common.AdData;
import com.example.lenovo.taoshop.bean.common.AdItem;
import com.example.lenovo.taoshop.bean.common.IndexMulEntity;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public interface IHomeView extends BaseView {
    void loadAdData(List<AdItem> list);

    void load(List<IndexMulEntity> list);

    void fail(String msg);

    void loadMore(List<IndexMulEntity> list);

    void noMore();
}
