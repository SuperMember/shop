package com.example.lenovo.taoshop.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.User;

/**
 * Created by lenovo on 2017  五月  18  0018.
 */

public class UserPopupWindow extends BasePopupWindow implements View.OnClickListener {
    private Context context;
    private View view;
    private TextView tv_portrait;
    private Button btn_logout;
    private OnItemClickListener listener;

    public UserPopupWindow(Context context) {
        super();
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_portrait_set_style, null);
        setContentView(view);
        initView();
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F6F6F6")));
    }

    private void initView() {
        tv_portrait = (TextView) view.findViewById(R.id.text_take_photo);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        setListener();
    }

    private void setListener() {
        tv_portrait.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_take_photo:
                if (listener != null) listener.onClick(0);
                break;
            case R.id.btn_logout:
                if (listener != null) listener.onClick(1);
                break;
        }
    }

    public interface OnItemClickListener {
        void onClick(int index);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
