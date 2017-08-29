package com.example.lenovo.taoshop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


/*
* 星星
* */
public class DegreeLinearLayout extends LinearLayout implements View.OnClickListener {
    private List<ImageView> images = new ArrayList<>();
    private OnDegreeClickListener listener;

    public DegreeLinearLayout(Context context) {
        this(context, null);
    }

    public DegreeLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DegreeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            ImageView imageView = (ImageView) getChildAt(i);
            imageView.setTag(i);
            imageView.setOnClickListener(this);
            images.add(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        setClick((int) v.getTag());
        if (listener != null) {
            listener.onClick((int) v.getTag());
        }
    }

    public void setClick(int index) {
        if (index < 0) return;
        for (int i = 0; i < images.size(); i++) {
            if (i <= index) {
                images.get(i).setSelected(true);
            } else {
                images.get(i).setSelected(false);
            }
        }
    }

    public interface OnDegreeClickListener {
        void onClick(int index);
    }

    public void setOnDegreeClickListener(OnDegreeClickListener listener) {
        this.listener = listener;
    }
}
