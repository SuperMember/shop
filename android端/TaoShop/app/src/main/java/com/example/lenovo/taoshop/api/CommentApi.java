package com.example.lenovo.taoshop.api;

import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.bean.common.TbComments;
import com.example.lenovo.taoshop.bean.common.TbCommentsReply;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2017  五月  09  0009.
 */

public interface CommentApi {
    @GET("/comments/show/goods/comments")
    Observable<TaoTaoResult> getComments(@Query("itemId") long itemId, @Query("page") long page);

    @POST("/comments/create/{orderId}")
    Observable<TaoTaoResult> submit(@Body TbComments tbComments, @Path("orderId") long orderId);

    @POST("/comments/create/reply/{index}")
    Observable<TaoTaoResult> submitReply(@Body TbCommentsReply tbCommentsReply, @Path("index") Integer type);
}
