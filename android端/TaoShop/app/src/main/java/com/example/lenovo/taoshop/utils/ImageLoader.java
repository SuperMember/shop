package com.example.lenovo.taoshop.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class ImageLoader {
    public static ImageLoader imageLoader;

    public static ImageLoader getInstance() {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                imageLoader = new ImageLoader();
            }
        }
        return imageLoader;
    }

    //加载图片
    public void displayImage(Context context, String url, ImageView imageview) {
        //这里用glide
        Glide.with(context).load(url).asBitmap().into(imageview);
    }

    //加载图片
    public void displayImage(Context context, String url, ImageView imageView, int defaultId) {
        Glide.with(context).load(url).asBitmap().placeholder(defaultId).into(imageView);
    }

}
