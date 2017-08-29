package com.example.lenovo.taoshop.dao.mvp.view;

import com.example.lenovo.taoshop.bean.common.BuyGoods;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.mvp.view.BaseView;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public interface ISqlView extends BaseView {
    void show(List<BuyGoods> list);

    void empty();

    void error(String msg);
}
