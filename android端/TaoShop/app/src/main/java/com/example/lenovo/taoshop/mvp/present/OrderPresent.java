package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.GoodsListItem;
import com.example.lenovo.taoshop.bean.common.Order;
import com.example.lenovo.taoshop.bean.common.OrderItem;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.mvp.view.IOrderView;
import com.example.lenovo.taoshop.utils.JsonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

public class OrderPresent extends BasePresent<IOrderView> {
    public OrderPresent() {
    }

    public void getOrderAll(long userId, final long page, Integer type) {
        addSubscription(ApiService.getInstanceOrder().getOrderAll(userId, page, type)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TaoTaoResult, GoodsListItem>() {
                    @Override
                    public GoodsListItem call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            String json = (String) taoTaoResult.getData();
                            return JsonUtils.jsonToPojo(json, GoodsListItem.class);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<GoodsListItem>() {
                    @Override
                    public void call(GoodsListItem goodsListItem) {
                        if (page > 1) {
                            if (goodsListItem != null) {
                                myView.loadMoreData(goodsListItem.getRows());
                            } else {
                                myView.loadNoMoreData();
                            }
                        } else {
                            if (goodsListItem != null) {
                                myView.loadData(goodsListItem.getRows());
                            } else {
                                myView.loadNoData();
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.failure(throwable.getMessage());
                    }
                }));
    }

    public void submitOrder(Order order) {
        addSubscription(ApiService.getInstanceOrder().submitOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TaoTaoResult>() {
                    @Override
                    public void call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            myView.submit("提交成功");
                        } else {
                            myView.submit("提交失败");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.failure(throwable.getMessage());
                    }
                }));
    }

    public void deleteOrder(long orderId) {
        addSubscription(ApiService.getInstanceOrder().deleteOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TaoTaoResult>() {
                    @Override
                    public void call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            myView.submit("删除成功");
                        } else {
                            myView.submit("删除失败，请重试");
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
