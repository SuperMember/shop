package com.example.lenovo.taoshop.fragments.base;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.BasePresent;
import com.example.lenovo.taoshop.mvp.view.BaseView;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public abstract class BaseFragment<T extends BasePresent> extends Fragment implements BaseView {
    /**
     * 注意，资源的ID一定要一样
     */
    @Nullable
    @Bind(R.id.empty_layout)
    EmptyLayout mEmptyLayout;

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    protected Context mContext;
    protected View mRootView;
    private boolean mIsMulti = false;
    protected boolean isVisible = false;

    @Inject
    public T myPresent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayout(), null);
            ButterKnife.bind(this, mRootView);
            initInjector();
            initView();
            initToolbar();
            setListener();
            updateView();
        }
        //防止共用一个fragment
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateView();
        }
    }*/

  /*  @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateView();
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }*/

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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }

    /*
    * 注入present
    * */
    protected abstract void initInjector();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*
    *   初始化
    * */
    protected abstract void initView();

    /*
    * 设置监听事件
    * */
    protected abstract void setListener();

    /*
    *设置布局文件
    * */
    protected abstract int getLayout();

    /*
    * 更新布局
    * */
    protected abstract void updateView();


    /*
    * 添加toolbar
    * */
    protected abstract void initToolbar();

    /*
    * 界面开始渲染
    * */
    @Override
    public void start() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);

        }
    }

    /*
    * 界面结束渲染
    * */

    @Override
    public void end() {
        if (mEmptyLayout != null) {
            mEmptyLayout.hide();
        }
    }

    /*
    * 渲染失败
    * */

    @Override
    public void error(EmptyLayout.OnRetryListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(onRetryListener);
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
    public void nodata() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_DATA);
        }
    }

    private Handler mHandler = new Handler();

    public void showToast(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
    * 获取控件
    * */
    protected <T extends View> T getView(@IdRes int Id) {
        return (T) mRootView.findViewById(Id);
    }
}
