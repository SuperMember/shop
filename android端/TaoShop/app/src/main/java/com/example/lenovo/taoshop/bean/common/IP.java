package com.example.lenovo.taoshop.bean.common;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class IP {
    public static final String BASE = "192.168.0.108";
    //获取数据的服务地址
    public static final String IMG_IP = "";
    //登录地址
    public static final String SSO_URL = "http://" + BASE + ":8082/";
    //搜索地址
    public static final String SEARCH_URL = "http://" + BASE + ":8084/";
    //订单地址
    public static final String ORDER_URL = "http://" + BASE + ":8081/";
    //物流地址
    public static final String LOGISTICS_URL = "http://" + BASE + ":8083/";
    //服务地址
    public static final String REST_URL = "http://" + BASE + ":8086/";
    //评论地址
    public static final String COMMENT_URL = "http://" + BASE + ":8085/";
}
