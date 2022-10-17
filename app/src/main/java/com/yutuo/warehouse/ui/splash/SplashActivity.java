package com.yutuo.warehouse.ui.splash;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import com.yutuo.warehouse.BR;
import com.yutuo.warehouse.R;
import com.yutuo.warehouse.databinding.ActivitySplashBinding;
import com.yutuo.warehouse.ui.login.LoginActivity;
import com.yutuo.warehouse.ui.main.MainActivity;
import com.zhongjh.mvvmrapid.base.ui.BaseActivity;
import com.zhongjh.mvvmrapid.utils.ScreenUtil;
import com.zhongjh.mvvmrapid.utils.StatusBarUtil;

/**
 * 全屏的开场图，请注意图片放以下格式
 * xxxhdpi	2160 x 3840	640	4.0	48dp	192px
 * xxhdpi	1080 x 1920	480	3.0	48dp	144px
 * xhdpi	720 x 1280	320	2.0	48dp	96px
 * hdpi	480 x 800	240	1.5	48dp	72pxetong.bottomnavigation.lib.BottomNavigationBar
 * mdpi	320 x 480	160	1.0	48dp	48px
 * @author zhongjh
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, com.yutuo.warehouse.ui.splash.SplashViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenUtil.setFullScreen(SplashActivity.this, false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public boolean isStartAutoHideSoftKeyboard() {
        return false;
    }

    @Override
    public void initParam() {

    }

    @Override
    public void initData() {
        StatusBarUtil.initStatusBarHeight(binding.clMain);
        scaleSplash();
    }

    @Override
    public void initViewObservable() {
        viewModel.mUC.toMain.observe(this, aVoid -> toMain());
        viewModel.mUC.toLogin.observe(this, aVoid -> toLogin());
    }

    /**
     * 跳转登录
     */
    private void toLogin() {
        finish();

        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        overridePendingTransition(0, 0);
    }


    /**
     * 跳转首页
     */
    private void toMain() {
        finish();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(0, 0);
    }

    /**
     * 放大动画
     */
    private void scaleSplash() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(binding.imgSplash, "scaleX", 1f,
                1.2f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(binding.imgSplash, "scaleY", 1f,
                1.2f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300).play(animatorX).with(animatorY);
        set.start();
    }

}
