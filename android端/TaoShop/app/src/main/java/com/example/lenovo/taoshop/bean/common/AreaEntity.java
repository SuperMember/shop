package com.example.lenovo.taoshop.bean.common;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  21  0021.
 */

public class AreaEntity<T> {
    private String type;
    private List<T> list;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
