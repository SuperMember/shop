package com.example.lenovo.taoshop;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.taoshop.bean.common.TbAddr;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.injector.components.DaggerUserComponent;
import com.example.lenovo.taoshop.injector.modules.UserModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.UserPresent;
import com.example.lenovo.taoshop.mvp.view.IUserView;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.AddrEvent;
import com.example.lenovo.taoshop.rxbus.event.AreaEvent;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class EditAddrActivity extends BaseActivity<UserPresent> implements IUserView {

    @Bind(R.id.et_user)
    EditText etUser;//编写收货人
    @Bind(R.id.et_phone)
    EditText etPhone;//编写手机
    @Bind(R.id.tv_addr)
    TextView tvArea;//选择地区
    @Bind(R.id.et_detail)
    EditText etDetail;//详细地区
    @Bind(R.id.cb_set)
    CheckBox cbSet;//设置默认地址
    @Bind(R.id.tv_edit)
    TextView tvEdit;//编辑
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private TbAddr tbAddr = null;
    private static final int AREA_SELECT = 0x1;
    private static final int RESULT_OK = 0;
    private int type;
    private ProgressDialog dialog;
    private Subscription subscription;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        cbSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tbAddr.setDefaultaddr(1);//设置默认
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    @Override
    protected void inject() {
        DaggerUserComponent.builder().userModule(new UserModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        //获取参数，判断是修改还是新增
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                //修改
                //设置收货人
                //获取信息
                tbAddr = (TbAddr) getIntent().getSerializableExtra("item");
                etUser.setText(tbAddr.getName());//收货人

                etPhone.setText(tbAddr.getTel());//手机

                tvArea.setText(tbAddr.getAddr());//市区
                etDetail.setText(tbAddr.getArea());//详细地址

                tvEdit.setVisibility(View.VISIBLE);
                initToolbar("修改地址", true, true, toolbar);
                break;
            case 1:
                //新增
                cbSet.setVisibility(View.VISIBLE);
                tbAddr = new TbAddr();
                initToolbar("新增收货地址", true, true, toolbar);
                break;
        }

        dialog = new ProgressDialog(this);

        subscription = RxBus.getInstance().toObservable(AreaEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<AreaEvent>() {
                    @Override
                    public void call(AreaEvent areaEvent) {
                        if (areaEvent.getArea() != null && !areaEvent.getArea().equals(""))
                            tvArea.setText(areaEvent.getArea());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                });


        alertDialog = new AlertDialog.Builder(EditAddrActivity.this)
                .setMessage("你还没有保存设置，确定要放弃并且退出吗")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_addr;
    }

    @Override
    protected void updateView() {

    }

    @OnClick({R.id.tv_addr, R.id.tv_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_addr:
                //选择地址
                Intent intent = new Intent(this, AreaActivity.class);
                startActivityForResult(intent, AREA_SELECT);
                break;
            case R.id.tv_edit:
                if (tbAddr != null && tbAddr.getAddrid() != null) {
                    myPresent.deleteAddr(tbAddr.getAddrid());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AREA_SELECT && resultCode == RESULT_OK) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if (validation()) {
                    dialog.show();
                    if (type == 0) {
                        tbAddr.setArea(etDetail.getText().toString().trim());
                        tbAddr.setName(etUser.getText().toString().trim());
                        tbAddr.setTel(etPhone.getText().toString().trim());
                        tbAddr.setAddr(tvArea.getText().toString().trim());
                        myPresent.editAddr(tbAddr);//修改
                    } else {
                        //设置信息
                        tbAddr.setArea(etDetail.getText().toString().trim());
                        tbAddr.setDefaultaddr(tbAddr.getDefaultaddr() == null ? 0 : 1);
                        tbAddr.setName(etUser.getText().toString().trim());
                        tbAddr.setTel(etPhone.getText().toString().trim());
                        tbAddr.setMuserid(SharedPreferencesUtil.getInstance(this).getInfo().getId());
                        tbAddr.setAddr(tvArea.getText().toString().trim());
                        myPresent.addAddr(tbAddr);//新增
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //验证字段的有效性
    private boolean validation() {
        //收货人
        String user = etUser.getText().toString();
        if (user.equals("")) {
            showToast("收货人不能为空");
            return false;
        }
        //手机
        String phone = etPhone.getText().toString();
        if (phone.equals("")) {
            showToast("联系电话不能为空");
            return false;
        }
        //详细地址
        String detailarea = etDetail.getText().toString();
        if (detailarea.equals("")) {
            showToast("详细地址不能为空");
            return false;
        }
        //街区
        if (tvArea.getText().toString().equals("请选择")) {
            showToast("请选择地区");
            return false;
        }
        return true;
    }

    @Override
    public void result(String msg) {
        dialog.dismiss();
        showToast(msg);
        //修改信息
        UserEntity userEntity = SharedPreferencesUtil.getInstance(this).getInfo();
        if (msg.equals("删除成功")) {
            List<TbAddr> list = userEntity.getTbAddrs();
            for (int i = 0; i < userEntity.getTbAddrs().size(); i++) {
                if (list.get(i).getAddrid().equals(tbAddr.getAddrid())) {
                    list.remove(i);
                    break;
                }
            }
        } else if (msg.equals("修改成功")) {
            List<TbAddr> list = userEntity.getTbAddrs();
            for (int i = 0; i < userEntity.getTbAddrs().size(); i++) {
                if (list.get(i).getAddrid().equals(tbAddr.getAddrid())) {
                    list.remove(i);
                    list.add(i, tbAddr);
                    break;
                }
            }
        } else {
            //新增
            if (userEntity.getTbAddrs() == null) {
                List<TbAddr> list = new ArrayList<>();
                list.add(tbAddr);
                userEntity.setTbAddrs(list);
            } else {
                userEntity.getTbAddrs().add(tbAddr);
            }
        }
        SharedPreferencesUtil.getInstance(this).save(userEntity);
        RxBus.getInstance().post(new AddrEvent());
        finish();
    }

    @Override
    public void failure(String msg) {
        dialog.dismiss();
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
