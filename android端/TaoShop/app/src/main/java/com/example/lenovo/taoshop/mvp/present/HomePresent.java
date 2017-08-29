package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.AdData;
import com.example.lenovo.taoshop.bean.common.IndexMulEntity;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.mvp.view.IHomeView;
import com.example.lenovo.taoshop.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class HomePresent extends BasePresent<IHomeView> {
    public HomePresent() {
    }

    public void getDatas() {
        addSubscription(ApiService.getInstanceGood().getIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TaoTaoResult, AdData>() {
                    @Override
                    public AdData call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            String json = (String) taoTaoResult.getData();
                            AdData adData = JsonUtils.jsonToPojo(json, AdData.class);
                            if (adData != null && adData.getAds() != null) {
                                myView.loadAdData(adData.getAds());
                            }
                            return adData;
                        }
                        return null;
                    }
                })
                .subscribe(new Action1<AdData>() {
                    @Override
                    public void call(AdData adData) {
                        //封装数据
                        List<IndexMulEntity> list = new ArrayList<IndexMulEntity>();
                        //新品
                        IndexMulEntity indexMulEntity = new IndexMulEntity(IndexMulEntity.RECOMMEND_ITEM);
                        indexMulEntity.setItemResults(adData.getNews());
                        indexMulEntity.setTitle("新品推荐");
                        list.add(indexMulEntity);
                        //专区
                        list.add(new IndexMulEntity(IndexMulEntity.SPECIAL_ITEM, adData.getPhones(), "手机专区"));
                        list.add(new IndexMulEntity(IndexMulEntity.SPECIAL_ITEM, adData.getCps(), "电脑专区"));
                        list.add(new IndexMulEntity(IndexMulEntity.SPECIAL_ITEM, adData.getHometool(), "家居专区"));
                        //其他

                        for (int i = 0; i < adData.getOthers().size(); i++) {
                            List<TbItemResult> tbItemResults = new ArrayList<TbItemResult>();
                            IndexMulEntity other = new IndexMulEntity(IndexMulEntity.GOOD_OTHER_ITEM);
                            if (i == 0) other.setTitle("更多商品");
                            else {
                                other.setTitle(null);
                            }
                            tbItemResults.add(adData.getOthers().get(i));
                            other.setItemResults(tbItemResults);
                            list.add(other);
                        }
                        myView.load(list);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail(throwable.getMessage());
                    }
                }));
    }

    //首页加载更多
    public void getIndexMore(int page) {
        addSubscription(ApiService.getInstanceGood().getIndexMore(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<TaoTaoResult, List<TbItemResult>>() {
                    @Override
                    public List<TbItemResult> call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            List<TbItemResult> list = JsonUtils.jsonToList((String) taoTaoResult.getData(), TbItemResult.class);
                            return list;
                        }
                        return null;
                    }
                }).subscribe(new Action1<List<TbItemResult>>() {
                    @Override
                    public void call(List<TbItemResult> tbItemResults) {
                        if (tbItemResults.size() > 0) {
                            List<IndexMulEntity> list = new ArrayList<IndexMulEntity>();
                            for (int i = 0; i < tbItemResults.size(); i++) {
                                List<TbItemResult> ts = new ArrayList<TbItemResult>();
                                ts.add(tbItemResults.get(i));
                                IndexMulEntity indexMulEntity = new IndexMulEntity(IndexMulEntity.GOOD_OTHER_ITEM);
                                indexMulEntity.setItemResults(ts);
                                list.add(indexMulEntity);
                            }
                            myView.loadMore(list);
                        } else {
                            myView.nodata();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail(throwable.getMessage());
                    }
                }));
    }
}
