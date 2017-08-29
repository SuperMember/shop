package com.example.lenovo.taoshop.mvp.present;


import com.example.lenovo.taoshop.mvp.view.BaseView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class BasePresent<V extends BaseView> {
    protected V myView;
    private CompositeSubscription compositeSubscription;

    //关联view
    public void attachView(V baseView) {
        this.myView = baseView;
    }

    //取消关联
    public void detachView() {
        if (myView != null) {
            myView = null;
        }
        onUnSubscribe();
    }

    //取消注册rxjava，避免内存泄漏
    public void onUnSubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }

    //注册rxjava
    public void addSubscription(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
        }
    }
}
