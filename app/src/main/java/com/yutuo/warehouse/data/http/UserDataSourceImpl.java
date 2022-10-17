package com.yutuo.warehouse.data.http;

import com.yutuo.warehouse.data.http.retrofit.RetrofitClient;
import com.yutuo.warehouse.data.http.service.UserApi;
import com.yutuo.warehouse.entity.LoginBean;
import com.yutuo.warehouse.entity.WanEntity;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2021/3/25.
 */
public class UserDataSourceImpl implements UserApi {

    private final UserApi userApi;

    public UserDataSourceImpl() {
        this.userApi = RetrofitClient.getInstance().create(UserApi.class);
    }

    @Override
    public Observable<WanEntity<LoginBean>> login(String username, String password) {
        return userApi.login(username, password);
    }

    @Override
    public Observable<WanEntity<LoginBean>> register(String username, String password, String repassword) {
        return userApi.register(username, password, repassword);
    }

}
