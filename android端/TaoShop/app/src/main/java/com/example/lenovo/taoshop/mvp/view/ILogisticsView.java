package com.example.lenovo.taoshop.mvp.view;


import com.example.lenovo.taoshop.bean.common.AreaEntity;
import com.example.lenovo.taoshop.bean.common.Data;
import com.example.lenovo.taoshop.bean.common.LogisticsMultiEntity;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  05  0005.
 */

public interface ILogisticsView extends BaseView {
    void get(List<LogisticsMultiEntity> list);

    void empty();

    void fail(String msg);

    void getArea(AreaEntity areaEntity);
}
