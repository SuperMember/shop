package com.example.lenovo.taoshop.rxbus.event;

import android.support.annotation.IntDef;
import android.view.View;
import android.widget.ImageView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lenovo on 2017  五月  14  0014.
 */


//购物车事件处理
public class BuyEvent {
    public static final int ADD_NUM = 301;
    public static final int DEL_NUM = 302;

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({ADD_NUM, DEL_NUM})
    public @interface BuyEventType {
    }

    private int type;
    private ImageView view;

    public BuyEvent(@BuyEventType int type, ImageView view) {
        this.type = type;
        this.view = view;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
