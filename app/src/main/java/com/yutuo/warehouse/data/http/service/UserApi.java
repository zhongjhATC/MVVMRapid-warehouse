package com.yutuo.warehouse.data.http.service;


import com.yutuo.warehouse.entity.LoginBean;
import com.yutuo.warehouse.entity.WanEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhongjh on 2021/3/25.
 */
public interface UserApi {

    /**
     * @return {"data":null,"errorCode":-1,"errorMsg":"账号密码不匹配！"}
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<WanEntity<LoginBean>> login(@Field("username") String username,
                                           @Field("password") String password);

    /**
     * 注册
     * 方法： POST
     * 参数：
     * username，password,repassword
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<WanEntity<LoginBean>> register(@Field("username") String username,
                                              @Field("password") String password,
                                              @Field("repassword") String repassword);

}
