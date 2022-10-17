package com.yutuo.warehouse.data.http;


import com.yutuo.warehouse.data.http.retrofit.RetrofitClient;
import com.yutuo.warehouse.data.http.service.ArticleApi;
import com.yutuo.warehouse.entity.ArticleTop;
import com.yutuo.warehouse.entity.WanEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2021/4/16.
 */
public class ArticleDataSourceImpl implements ArticleApi {

    private final ArticleApi articleApi;

    public ArticleDataSourceImpl() {
        this.articleApi = RetrofitClient.getInstance().create(ArticleApi.class);
    }

    @Override
    public Observable<WanEntity<List<ArticleTop>>> topJson() {
        return articleApi.topJson();
    }

}
