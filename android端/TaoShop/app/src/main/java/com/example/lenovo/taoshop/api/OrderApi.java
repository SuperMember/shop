package com.example.lenovo.taoshop.api;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.lenovo.taoshop.bean.common.Order;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;

import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2017  五月  03  0003.
 */

public interface OrderApi {
    /*
    * 获取订单
    * */
    @GET("/order/show/user/{userId}")
    Observable<TaoTaoResult> getOrderAll(@Path("userId") long userId, @Query("page") long page, @Query(value = "type") Integer type);

    /*
    * 提交订单
    * */
    @POST("/order/create/")
    Observable<TaoTaoResult> submitOrder(@Body Order order);

    /*
    *删除订单
    * */
    @GET("/order/delete/order/{orderId}")
    Observable<TaoTaoResult> deleteOrder(@Path("orderId") long orderId);
}
