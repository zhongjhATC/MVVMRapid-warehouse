package com.yutuo.warehouse.data.http.service;


import com.yutuo.warehouse.entity.Hotkey;
import com.yutuo.warehouse.entity.WanEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhongjh on 2021/4/9.
 */
public interface HotkeyApi {

    @GET("hotkey/json")
    Observable<WanEntity<List<Hotkey>>> json();

}
