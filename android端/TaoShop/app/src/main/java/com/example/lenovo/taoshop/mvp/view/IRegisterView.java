package com.example.lenovo.taoshop.mvp.view;

/**
 * Created by lenovo on 2017  五月  02  0002.
 */

public interface IRegisterView extends BaseView {
    void success(String code); //验证码获取成功

    void failure(String msg);

    void check(int type, String msg);//检测注册字段是否可用

    void register();//注册成功

}
