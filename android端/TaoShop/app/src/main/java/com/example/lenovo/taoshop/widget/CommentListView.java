package com.example.lenovo.taoshop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.lenovo.taoshop.adapter.ReplyAdapter;
import com.example.lenovo.taoshop.bean.common.TbCommentsReply;


/**
 * Created by yiwei on 16/3/2.
 */
public class CommentListView extends LinearLayout{

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public CommentListView(Context context) {
        this(context,null);
    }

    public CommentListView(Context context, AttributeSet attrs){
        this(context, attrs,0);

    }

    public CommentListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
    }


    public void setAdapter(ReplyAdapter adapter){
        adapter.bindListView(this);
    }

    public void setOnItemClick(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClick(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return mOnItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener(){
        return mOnItemLongClickListener;
    }


    public static interface OnItemClickListener{
        public void onItemClick(TbCommentsReply tbCommentsReply,int index);
    }

    public static interface OnItemLongClickListener{
        public void onItemLongClick(int position);
    }
}
