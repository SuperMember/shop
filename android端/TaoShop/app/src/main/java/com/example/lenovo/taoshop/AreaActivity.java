package com.example.lenovo.taoshop;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.example.lenovo.taoshop.adapter.ListViewAreaAdapter;
import com.example.lenovo.taoshop.bean.common.AreaEntity;
import com.example.lenovo.taoshop.bean.common.LogisticsMultiEntity;
import com.example.lenovo.taoshop.bean.common.TbArea;
import com.example.lenovo.taoshop.bean.common.TbCity;
import com.example.lenovo.taoshop.bean.common.TbProvince;
import com.example.lenovo.taoshop.injector.components.DaggerLogisticsComponent;
import com.example.lenovo.taoshop.injector.modules.LogisticsModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.LogisticsPresent;
import com.example.lenovo.taoshop.mvp.view.ILogisticsView;
import com.example.lenovo.taoshop.rxbus.RxBus;
import com.example.lenovo.taoshop.rxbus.event.AreaEvent;

import java.util.List;

import butterknife.Bind;


//选择城市
public class AreaActivity extends BaseActivity<LogisticsPresent> implements ILogisticsView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.area)
    ViewFlipper vf_area;

    private View vprovince;
    private View vcity;
    private View varea;

    private ListView lvProvince;
    private ListView lvCity;
    private ListView lvArea;
    private ListViewAreaAdapter<TbProvince> aProvince;
    private ListViewAreaAdapter<TbCity> aCity;
    private ListViewAreaAdapter<TbArea> aArea;

    private ProgressDialog dialog;
    private String type = "province";
    private String area = null;//拼接字符串
    private int currentId = 0;//判断当前位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        aProvince.setOnItemClickListener(new ListViewAreaAdapter.OnItemClickListener() {
            @Override
            public void Onclick(Integer id, String name) {
                dialog.show();
                type = "city";
                area = name;
                myPresent.getArea(type, id);
            }
        });

        aCity.setOnItemClickListener(new ListViewAreaAdapter.OnItemClickListener() {
            @Override
            public void Onclick(Integer id, String name) {
                dialog.show();
                type = "area";
                area = area + "  " + name;
                myPresent.getArea("area", id);
            }
        });

        aArea.setOnItemClickListener(new ListViewAreaAdapter.OnItemClickListener() {
            @Override
            public void Onclick(Integer id, String name) {
                area = area + " " + name;
                RxBus.getInstance().post(new AreaEvent(area));
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentId != 0) {
                    vf_area.showPrevious();
                    currentId--;
                } else {
                    onBackPressed();
                }
            }
        });
    }

    @Override
    protected void inject() {
        DaggerLogisticsComponent.builder().logisticsModule(new LogisticsModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        //省
        vprovince = LayoutInflater.from(this).inflate(R.layout.layout_province_item, null);
        //城市
        vcity = LayoutInflater.from(this).inflate(R.layout.layout_city_item, null);
        //地区
        varea = LayoutInflater.from(this).inflate(R.layout.layout_area_item, null);
        findView();

        vf_area.addView(vprovince);
        vf_area.addView(vcity);
        vf_area.addView(varea);

        dialog = new ProgressDialog(this);

        initToolbar("选择", true, true, toolbar);
    }

    private void findView() {
        //省
        lvProvince = (ListView) vprovince.findViewById(R.id.lv_province);
        aProvince = new ListViewAreaAdapter<>(this);
        lvProvince.setAdapter(aProvince);
        //城市
        lvCity = (ListView) vcity.findViewById(R.id.lv_city);
        aCity = new ListViewAreaAdapter<>(this);
        lvCity.setAdapter(aCity);
        //地区
        lvArea = (ListView) varea.findViewById(R.id.lv_area);
        aArea = new ListViewAreaAdapter<>(this);
        lvArea.setAdapter(aArea);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_area;
    }

    @Override
    protected void updateView() {
        dialog.show();
        myPresent.getArea(type, null);//获取全部省
    }


    @Override
    public void get(List<LogisticsMultiEntity> list) {

    }

    @Override
    public void empty() {

    }

    @Override
    public void fail(String msg) {
        dialog.dismiss();
        showToast(msg);
    }

    @Override
    public void getArea(AreaEntity areaEntity) {
        dialog.dismiss();
        if (areaEntity.getType().equals("province")) {
            aProvince.addItems(areaEntity.getList(), areaEntity.getType());
        } else if (areaEntity.getType().equals("city")) {
            vf_area.showNext();
            currentId++;
            aCity.addItems(areaEntity.getList(), areaEntity.getType());
        } else {
            vf_area.showNext();
            currentId++;
            aArea.addItems(areaEntity.getList(), areaEntity.getType());
        }
    }


}
