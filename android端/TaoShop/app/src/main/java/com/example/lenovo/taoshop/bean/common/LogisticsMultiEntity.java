package com.example.lenovo.taoshop.bean.common;

import android.support.annotation.IntDef;

import com.dl7.recycler.entity.MultiItemEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lenovo on 2017  五月  06  0006.
 */

public class LogisticsMultiEntity extends MultiItemEntity {
    public static final int TYPE_HEAD = 0x1;
    public static final int TYPE_LOGISTICS_DETAIL = 0x2;
    private boolean header = false;

    public static final int HEADER = 1;
    public static final int CURRENT = 2;
    public static final int BOTTOM = 3;
    private Status status = Status.CURRENT;//默认

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        HEADER, CURRENT, BOTTOM
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    private OrderItem orderItem;
    private Data data;

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public LogisticsMultiEntity(@NewsItemType int itemType) {
        super(itemType);
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_HEAD, TYPE_LOGISTICS_DETAIL})
    public @interface NewsItemType {
    }

}
