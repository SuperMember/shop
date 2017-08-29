package com.example.lenovo.taoshop.bean.common;

import com.example.lenovo.taoshop.mvp.present.MessagePresent;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  28  0028.
 */

public class OrderMessageList extends MessagePresent {
    private List<TbOrderMsg> rows;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TbOrderMsg> getRows() {
        return rows;
    }

    public void setRows(List<TbOrderMsg> rows) {
        this.rows = rows;
    }
}
