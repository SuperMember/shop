package com.example.lenovo.taoshop.api;

import com.example.lenovo.taoshop.bean.common.AreaEntity;
import com.example.lenovo.taoshop.bean.common.LogisticsJsonDetail;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2017  五月  05  0005.
 */

public interface LogisticsApi {
    /*
    * 查看物流的详情
    * */
    @GET("/logistics/show/logistics/detail")
    Observable<LogisticsJsonDetail> getDetail(@Query("logisticsId") String logisticsId);

    /*
    * 城市联动
    * */
    @GET("/logistics/location")
    Observable<TaoTaoResult> getArea(@Query("type") String type, @Query("id")Integer id);
}
