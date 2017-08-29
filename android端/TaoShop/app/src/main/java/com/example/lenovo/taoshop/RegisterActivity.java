package com.example.lenovo.taoshop;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.example.lenovo.taoshop.bean.common.User;
import com.example.lenovo.taoshop.injector.components.DaggerUserComponent;
import com.example.lenovo.taoshop.injector.modules.UserModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.RegisterPresent;
import com.example.lenovo.taoshop.mvp.view.IRegisterView;
import com.example.lenovo.taoshop.utils.RxHelper;

import net.anumbrella.customedittext.FloatLabelView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Subscriber;

public class RegisterActivity extends BaseActivity<RegisterPresent> implements IRegisterView, View.OnClickListener {

    @Bind(R.id.details)
    ViewFlipper vf_details;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    //注册提交的格式类
    private User user;


    //视图1的布局
    private View pre;
    private EditText registerPhone;
    private EditText registerCode;
    private Button btnNext;
    private Button btn_Code;

    //视图2的布局
    private View next;
    private FloatLabelView register_Username;
    private FloatLabelView register_Password;
    private FloatLabelView register_PasswordAgain;
    private Button btn_Finish;


    private String phonenum;
    private String codeNum = null;//返回的验证码
    private String passwordagain;
    private String username;
    private Object code;


    /*
    * mob短信集成
    * */
    private EventHandler eventHandler;
    private static final String APP_KEY = "1d7bddde528c1";
    private static final String APP_SECRET = "bd6ddad4753d0eacf1df5f490ced31b8";
    private ProgressDialog dialog;

    private static final int MSG = 0x1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG:
                    vf_details.showNext();//进入下一步
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        btnNext.setOnClickListener(this);
        btn_Finish.setOnClickListener(this);
        btn_Code.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //注入依赖
    @Override
    protected void inject() {
        DaggerUserComponent.builder().userModule(new UserModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {


        /*
        * 初始化短信sdk
        * */
        SMSSDK.initSDK(this, APP_KEY, APP_SECRET);
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        //showToast("提交验证码成功");
                        Message message = mHandler.obtainMessage();
                        message.what = MSG;
                        mHandler.sendMessage(message);//通知进入下一步
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    showToast("验证码错误");
                }
            }
        };
        //注册
        SMSSDK.registerEventHandler(eventHandler);
        /*
        * ViewFlipper初始化view
        * */
        pre = LayoutInflater.from(this).inflate(R.layout.activity_register_pre, null);
        next = LayoutInflater.from(this).inflate(R.layout.activity_register_next, null);
        findView();//初始化控件

        vf_details.addView(pre);
        vf_details.addView(next);
        //设置效果
        register_Password.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        register_PasswordAgain.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        dialog = new ProgressDialog(this);
        dialog.setMessage("注册中...");
        dialog.setTitle("注册");
    }

    private void findView() {
        //视图1
        registerPhone = (EditText) pre.findViewById(R.id.register_phone);
        registerCode = (EditText) pre.findViewById(R.id.register_code);
        btnNext = (Button) pre.findViewById(R.id.btn_next);
        btn_Code = (Button) pre.findViewById(R.id.btn_code);

        //视图2
        register_Username = (FloatLabelView) next.findViewById(R.id.register_username);
        register_Password = (FloatLabelView) next.findViewById(R.id.register_password);
        register_PasswordAgain = (FloatLabelView) next.findViewById(R.id.register_password_again);
        btn_Finish = (Button) next.findViewById(R.id.btn_finish);

        initToolbar("注册", true, true, toolbar);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                //验证数据的可靠性
                validation();
                break;
            case R.id.btn_finish:
                validationRegister();
                break;
            case R.id.btn_code:
                //请求验证码
                String phone = registerPhone.getText().toString().trim();
                if (phone.equals("")) {
                    showToast("手机号不能为空");
                    break;
                } else if (!checkPhoneNumber(phone)) {
                    showToast("手机格式不对，请重新输入");
                    break;
                } else {
                    myPresent.check(3, phone);
                }
                break;
        }
    }

    //注册
    private void doRegister() {
        //如果前面注册过程中出现验证错误，则isCorrect为false
        //补充完整信息
        user = new User();
        user.setPassword(passwordagain);
        user.setPhone(phonenum);
        user.setSeller(0);
        user.setUsername(username);
        dialog.show();
        myPresent.doRegister(user);//注册
    }

    //视图1验证号码的可靠性
    private boolean validation() {
        phonenum = registerPhone.getText().toString().trim();
        if (phonenum.equals("")) {
            showToast("手机号码不能为空");
            return false;
        } else {
            if (!checkPhoneNumber(registerPhone.getText().toString().trim())) {
                showToast("手机号码格式不对，请重新输入");
                return false;
            }
        }
        String code = registerCode.getText().toString().trim();//获取用户输入的验证码
        if (code.equals("")) {
            showToast("验证码不能为空");
        } else {
            //验证验证码
            SMSSDK.submitVerificationCode("86", phonenum, code);
        }
        return true;
    }

    //验证视图2号码的可靠性
    public boolean validationRegister() {
        username = register_Username.getEditText().getText().toString().trim();
        if (username.equals("")) {
            showToast("用户名不能为空");
            return false;
        } else {
            myPresent.check(1, username);//检测字段名
        }

        //第一次密码
        String password = register_Password.getEditText().getText().toString().trim();
        if (password.equals("")) {
            showToast("密码不能为空");
            return false;
        }

        //第二次密码
        passwordagain = register_PasswordAgain.getEditText().getText().toString().trim();
        if (passwordagain.equals("") || !passwordagain.equals(password)) {
            showToast("两次密码不同，请重新输入");
            return false;
        }
        return true;
    }

    //验证手机号码
    public static boolean checkPhoneNumber(String mobiles) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobiles);
        b = m.matches();
        return b;
    }

    //view回调接口
    //成功回调接口
    @Override
    public void success(String msg) {
        if (msg.equals("user")) {
            //字段检测成功
            //完成注册
            doRegister();
        } else if (msg.equals("phone")) {
            //检测手机成功
            //发送验证码
            getCode(registerPhone.getText().toString().trim());
        }
    }

    //失败回调接口
    @Override
    public void failure(String msg) {
        if (dialog != null) {
            dialog.dismiss();
        }
        showToast(msg);//显示错误信息
    }

    /*
    * 字段冲突检测
    * */
    @Override
    public void check(int type, String msg) {
        //字段检测失败，提示失败原因
        showToast(msg);
    }

    @Override
    public void register() {
        if (dialog != null) {
            dialog.dismiss();
        }
        showToast("注册成功");
        //跳转到登录页面
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        invoke(true, intent);//跳转主页面
    }


    //获取验证码
    public void getCode(String phone) {
        //获取验证码
        //开启倒计时
        btn_Code.setEnabled(false);
        SMSSDK.getVerificationCode("86", phone);//获取验证码
        RxHelper.countdown(60)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        btn_Code.setEnabled(true);
                        btn_Code.setText("点击重发");
                    }

                    @Override
                    public void onError(Throwable e) {
                        btn_Code.setEnabled(true);
                        btn_Code.setText("点击重发");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        btn_Code.setText(integer + "s");
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
