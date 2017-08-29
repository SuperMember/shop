package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.app.MyApplication;

import java.util.List;

/**
 * Created by lenovo on 2017  六月  04  0004.
 */

public class GridViewIndexAdapter extends BaseAdapter {
    private List<String> mTitle;
    private Context context;
    private OnItemIndexClickListener listener;
    private int[] colors = {R.mipmap.icon_index_news, R.mipmap.icon_index_recommand, R.mipmap.icon_index_hot,
            R.mipmap.icon_index_phone, R.mipmap.icon_index_com, R.mipmap.icon_index_customs,
            R.mipmap.icon_index_home, R.mipmap.icon_index_classify};

    public GridViewIndexAdapter(List<String> mTitle, Context context) {
        this.mTitle = mTitle;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mTitle.size();
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_index_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置标题
        viewHolder.setTextView(mTitle.get(position));
        //设置背景
        viewHolder.setImageView(colors[position]);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position, mTitle.get(position));
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.text_item);
            imageView = (ImageView) itemView.findViewById(R.id.circle_bg);
        }

        public void setTextView(String title) {
            textView.setText(title);
        }

        public void setImageView(int res) {
            imageView.setImageResource(res);
        }
    }

    public interface OnItemIndexClickListener {
        void onClick(int position, String title);
    }

    public void setOnItemIndexClickListener(OnItemIndexClickListener listener) {
        this.listener = listener;
    }
}
