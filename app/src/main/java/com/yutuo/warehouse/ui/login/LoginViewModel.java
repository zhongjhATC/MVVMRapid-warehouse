package com.yutuo.warehouse.ui.login;

import static com.yutuo.warehouse.data.local.MMKVLocal.PASS_WORD;
import static com.yutuo.warehouse.data.local.MMKVLocal.USER_NAME;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.tencent.mmkv.MMKV;
import com.yutuo.warehouse.data.http.UserDataSourceImpl;
import com.yutuo.warehouse.data.http.retrofit.Rxjava2Manage;
import com.yutuo.warehouse.entity.LoginBean;
import com.yutuo.warehouse.entity.WanEntity;
import com.yutuo.warehouse.ui.main.MainActivity;
import com.zhongjh.mvvmrapid.base.viewmodel.BaseViewModel;
import com.zhongjh.mvvmrapid.binding.command.BindingCommand;
import com.zhongjh.mvvmrapid.bus.event.SingleLiveEvent;
import com.zhongjh.mvvmrapid.utils.ToastUtils;

import java.util.Objects;

/**
 * @author zhongjh
 * @date 2021/3/25
 */
public class LoginViewModel extends BaseViewModel {

    private static final int PASSWORD_LENGTH = 6;

    UserDataSourceImpl mUserDataSourceImpl = new UserDataSourceImpl();

    /**
     * 登录界面用户名的绑定
     */
    public ObservableField<String> mUserNameLogin = new ObservableField<>("");
    /**
     * 登录界面密码的绑定
     */
    public ObservableField<String> mPasswordLogin = new ObservableField<>("");

    /**
     * 界面发生改变的观察者
     */
    public UiChangeObservable mUiChange = new UiChangeObservable();

    /**
     * 通知UI事件
     */
    public static class UiChangeObservable {
        // 登录界面的账号错误
        public SingleLiveEvent<String> userNameLoginError = new SingleLiveEvent<>();

        public SingleLiveEvent<String> passwordLoginError = new SingleLiveEvent<>();

        // 登录中
        public SingleLiveEvent<Void> showLoginProgress = new SingleLiveEvent<>();
        // 登录成功
        public SingleLiveEvent<Void> showLoginSucceed = new SingleLiveEvent<>();
        // 登录异常
        public SingleLiveEvent<Void> showLoginError = new SingleLiveEvent<>();

    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 登录按钮的点击事件
     */
    public BindingCommand<Void> onClickTvLogin = new BindingCommand<>(() -> {
        // 判断长度
        if (Objects.requireNonNull(mUserNameLogin.get()).length() < PASSWORD_LENGTH) {
            // 通知ui
            mUiChange.userNameLoginError.setValue("账号长度需要超过6位");
            return;
        }
        if (Objects.requireNonNull(mPasswordLogin.get()).length() < PASSWORD_LENGTH) {
            mUiChange.passwordLoginError.setValue("密码长度需要超过6位");
            return;
        }
        // 进行登录
        Rxjava2Manage.simplifyConnection(mUserDataSourceImpl.login(mUserNameLogin.get(), mPasswordLogin.get()), this,
                disposable -> {
                    // 显示等待动画
                    mUiChange.showLoginProgress.call();
                    // 關閉觸屏
                    closeTouching();
                },
                new Rxjava2Manage.CustomObserver<LoginBean>() {
                    @Override
                    public void onNext(WanEntity<LoginBean> loginBeanWanEntity) {
                        openTouching();
                        mUiChange.showLoginSucceed.call();
                        // 保存账号密码
                        MMKV kv = MMKV.defaultMMKV();
                        if (kv != null) {
                            kv.encode(USER_NAME, mUserNameLogin.get());
                            kv.encode(PASS_WORD, mPasswordLogin.get());
                        }
                        // 进入DemoActivity页面
                        startActivity(MainActivity.class);
                        // 关闭页面
                        finish();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        openTouching();
                        mUiChange.showLoginError.call();
                        super.onError(e);
                    }

                    @Override
                    public void onBusinessError(WanEntity<LoginBean> t) {
                        openTouching();
                        mUiChange.showLoginError.call();
                        super.onBusinessError(t);
                    }
                });
    });

}
