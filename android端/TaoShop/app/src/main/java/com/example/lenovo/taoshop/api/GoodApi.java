package com.example.lenovo.taoshop.api;

import com.example.lenovo.taoshop.bean.common.ItemDetail;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2017  五月  08  0008.
 */

public interface GoodApi {
    //获取商品详情
    @GET("/rest/item/detail")
    Observable<ItemDetail> getDetail(@Query("itemId") long itemId);

    //获取商品分类
    @GET("/rest/item/classify")
    Observable<TaoTaoResult> getClassify(@Query("parentId") long parentId);

    //根据商品类别选出商品
    @GET("/rest/item/classify/goods")
    Observable<TaoTaoResult> getGoods(@Query("parentId") long parentId, @Query("page") long page, @Query("cid") Long cid);

    //首页
    @GET("/rest/item/index")
    Observable<TaoTaoResult> getIndex();

    //首页加载更多
    @GET("/rest/item/index/more")
    Observable<TaoTaoResult> getIndexMore(@Query("page") Integer page);

    //用户消息
    @GET("/rest/message")
    Observable<TaoTaoResult> getMessage(@Query("userId") long userId, @Query("page") int page, @Query("type") int type);

    //修改用户信息
    @Multipart
    @POST("/rest/edit/info")
    Observable<Map> edit(@Part("description") RequestBody description, @Part MultipartBody.Part file);
}
