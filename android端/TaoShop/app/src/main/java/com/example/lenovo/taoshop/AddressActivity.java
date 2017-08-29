package com.example.lenovo.taoshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lenovo.taoshop.adapter.ListViewAddrAdapter;
import com.example.lenovo.taoshop.bean.common.TbAddr;
import com.example.lenovo.taoshop.injector.components.DaggerUserComponent;
import com.example.lenovo.taoshop.injector.modules.UserModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.UserPresent;
import com.example.lenovo.taoshop.mvp.view.IUserView;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.AddrEvent;
import com.example.lenovo.taoshop.utils.JsonUtils;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/*
* 邮编地址修改
* */
public class AddressActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv_addr)
    ListView lvAddr;
    @Bind(R.id.btn_add)
    Button btnAdd;//增加地址

    private ListViewAddrAdapter addrAdapter;
    private static final int EDIT_ADDR = 1;
    private static final int RESULT_OK = 0;
    private Subscription subscription;
    private TbAddr tbAddr;
    private boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addrAdapter.checkSet()) {
                    if (isChange) {
                        RxBus.getInstance().post(new AddrEvent(tbAddr.getTel(), tbAddr.getName(), (tbAddr.getAddr() + tbAddr.getArea())));
                    }
                    onBackPressed();
                } else {
                    showToast("请设置默认地址");
                }
            }
        });

        //监听删除，编辑点击
        addrAdapter.setOnItemClickListener(new ListViewAddrAdapter.OnItemClickListener() {
                                               @Override
                                               public void onClick(TbAddr tbAddr) {
                                                   //修改
                                                   Intent intent = new Intent();
                                                   intent.setClass(AddressActivity.this, EditAddrActivity.class);
                                                   intent.putExtra("type", 0);
                                                   intent.putExtra("item", tbAddr);
                                                   invoke(false, intent);
                                               }

                                           }

        );

        //监听修改默认地址点击
        addrAdapter.setOnCheckBoxClickListener(new ListViewAddrAdapter.OnCheckBoxClickListener() {
                                                   @Override
                                                   public void onClick(TbAddr ta) {
                                                       //修改默认地址
                                                       tbAddr = ta;
                                                       //修改状态
                                                       SharedPreferencesUtil.getInstance(AddressActivity.this).editAddr(ta.getAddrid());
                                                       isChange = true;
                                                   }
                                               }

        );
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        initToolbar("管理邮寄地址", true, true, toolbar);

        addrAdapter = new ListViewAddrAdapter(this);
        lvAddr.setAdapter(addrAdapter);

        subscription = RxBus.getInstance().toObservable(AddrEvent.class).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AddrEvent>() {
                    @Override
                    public void call(AddrEvent addrEvent) {
                        List<TbAddr> addrList = SharedPreferencesUtil.getInstance(AddressActivity.this).getInfo().getTbAddrs();
                        addrAdapter.updateItems(addrList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void updateView() {
        //从前一个页面中获取邮编地址
        List<TbAddr> list = JsonUtils.jsonToList(getIntent().getStringExtra("address"), TbAddr.class);
        //刷新页面
        addrAdapter.addItems(list);
    }

    @OnClick({R.id.btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                //新增地址
                Intent intent = new Intent();
                intent.setClass(AddressActivity.this, EditAddrActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 2);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 3) {
            List<TbAddr> addrList = SharedPreferencesUtil.getInstance(this).getInfo().getTbAddrs();
            addrAdapter.updateItems(addrList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
