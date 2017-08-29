package com.example.lenovo.taoshop.api;


import com.example.lenovo.taoshop.bean.common.IP;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class ApiService {
    private static ApiService apiService;
    private static ImageApi imageApi;
    private static UserApi userApi;
    private static OrderApi orderApi;
    private static SearchApi searchApi;
    private static LogisticsApi logisticsApi;
    private static GoodApi goodApi;
    private static CommentApi commentApi;

    private ApiService() {
        //初始化retrofit
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
        //启动页
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(IP.REST_URL)
                .build();
        imageApi = retrofit.create(ImageApi.class);

        //用户相关的操作
        Retrofit user = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(IP.SSO_URL)
                .build();
        userApi = user.create(UserApi.class);

        //订单
        Retrofit order = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(IP.ORDER_URL)
                .build();
        orderApi = order.create(OrderApi.class);

        //物流
        Retrofit logistics = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(IP.LOGISTICS_URL)
                .build();
        logisticsApi = logistics.create(LogisticsApi.class);

        //搜搜
        Retrofit search = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(IP.SEARCH_URL)
                .build();
        searchApi = search.create(SearchApi.class);

        //商品详情
        Retrofit good = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(IP.REST_URL)
                .build();
        goodApi = good.create(GoodApi.class);

        //评论
        Retrofit comment = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(IP.COMMENT_URL)
                .build();
        commentApi = comment.create(CommentApi.class);

    }

    //单例模式
    //获取图片
    public static ImageApi getInstanceImage() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                apiService = new ApiService();
            }
        }
        return imageApi;
    }

    //用户
    public static UserApi getInstanceUser() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                apiService = new ApiService();
            }
        }
        return userApi;
    }

    //订单
    public static OrderApi getInstanceOrder() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                apiService = new ApiService();
            }
        }
        return orderApi;
    }

    //物流
    public static LogisticsApi getInstanceLogistics() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                apiService = new ApiService();
            }
        }
        return logisticsApi;
    }

    //搜索
    public static SearchApi getInstanceSearch() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                apiService = new ApiService();
            }
        }
        return searchApi;
    }

    //商品
    public static GoodApi getInstanceGood() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                apiService = new ApiService();
            }
        }
        return goodApi;
    }

    //评论
    public static CommentApi getInstanceComment() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                apiService = new ApiService();
            }
        }
        return commentApi;
    }
}
