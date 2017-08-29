package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.Search;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.mvp.view.ISearchView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  07  0007.
 */

public class SearchPresent extends BasePresent<ISearchView> {
    public SearchPresent() {
    }

    public void getSearchData(String query, final long page, long rows) {
        addSubscription(ApiService.getInstanceSearch().getSearch(query, page, rows)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<Search, List<ItemList>>() {
                    @Override
                    public List<ItemList> call(Search search) {
                        return search.getItemList();
                    }
                })
                .subscribe(new Action1<List<ItemList>>() {
                    @Override
                    public void call(List<ItemList> list) {
                        if (page > 1) {
                            //加载更多
                            if (list != null && list.size() != 0) {
                                myView.getMore(list);
                            } else {
                                myView.noMoredata();//没有更多数据
                            }
                        } else {
                            if (list != null && list.size() != 0) {
                                myView.getData(list);
                            } else {
                                myView.empty();
                            }
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail();
                    }
                }));
    }
}
