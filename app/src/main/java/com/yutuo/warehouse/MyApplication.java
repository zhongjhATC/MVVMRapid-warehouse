package com.yutuo.warehouse;


import static com.yutuo.warehouse.BuildConfig.DEBUG;

import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.squareup.leakcanary.LeakCanary;
import com.yutuo.warehouse.constants.FilePaths;
import com.yutuo.warehouse.data.db.MySQLiteOpenHelper;
import com.yutuo.warehouse.data.db.dao.DaoMaster;
import com.yutuo.warehouse.data.db.dao.DaoSession;
import com.yutuo.warehouse.ui.splash.SplashActivity;
import com.zhongjh.mvvmrapid.BuildConfig;
import com.zhongjh.mvvmrapid.base.BaseApplication;
import com.zhongjh.mvvmrapid.ui.error.ErrorActivity;
import com.zhongjh.mvvmrapid.utils.DynamicTimeFormat;
import com.zhongjh.mvvmrapid.utils.KLog;
import com.zhongjh.mvvmrapid.utils.ToastUtils;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import io.reactivex.annotations.NonNull;

/**
 * @author zhongjh
 * @date 2021/3/25
 * <p>
 * 代码规范：https://github.com/getActivity/AndroidCodeStandard
 */
public class MyApplication extends BaseApplication {

    DaoSession mDaoSession;
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    static {
        // 启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        // 设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true);
            layout.setEnableOverScrollDrag(false);
            layout.setEnableOverScrollBounce(true);
            layout.setEnableLoadMoreWhenContentNotFull(true);
            layout.setEnableScrollContentWhenRefreshed(true);
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            layout.setFooterMaxDragRate(4.0F);
            layout.setFooterHeight(45);
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            layout.setEnableHeaderTranslationContent(true);
            return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 是否开启打印日志
        KLog.init(BuildConfig.DEBUG);
        // 初始化全局异常崩溃
        initCrash();
        // 初始化Log
        initLog();
        // 初始化Toast的全局样式
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);

        // 内存泄漏检测
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }

        // 初始化本地数据库
        initGreenDao();
    }

    /**
     * 异常奔溃后自动打开新的Activity,还可以选择重新启动
     */
    private void initCrash() {
        CaocConfig.Builder.create()
                // 背景模式,开启沉浸式
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
                // 是否启动全局异常捕获
                .enabled(true)
                // 是否显示错误详细信息
                .showErrorDetails(true)
                // 是否显示重启按钮
                .showRestartButton(true)
                // 是否跟踪Activity
                .trackActivities(true)
                // 崩溃的间隔时间(毫秒)
                .minTimeBetweenCrashesMs(2000)
                // 错误图标
                .errorDrawable(R.mipmap.ic_launcher)
                // 重新启动后的activity
                .restartActivity(SplashActivity.class)
                // 崩溃后的错误监听
//                .eventListener(new YourCustomEventListener())
                // 崩溃后的错误activity
                .errorActivity(ErrorActivity.class)
                .apply();
    }

    /**
     * 初始化log，搭配奔溃把奔溃信息存储到Log
     */
    private void initLog() {
        LogUtils.getConfig().setLogSwitch(true).setLog2FileSwitch(true)
                .setDir(FilePaths.log(this)).setSaveDays(7);
    }

    private void initGreenDao() {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this,"warehouse-db",null);
        if (DEBUG) {
            mDaoSession = new DaoMaster(mySQLiteOpenHelper.getWritableDb()).newSession();
        } else {
            // 加密
            mDaoSession = new DaoMaster(mySQLiteOpenHelper.getEncryptedWritableDb("databasePasswordKey")).newSession();
        }
    }

}
