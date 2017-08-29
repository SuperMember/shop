package com.example.lenovo.taoshop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.dao.mvp.present.SqlPresent;
import com.example.lenovo.taoshop.fragments.BuyFragment;
import com.example.lenovo.taoshop.fragments.ClassifyFragment;
import com.example.lenovo.taoshop.fragments.HomeFragment;
import com.example.lenovo.taoshop.fragments.SelfFragment;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.AddrEvent;
import com.example.lenovo.taoshop.rxbus.event.BuyEvent;
import com.example.lenovo.taoshop.service.MinaService;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.example.lenovo.taoshop.widget.animation.BezierEvaluator;

import java.util.Random;

import butterknife.Bind;
import rx.Subscription;
import rx.functions.Action1;


public class MainActivity extends BaseActivity implements ViewTreeObserver.OnGlobalLayoutListener {
    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;

    @Bind(R.id.tabhost)
    FragmentTabHost tabhost;

    private MessageBroadCastReceiver messageBroadCastReceiver = new MessageBroadCastReceiver();
    private NotificationManager notificationManager;
    private SqlPresent sqlPresent;//购物车数据库
    private int num = 0;
    private TextView text_info_num;//购物车上的数量通知
    private int buylocation[] = new int[2];//保存购物车图片的位置
    private Subscription subscription;
    private Class fragmentArray[] = {HomeFragment.class, ClassifyFragment.class, BuyFragment.class,
            SelfFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.btn_h_home_drawable, R.drawable.btn_h_classify_drawable, R.drawable.btn_h_buy_drawable,
            R.drawable.btn_h_self_drawable};

    private String mText[] = {"首页", "分类", "购物车", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void inject() {

    }

    private void initNotification(String msg) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MessageActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("新信息")
                .setContentText(msg)
                .setContentIntent(pendingIntent);
        //发送通知
        notificationManager.notify(3, builder.build());
    }

    public void register() {
        IntentFilter intentFilter = new IntentFilter("com.example.lenovo.taoshop.utils.mina");
        LocalBroadcastManager.getInstance(this).registerReceiver(messageBroadCastReceiver, intentFilter);
    }

    public void unregister() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageBroadCastReceiver);
    }

    @Override
    protected void initView() {
        /*
        * 加入fragment
        * */
        tabhost.setup(this, getSupportFragmentManager()
                , R.id.frameLayout);
        tabhost.getTabWidget().setDividerDrawable(null);
        initTab();

        sqlPresent = new SqlPresent();
        //获取购物车的数量，展示提醒
        int count = sqlPresent.getCount();
        if (count != -1 && count != 0) {
            text_info_num.setVisibility(View.VISIBLE);
            text_info_num.setText(count + "");
        }

        //注册rxbus事件，用于当添加商品时改变底部图标
        subscription = RxBus.getInstance().toObservable(BuyEvent.class).subscribe(new Action1<BuyEvent>() {
            @Override
            public void call(BuyEvent buyEvent) {
                //购物车动画

                num = Integer.parseInt(text_info_num.getText().toString().trim());
                if (buyEvent.getType() == BuyEvent.ADD_NUM) {
                    //添加
                    num++;
                    anim(buyEvent.getView());//执行动画
                } else {
                    //删除
                    if (num == 1) {
                        text_info_num.setVisibility(View.GONE);
                    }
                    num--;
                    text_info_num.setText(num + "");
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                showToast(throwable.getMessage());
            }
        });
        RxBus.getInstance().addSubscription(this, subscription);
    }

    //购物车动画
    private void anim(final ImageView view) {
        int location[] = new int[2];
        view.getLocationInWindow(location);//获取view的位置
        final int startX = location[0];
        final int startY = location[1];
        PointF startF = new PointF(startX, startY);//开始位置
        //购物车view的位置
        int endX = buylocation[0];
        final int endY = buylocation[1];
        PointF endF = new PointF(endX, endY);//结束位置


        //添加view
        final ImageView transView = new ImageView(this);
        // ImageView imageView = (ImageView) view;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (view.getDrawable() != null) {
                transView.setBackground(view.getDrawable());
            } else {
                transView.setBackground(view.getBackground());
            }
        }

        final FrameLayout frameLayout = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
        frameLayout.addView(transView, new ViewGroup.LayoutParams(view.getMeasuredWidth(), view.getMeasuredHeight()));

        //缩小
        ValueAnimator scale = ValueAnimator.ofFloat(startY, endY);
        scale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                //设置缩放
                float t = (endY - value) / (endY - startY);
                transView.setScaleX(t);
                transView.setScaleY(t);
            }
        });


        //赛贝尔
        //控制点
        //随机产生x和y
        Random random = new Random();
        //范围在0~（endX-startX）之间
        float mControlX = random.nextFloat() / (endX - startX) * (endX - startX);
        //范围在0~（endY-startY）之间
        float mControlY = random.nextFloat() / (endY - startY) * (endY - startY);
        //添加动画
        BezierEvaluator evaluator = new BezierEvaluator(new PointF(mControlX, mControlY));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, startF, endF);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                transView.setTranslationX(pointF.x);
                transView.setTranslationY(pointF.y);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                frameLayout.removeView(transView);//移除view
                if (text_info_num.getVisibility() == View.GONE) {
                    text_info_num.setVisibility(View.VISIBLE);
                }
                text_info_num.setText(num + "");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(scale, animator);
        set.setDuration(1000);
        set.start();

    }

    /*
    * 初始化导航栏
    * */
    private void initTab() {
        for (int i = 0; i < fragmentArray.length; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(mText[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            tabhost.addTab(tabSpec, fragmentArray[i], null);
        }
    }

    //获取tab的布局
    private View getTabItemView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_footer, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivImg);
        imageView.setImageResource(mImageViewArray[i]);
        TextView textView = (TextView) view.findViewById(R.id.text_info_num);
        if (i == 2) {
            text_info_num = textView;
        }
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void updateView() {
        //开启服务
        register();
        UserEntity userEntity = SharedPreferencesUtil.getInstance(this).getInfo();
        if (userEntity.getId() != null) {
            Intent intent = new Intent(this, MinaService.class);
            intent.setAction("com.mina.service");
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();//防止内容泄漏
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        stopService(new Intent(this, MinaService.class));
        unregister();
    }

    @Override
    public void onGlobalLayout() {
        tabhost.getTabWidget().getChildAt(2).findViewById(R.id.ivImg).getLocationInWindow(buylocation);
    }

    public class MessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mes = intent.getStringExtra("message");
            String[] msg = mes.split(",");
            initNotification(msg[0]);
            /*if (msg[1].equals("接受订单")) {
                RxBus.getInstance().post(new AddrEvent(AddrEvent.Status.UNSEND));
            } else if (msg[1].equals("已发货")) {
                RxBus.getInstance().post(new AddrEvent(AddrEvent.Status.SEND));
            }*/
        }
    }
}
