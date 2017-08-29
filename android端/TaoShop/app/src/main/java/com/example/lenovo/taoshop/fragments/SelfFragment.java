package com.example.lenovo.taoshop.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lenovo.taoshop.AddressActivity;
import com.example.lenovo.taoshop.LoginActivity;
import com.example.lenovo.taoshop.MessageActivity;
import com.example.lenovo.taoshop.OrderActivity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.bean.common.TbAddr;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.fragments.base.BaseFragment;
import com.example.lenovo.taoshop.injector.components.DaggerInfoComponent;
import com.example.lenovo.taoshop.injector.modules.InfoModule;
import com.example.lenovo.taoshop.mvp.present.InfoPresent;
import com.example.lenovo.taoshop.mvp.view.IInfoView;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.AddrEvent;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.utils.JsonUtils;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.example.lenovo.taoshop.widget.NotifyingScrollView;
import com.example.lenovo.taoshop.widget.UserPopupWindow;
import com.hankkin.library.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.lijunguan.imgselector.ImageSelector;
import io.github.lijunguan.imgselector.album.AlbumActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class SelfFragment extends BaseFragment<InfoPresent> implements ViewTreeObserver.OnGlobalLayoutListener, IInfoView {
    @Bind(R.id.view_title)
    View viewTitle;
    @Bind(R.id.scrollview)
    NotifyingScrollView scrollview;
    @Bind(R.id.toolbar)
    Toolbar llTitle;
    @Bind(R.id.title)
    TextView title;
    //@Bind(R.id.progressBar)
    //ProgressBar progressBar;
    @Bind(R.id.order_unsend)
    ImageView orderUnsend;//未发货
    @Bind(R.id.order_send)
    ImageView orderSend;//已发货
    @Bind(R.id.order_finish)
    ImageView orderFinish;//交易完成
    @Bind(R.id.order_money)
    ImageView orderMoney;//已付款
    @Bind(R.id.topimage)
    ImageView topimage;//头部图片
    @Bind(R.id.img_user)
    CircleImageView imgUser;//用户图片
    @Bind(R.id.user_name)
    TextView userName;//用户名

    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.img_edit_info)
    ImageView imgEditInfo;//编辑信息
    @Bind(R.id.img_edit_addr)
    ImageView imgEditAddr;//编辑地址
    @Bind(R.id.tv_addr_info)
    TextView tvAddrInfo;    //提示没有设置邮寄地址
    @Bind(R.id.tv_addr)
    LinearLayout tvAddr;//有设置邮寄地址，设置信息

    //邮编信息
    @Bind(R.id.text_logistics_username)
    TextView textLogisticsUsername;
    @Bind(R.id.text_logistics_phone)
    TextView textLogisticsPhone;
    @Bind(R.id.text_logistics_location)
    TextView textLogisticsLocation;
    @Bind(R.id.text_money_num)
    TextView textMoneyNum;
    @Bind(R.id.text_unsend_num)
    TextView textUnsendNum;
    @Bind(R.id.text_send_num)
    TextView textSendNum;
    @Bind(R.id.img_message)
    ImageView imgMessage;
    @Bind(R.id.relativelayout_info)
    RelativeLayout relativelayoutInfo;

    private UserPopupWindow userPopupWindow;
    private int height = 200;//默认高度
    private boolean isLogin = false;
    private static final int SELECT_IMAGE_ACTIVITY_REQUEST_CODE = 0x1;
    private static final int RESULT_OK = 0;
    private Subscription subscription;
    private String addrs = null;//存放地址的json格式，方便修改时传递给修改页面
    private ProgressDialog dialog;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initInjector() {
        DaggerInfoComponent.builder().infoModule(new InfoModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        //设置状态栏高度
        AppBarLayout.LayoutParams layoutParams = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusHeight());
        viewTitle.setLayoutParams(layoutParams);

        //获取headerview的高度
        getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);

        //初始化弹出框
        userPopupWindow = new UserPopupWindow(mContext);
        ImageSelector.getInstance()
                .setSelectModel(ImageSelector.AVATOR_MODE)
                .setMaxCount(6)
                .setGridColumns(3)
                .setToolbarColor(getResources().getColor(R.color.colorPrimary));

        //监听事件
        subscription = RxBus.getInstance().toObservable(AddrEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<AddrEvent>() {
                    @Override
                    public void call(AddrEvent addrEvent) {
                        //设置信息
                        textLogisticsPhone.setText(addrEvent.getPhone());
                        textLogisticsLocation.setText(addrEvent.getArea());
                        textLogisticsUsername.setText(addrEvent.getUsername());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        dialog = new ProgressDialog(mContext);
        dialog.setMessage("上传中...");
        dialog.setTitle("上传");
    }

    @Override
    protected void setListener() {
        scrollview.setOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView who, int xl, int y, int oldl, int oldt) {
                if (y <= 0) {
                    //显示刷新条
                    //progressBar.setVisibility(View.VISIBLE);
                    //设置标题的背景颜色
                    llTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) y / height;
                    float alpha = (255 * scale);
                    title.setTextColor(Color.argb((int) alpha, 1, 24, 28));
                    llTitle.setBackgroundColor(Color.argb((int) alpha, 63, 81, 181));
                    viewTitle.setBackgroundColor(Color.argb((int) alpha, 63, 81, 181));
                } else {
                    //滑动到banner下面设置普通颜色
                    llTitle.setBackgroundColor(Color.parseColor("#ff3F51B5"));
                    viewTitle.setBackgroundColor(Color.parseColor("#ff3F51B5"));
                }

            }
        });

        //图片变化监听
        scrollview.setOnImageViewSizeChange(new NotifyingScrollView.OnImageViewSizeChangeListener() {
            @Override
            public void changing() {
                /*if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void end() {
                /*if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }*/
            }
        });

        //监听弹出框的点击事件
        userPopupWindow.setOnItemClickListener(new UserPopupWindow.OnItemClickListener() {
            @Override
            public void onClick(int index) {
                switch (index) {
                    case 0:
                        //打开相册
                        ImageSelector.getInstance().startSelect(mContext);
                        Intent intents = new Intent(getActivity(), AlbumActivity.class);
                        intents.putExtra(ImageSelector.ARG_ALBUM_CONFIG, ImageSelector.getInstance().getConfig());
                        startActivityForResult(intents, ImageSelector.AVATOR_MODE);
                        break;
                    case 1:
                        SharedPreferencesUtil.getInstance(mContext).delete();//删除
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                }
                userPopupWindow.dismiss();
            }
        });
    }

    //系统相册选择图片之后
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.AVATOR_MODE
                && resultCode == Activity.RESULT_OK) {
            List<String> imagesPath = data.getStringArrayListExtra(ImageSelector.SELECTED_RESULT);
            if (imagesPath != null && imagesPath.size() != 0) {
                //上传
                dialog.show();
                myPresent.edit(imagesPath.get(0), SharedPreferencesUtil.getInstance(MyApplication.getContext()).getInfo().getId());
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_self;
    }

    @Override
    protected void updateView() {
        //获取数据
        UserEntity user = SharedPreferencesUtil.getInstance(mContext).getInfo();
        if (user.getUsername() == null) {
            //没有登录
            isLogin = false;
            //跳转到登录页面
        } else {
            isLogin = true;
            //初始化数据
            //设置头像
            ImageLoader.getInstance().displayImage(mContext, user.getImg(), imgUser, R.mipmap.user);
            ImageLoader.getInstance().displayImage(mContext, user.getImg(), topimage, R.mipmap.user);
            //设置昵称
            userName.setText(user.getUsername());
            //设置手机
            tvPhone.setText(user.getPhone());

            //设置邮寄地址
            List<TbAddr> tbAddrs = user.getTbAddrs();
            if (tbAddrs != null && tbAddrs.size() != 0) {
                tvAddrInfo.setVisibility(View.GONE);

                tvAddr.setVisibility(View.VISIBLE);
                TbAddr tbAddr = tbAddrs.get(0);//取第一项
                tbAddr.setDefaultaddr(1);//设置为默认地址

                //设置信息
                textLogisticsPhone.setText(tbAddr.getTel());
                textLogisticsLocation.setText(tbAddr.getAddr() + " " + tbAddr.getArea());
                textLogisticsUsername.setText(tbAddr.getName());

                //保存
                addrs = JsonUtils.objectToJson(tbAddrs);
            }
        }
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActivity().getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        subscription.unsubscribe();
    }

    @Override
    public void onGlobalLayout() {
        height = topimage.getMeasuredHeight();
    }

    @OnClick({R.id.order_unsend, R.id.img_message, R.id.order_send, R.id.order_finish, R.id.order_money, R.id.img_user, R.id.relativelayout_info, R.id.img_edit_info})
    public void onClick(View view) {
        if (!isLogin) {
            //跳转到登录页面
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            switch (view.getId()) {
                case R.id.order_money:
                    invoke(1);
                    break;
                case R.id.order_unsend:
                    invoke(2);
                    break;
                case R.id.order_send:
                    invoke(3);
                    break;
                case R.id.order_finish:
                    invoke(4);
                    break;
                case R.id.relativelayout_info:
                    //修改邮寄地址
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), AddressActivity.class);
                    intent.putExtra("address", addrs);
                    startActivity(intent);
                    break;
                case R.id.img_edit_info:
                    //修改用户信息
                    break;
                case R.id.img_user:
                    if (userPopupWindow.isShowing()) {
                        userPopupWindow.dismiss();
                    } else {
                        userPopupWindow.showAtLocation(imgUser, Gravity.CENTER, 0, 0);
                    }
                    break;
                case R.id.img_message:
                    //消息中心
                    Intent message = new Intent(getActivity(), MessageActivity.class);
                    startActivity(message);
                    break;
            }
        }
    }

    //订单跳转
    public void invoke(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), OrderActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    @Override
    public void success(String msg, String url) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        showToast(msg);
        if (url != null) {
            ImageLoader.getInstance().displayImage(mContext, url, imgUser, R.mipmap.user);
            ImageLoader.getInstance().displayImage(mContext, url, topimage, R.mipmap.user);
        }
    }

    @Override
    public void fail(String msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        showToast(msg);
    }

    @OnClick(R.id.relativelayout_info)
    public void onClick() {
    }
}
