package com.yutuo.warehouse.data.http.service;


import com.yutuo.warehouse.entity.ArticleTop;
import com.yutuo.warehouse.entity.WanEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhongjh on 2021/4/16.
 */
public interface ArticleApi {

    @GET("article/top/json")
    Observable<WanEntity<List<ArticleTop>>> topJson();

}
