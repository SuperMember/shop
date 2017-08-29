package com.example.lenovo.taoshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.lenovo.taoshop.injector.components.DaggerImageConponent;
import com.example.lenovo.taoshop.injector.modules.ImageModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.SplashPresent;
import com.example.lenovo.taoshop.mvp.view.ISplashView;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.utils.RxHelper;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;

public class SplashActivity extends BaseActivity<SplashPresent> implements ISplashView {
    @Bind(R.id.img_splash)
    ImageView img_Splash;
    @Bind(R.id.text_msg)
    TextView text_Msg;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        //跳过按钮的点击事件
        text_Msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscription.unsubscribe();//取消倒计时
                skipActivity();
            }
        });
    }

    //跳转activity
    public void skipActivity() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);
        invoke(true, intent);
    }

    @Override
    protected void inject() {
        DaggerImageConponent.builder().imageModule(new ImageModule()).build().inject(this);
        myPresent.attachView(this);//关联view
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void updateView() {
        //倒计时
        //借助rxjava
        subscription = RxHelper.countdown(5)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        skipActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        skipActivity();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        text_Msg.setText("跳过 " + integer);
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    //mvp中的view，用于展示视图
    @Override
    public void showImage(String url) {
        //请求网络中的图片地址，展示图片
        ImageLoader.getInstance().displayImage(this, url, img_Splash);
    }

    //请求图片失败
    @Override
    public void error() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
