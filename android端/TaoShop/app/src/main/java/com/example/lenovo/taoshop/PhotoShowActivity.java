package com.example.lenovo.taoshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lenovo.taoshop.adapter.PhotoSetAdapter;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.widget.PhotoViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lenovo on 2017  六月  05  0005.
 */

public class PhotoShowActivity extends BaseActivity {
    private static final String PHOTO_SET_KEY = "PhotoSetKey";
    private static final String PHOTO_SET_POSITION = "PhotoPosition";
    @Bind(R.id.vp_photo)
    PhotoViewPager vpPhoto;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private List<String> imgUrls;
    private int position = 0;
    private PhotoSetAdapter mAdapter;

    public static void launch(Context context, List<String> photos, int position) {
        String[] urls = (String[]) photos.toArray(new String[photos.size()]);
        Intent intent = new Intent(context, PhotoShowActivity.class);
        intent.putExtra(PHOTO_SET_KEY, urls);
        intent.putExtra(PHOTO_SET_POSITION, position);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        getIntentData();
        initToolbar("", true, true, toolbar);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_photoshow;
    }

    @Override
    protected void updateView() {
        mAdapter = new PhotoSetAdapter(this, imgUrls);
        vpPhoto.setAdapter(mAdapter);
        vpPhoto.setOffscreenPageLimit(imgUrls.size());
        vpPhoto.setCurrentItem(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getIntentData() {
        String[] images = getIntent().getStringArrayExtra(PHOTO_SET_KEY);
        imgUrls = Arrays.asList(images);
        position = getIntent().getIntExtra(PHOTO_SET_POSITION, 0);
    }
}
