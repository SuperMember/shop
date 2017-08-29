package com.example.lenovo.taoshop.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.mvp.present.BasePresent;
import com.example.lenovo.taoshop.mvp.view.BaseView;
import com.example.lenovo.taoshop.widget.EmptyLayout;


import java.util.List;


import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseActivity<T extends BasePresent> extends AppCompatActivity implements BaseView {
    @Inject
    public T myPresent;
    @Nullable
    @Bind(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;

    private Handler mHandler = new Handler();
    /*
    * 当前fragment
    * */
    private Fragment mCurrentFrgment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        inject();
        initView();
        setListener();
        updateView();

    }

    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    //获取状态栏高度
    public int getStatusHeight() {
        int statusBarHeight = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /*
    * 页面跳转
    * */
    public void invoke(boolean isFinish, Intent intent) {
        startActivity(intent);
        if (isFinish) {
            finish();
        }
        overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
    }

    /*
    * 设置监听事件
    * */
    protected abstract void setListener();

    public void showToast(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
    * 初始化toolbar
    * */
    public void initToolbar(String title, boolean homeAsUpEnabled, boolean HomeButtonEnabled, Toolbar toolbar) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeAsUpEnabled);
            actionBar.setHomeButtonEnabled(HomeButtonEnabled);
        }
    }


    /*
    * 切换fragment
    * */
    public void changeFragment(int index, @IdRes int framelayoutId, List<Fragment> list) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFrgment) {
            ft.hide(mCurrentFrgment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(list.get(index).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = list.get(index);
        }
        mCurrentFrgment = fragment;

        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(framelayoutId, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }

    //注入present
    protected abstract void inject();

    /*
    * 初始化布局
    * */
    protected abstract void initView();

    /*
    * 引入布局文件
    * */
    protected abstract int getLayout();

    /*
    * 更新布局
    * */
    protected abstract void updateView();

    /*
    * 视图开始渲染
    * */
    @Override
    public void start() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    /*
    * 渲染视图结束
    * */
    @Override
    public void end() {
        if (mEmptyLayout != null) {
            mEmptyLayout.hide();
        }
    }

    /*
    * 渲染视图出错
    * */
    @Override
    public void error(EmptyLayout.OnRetryListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(onRetryListener);
        }
    }

    @Override
    public void nodata() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_DATA);
        }
    }

    @Override
    public void nodata(EmptyLayout.OnRetryListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_DATA);
            mEmptyLayout.setRetryListener(onRetryListener);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (myPresent != null) myPresent.detachView();
    }


}
