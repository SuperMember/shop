package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.TbAddr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  20  0020.
 */

public class ListViewAddrAdapter extends BaseAdapter {
    private List<TbAddr> list;
    private Context context;
    private OnItemClickListener listener;
    private OnCheckBoxClickListener checkBoxClickListener;

    public ListViewAddrAdapter(List<TbAddr> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ListViewAddrAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    //增加items
    public void addItems(List<TbAddr> items) {
        if (items != null) {
            list.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void updateItems(List<TbAddr> items) {
        this.list = items;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_addr_item, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置信息
        viewHolder.tv_addr.setText(list.get(position).getAddr());
        viewHolder.tv_phone.setText(list.get(position).getTel());
        viewHolder.tv_user.setText(list.get(position).getName());
        viewHolder.cb_default.setChecked(list.get(position).getDefaultaddr() == 0 ? false : true);

        //编辑操作
        viewHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(list.get(position));
                }
            }
        });


        //修改默认
        viewHolder.cb_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkBoxClickListener != null) {
                        checkBoxClickListener.onClick(list.get(position));
                    }
                    reSet(position);
                }

            }
        });
        return convertView;
    }

    //检查check是否有点击
    public boolean checkSet() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDefaultaddr() == 1) {
                return true;
            }
        }
        return false;
    }

    //重置check的点击
    public void reSet(int position) {
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(i).setDefaultaddr(1);
            } else {
                list.get(i).setDefaultaddr(0);
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        private TextView tv_user;
        private TextView tv_phone;
        private TextView tv_addr;
        private CheckBox cb_default;
        private TextView tv_edit;

        public ViewHolder(View itemView) {
            tv_user = (TextView) itemView.findViewById(R.id.tv_username);
            tv_addr = (TextView) itemView.findViewById(R.id.tv_addr);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            cb_default = (CheckBox) itemView.findViewById(R.id.cb_default);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
        }
    }

    //编辑点击事件
    public interface OnItemClickListener {
        void onClick(TbAddr tbAddr);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnCheckBoxClickListener {
        void onClick(TbAddr tbAddr);
    }

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener checkBoxClickListener) {
        this.checkBoxClickListener = checkBoxClickListener;
    }
}
