package com.example.lenovo.taoshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.taoshop.bean.common.User;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.injector.components.DaggerUserComponent;
import com.example.lenovo.taoshop.injector.modules.UserModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.LoginPresent;
import com.example.lenovo.taoshop.mvp.view.ILoginView;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.utils.JsonUtils;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.hankkin.library.CircleImageView;

import net.anumbrella.customedittext.FloatLabelView;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresent> implements ILoginView {

    @Bind(R.id.login_phone)
    FloatLabelView loginPhone;
    @Bind(R.id.login_password)
    FloatLabelView loginPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.text_register)
    TextView textRegister;
    @Bind(R.id.img_user)
    CircleImageView imgUser;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void inject() {
        DaggerUserComponent.builder().userModule(new UserModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        //进度条
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("登录中...");
        mDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        mDialog.setCancelable(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void updateView() {
        //获取用户信息
        UserEntity user = SharedPreferencesUtil.getInstance(this).getInfo();
        if (user != null) {
            //设置头像
            ImageLoader.getInstance().displayImage(this, user.getImg(), imgUser, R.mipmap.user);
            //设置昵称
            loginPhone.setText(user.getUsername());
        }
    }


    //登录回调接口
    @Override
    public void login(String token) {
        mDialog.dismiss();
        showToast("登录成功");

        //存储信息
        //转化信息
        UserEntity user = JsonUtils.jsonToPojo(token, UserEntity.class);
        SharedPreferencesUtil.getInstance(this).save(user);

        //登录成功
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        invoke(true, intent);
    }

    @Override
    public void unsuccess(String msg) {
        mDialog.dismiss();
        try {
            String message=new String(msg.getBytes("GBK"),"utf-8");
            showToast(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.text_register, R.id.btn_login, R.id.forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_register:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                invoke(false, intent);
                break;
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.forget_password:
                break;
        }
    }

    /*
    * 登录检测
    * */
    private void doLogin() {
        String username = loginPhone.getEditText().getText().toString().trim();
        String password = loginPassword.getEditText().getText().toString().trim();
        if (username.equals("")) {
            showToast("用户名不能为空");
        } else if (password.equals("")) {
            showToast("密码不能为空");
        } else {
            //登录
            mDialog.show();
            myPresent.doLogin(username, password);
        }
    }
}
