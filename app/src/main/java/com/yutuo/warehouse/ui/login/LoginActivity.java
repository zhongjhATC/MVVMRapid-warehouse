package com.yutuo.warehouse.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.yutuo.warehouse.AppViewModelFactory;
import com.yutuo.warehouse.BR;
import com.yutuo.warehouse.R;
import com.yutuo.warehouse.databinding.ActivityLoginBinding;
import com.zhongjh.mvvmrapid.base.ui.BaseActivity;
import com.zhongjh.mvvmrapid.utils.ScreenUtil;

/**
 * Created by zhongjh on 2021/3/25.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenUtil.setFullScreen(LoginActivity.this, false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        // 使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        return new ViewModelProvider(this, AppViewModelFactory.getInstance(getApplication())).get(LoginViewModel.class);
    }

    @Override
    public boolean isStartAutoHideSoftKeyboard() {
        return true;
    }

    @Override
    public void initParam() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {
        viewModel.mUiChange.userNameLoginError.observe(this, s -> binding.etUserName.setError(s));
        viewModel.mUiChange.passwordLoginError.observe(this, s -> binding.etPassword.setError(s));
        viewModel.mUiChange.showLoginProgress.observe(this, aVoid -> showLoginProgress());
        viewModel.mUiChange.showLoginSucceed.observe(this, aVoid -> showLoginSucceed());
        viewModel.mUiChange.showLoginError.observe(this, aVoid -> showLoginError());
    }

    /**
     * 登录中显示进度
     */
    private void showLoginProgress() {
        binding.btnLogin.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.VISIBLE);
    }

    /**
     * 登录成功
     */
    private void showLoginSucceed() {
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.pbLoading.setVisibility(View.GONE);
    }

    /**
     * 登录失败
     */
    private void showLoginError() {
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.pbLoading.setVisibility(View.GONE);
    }


}
