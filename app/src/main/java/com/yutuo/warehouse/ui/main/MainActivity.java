package com.yutuo.warehouse.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.yutuo.warehouse.BR;
import com.yutuo.warehouse.R;
import com.yutuo.warehouse.databinding.ActivityMainBinding;
import com.yutuo.warehouse.ui.login.LoginActivity;
import com.yutuo.warehouse.ui.product.ProductActivity;
import com.yutuo.warehouse.ui.splash.SplashActivity;
import com.zhongjh.mvvmrapid.base.ui.BaseActivity;
import com.zhongjh.mvvmrapid.utils.ScreenUtil;
import com.zhongjh.mvvmrapid.utils.StatusBarUtil;



/**
 * 首页
 * Created by zhongjh on 2021/3/31.
 * https://blog.mindorks.com/shared-viewmodel-in-android-shared-between-fragments 看如何处理共享数据
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenUtil.setFullScreen(MainActivity.this, false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
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
        initToolbar(binding.toolbar, "功能", true);
        binding.clMain.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ProductActivity.class));
            overridePendingTransition(0, 0);
        });
    }

    @Override
    public void initViewObservable() {
    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            boolean d = moveTaskToBack(false);
            if (!d) {
                moveTaskToBack(true);
            }
            return true;
        } else {
            flag = super.onKeyDown(keyCode, event);
        }
        return flag;
    }

}
