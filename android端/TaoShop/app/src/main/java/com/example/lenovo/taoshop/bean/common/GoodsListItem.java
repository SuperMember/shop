package com.example.lenovo.taoshop.bean.common;

import java.util.List;

public class GoodsListItem {
    private int total;
    private List<OrderItem> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderItem> getRows() {
        return rows;
    }

    public void setRows(List<OrderItem> rows) {
        this.rows = rows;
    }

}
