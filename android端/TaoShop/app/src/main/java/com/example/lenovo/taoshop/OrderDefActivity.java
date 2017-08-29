package com.example.lenovo.taoshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.Order;
import com.example.lenovo.taoshop.bean.common.OrderItem;
import com.example.lenovo.taoshop.bean.common.TbAddr;
import com.example.lenovo.taoshop.bean.common.TbOrderItem;
import com.example.lenovo.taoshop.bean.common.TbOrderShipping;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.injector.components.DaggerOrderComponent;
import com.example.lenovo.taoshop.injector.modules.OrderModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.OrderPresent;
import com.example.lenovo.taoshop.mvp.view.IOrderView;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.AddrEvent;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/*
* 订单确定
* */
public class OrderDefActivity extends BaseActivity<OrderPresent> implements IOrderView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.text_user)
    TextView textUser;
    @Bind(R.id.text_phone)
    TextView textPhone;
    @Bind(R.id.text_addr)
    TextView textAddr;
    @Bind(R.id.tv_intro)
    TextView tvIntro;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.text_num)
    TextView textNum;
    @Bind(R.id.btn_num)
    AnimShopButton btnNum;
    @Bind(R.id.et_comments)
    EditText etComments;
    @Bind(R.id.text_total)
    TextView textTotal;
    @Bind(R.id.text_all_total)
    TextView textAllTotal;
    @Bind(R.id.tv_good_detail_buy)
    TextView tvGoodDetailBuy;
    @Bind(R.id.iv_good_pic)
    ImageView ivGoodPic;
    @Bind(R.id.img_select)
    ImageView imgSelect;//选择地址
    @Bind(R.id.text_show_num)
    TextView textShowNum;
    @Bind(R.id.relativelayout_confirm)
    RelativeLayout relativelayoutConfirm;

    private ItemList itemList;
    private ProgressDialog dialog;
    private Order order;
    private UserEntity userEntity;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void inject() {
        DaggerOrderComponent.builder().orderModule(new OrderModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        initData();
        initToolbar("确定订单", true, true, toolbar);
        dialog = new ProgressDialog(this);
        dialog.setTitle("提交");
        dialog.setMessage("正在提交...");

        //监听地址选择返回的结果
        subscription = RxBus.getInstance().toObservable(AddrEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<AddrEvent>() {
                    @Override
                    public void call(AddrEvent addrEvent) {
                        //重新设置内容
                        if (addrEvent != null) {
                            textUser.setText(addrEvent.getUsername());
                            textPhone.setText(addrEvent.getPhone());
                            textAddr.setText(addrEvent.getArea());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                });
    }


    private void initData() {
        //邮编地址
        userEntity = SharedPreferencesUtil.getInstance(this).getInfo();
        List<TbAddr> list = userEntity.getTbAddrs();
        if (list != null && list.size() != 0) {
            TbAddr tbAddr = list.get(0);
            textUser.setText(tbAddr.getName());
            textPhone.setText(tbAddr.getTel());
            textAddr.setText(tbAddr.getAddr() + " " + tbAddr.getArea());
        }
        //商品信息
        itemList = (ItemList) getIntent().getSerializableExtra("item");
        //图片
        ImageLoader.getInstance().displayImage(this, itemList.getImage().split(",")[0], ivGoodPic);
        //商品名称
        tvIntro.setText(itemList.getTitle());
        //商品价格
        tvPrice.setText(itemList.getPrice() + "");
        //数量
        textNum.setText("X1");
        btnNum.setCount(1);
        //总价格
        textAllTotal.setText(itemList.getPrice() + "");
        textTotal.setText(itemList.getPrice() + "");


        btnNum.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                //总价格
                textAllTotal.setText((itemList.getPrice() * i) + "");
                textTotal.setText((itemList.getPrice() * i) + "");
                textShowNum.setText(i + "");
                textNum.setText("X" + i);
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                if (i == 0) {
                    btnNum.setCount(1);
                    showToast("已经达到最小数量");
                    //总价格
                    textAllTotal.setText(itemList.getPrice() + "");
                    textTotal.setText(itemList.getPrice() + "");
                    textShowNum.setText("1");
                    textNum.setText("X1");
                } else {
                    //总价格
                    textAllTotal.setText((itemList.getPrice() * i) + "");
                    textTotal.setText((itemList.getPrice() * i) + "");
                    textShowNum.setText(i + "");
                    textNum.setText("X" + i);
                }

            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_def;
    }

    @Override
    protected void updateView() {

    }

    @OnClick({R.id.relativelayout_confirm, R.id.img_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativelayout_confirm:
                dialog.show();
                //封装数据
                //商品
                order = new Order();
                order.setBuyerMessage(etComments.getText().toString().trim().equals("") ? "该买家没有进行留言" : etComments.getText().toString().trim());
                order.setPostFee("0");
                order.setBuyerNick(userEntity.getUsername());
                order.setUserId(userEntity.getId());
                order.setPayment(textTotal.getText().toString());
                order.setMuserId(itemList.getMuser_id());
                order.setGoodcidname(itemList.getCidname());
                //商品信息
                TbOrderItem tbOrderItem = new TbOrderItem();
                tbOrderItem.setPrice((long) (itemList.getPrice()));
                tbOrderItem.setItemId(itemList.getId());
                tbOrderItem.setNum(btnNum.getCount());
                tbOrderItem.setTitle(itemList.getTitle());
                tbOrderItem.setTotalFee(Long.parseLong(textTotal.getText().toString()));
                tbOrderItem.setPicPath(itemList.getImage().split(",")[0]);
                order.setOrderItems(tbOrderItem);

                //地址
                TbOrderShipping tbOrderShipping = new TbOrderShipping();
                tbOrderShipping.setReceiverName(textUser.getText().toString());
                tbOrderShipping.setReceiverMobile(textPhone.getText().toString());
                String[] addrs = textAddr.getText().toString().split(" ");
                tbOrderShipping.setReceiverState(addrs[0]);
                tbOrderShipping.setReceiverCity(addrs[2]);
                tbOrderShipping.setReceiverDistrict(addrs[3]);
                tbOrderShipping.setReceiverAddress(addrs[4]);
                order.setOrderShipping(tbOrderShipping);

                myPresent.submitOrder(order);
                break;
            case R.id.img_select:
                Intent intent = new Intent(OrderDefActivity.this, LogisticsSelectActivity.class);
                invoke(false, intent);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    //view
    @Override
    public void loadData(List<OrderItem> list) {

    }

    @Override
    public void loadMoreData(List<OrderItem> list) {

    }

    @Override
    public void loadNoData() {

    }

    @Override
    public void loadNoMoreData() {

    }

    @Override
    public void failure(String msg) {
        dialog.dismiss();
        showToast("提交失败，请重试");
    }

    @Override
    public void submit(String msg) {
        dialog.dismiss();
        showToast("提交成功");
        // RxBus.getInstance().post(new AddrEvent(AddrEvent.Status.PAY));
        finish();
    }

}
