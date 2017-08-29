package com.example.lenovo.taoshop.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.TbItemCat;

import java.util.List;

import butterknife.Bind;

/**
 * Created by lenovo on 2017  五月  12  0012.
 */

public class ClassifyPopupWindow extends BasePopupWindow {
    private FlowLayout flowlayout;
    private Context context;
    private View view;
    private OnViewClickListener listener;

    public ClassifyPopupWindow(Context context) {
        super();
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_item, null);
        setContentView(view);
        initView();
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F6F6F6")));
    }

    public void initView() {
        flowlayout = (FlowLayout) view.findViewById(R.id.flowlayout);
    }

    public void show(View view, int x, int y, int id) {
        //reSet();
       // Button button = (Button) flowlayout.findViewById(id);
       // button.setBackgroundResource(R.drawable.btn_bg_classify_selected);//标记
        showAsDropDown(view, x, y);
    }

    //重新设置按钮的背景
    /*public void reSet() {
        List<View> list = flowlayout.getAllView();
        for (int i = 0; i < list.size(); i++) {
            Button button = (Button) list.get(i);
            button.setBackgroundResource(R.drawable.btn_bg_classify_normal);
        }
    }*/

    public void setDatas(List<TbItemCat> tabs) {
        flowlayout.removeallview();
        for (int i = 0; i < tabs.size(); i++) {
            final Button button = new Button(context);
            button.setText(tabs.get(i).getName());
            button.setTag(tabs.get(i).getId());
            button.setId(i);
            button.setBackgroundResource(R.drawable.btn_classify);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick((Long) v.getTag(), button.getText().toString().trim(), v.getId());//返回所点击按钮的分类id
                        dismiss();
                    }
                }
            });
            flowlayout.setChildView(button, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public interface OnViewClickListener {
        void onClick(Long msg, String name, int resId);
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        this.listener = listener;
    }

}
