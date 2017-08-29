package com.example.lenovo.taoshop.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/*
* textview显示图片
* */
public class HtmlTextView extends TextView {
    private SpannableStringBuilder imageClickable;
    private ImageSpan[] mImageSpans;
    private String[] mImageUrls;
    private OnImageClick listener;//响应图片点击

    public HtmlTextView(Context context) {
        super(context);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHtmlFromString(String html, List<String> url) {
        Html.ImageGetter imgGetter;
        imgGetter = new UrlImageGetter(this, getContext());

        //清空数据
        setText("");
        //Html.fromHtml:参数一是文本，参数二一遇到img标签则用该参数getDrawable返回的图片取代
        String[] s = html.split("/>");
        String newstring = "";
        //修改路径，换成服务器返回的地址
        if (url != null && url.size() != 0) {
            for (int j = 0, i = 0; j < s.length; j++) {
                if (s[j].contains("<img src") && !s[j].contains("R.raw.f") && i < url.size()) {
                    s[j] = s[j].replaceAll("<img src=\"[/\\w+/]+(.*).jpg(.[0-9]+)(.[0-9]+)\"\\s*", "<img src='" + url.get(i) + "'/>");
                    i++;
                } else if (s[j].contains("R.raw.f") && s[j].contains("<img src")) {
                    s[j] = s[j] + "/>";
                }

                newstring = newstring + s[j];

            }
            SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) Html.fromHtml(newstring, imgGetter, null);
            //设置图片可以点击
            setImageClickable(spannableStringBuilder);
            append(spannableStringBuilder);
        } else {
            append(Html.fromHtml(html, imgGetter, null));
        }
        // 设置可以点击
        setMovementMethod(LinkMovementMethod.getInstance());
        //  text.setTextColor(getResources().getColor(android.R.color.secondary_text_dark_nodisable));
    }

    //设置图片可以点击
    public void setImageClickable(SpannableStringBuilder spannableStringBuilder) {
        mImageSpans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ImageSpan.class);

        mImageUrls = new String[mImageSpans.length];
        for (int i = 0; i < mImageSpans.length; i++) {
            mImageUrls[i] = mImageSpans[i].getSource();
        }
        for (int i = 0; i < mImageSpans.length; i++) {

            int start = spannableStringBuilder.getSpanStart(mImageSpans[i]);
            int end = spannableStringBuilder.getSpanEnd(mImageSpans[i]);
            final int finalI = i;
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (listener != null) listener.onClick(mImageUrls,finalI);
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }


    static class UrlImageGetter implements Html.ImageGetter {
        Context c;
        TextView container;
        int width;

        public UrlImageGetter(TextView t, Context c) {
            this.c = c;
            this.container = t;
            width = c.getResources().getDisplayMetrics().widthPixels;
        }

        @Override
        public Drawable getDrawable(final String source) {
            final UrlDrawable urlDrawable = new UrlDrawable();
            int index=source.lastIndexOf("?");

            final String ss;
            if(index!=-1){
                ss=source.substring(0, index);
            }else{
                ss=source;
            }
            System.out.println(ss);
            //处理表情

            return urlDrawable;
        }

        @SuppressWarnings("deprecation")
        public class UrlDrawable extends BitmapDrawable {
            protected Bitmap bitmap;

            @Override
            public void draw(Canvas canvas) {
                // override the draw to facilitate refresh function later
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, 0, 0, getPaint());
                }
            }
        }
    }

    //图片点击
    public interface OnImageClick {
        void onClick(String[] url, int position);
    }

    public void setOnImageClick(OnImageClick listener) {
        this.listener = listener;
    }
}
