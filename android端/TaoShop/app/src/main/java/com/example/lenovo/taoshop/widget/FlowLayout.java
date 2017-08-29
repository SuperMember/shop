package com.example.lenovo.taoshop.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.lenovo.taoshop.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;


public class FlowLayout extends ViewGroup {
    private static final int TEXT_H_SPACING = 20;//文字宽度之间的间距
    private static final int TEXT_V_SPACING = 10;//文字高度之间的间距
    private int text_h_spcing = TEXT_H_SPACING;
    private int text_v_spcing = TEXT_V_SPACING;
    private List<View> mChildView = new ArrayList<>();//存放孩子view
    private OnFlowLayoutItemClickListener listener;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        //  TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShowHot);
        // text_h_spcing = (int) a.getDimension(R.styleable.ShowHot_text_h_spcing, TEXT_H_SPACING);
        // text_v_spcing = (int) a.getDimension(R.styleable.ShowHot_text_v_spcing, TEXT_V_SPACING);

        //  a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);


        int itemheight = 0;
        int itemwidth;
        int maxheight = 0;
        int maxwidth = 0;
        int width = 0;
        int height = 0;
        int i;
        //测量父view的宽度和高度
        for (i = 0; i < mChildView.size(); i++) {
            View v = mChildView.get(i);
            measureChild(v, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) v.getLayoutParams();
            itemwidth = v.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + text_h_spcing;
            itemheight = v.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + text_v_spcing;


            if (maxwidth + itemwidth > widthsize) {
                height = height + maxheight;
                width = Math.max(maxwidth, width);
                maxwidth = itemwidth;
                maxheight = itemheight;
            } else {
                maxwidth = maxwidth + itemwidth;
                maxheight = Math.max(maxheight, itemheight);
            }
        }

        if (i == getChildCount()) {
            width = Math.max(width, maxwidth);
            height = height + Math.max(maxheight, itemheight);
        }

        //设置父布局的宽度和高度
        setMeasuredDimension(widthmode == MeasureSpec.EXACTLY ? widthsize : width - getPaddingRight() - getPaddingLeft(),
                heightmode == MeasureSpec.EXACTLY ? heightsize : height - getPaddingTop() - getPaddingBottom());
    }

    private List<View> mView = new ArrayList<>();
    private List<List<View>> mList = new ArrayList<>();
    private List<Integer> maxHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //注意一定要先clear
        mView.clear();
        mList.clear();
        maxHeight.clear();
        int itemwidth;
        int itemheight;
        int width = 0;
        int height = 0;
        int i;
        for (i = 0; i < mChildView.size(); i++) {
            View v = mChildView.get(i);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) v.getLayoutParams();
            itemwidth = v.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + text_h_spcing;
            itemheight = v.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + text_v_spcing;
            if (itemwidth + width > getWidth()) {

                mList.add(mView);
                width = 0;
                maxHeight.add(height);
                height = itemheight;
                mView = new ArrayList<>();
            }

            height = Math.max(height, itemheight);
            width = width + itemwidth;

            mView.add(v);
        }

        mList.add(mView);
        maxHeight.add(height);

        int top = 0;
        int left = 0;
        //布局
        for (int j = 0; j < maxHeight.size(); j++) {
            List<View> lineview = mList.get(j);
            for (int k = 0; k < lineview.size(); k++) {
                View v = mList.get(j).get(k);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) v.getLayoutParams();
                itemwidth = v.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                itemheight = v.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                v.layout(left, top, itemwidth + left, top + itemheight);
                left = left + itemwidth + text_h_spcing;
            }
            left = 0;
            top = top + maxHeight.get(j);
        }
    }

    public List<View> getAllView() {
        return mChildView;
    }

    public View findChildViewById(@IdRes int id) {
        return mChildView.get(id);
    }

    public void removeallview() {
        for (int i = 0; i < mChildView.size(); i++) {
            this.removeView(mChildView.get(i));
        }
        mChildView = new ArrayList<>();
        requestLayout();
    }

    public void removeView(int position) {
        mChildView.remove(position);
        requestLayout();
    }

    public void setChildView(View v, int width, int height) {
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(width, height);

        this.addView(v, marginLayoutParams);
        mChildView.add(v);
        requestLayout();
    }

    public void setHots(List<String> hots) {
        for (int i = 0; i < hots.size(); i++) {
            MarginLayoutParams marginLayoutParams = new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            marginLayoutParams.rightMargin = 5;
            marginLayoutParams.leftMargin = 5;
            final TextView textView = new TextView(getContext());
            textView.setText(hots.get(i));
            textView.setTextSize(13);
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.drawable.textview_border);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onItemClick(textView.getText().toString());
                }
            });
            this.addView(textView, marginLayoutParams);
            mChildView.add(textView);
        }
        requestLayout();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    //完成布局时获取子view
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);

            mChildView.add(v);
        }
    }

    public interface OnFlowLayoutItemClickListener {
        void onItemClick(String str);
    }

    public void setOnItemClick(OnFlowLayoutItemClickListener listener) {
        this.listener = listener;
    }
}



