package com.yutuo.warehouse.ui.splash;


import static com.yutuo.warehouse.data.local.MMKVLocal.PASS_WORD;
import static com.yutuo.warehouse.data.local.MMKVLocal.USER_NAME;

import android.app.Application;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;
import com.yutuo.warehouse.data.http.UserDataSourceImpl;
import com.yutuo.warehouse.data.http.retrofit.Rxjava2Manage;
import com.yutuo.warehouse.entity.LoginBean;
import com.yutuo.warehouse.entity.WanEntity;
import com.zhongjh.mvvmrapid.base.viewmodel.BaseViewModel;
import com.zhongjh.mvvmrapid.bus.event.SingleLiveEvent;

public class SplashViewModel extends BaseViewModel {

    UserDataSourceImpl mUserDataSourceImpl = new UserDataSourceImpl();

    /**
     * 界面发生改变的观察者
     */
    public UIChangeObservable mUC = new UIChangeObservable();

    public static class UIChangeObservable {

        // 跳转首页
        public SingleLiveEvent<Void> toMain = new SingleLiveEvent<>();

        // 跳转登录
        public SingleLiveEvent<Void> toLogin = new SingleLiveEvent<>();
    }

    public SplashViewModel(@NonNull Application application) {
        super(application);

        // 判断是否有存储帐号
        MMKV kv = MMKV.defaultMMKV();

        // 进行登录
        if (kv != null) {
            Rxjava2Manage.simplifyConnection(mUserDataSourceImpl.login(kv.decodeString(USER_NAME), kv.decodeString(PASS_WORD)), this,
                    new Rxjava2Manage.CustomObserver<LoginBean>() {

                        @Override
                        public void onNext(WanEntity<LoginBean> t) {
                            // 跳到首页
                            mUC.toMain.call();
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            super.onError(e);
                            // 跳到登录
                            mUC.toLogin.call();
                        }

                        @Override
                        public void onBusinessError(WanEntity<LoginBean> t) {
                            super.onBusinessError(t);
                            // 跳到登录
                            mUC.toLogin.call();
                        }
                    });
        } else {
            mUC.toLogin.call();
        }
    }


}
