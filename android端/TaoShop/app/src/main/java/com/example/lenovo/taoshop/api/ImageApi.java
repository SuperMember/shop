package com.example.lenovo.taoshop.api;

import retrofit2.http.GET;
import rx.Observable;


public interface ImageApi {
    /*
    * 获取启动页图片地址
    * */
    @GET("/")
    Observable<String> getImage();


}
