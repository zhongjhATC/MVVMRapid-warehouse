package com.yutuo.warehouse.ui.qrcodescanning;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yutuo.warehouse.BR;
import com.yutuo.warehouse.R;
import com.yutuo.warehouse.databinding.ActivityQrCodeScanningBinding;
import com.zhongjh.mvvmrapid.base.ui.BaseActivity;
import com.zhongjh.mvvmrapid.utils.PermissionsUtil;
import com.zhongjh.mvvmrapid.utils.ScreenUtil;
import com.zhongjh.mvvmrapid.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhongjh on 2021/4/15.
 */
public class QRCodeScanningActivity extends BaseActivity<ActivityQrCodeScanningBinding, QRCodeScanningViewModel>
        implements QRCodeView.Delegate {

    private static final String TAG = QRCodeScanningActivity.class.getSimpleName();
    public static final String SCANNING_RESULT_KEY = "scanning_result_key";

    public static Disposable startActivity(RxAppCompatActivity activity) {
        // 判断权限
        final RxPermissions rxPermissions = new RxPermissions(activity);

        return rxPermissions
                .requestEachCombined(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> PermissionsUtil.managePermissions(permission, activity, new PermissionsUtil.OnPermissions() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(activity, QRCodeScanningActivity.class);
                        activity.startActivity(intent);
                    }
                }));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenUtil.setFullScreen(QRCodeScanningActivity.this, false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_qr_code_scanning;
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
        binding.zxingview.setDelegate(this);

        // 初始化toolbar
        initToolbar(binding.toolbar, "扫一扫", true);

        // 这只是一个测试，测试5秒扫描后的数据，使用真机时记得删除该段代码
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> onScanQRCodeSuccess("这只是一个测试，测试5秒扫描后的数据，使用真机时记得删除该段代码"));
            }
        };
        timer.schedule(task, 3000);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        // 打开后置摄像头开始预览，但是并未开始识别
        binding.zxingview.startCamera();

        // 显示扫描框，并开始识别
        binding.zxingview.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        // 关闭摄像头预览，并且隐藏扫描框
        binding.zxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 销毁二维码扫描控件
        binding.zxingview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        ToastUtils.showShort("扫描结果为：" + result);
        Intent intent = new Intent();
        intent.putExtra(SCANNING_RESULT_KEY, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = binding.zxingview.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                binding.zxingview.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                binding.zxingview.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, 10));
        } else {
            vibrator.vibrate(200);
        }
    }

}
