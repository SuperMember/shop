package com.example.lenovo.taoshop.app;

import android.app.Application;
import android.content.Context;


/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
