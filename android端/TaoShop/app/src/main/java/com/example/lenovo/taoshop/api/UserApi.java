package com.example.lenovo.taoshop.api;

import com.example.lenovo.taoshop.bean.common.TaoTaoResult;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2017  五月  02  0002.
 */

public interface UserApi {

    /*
  * 登录
  * */
    @FormUrlEncoded
    @POST("/sso/login/")
    Observable<TaoTaoResult> doLogin(@Field("username") String username, @Field("password") String password, @Field("user") Integer index);

    /*
    * 注册
    *
    * baseUrl=http://localhost:8082/sso/
    * */
    //post方式提交

    @FormUrlEncoded
    @POST("/sso/register")
    Observable<TaoTaoResult> doRegister(@FieldMap() Map<String, Object> map);


    /*
    * 注册检测字段的占用情况
    * */
    @GET("/sso/check/{type}")
    Observable<TaoTaoResult> check(@Path("type") int type, @Query("datas") String datas);

    /*
    * 修改邮编地址
    * */
    @FormUrlEncoded
    @POST("/sso/edit/addr")
    Observable<TaoTaoResult> editAddr(@FieldMap() Map<String, Object> map);

    /*
    * 删除邮编地址
    * */
    @GET("/sso/delete/addr")
    Observable<TaoTaoResult> deleteAddr(@Query("id") Long id);

    /*
    * 新增邮编地址
    * */
    @FormUrlEncoded
    @POST("/sso/add/addr")
    Observable<TaoTaoResult> addAddr(@FieldMap() Map<String, Object> map);

}
