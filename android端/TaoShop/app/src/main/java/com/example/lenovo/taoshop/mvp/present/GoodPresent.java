package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.ItemDetail;
import com.example.lenovo.taoshop.mvp.view.IGoodView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  08  0008.
 */

public class GoodPresent extends BasePresent<IGoodView> {
    public GoodPresent() {
    }

    public void getDetail(long itemId) {
        addSubscription(ApiService.getInstanceGood().getDetail(itemId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<ItemDetail>() {
            @Override
            public void call(ItemDetail itemDetail) {
                myView.show(itemDetail);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                myView.fail(throwable.getMessage());
            }
        }));
    }
}
