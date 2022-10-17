package com.yutuo.warehouse.data.http.service;


import com.yutuo.warehouse.entity.Banner;
import com.yutuo.warehouse.entity.WanEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhongjh on 2021/4/9.
 */
public interface BannerApi {

    @GET("banner/json")
    Observable<WanEntity<List<Banner>>> json();

}
