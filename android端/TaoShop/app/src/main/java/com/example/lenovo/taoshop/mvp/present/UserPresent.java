package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.bean.common.TbAddr;
import com.example.lenovo.taoshop.mvp.view.IUserView;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  20  0020.
 */

public class UserPresent extends BasePresent<IUserView> {
    public UserPresent() {

    }

    //新增
    public void addAddr(TbAddr tbAddr) {
        Map<String, Object> map = new HashMap<>();
        map.put("muserid", tbAddr.getMuserid());
        map.put("name", tbAddr.getName());
        map.put("tel", tbAddr.getTel());
        map.put("addr", tbAddr.getAddr());
        map.put("area", tbAddr.getArea());
        map.put("defaultaddr", tbAddr.getDefaultaddr());
        addSubscription(ApiService.getInstanceUser().addAddr(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TaoTaoResult, Integer>() {
                    @Override
                    public Integer call(TaoTaoResult taoTaoResult) {
                        return taoTaoResult.getStatus();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer == 200) {
                            myView.result("新增成功");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.failure(throwable.getMessage());
                    }
                }));
    }

    //删除地址
    public void deleteAddr(Long id) {
        addSubscription(ApiService.getInstanceUser().deleteAddr(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TaoTaoResult, Integer>() {
                    @Override
                    public Integer call(TaoTaoResult taoTaoResult) {
                        return taoTaoResult.getStatus();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer == 200) {
                            myView.result("删除成功");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.failure(throwable.getMessage());
                    }
                }));
    }

    //修改地址
    public void editAddr(TbAddr tbAddr) {
        Map<String, Object> map = new HashMap<>();
        map.put("addrid", tbAddr.getAddrid());
        map.put("muserid", tbAddr.getMuserid());
        map.put("name", tbAddr.getName());
        map.put("tel", tbAddr.getTel());
        map.put("addr", tbAddr.getAddr());
        map.put("area", tbAddr.getArea());
        map.put("defaultaddr", tbAddr.getDefaultaddr());
        addSubscription(ApiService.getInstanceUser().editAddr(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TaoTaoResult, Integer>() {
                    @Override
                    public Integer call(TaoTaoResult taoTaoResult) {
                        return taoTaoResult.getStatus();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer == 200) {
                            myView.result("修改成功");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.failure(throwable.getMessage());
                    }
                }));
    }


}
