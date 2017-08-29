package com.example.lenovo.taoshop.widget;

import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by lenovo on 2017  二月  21  0021.
 */

public abstract class BasePopupWindow extends PopupWindow {
    public BasePopupWindow() {
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        //监听从外部点击popupwindow关闭
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //在外部点击,则关闭弹窗
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }
}
