package com.yutuo.warehouse.data.http.retrofit;


import android.annotation.SuppressLint;
import android.util.Log;

import com.yutuo.warehouse.entity.WanEntity;
import com.zhongjh.mvvmrapid.http.ResponseThrowable;
import com.zhongjh.mvvmrapid.http.RxUtils;
import com.zhongjh.mvvmrapid.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DefaultObserver;

/**
 * Created by zhongjh on 2021/4/12.
 * 封装rxjava2,简化代码
 */
@SuppressWarnings("ALL")
public class Rxjava2Manage {

    @SuppressLint("CheckResult")
    public static <T> void simplifyConnection(Observable<WanEntity<T>> value, Consumer<? super Disposable> onSubscribe,
                                              CustomObserverInterface<T> customObserver) {
        Observable<WanEntity<T>> observable = simplifyStart(value, onSubscribe);

        simplifyEnd(observable, customObserver);
    }

    @SuppressLint("CheckResult")
    public static <T> void simplifyConnection(Observable<WanEntity<T>> value, Consumer<? super Disposable> onSubscribe,
                                              CustomSubscribe customSubscribe, CustomObserverInterface<T> customObserver) {
        Observable<WanEntity<T>> observable = simplifyStart(value, onSubscribe);

        if (customSubscribe != null) {
            observable = observable.doOnSubscribe(customSubscribe::doOnSubscribe);
        }

        simplifyEnd(observable, customObserver);
    }

    /**
     * 简化前面
     *
     * @return Observable<WanEntity < T>>
     */
    private static <T> Observable<WanEntity<T>> simplifyStart(Observable<WanEntity<T>> value, Consumer<? super Disposable> onSubscribe) {
        return  // 线程调度
                value.compose(RxUtils.io2main())
                        // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                        .compose(RxUtils.exceptionTransformer())
                        // 请求与ViewModel周期同步
                        .doOnSubscribe(onSubscribe);
    }

    /**
     * 简化后面部分
     */
    private static <T> void simplifyEnd(Observable<WanEntity<T>> observable, CustomObserverInterface<T> customObserver) {
        observable.subscribe(new DefaultObserver<WanEntity<T>>() {
            @Override
            public void onNext(@io.reactivex.annotations.NonNull WanEntity<T> t) {
                if (t.getCode() == -1) {
                    customObserver.onBusinessError(t);
                    return;
                }
                customObserver.onNext(t);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.d("TAG", e.getMessage());
                customObserver.onError(e);
            }

            @Override
            public void onComplete() {
                customObserver.onComplete();
            }
        });
    }

    /**
     * 选择性实现以下方法，如果特殊情况可以覆写onBusinessError方法不Toast，根据接口状态进行相应的判断
     *
     * @param <T>
     */
    public abstract static class CustomObserver<T> implements CustomObserverInterface<T> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
            if (e instanceof ResponseThrowable) {
                ToastUtils.showShort(((ResponseThrowable) e).message);
            }
        }

        @Override
        public void onBusinessError(WanEntity<T> t) {
            ToastUtils.showShort(t.getCode() + ": " + t.getMsg());
        }
    }

    /**
     * 必须实现的接口
     */
    public interface CustomObserverInterface<T> {

        /**
         * 正常运行成功的下一步
         */
        void onNext(WanEntity<T> t);

        /**
         * 正常运行一轮后最终会执行该方法
         */
        void onComplete();

        /**
         * 指不可抗拒的错误，例如没有网络
         */
        void onError(@io.reactivex.annotations.NonNull Throwable e);

        /**
         * 指业务错误，接口返回的错误，具体可以根据服务端而改动
         */
        void onBusinessError(WanEntity<T> t);

    }

    public interface CustomSubscribe {
        void doOnSubscribe(Disposable disposable);
    }

}
