package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.AreaEntity;
import com.example.lenovo.taoshop.bean.common.Data;
import com.example.lenovo.taoshop.bean.common.LogisticsJsonDetail;
import com.example.lenovo.taoshop.bean.common.LogisticsMultiEntity;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.bean.common.TbArea;
import com.example.lenovo.taoshop.bean.common.TbCity;
import com.example.lenovo.taoshop.bean.common.TbProvince;
import com.example.lenovo.taoshop.mvp.view.ILoginView;
import com.example.lenovo.taoshop.mvp.view.ILogisticsView;
import com.example.lenovo.taoshop.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  05  0005.
 */

public class LogisticsPresent extends BasePresent<ILogisticsView> {
    public LogisticsPresent() {
    }

    public void getDetail(final String logisticsId) {
        addSubscription(ApiService.getInstanceLogistics().getDetail(logisticsId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<LogisticsJsonDetail, List<Data>>() {
                    @Override
                    public List<Data> call(LogisticsJsonDetail logisticsJsonDetail) {
                        return logisticsJsonDetail.getData();
                    }
                })
                .subscribe(new Action1<List<Data>>() {
                    @Override
                    public void call(List<Data> list) {
                        List<LogisticsMultiEntity> losisticsList = new ArrayList<LogisticsMultiEntity>();
                        if (list.size() == 0) {
                            LogisticsMultiEntity en = new LogisticsMultiEntity(LogisticsMultiEntity.TYPE_LOGISTICS_DETAIL);
                            Data dt = new Data();
                            dt.setContext("暂无快递信息");
                            en.setData(dt);
                            losisticsList.add(en);
                        } else {
                            for (Data data :
                                    list) {
                                LogisticsMultiEntity entity = new LogisticsMultiEntity(LogisticsMultiEntity.TYPE_LOGISTICS_DETAIL);
                                entity.setData(data);
                                losisticsList.add(entity);
                            }
                        }
                        myView.get(losisticsList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail(throwable.getMessage());
                    }
                }));
    }

    //获取城市
    public void getArea(final String type, Integer id) {
        addSubscription(ApiService.getInstanceLogistics().getArea(type, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TaoTaoResult, AreaEntity>() {
                    @Override
                    public AreaEntity call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {

                            String json = (String) taoTaoResult.getData();
                            if (type.equals("province")) {
                                AreaEntity<TbProvince> areaEntity = new AreaEntity<>();
                                areaEntity.setType(type);
                                List<TbProvince> list = JsonUtils.jsonToList(json, TbProvince.class);
                                areaEntity.setList(list);
                                return areaEntity;
                            } else if (type.equals("city")) {
                                AreaEntity<TbCity> areaEntity = new AreaEntity<>();
                                areaEntity.setType(type);
                                List<TbCity> list = JsonUtils.jsonToList(json, TbCity.class);
                                areaEntity.setList(list);
                                return areaEntity;
                            } else {
                                AreaEntity<TbArea> areaEntity = new AreaEntity<>();
                                areaEntity.setType(type);
                                List<TbArea> list = JsonUtils.jsonToList(json, TbArea.class);
                                areaEntity.setList(list);
                                return areaEntity;
                            }
                        }
                        return null;
                    }
                })
                .subscribe(new Action1<AreaEntity>() {
                    @Override
                    public void call(AreaEntity areaEntity) {
                        myView.getArea(areaEntity);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail(throwable.getMessage());
                    }
                }));
    }
}
