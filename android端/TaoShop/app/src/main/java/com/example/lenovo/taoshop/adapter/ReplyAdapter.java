package com.example.lenovo.taoshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.bean.common.TbCommentsReply;
import com.example.lenovo.taoshop.spannable.CircleMovementMethod;
import com.example.lenovo.taoshop.widget.CommentListView;
import com.example.lenovo.taoshop.widget.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiwei on 16/3/2.
 */
public class ReplyAdapter {

    private Context mContext;
    private CommentListView mListview;
    private List<TbCommentsReply> mDatas;

    public ReplyAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<TbCommentsReply>();
    }

    public ReplyAdapter(Context context, List<TbCommentsReply> datas) {
        mContext = context;
        setDatas(datas);
    }

    public void bindListView(CommentListView listView) {
        if (listView == null) {
            throw new IllegalArgumentException("CommentListView is null....");
        }
        mListview = listView;
    }

    public void setDatas(List<TbCommentsReply> datas) {
        if (datas == null) {
            datas = new ArrayList<TbCommentsReply>();
        }
        mDatas = datas;
    }

    public List<TbCommentsReply> getDatas() {
        return mDatas;
    }

    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    public TbCommentsReply getItem(int position) {
        if (mDatas == null) {
            return null;
        }
        if (position < mDatas.size()) {
            return mDatas.get(position);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private View getView(final int position) {

        View convertView = View.inflate(mContext,
                R.layout.im_social_item_comment, null);
        HtmlTextView commentTv = (HtmlTextView) convertView.findViewById(R.id.commentTv);
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(R.color.name_selector_color,
                R.color.name_selector_color);

        final TbCommentsReply reply = mDatas.get(position);
        String name = reply.getName();//回复者
        String toReplyName = reply.getReplyname();//被回复者名称
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name, position, 0));//加点击事件

        if (!TextUtils.isEmpty(toReplyName)) {
            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName, position, 1));
        }
        builder.append(": ");
        //转换表情字符
        //获取回复的内容
        String contentBodyStr = reply.getReplycomments();
        builder.append(contentBodyStr);
        commentTv.setText(builder);
        //commentTv.setHtmlFromString(contentBodyStr, null);


        commentTv.setMovementMethod(circleMovementMethod);
        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    // mListview.getOnItemClickListener().onItemClick(mDatas.get(position));
                }
            }
        });
       /* commentTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    mListview.getOnItemLongClickListener().onItemLongClick(position);
                    return true;
                }
                return false;
            }
        });*/
        return convertView;
    }

    @NonNull
    private SpannableString setClickableSpan(final String textStr, final int position, final int index) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        if (mListview.getOnItemClickListener() != null) {
                                            mListview.getOnItemClickListener().onItemClick(mDatas.get(position), index);
                                        }
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public void notifyDataSetChanged() {
        if (mListview == null) {
            throw new NullPointerException("listview is null, please bindListView first...");
        }
        mListview.removeAllViews();
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mDatas.size(); i++) {
            int index = i;
            View view = getView(index);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }
            mListview.addView(view, index, layoutParams);
        }

    }

}
