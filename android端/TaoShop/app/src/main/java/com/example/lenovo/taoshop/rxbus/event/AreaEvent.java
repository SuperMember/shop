package com.example.lenovo.taoshop.rxbus.event;

import com.example.lenovo.taoshop.bean.common.AreaEntity;

/**
 * Created by lenovo on 2017  五月  21  0021.
 */

public class AreaEvent {
    private String area;
    public AreaEvent(String area) {
        this.area=area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
