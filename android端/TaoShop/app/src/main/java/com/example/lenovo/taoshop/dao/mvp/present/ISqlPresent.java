package com.example.lenovo.taoshop.dao.mvp.present;

import com.example.lenovo.taoshop.bean.common.TbItemResult;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public interface ISqlPresent<T> {
    void insert(T item);

    void delete(long id);

    void update(T item);

    void select();

    int getCount();
}
