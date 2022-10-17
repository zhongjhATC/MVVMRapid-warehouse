package com.yutuo.warehouse.data.http;


import com.yutuo.warehouse.data.http.retrofit.RetrofitClient;
import com.yutuo.warehouse.data.http.service.HotkeyApi;
import com.yutuo.warehouse.entity.Hotkey;
import com.yutuo.warehouse.entity.WanEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2021/4/9.
 */
public class HotkeyDataSourceImpl implements HotkeyApi {

    private final HotkeyApi hotkeyApi;

    public HotkeyDataSourceImpl() {
        this.hotkeyApi = RetrofitClient.getInstance().create(HotkeyApi.class);
    }

    @Override
    public Observable<WanEntity<List<Hotkey>>> json() {
        return hotkeyApi.json();
    }

}
