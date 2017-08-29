package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.AreaEntity;
import com.example.lenovo.taoshop.bean.common.TbArea;
import com.example.lenovo.taoshop.bean.common.TbCity;
import com.example.lenovo.taoshop.bean.common.TbProvince;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  21  0021.
 */

public class ListViewAreaAdapter<T> extends BaseAdapter {
    private List<T> list;
    private Context context;
    private OnItemClickListener listener;
    private String type;

    public ListViewAreaAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ListViewAreaAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void updateItems(List<T> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    public void addItems(List<T> items, String type) {
        list.addAll(items);
        this.type = type;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_area_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name;
        Integer Id;
        if (type.equals("province")) {
            TbProvince tbProvince = (TbProvince) list.get(position);
            name = tbProvince.getProvince();
            Id = Integer.parseInt(tbProvince.getProvinceid());
        } else if (type.equals("city")) {
            TbCity tbCity = (TbCity) list.get(position);
            name = tbCity.getCity();
            Id = Integer.parseInt(tbCity.getCityid());
        } else {
            TbArea tbArea = (TbArea) list.get(position);
            name = tbArea.getArea();
            Id = Integer.parseInt(tbArea.getAreaid());
        }

        final String n = name;
        final Integer id = Id;
        viewHolder.setTextView(name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.Onclick(id, n);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void setTextView(String text) {
            textView.setText(text);
        }
    }

    public interface OnItemClickListener {
        void Onclick(Integer id, String name);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
