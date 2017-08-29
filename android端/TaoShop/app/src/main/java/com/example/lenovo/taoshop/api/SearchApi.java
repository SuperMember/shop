package com.example.lenovo.taoshop.api;

import com.example.lenovo.taoshop.bean.common.Search;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2017  五月  07  0007.
 */

public interface SearchApi {
    @GET("/search/")
    Observable<Search> getSearch(@Query("q") String query, @Query("page") long page,@Query("rows")long rows);
}
