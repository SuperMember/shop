package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.bean.common.User;
import com.example.lenovo.taoshop.mvp.view.IRegisterView;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  02  0002.
 */

public class RegisterPresent extends BasePresent<IRegisterView> {
    public RegisterPresent() {
    }

    public void getCode(String phoneNum) {
        SMSSDK.getVerificationCode("china", phoneNum, new OnSendMessageHandler() {
            @Override
            public boolean onSendMessage(String s, String s1) {
                return false;
            }
        });
    }

    /*
    * 注册接口
    * */
    public void doRegister(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("phone", user.getPhone());
        addSubscription(ApiService.getInstanceUser().doRegister(map).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()
        ).subscribe(new Action1<TaoTaoResult>() {
            @Override
            public void call(TaoTaoResult s) {
                if (s.getStatus() == 200) {
                    myView.register();
                } else {
                    myView.success("注册失败");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                myView.failure(throwable.getMessage());
            }
        }));
    }

    /*
    * 检测字段是否可用
    * */
    public void check(final int type, final String username) {
        addSubscription(ApiService.getInstanceUser().check(type, username).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<TaoTaoResult>() {
                    @Override
                    public void call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() != 200) {
                            myView.check(type, taoTaoResult.getMsg());
                        } else if (taoTaoResult.getStatus() == 200) {
                            switch (type) {
                                case 1:
                                    myView.success("user");
                                    break;
                                case 3:
                                    myView.success("phone");
                                    break;
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.failure(throwable.getMessage());
                    }
                }));
    }
}
