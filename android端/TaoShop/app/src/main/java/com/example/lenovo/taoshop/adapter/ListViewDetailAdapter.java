package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  09  0009.
 */

public class ListViewDetailAdapter extends BaseAdapter {
    private List<String> images;
    private Context context;
    private OnImageViewClickListener listener;

    public ListViewDetailAdapter(Context context) {
        this.context = context;
        this.images = new ArrayList<>();
    }

    public void addItems(List<String> img) {
        images.addAll(img);
        notifyDataSetChanged();
    }

    public ListViewDetailAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_img_item, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageView imageView = (ImageView) viewHolder.getItemView(R.id.img_goods);
        ImageLoader.getInstance().displayImage(context, images.get(position), imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(images, position);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        private View itemView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public View getItemView(int id) {
            return itemView.findViewById(id);
        }
    }

    public interface OnImageViewClickListener {
        void onClick(List<String> urls, int position);
    }

    public void setOnImageViewClickListener(OnImageViewClickListener listener) {
        this.listener = listener;
    }
}
