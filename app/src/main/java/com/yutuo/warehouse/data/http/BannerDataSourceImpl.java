package com.yutuo.warehouse.data.http;


import com.yutuo.warehouse.data.http.retrofit.RetrofitClient;
import com.yutuo.warehouse.data.http.service.BannerApi;
import com.yutuo.warehouse.entity.Banner;
import com.yutuo.warehouse.entity.WanEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2021/3/25.
 */
public class BannerDataSourceImpl implements BannerApi {

    private final BannerApi bannerApi;

    public BannerDataSourceImpl() {
        this.bannerApi = RetrofitClient.getInstance().create(BannerApi.class);
    }

    @Override
    public Observable<WanEntity<List<Banner>>> json() {
        return bannerApi.json();
    }

}
