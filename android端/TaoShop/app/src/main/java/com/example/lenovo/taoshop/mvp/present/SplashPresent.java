package com.example.lenovo.taoshop.mvp.present;



import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.mvp.view.ISplashView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class SplashPresent extends BasePresent<ISplashView> {
    public SplashPresent() {
    }


    //请求图片
    public void getImage() {
        addSubscription(ApiService.getInstanceImage().getImage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        myView.showImage(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.error();
                    }
                }));
    }
}
