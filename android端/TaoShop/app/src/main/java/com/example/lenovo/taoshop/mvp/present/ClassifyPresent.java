package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.bean.common.TbItem;
import com.example.lenovo.taoshop.bean.common.TbItemCat;
import com.example.lenovo.taoshop.bean.common.TbItemCatResult;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.mvp.view.IClassifyView;
import com.example.lenovo.taoshop.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  10  0010.
 */

public class ClassifyPresent extends BasePresent<IClassifyView> {
    public ClassifyPresent() {
    }


    //根据类别筛选出商品
    public void getGoods(long itemId, final long page, Long cid) {
        addSubscription(ApiService.getInstanceGood().getGoods(itemId, page, cid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<TaoTaoResult, List<TbItemResult>>() {
                    @Override
                    public List<TbItemResult> call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            String json = (String) taoTaoResult.getData();
                            return JsonUtils.jsonToList(json, TbItemResult.class);
                        }
                        return null;
                    }
                })
                .subscribe(new Action1<List<TbItemResult>>() {
                    @Override
                    public void call(List<TbItemResult> tbItemResults) {
                        if (page > 1) {
                            if (tbItemResults != null && tbItemResults.size() != 0) {
                                myView.getMore(tbItemResults);
                            } else {
                                myView.noMore();
                            }
                        } else {
                            if (tbItemResults != null && tbItemResults.size() != 0) {
                                myView.get(tbItemResults);
                            } else {
                                myView.empty();
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail(throwable.getMessage());
                    }
                }));
    }

    //获取分类信息
    public void getClassify(long parentId) {
        addSubscription(ApiService.getInstanceGood().getClassify(parentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<TaoTaoResult, List<TbItemCatResult>>() {
                    @Override
                    public List<TbItemCatResult> call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            String json = (String) taoTaoResult.getData();
                            return JsonUtils.jsonToList(json, TbItemCatResult.class);
                        }
                        return null;
                    }
                })
                .subscribe(new Action1<List<TbItemCatResult>>() {
                    @Override
                    public void call(List<TbItemCatResult> strings) {
                        myView.getClassify(strings);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail(throwable.getMessage());
                    }
                }));
    }
}
