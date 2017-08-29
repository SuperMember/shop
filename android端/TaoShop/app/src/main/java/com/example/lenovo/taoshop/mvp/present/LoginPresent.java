package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.mvp.view.ILoginView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

public class LoginPresent extends BasePresent<ILoginView> {
    public LoginPresent() {
    }

    public void doLogin(String username, String password) {
        addSubscription(ApiService.getInstanceUser().doLogin(username, password, 1).observeOn(AndroidSchedulers.mainThread()
        ).subscribeOn(Schedulers.io())
                .subscribe(new Action1<TaoTaoResult>() {
                    @Override
                    public void call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            myView.login((String) taoTaoResult.getData());
                        } else {
                            myView.unsuccess(taoTaoResult.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.unsuccess(throwable.getMessage());
                    }
                }));
    }
}
