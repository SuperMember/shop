package com.example.lenovo.taoshop.mvp.view;

import com.example.lenovo.taoshop.bean.common.TbItem;
import com.example.lenovo.taoshop.bean.common.TbItemCat;
import com.example.lenovo.taoshop.bean.common.TbItemCatResult;
import com.example.lenovo.taoshop.bean.common.TbItemResult;

import org.jsoup.Connection;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  10  0010.
 */

public interface IClassifyView extends BaseView {

    void get(List<TbItemResult> tbItems);

    void fail(String msg);

    void getMore(List<TbItemResult> tbItems);

    void noMore();

    void empty();

    void getClassify(List<TbItemCatResult> list);
}
