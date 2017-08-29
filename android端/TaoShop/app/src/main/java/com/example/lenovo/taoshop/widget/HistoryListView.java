package com.example.lenovo.taoshop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lenovo on 2017  二月  06  0006.
 */

public class HistoryListView extends ListView {
    public HistoryListView(Context context) {
        super(context);
    }

    public HistoryListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HistoryListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
