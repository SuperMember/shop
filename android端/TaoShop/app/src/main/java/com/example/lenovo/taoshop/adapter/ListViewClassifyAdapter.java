package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.TbItemCatResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  14  0014.
 */

public class ListViewClassifyAdapter extends BaseAdapter {
    private Context context;
    private List<TbItemCatResult> list;
    private OnItemClickListener listener;
    private static int currentItemId = 0;

    public ListViewClassifyAdapter(Context context, List<TbItemCatResult> list) {
        this.context = context;
        this.list = list;
    }

    public ListViewClassifyAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void addItems(List<TbItemCatResult> strings) {
        this.list.addAll(list.size(), strings);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recyclerview_classify_left_item, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置点击之后的选择状态
        if (position == currentItemId) {
            viewHolder.button.setBackgroundResource(R.drawable.btn_listview_classify_selected);
            viewHolder.button.setTextColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.button.setBackgroundResource(R.drawable.btn_listview_classify_normal);
            viewHolder.button.setTextColor(Color.parseColor("#000000"));
        }
        viewHolder.setText(list.get(position).getTbItemCat().getName().charAt(0) + "");
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(list.get(position));
                    currentItemId = position;
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private Button button;

        public ViewHolder(View itemView) {
            button = (Button) itemView.findViewById(R.id.text_classify_name);
        }

        public void setText(String msg) {
            button.setText(msg);
        }
    }

    public interface OnItemClickListener {
        void onClick(TbItemCatResult tbItemCatResult);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
