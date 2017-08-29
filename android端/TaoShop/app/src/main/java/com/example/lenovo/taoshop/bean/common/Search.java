package com.example.lenovo.taoshop.bean.common;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  07  0007.
 */

public class Search {
    private List<ItemList> itemList;

    private int total;

    private int pageCount;

    private int curPage;

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }

    public List<ItemList> getItemList() {
        return this.itemList;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getCurPage() {
        return this.curPage;
    }
}
