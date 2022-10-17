package com.yutuo.warehouse.ui.manage;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yutuo.warehouse.data.db.business.BarCodesBusiness;
import com.yutuo.warehouse.entity.db.BarCode;
import com.zhongjh.mvvmrapid.base.viewmodel.BaseViewModel;
import com.zhongjh.mvvmrapid.bus.event.SingleLiveEvent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 管理界面 - 扫描后有相应处理返回数据添加到本地
 *
 * @author zhongjh
 * @date 2021/4/15
 */
public class ManageModel extends BaseViewModel {

    BarCodesBusiness barCodesBusiness = new BarCodesBusiness();
    List<BarCode> barCodes;

    /*** 界面发生改变的观察者 */
    public UiChangeObservable uiChangeObservable = new UiChangeObservable();

    public static class UiChangeObservable {
        // 显示barCodes
        public SingleLiveEvent<List<BarCode>> barCodes = new SingleLiveEvent<>();
        // 添加barCode
        public SingleLiveEvent<Long> addBarCode = new SingleLiveEvent<>();
    }

    public ManageModel(@NonNull Application application) {
        super(application);
        init();
    }

    public void init() {
        initBarCodeData();
    }

    /**
     * 初始化 BarCodeData
     */
    public void initBarCodeData() {
        Observable<List<BarCode>> observable = Observable.create(emitter -> {
            barCodes = barCodesBusiness.getBarCodes();
            emitter.onNext(barCodes);
            emitter.onComplete();
        });
        Observer<List<BarCode>> observer = new Observer<List<BarCode>>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onNext(List<BarCode> value) {
                uiChangeObservable.barCodes.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribe(observer);
    }

    /**
     * 添加二维码数据
     *
     * @param barCodeStr 扫描文本内容
     */
    public void addBarCodeData(String barCodeStr) {
        Observable<Long> observable = Observable.create(emitter -> {
            emitter.onNext(barCodesBusiness.addBarCodes(barCodeStr));
            emitter.onComplete();
        });
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onNext(Long value) {
                uiChangeObservable.addBarCode.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribe(observer);
    }

    /**
     * 删除二维码数据
     */
    public void removeBarCodeData(int position) {
        Observable<Boolean> observable = Observable.create(emitter -> {
            BarCode barCode = barCodes.get(position);
            emitter.onNext(barCodesBusiness.removeBarCode(barCode.getId()));
            emitter.onComplete();
        });
        Observer<Boolean> observer = new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onNext(Boolean value) {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribe(observer);
    }

}
