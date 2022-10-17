package com.yutuo.warehouse.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yutuo.warehouse.data.http.BannerDataSourceImpl;
import com.yutuo.warehouse.data.http.retrofit.Rxjava2Manage;
import com.yutuo.warehouse.entity.Banner;
import com.yutuo.warehouse.entity.WanEntity;
import com.zhongjh.mvvmrapid.base.viewmodel.BaseViewModel;
import com.zhongjh.mvvmrapid.bus.event.SingleLiveEvent;

import java.util.List;

/**
 * 产品
 *
 * @author zhongjh
 * @date 2021/4/15
 */
public class ProductViewModel extends BaseViewModel {

    BannerDataSourceImpl mBannerDataSourceImpl = new BannerDataSourceImpl();


    /*** 界面发生改变的观察者 */
    public UiChangeObservable uiChangeObservable = new UiChangeObservable();

    public static class UiChangeObservable {
        // 显示product
        public SingleLiveEvent<List<Banner>> showBanners = new SingleLiveEvent<>();
    }

    public ProductViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    public void init() {
        initProductData();
    }

    /**
     * 初始化 productDatas
     */
    public void initProductData() {
        Rxjava2Manage.simplifyConnection(mBannerDataSourceImpl.json(), this,
                new Rxjava2Manage.CustomObserver<List<Banner>>() {
                    @Override
                    public void onNext(WanEntity<List<Banner>> banners) {
                        // 显示banners
                        uiChangeObservable.showBanners.setValue(banners.getData());
                    }
                });
    }

}
